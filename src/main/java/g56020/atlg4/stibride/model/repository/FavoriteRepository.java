package g56020.atlg4.stibride.model.repository;

import g56020.atlg4.stibride.model.dao.FavoriteDao;
import g56020.atlg4.stibride.model.dto.FavoriteDto;
import g56020.atlg4.stibride.model.dto.StationDto;
import javafx.util.Pair;

import java.util.List;

public class FavoriteRepository implements Repository<Pair<StationDto, StationDto>, FavoriteDto> {
    private final FavoriteDao dao;

    public FavoriteRepository() throws RepositoryException {
        this.dao = FavoriteDao.getInstance();
    }

    FavoriteRepository(FavoriteDao dao) {
        this.dao = dao;
    }


    @Override
    public List<FavoriteDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public FavoriteDto get(Pair<StationDto, StationDto> key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Pair<StationDto, StationDto> key) throws RepositoryException {
        return dao.select(key) != null;
    }

    public int add(FavoriteDto favoriteDto) throws RepositoryException {
        if (!contains(favoriteDto.getKey())) {
            return dao.insert(favoriteDto);
        }
        return -1;
    }

    public int remove(Pair<StationDto, StationDto> key) throws RepositoryException {
        if (contains(key)) {
            return dao.delete(key);
        }
        return -1;
    }
}
