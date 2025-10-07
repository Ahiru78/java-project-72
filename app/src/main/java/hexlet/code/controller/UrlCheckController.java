package hexlet.code.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

@Slf4j
public class UrlCheckController {

    public static void check(Context ctx) throws URISyntaxException, MalformedURLException, SQLException {
        Long urlId = ctx.pathParamAsClass("id", Long.class).get();
        URL toUrl;
        try {
            HttpResponse<String> response = Unirest.get("https://api.example.com/data")
                    .header("accept", "application/json").asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        ctx.redirect(NamedRoutes.urlsPath());
    }
}
