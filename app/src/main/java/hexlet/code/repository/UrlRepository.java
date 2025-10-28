package hexlet.code.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import hexlet.code.model.Url;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UrlRepository extends BaseRepository {

    public static void save(Url url) {
        var sql = "INSERT INTO urls (name, createdAt) VALUES (?, ?)";
        try (Connection con = dataSource.getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, url.getName());
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            var generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        } catch (Exception e) {
            log.info("Failed to save entity in the url repository", e);
        }
    }

    public static Optional<Url> findById(Long id) {
        String sql = "SELECT * FROM urls WHERE id = ?";
        try (Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var result = stmt.executeQuery();
            if (result.next()) {
                var url = new Url(result.getString("name"));
                url.setId(result.getLong("id"));
                url.setCreatedAt(result.getTimestamp("createdAt").toLocalDateTime());
                return Optional.of(url);
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.info("Failed to find entity by id in the url repository", e);
            return Optional.empty();
        }
    }

    public static Optional<Url> findByName(String name) {
        String sql = "SELECT * FROM urls WHERE name = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, name);
            var result = stmt.executeQuery();
            if (result.next()) {
                var url = new Url(result.getString("name"));
                url.setId(result.getLong("id"));
                url.setCreatedAt(result.getTimestamp("createdAt").toLocalDateTime());
                return Optional.of(url);
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.info("Failed to find entity by name in the url repository", e);
            return Optional.empty();
        }
    }

    public static List<Url> getEntities() {
        List<Url> urlList = new ArrayList<>();
        String sql = "SELECT * FROM urls";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            var result = stmt.executeQuery();
            while (result.next()) {
                var url = new Url(result.getString("name"));
                url.setId(result.getLong("id"));
                url.setCreatedAt(result.getTimestamp("createdAt").toLocalDateTime());
                urlList.add(url);
            }
        } catch (SQLException e) {
            log.info("Failed to get entities in the url repository", e);
        }
        return urlList;
    }

    public static void removeAll() throws SQLException {
        String sql = "DELETE * FROM urls";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            log.info("Failed to clear the url repository", e);
        }
    }
}
