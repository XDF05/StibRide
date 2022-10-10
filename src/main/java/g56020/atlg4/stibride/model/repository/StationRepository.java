package g56020.atlg4.stibride.model.repository;

import g56020.atlg4.stibride.model.dao.StationDao;
import g56020.atlg4.stibride.model.dto.StationDto;

import java.util.List;

public class StationRepository implements Repository<Integer, StationDto> {
    private final StationDao dao;

    public StationRepository() throws RepositoryException {
        this.dao = StationDao.getInstance();
    }

    StationRepository(StationDao dao) {
        this.dao = dao;
    }

    @Override
    public List<StationDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StationDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        return dao.select(key) != null;
    }
}
