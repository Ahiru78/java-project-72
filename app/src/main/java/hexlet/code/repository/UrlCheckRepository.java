package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck urlCheck) throws SQLException {
        var sql = "INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at) VALUES "
                + "(?, ?, ?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, urlCheck.getStatusCode());
            stmt.setString(2, urlCheck.getTitle());
            stmt.setString(3, urlCheck.getH1());
            stmt.setString(4, urlCheck.getDescription());
            stmt.setLong(5, urlCheck.getUrlId());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            var generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        } catch (Exception e) {
            log.info("Failed to save entity in the url_checks repository", e);
        }
    }

    public static List<UrlCheck> findById(Long urlID) {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC";
        List<UrlCheck> checksList = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, urlID);
            var result = stmt.executeQuery();
            while (result.next()) {
                var urlCheck = new UrlCheck(
                        result.getInt("status_code"),
                        result.getString("title"),
                        result.getString("H1"),
                        result.getString("Description"),
                        result.getLong("url_id"));
                urlCheck.setId(result.getLong("id"));
                urlCheck.setCreatedAt(result.getTimestamp("created_at").toLocalDateTime());
                checksList.add(urlCheck);
            }
        } catch (SQLException e) {
            log.info("Failed to find entity in the url_checks repository", e);
        }
        return  checksList;
    }

    public static Optional<Map<Long, UrlCheck>> findLatest() throws SQLException {
        var sql = "SELECT DISTINCT ON (url_id) * from url_checks order by url_id DESC, id DESC";
        UrlCheck urlCheck = null;
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var result = new HashMap<Long, UrlCheck>();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var urlId = resultSet.getLong("url_id");
                var statusCode = resultSet.getInt("status_code");
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var description = resultSet.getString("description");
                var check = new UrlCheck(statusCode, title, h1, description, urlId);
                check.setId(id);
                check.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                result.put(urlId, check);
            }
            return Optional.ofNullable(result);
        }
    }

    public static void removeAll() {
        String sql = "DELETE * FROM url_checks";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            log.info("Failed to clear the url_checks repository", e);
        }
    }
}
