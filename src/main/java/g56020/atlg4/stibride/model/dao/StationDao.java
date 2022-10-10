package g56020.atlg4.stibride.model.dao;

import g56020.atlg4.stibride.model.dto.StationDto;
import g56020.atlg4.stibride.model.repository.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StationDao implements Dao<Integer, StationDto> {
    private Connection connection;

    private StationDao() throws RepositoryException {
        connection = DBManager.getInstance().getConnection();
    }

    public static StationDao getInstance() throws RepositoryException {
        return StationDaoHolder.getInstance();
    }

    @Override
    public StationDto select(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Missing key");
        }
        StationDto dto = null;
        String sql = "SELECT id, name FROM STATIONS WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new StationDto(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }

    @Override
    public List<StationDto> selectAll() throws RepositoryException {
        List<StationDto> stations = new ArrayList<>();
        String sql = "SELECT id, name FROM STATIONS";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                StationDto dto = new StationDto(
                        rs.getInt("id"),
                        rs.getString("name"));
                stations.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return stations;
    }

    private static class StationDaoHolder {
        private static StationDao getInstance() throws RepositoryException {
            return new StationDao();
        }
    }
}
