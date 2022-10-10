package g56020.atlg4.stibride.model.dao;

import g56020.atlg4.stibride.model.dto.StationDto;
import g56020.atlg4.stibride.model.dto.StopDto;
import g56020.atlg4.stibride.model.repository.RepositoryException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StopsDao implements Dao<Pair<Integer, StationDto>, StopDto> {

    private Connection connection;

    private StopsDao() throws RepositoryException {
        connection = DBManager.getInstance().getConnection();
    }

    public static StopsDao getInstance() throws RepositoryException {
        return StopsDaoHolder.getInstance();
    }

    @Override
    public StopDto select(Pair<Integer, StationDto> key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Missing key");
        }
        if (key.getKey() == null || key.getValue() == null) {
            throw new RepositoryException("Incomplete key");
        }

        StopDto dto = null;
        String sql =
                "SELECT STOPS.id_line, STOPS.id_station, STOPS.id_order, STATIONS.name " +
                        "FROM STOPS " +
                        "JOIN LINES ON LINES.id = STOPS.id_line " +
                        "JOIN STATIONS ON STATIONS.id = STOPS.id_station " +
                        "WHERE STOPS.id_line= ? AND STOPS.id_station = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key.getKey());
            pstmt.setInt(2, key.getValue().getKey());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new StopDto(
                        rs.getInt("id_line"),
                        new StationDto(
                                rs.getInt("id_station"),
                                rs.getString("name")),
                        rs.getInt("id_order"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }

    @Override
    public List<StopDto> selectAll() throws RepositoryException {
        List<StopDto> stops = new ArrayList<>();
        String sql =
                "SELECT STOPS.id_line, STOPS.id_station, STOPS.id_order, STATIONS.name " +
                        "FROM STOPS " +
                        "JOIN LINES ON LINES.id = STOPS.id_line " +
                        "JOIN STATIONS ON STATIONS.id = STOPS.id_station " +
                        "ORDER BY STOPS.id_line, STOPS.id_order";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                StopDto dto = new StopDto(
                        rs.getInt("id_line"),
                        new StationDto(rs.getInt("id_station"),
                                rs.getString("name")),
                        rs.getInt("id_order"));
                stops.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return stops;
    }


    private static class StopsDaoHolder {
        private static StopsDao getInstance() throws RepositoryException {
            return new StopsDao();
        }
    }
}
