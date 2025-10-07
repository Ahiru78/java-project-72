package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UrlCheckRepository extends BaseRepository{

    public static void save(UrlCheck urlCheck) throws SQLException {
        var sql = "INSERT INTO url_checks (statusCode, title, h1, description, urlId, createdAt) VALUES " +
                "(?, ?, ?, ?, ?, ?)";
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
            e.printStackTrace();
        }
    }

    public static List<UrlCheck> findById(Long urlID) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE id = ?";
        List<UrlCheck> checksList = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setLong(1, urlID);
            var result = stmt.executeQuery();
            while (result.next()) {
                var urlCheck = new UrlCheck(
                        result.getInt("statusCode"),
                        result.getString("title"),
                        result.getString("H1"),
                        result.getString("Description"),
                        result.getLong("urlCheck"));
                urlCheck.setId(result.getLong("id"));
                urlCheck.setCreatedAt(result.getTimestamp("createdAt").toLocalDateTime());
                checksList.add(urlCheck);
            }
        } catch (SQLException e) {
            log.info("Failed to find entity i the url_checks repository", e);
        }
        return  checksList;
    }

    public static void removeAll() throws SQLException {
        String sql = "DELETE * FROM url_checks";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
             stmt.executeUpdate();
        } catch (SQLException e) {
            log.info("Failed to clear the url_checks repository", e);
        }
    }
}
