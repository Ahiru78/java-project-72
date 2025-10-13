package hexlet.code.controller;
import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import hexlet.code.repository.UrlRepository;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.dto.urls.UrlsPage;
import io.javalin.http.NotFoundResponse;
import java.net.URI;
import java.sql.SQLException;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import java.net.URISyntaxException;
import java.net.MalformedURLException;
import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

@Slf4j
public class RootController {
    public static void build(Context ctx) {
        var page = new BasePage();
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("index.jte", model("page", page));
    }

    public static void create(Context ctx) throws URISyntaxException, MalformedURLException, SQLException {
        String name = ctx.formParamAsClass("url", String.class).get();
        URL toUrl;
        try {
            toUrl = new URI(name).toURL();
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flash-type", "alert");
            ctx.redirect(NamedRoutes.mainPagePath());
            return;
        }
        if (name.isEmpty()) {
            ctx.sessionAttribute("flash", "Заполните поле ввода");
            ctx.sessionAttribute("flash-type", "alert");
            ctx.redirect(NamedRoutes.mainPagePath());
            return;
        }
        String protocol = toUrl.getProtocol();
        int port = toUrl.getPort();
        String host = toUrl.getHost();
        URL url = new URIBuilder().setScheme(protocol).setHost(host).setPort(port).build().toURL();
        if (UrlRepository.findByName(String.valueOf(url)).isEmpty()) {
            Url newUrl = new Url(String.valueOf(url));
            UrlRepository.save(newUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flash-type", "success");
            log.info("Url saved");
        } else {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flash-type", "info");
            ctx.redirect(NamedRoutes.mainPagePath());
            return;
        }
        ctx.redirect(NamedRoutes.urlsPath());
    }

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        for (Url url : urls) {
            if (!UrlCheckRepository.findById(url.getId()).isEmpty()) {
                url.setLastCheck(UrlCheckRepository.findLatest(url.getId()));
            }
        }
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        List<UrlCheck> list = UrlCheckRepository.findById(id);
        var page = new UrlPage(url, list);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/show.jte", model("page", page));
    }
}
