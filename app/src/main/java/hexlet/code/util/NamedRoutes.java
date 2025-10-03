package hexlet.code.util;

public class NamedRoutes {

    static final String PATH_MAIN_PAGE = "/";
    static final String PATH_SITES_PAGE = "/urls";

    public static String mainPagePath() {
        return PATH_MAIN_PAGE;
    }

    public static String urlsPath() {
        return PATH_SITES_PAGE;
    }

    public static String urlPath(Long id) {
        return urlPath(String.valueOf(id));
    }

    public static String urlPath(String id) {
        return PATH_SITES_PAGE + "/" + id;
    }
}
