package hexlet.code.controller;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import static io.javalin.rendering.template.TemplateUtil.model;

public class RootController {
    public static void main(String[] args) {
        app.get("/courses", ctx -> {
            var page = new CoursesPage(courses, header);
            ctx.render("courses/index.jte", model("page", page));
        });
    }
}