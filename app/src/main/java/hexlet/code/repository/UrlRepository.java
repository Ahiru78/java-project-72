package hexlet.code.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hexlet.code.model.Url;

public class UrlRepository {
    private static List<Post> entities = new ArrayList<Post>();

    public static void save(Post post) {
        post.setId((long) entities.size() + 1);
        post.setCreatedAt(LocalDateTime.now());
        entities.add(post);
    }

    public static Optional<Post> find(Long id) {
        var post = entities.stream()
                .filter(entity -> entity.getId() == id)
                .findAny();
        return post;
    }

    public static List<Post> getEntities() {
        return entities;
    }

    public static void delete(Long id) {
        entities.removeIf(post -> post.getId() == id);
    }

    public static void removeAll() {
        entities = new ArrayList<Post>();
    }
}