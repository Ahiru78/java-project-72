package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class AppTest {

    private Javalin app;
    private static MockWebServer mockWebServer;

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
        UrlRepository.removeAll();
        UrlCheckRepository.removeAll();
        mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody("Not Found"));
        mockWebServer.start();
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testPostUrl() {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.post(NamedRoutes.urlsPath(), "url=https://reddit.com");
            Optional<Url> url = UrlRepository.findByName("https://reddit.com");
            var urlId = url.get().getId().toString();
            var getResponse = client.get(NamedRoutes.urlPath(urlId));
            assertThat(getResponse.code()).isEqualTo(200);
            assertThat(getResponse.body().string()).contains("https://reddit.com");
        });
    }

    @Test
    public void testPostUrlCheck() {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.post(NamedRoutes.urlsPath(), "url=https://reddit.com");
            Optional<Url> url = UrlRepository.findByName("https://reddit.com");
            Long urlId = url.get().getId();
            Response responseCheck = client.post(NamedRoutes.urlChecksPath(urlId));
            var getResponse = client.get(NamedRoutes.urlPath(urlId));
            assertThat(getResponse.code()).isEqualTo(200);
            var checks = UrlCheckRepository.findById(urlId);
            for (UrlCheck check : checks) {
                assertThat(check.getTitle().contains("Reddit - The heart of the internet"));
                assertThat(check.getH1().contains("Reddit is where millions of people gather"));
                assertThat(check.getDescription().isEmpty());
            }
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/url/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    void testUrlAlreadyExists() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            Response response = client.post(NamedRoutes.urlsPath(), "url=https://reddit.com");
            Response response1 = client.post(NamedRoutes.urlsPath(), "url=https://reddit.com");
            var urlList = UrlRepository.getEntities();
            assertThat(urlList).hasSize(1);
        });
    }

    @Test
    void testUrlInvalid() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            Response response1 = client.post(NamedRoutes.urlsPath(), "url=htddit.com");
            var urlList = UrlRepository.getEntities();
            assertThat(urlList).isEmpty();
        });
    }

    @Test
    void testUrlRepoSave() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            Url url = new Url("https://ya.ru");
            UrlRepository.save(url);
            var urlTittle = UrlRepository.findByName("https://ya.ru").get().getName();
            assertThat(urlTittle).isEqualTo("https://ya.ru");
        });
    }

    @Test
    void testUrlRepoFindById() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            Url url = new Url("https://ya.ru");
            UrlRepository.save(url);
            var urlId = UrlRepository.findByName("https://ya.ru").get().getId();
            var urlTittle = UrlRepository.findById(urlId).get().getName();
            assertThat(urlTittle).isEqualTo("https://ya.ru");
        });
    }
    @Test
    void testUrlRepoGetEntities() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            Url url1 = new Url("https://ya.ru");
            Url url2 = new Url("https://www.reddit.com/");
            UrlRepository.save(url1);
            UrlRepository.save(url2);
            var urlsList = UrlRepository.getEntities();
            assertThat(!urlsList.isEmpty());
        });
    }

    @Test
    void testUrlRepoRemoveAll() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            Url url1 = new Url("https://ya.ru");
            Url url2 = new Url("https://www.reddit.com/");
            UrlRepository.save(url1);
            UrlRepository.save(url2);
            UrlRepository.removeAll();
            List<Url> urlsList = UrlRepository.getEntities();
            assertThat(urlsList.isEmpty());
        });
    }

    @Test
    void testUrlCheckRepoSave() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            Url url = new Url("https://ya.ru");
            UrlCheck urlCheck = new UrlCheck(200,
                    "https://ya.ru",
                    "Найдётся все",
                    "",
                    url.getId());
            UrlRepository.save(url);
            UrlCheckRepository.save(urlCheck);
            var checks = UrlCheckRepository.findById(url.getId());
            for (UrlCheck check : checks) {
                assertThat(check.getTitle()).isEqualTo("https://ya.ru");
                assertThat(check.getDescription()).isEqualTo("Найдётся все");
                assertThat(check.getUrlId()).isEqualTo(url.getId());
            }
        });
    }
}
