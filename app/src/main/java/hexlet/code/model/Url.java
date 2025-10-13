package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@ToString
@Slf4j
public final class Url {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private UrlCheck lastCheck;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Url(String name) {
        this.name = name;
    }

    public String getFormatTime() {
        return createdAt.format(formatter);
    }
}
