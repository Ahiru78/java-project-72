package hexlet.code.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlRepository;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import lombok.extern.slf4j.Slf4j;
import java.sql.SQLException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Slf4j
public class UrlCheckController {

    public static void check(Context ctx) throws SQLException, UnirestException {
        Long urlId = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.findById(urlId)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + urlId + " doesn't exist"));
        System.out.println("URL NAME IS " + url.getName());
        try {
            HttpResponse<String> response = Unirest.get(url.getName()).asString();
            String html = response.getBody();
            Document doc = Jsoup.parse(html);
            var title = "";
            var h1 = "";
            var description = "";
            int statusCode = response.getStatus();
            Element titleSel = doc.selectFirst("title");
            if (titleSel != null) {
                title = titleSel.text();
            }
            Element h1Sel = doc.selectFirst("h1");
            if (h1Sel != null) {
                h1 = h1Sel.text();
            }
            Element descriptionSel = doc.selectFirst("meta[name=description]");
            if (descriptionSel != null) {
                description = descriptionSel.attr("content");
            }
            var newCheck = new UrlCheck(
                    statusCode,
                    title,
                    description,
                    h1,
                    urlId);
            UrlCheckRepository.save(newCheck);
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flash-type", "success");
            log.info("Url check saved");
        } catch (UnirestException e) {
            log.info("UnirestException: ", e);
        } catch (SQLException e) {
            log.info("SQLException", e);
        }
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}
