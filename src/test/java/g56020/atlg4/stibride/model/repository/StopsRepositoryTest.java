package g56020.atlg4.stibride.model.repository;

import g56020.atlg4.stibride.model.dao.StopsDao;
import g56020.atlg4.stibride.model.dto.StationDto;
import g56020.atlg4.stibride.model.dto.StopDto;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class StopsRepositoryTest {

    @Mock
    private StopsDao mock;

    private final StopDto testStop;
    private final StopDto failTestStop;

    private static final Pair<Integer, StationDto> KEY = new Pair<>(99, new StationDto(9999,""));

    private final List<StopDto> all;

    public StopsRepositoryTest() {
        System.out.println("StudentRepositoryTest Constructor");
        //Test data
        failTestStop = new StopDto(1234, new StationDto(0, "TEST-STATION"), 99);
        testStop = new StopDto(KEY.getKey(), new StationDto(KEY.getValue().getKey(),"TEST-STATION"), 99);

        all = new ArrayList<>();
        all.add(testStop);
        all.add(failTestStop);
    }

    @BeforeEach
    void init() throws RepositoryException {
        System.out.println("==== BEFORE EACH =====");
        //Mock behaviour
        Mockito.lenient().when(mock.select(testStop.getKey())).thenReturn(testStop);
        Mockito.lenient().when(mock.select(failTestStop.getKey())).thenReturn(null);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
    }

    @Test
    public void testGetExist() throws Exception {
        System.out.println("testGetExist");
        //Arrange
        StopDto expected = testStop;
        StopsRepository repository = new StopsRepository(mock);
        //Action
        StopDto result = repository.get(KEY);
        //Assert
        assertEquals(expected, result);
        Mockito.verify(mock, times(1)).select(KEY);
    }

    @Test
    public void testGetNotExist() throws Exception {
        System.out.println("testGetNotExist");
        //Arrange
        StopsRepository repository = new StopsRepository(mock);
        //Action
        StopDto result = repository.get(failTestStop.getKey());
        //Assert
        assertNull(result);
        Mockito.verify(mock, times(1)).select(failTestStop.getKey());
    }

    @Test
    public void testGetIncorrectParameter() throws Exception {
        System.out.println("testGetIncorrectParameter");
        //Arrange
        StopsRepository repository = new StopsRepository(mock);
        Pair<Integer, StationDto> incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repository.get(incorrect);
        });
        Mockito.verify(mock, times(1)).select(null);
    }
}