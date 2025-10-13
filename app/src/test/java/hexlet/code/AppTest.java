package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

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
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var testUrl = new Url("test");
            UrlRepository.save(testUrl);
            var response = client.get("/urls/" + testUrl.getId());
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("test");
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/url/999999");
            assertThat(response.code()).isEqualTo(404);
        });
    }
}
