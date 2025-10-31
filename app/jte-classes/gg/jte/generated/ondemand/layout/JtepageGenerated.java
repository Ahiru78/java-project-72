package gg.jte.generated.ondemand.layout;
import hexlet.code.util.NamedRoutes;
import hexlet.code.dto.BasePage;
import gg.jte.Content;
@SuppressWarnings("unchecked")
public final class JtepageGenerated {
	public static final String JTE_NAME = "layout/page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,3,3,3,45,45,45,46,46,48,48,48,52,52,54,54,54,58,58,60,60,60,63,63,64,64,65,65,65,77,77,77,3,4,4,4,4};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content, BasePage page) {
		jteOutput.writeContent("\r\n\r\n<!DOCTYPE html>\r\n<html lang=\"en\">\r\n    <head>\r\n        <meta charset=\"UTF-8\">\r\n        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n        <title>Анализатор страниц</title>\r\n        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css\"\r\n              rel=\"stylesheet\"\r\n              integrity=\"sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr\"\r\n              crossorigin=\"anonymous\"\r\n        >\r\n    </head>\r\n\r\n    <body class=\"d-flex flex-column min-vh-100\">\r\n    <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js\"\r\n            integrity=\"sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q\"\r\n            crossorigin=\"anonymous\">\r\n\r\n    </script>\r\n\r\n    <nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\r\n        <div class=\"container-fluid\">\r\n            <a class=\"navbar-brand\" href=\"/\">Анализатор страниц</a>\r\n            <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNav\"\r\n                    aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\r\n                <span class=\"navbar-toggler-icon\"></span>\r\n            </button>\r\n            <div class=\"collapse navbar-collapse\" id=\"navbarNav\">\r\n                <div class=\"navbar-nav\">\r\n                    <a class=\"nav-link\" href=\"/\">Главная</a>\r\n                    <a class=\"nav-link\" href=\"/urls\">Сайты</a>\r\n                </div>\r\n            </div>\r\n        </div>\r\n    </nav>\r\n\r\n    <main class=\"flex-grow-1\">\r\n        ");
		if (page != null && page.getFlash() != null && page.getFlashType() != null) {
			jteOutput.writeContent("\r\n            ");
			if (page.getFlashType().equals("info")) {
				jteOutput.writeContent("\r\n                <div class=\"rounded-0 m-0 alert alert-dismissible fade show alert-info\" role=\"alert\">\r\n                    <p class=\"m-0\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(page.getFlash());
				jteOutput.writeContent("</p>\r\n                    <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n                </div>\r\n\r\n            ");
			} else if (page.getFlashType().equals("alert")) {
				jteOutput.writeContent("\r\n                <div class=\"rounded-0 m-0 alert alert-dismissible fade show alert-danger\" role=\"alert\">\r\n                    <p class=\"m-0\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(page.getFlash());
				jteOutput.writeContent("</p>\r\n                    <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n                </div>\r\n\r\n            ");
			} else if (page.getFlashType().equals("success")) {
				jteOutput.writeContent("\r\n                <div class=\"rounded-0 m-0 alert alert-dismissible fade show alert-success\" role=\"alert\">\r\n                    <p class=\"m-0\">");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(page.getFlash());
				jteOutput.writeContent("</p>\r\n                    <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n                </div>\r\n            ");
			}
			jteOutput.writeContent("\r\n        ");
		}
		jteOutput.writeContent("\r\n        ");
		jteOutput.setContext("main", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\r\n    </main>\r\n\r\n    <footer class=\"footer\">\r\n        <div class=\"container\">\r\n            <div class=\"text-center text-muted\">\r\n                <a></a>\r\n            </div>\r\n        </div>\r\n    </footer>\r\n    </body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		BasePage page = (BasePage)params.getOrDefault("page", null);
		render(jteOutput, jteHtmlInterceptor, content, page);
	}
}
