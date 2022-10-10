package g56020.atlg4.stibride.model.dao;

import g56020.atlg4.stibride.config.ConfigManager;
import g56020.atlg4.stibride.model.dto.StationDto;
import g56020.atlg4.stibride.model.dto.StopDto;
import g56020.atlg4.stibride.model.repository.RepositoryException;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StopsDaoTest {

    private final StopDto testStop;
    private final StopDto failTestStop;
    private static final Pair<Integer, StationDto> KEY = new Pair<>(99, new StationDto(9999,""));

    private final List<StopDto> all;
    private StopsDao instance;

    public StopsDaoTest() {
        System.out.println("==== StudentDaoTest Constructor =====");
        failTestStop = new StopDto(1234, new StationDto(0, "TEST-STATION"), 99);
        testStop = new StopDto(KEY.getKey(), new StationDto(KEY.getValue().getKey(), "TEST-STATION"), 99);

        all = new ArrayList<>();
        all.add(new StopDto(1, new StationDto(8012, "DE BROUCKERE"), 6));
        all.add(new StopDto(1, new StationDto(8022, "GARE CENTRALE"), 7));
        all.add(new StopDto(1, new StationDto(8032, "PARC"), 8));
        all.add(new StopDto(1, new StationDto(8042, "ARTS-LOI"), 9));
        all.add(new StopDto(2, new StationDto(8042, "ARTS-LOI"), 13));
        all.add(new StopDto(5, new StationDto(8012, "DE BROUCKERE"), 15));
        all.add(new StopDto(5, new StationDto(8022, "GARE CENTRALE"), 16));
        all.add(new StopDto(5, new StationDto(8032, "PARC"), 17));
        all.add(new StopDto(5, new StationDto(8042, "ARTS-LOI"), 18));
        all.add(new StopDto(6, new StationDto(8042, "ARTS-LOI"), 20));

        all.add(testStop);

        try {
            ConfigManager.getInstance().load();
            instance = StopsDao.getInstance();
        } catch (RepositoryException | IOException e) {
            fail("Connection to database failed: " + e);
        }

    }

    @Test
    public void testSelectExist() throws Exception {
        System.out.println("testSelectExist");
        //Arrange
        StopDto expected = testStop;
        //Action
        StopDto result = instance.select(KEY);
        //Assert
        assertEquals(expected, result);
    }


    @Test
    public void testSelectNotExist() throws Exception {
        System.out.println("testSelectNotExist");
        //Arrange
        //Action
        StopDto result = instance.select(failTestStop.getKey());
        //Assert
        assertNull(result);
    }

    @Test
    public void testSelectIncorrectParameter() throws Exception {
        System.out.println("testSelectIncorrectParameter");
        //Arrange
        Pair<Integer, StationDto> incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            instance.select(incorrect);
        });
    }


}
