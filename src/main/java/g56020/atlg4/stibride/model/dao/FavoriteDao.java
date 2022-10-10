package g56020.atlg4.stibride.model.dao;

import g56020.atlg4.stibride.model.dto.FavoriteDto;
import g56020.atlg4.stibride.model.dto.StationDto;
import g56020.atlg4.stibride.model.repository.RepositoryException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDao implements Dao<Pair<StationDto, StationDto>, FavoriteDto> {
    private Connection connection;

    private FavoriteDao() throws RepositoryException {
        connection = DBManager.getInstance().getConnection();
    }

    public static FavoriteDao getInstance() throws RepositoryException {
        return FavoriteDao.FavoriteDaoHolder.getInstance();
    }


    @Override
    public FavoriteDto select(Pair<StationDto, StationDto> key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("Missing key");
        }
        if (key.getKey() == null || key.getValue() == null) {
            throw new RepositoryException("Incomplete key");
        }
        FavoriteDto dto = null;
        String sql = "SELECT id_source, SRC.name, id_destination, DST.name, FAVORITES.nom FROM FAVORITES " +
                "JOIN STATIONS SRC on FAVORITES.id_source = SRC.id " +
                "JOIN STATIONS DST on FAVORITES.id_destination = DST.id " +
                "WHERE id_source = ? AND id_destination = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key.getKey().getKey());
            pstmt.setInt(2, key.getValue().getKey());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(4));
                dto = new FavoriteDto(new Pair<>(
                        new StationDto(rs.getInt("id_source"), rs.getString(2)),
                        new StationDto(rs.getInt("id_destination"), rs.getString(4))
                ), rs.getString(5));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;

    }

    @Override
    public List<FavoriteDto> selectAll() throws RepositoryException {
        List<FavoriteDto> dtos = new ArrayList<>();

        String sql = "SELECT id_source, SRC.name, id_destination, DST.name, FAVORITES.nom FROM FAVORITES " +
                "JOIN STATIONS SRC on FAVORITES.id_source = SRC.id " +
                "JOIN STATIONS DST on FAVORITES.id_destination = DST.id ";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dtos.add(new FavoriteDto(new Pair<>(
                        new StationDto(rs.getInt("id_source"), rs.getString(2)),
                        new StationDto(rs.getInt("id_destination"), rs.getString(4))
                ), rs.getString(5)));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    public int insert(FavoriteDto favorite) throws RepositoryException {
        int result;
        int source = favorite.getKey().getKey().getKey();
        int destination = favorite.getKey().getValue().getKey();
        String name = favorite.getName();

        String sql = "INSERT INTO FAVORITES('id_source','id_destination','nom')" +
                "VALUES (?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, source);
            pstmt.setInt(2, destination);
            pstmt.setString(3, name);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return result;
    }

    public int delete(Pair<StationDto, StationDto> favorite) throws RepositoryException {
        int result;
        int source = favorite.getKey().getKey();
        int destination = favorite.getValue().getKey();

        String sql = "DELETE FROM FAVORITES WHERE id_source = ? AND id_destination = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, source);
            pstmt.setInt(2, destination);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return result;
    }

    private static class FavoriteDaoHolder {
        private static FavoriteDao getInstance() throws RepositoryException {
            return new FavoriteDao();
        }
    }
}
