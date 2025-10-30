package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public final class UrlCheck {

    private Long id;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    private Long urlId;
    private LocalDateTime createdAt;

    public UrlCheck(int statusCode, String title, String description, String h1, Long urlId) {
        this.statusCode = statusCode;
        this.title = title;
        this.description = description;
        this.h1 = h1;
        this.urlId = urlId;
    }
}
