package g56020.atlg4.stibride.model.repository;

import g56020.atlg4.stibride.model.dao.StopsDao;
import g56020.atlg4.stibride.model.dto.StationDto;
import g56020.atlg4.stibride.model.dto.StopDto;
import javafx.util.Pair;

import java.util.List;

public class StopsRepository implements Repository<Pair<Integer, StationDto>, StopDto> {

    private final StopsDao dao;

    public StopsRepository() throws RepositoryException {
        this.dao = StopsDao.getInstance();
    }

    StopsRepository(StopsDao dao) {
        this.dao = dao;
    }

    @Override
    public List<StopDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopDto get(Pair<Integer, StationDto> key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Pair<Integer, StationDto> key) throws RepositoryException {
        return dao.select(key) != null;
    }
}
