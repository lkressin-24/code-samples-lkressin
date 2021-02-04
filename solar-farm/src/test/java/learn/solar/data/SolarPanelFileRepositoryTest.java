package learn.solar.data;

import learn.solar.models.MaterialType;
import learn.solar.models.SolarPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolarPanelFileRepositoryTest {
    static final String SEED_FILE_PATH = "./data/solar-panels-seed.txt";
    static final String TEST_FILE_PATH = "./data/solar-panels-test.txt";

    SolarPanelFileRepository repository = new SolarPanelFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setupTest() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        List<SolarPanel> actual = repository.findAll();
        assertEquals(6, actual.size());
    }

    @Test
    void shouldFindById()throws DataAccessException {
        SolarPanel actual =  repository.findById(3);
        assertNotNull(actual);
        assertEquals("Main", actual.getSection());
        assertFalse(actual.isTracking());
    }

    @Test
    void shouldNotFindNonExistingId() throws DataAccessException {
        assertNull(repository.findById(400));
    }

    @Test
    void shouldFindBySection() throws DataAccessException {
        List<SolarPanel> panels = repository.findAll();
        List<SolarPanel> actual = repository.findBySection("Main", panels);
        assertEquals(4, actual.size());
    }

    @Test
    void sizeShouldBeZeroForNonExistingSection() throws DataAccessException {
        List<SolarPanel> panels = repository.findAll();
        List<SolarPanel> actual = repository.findBySection("Far Hill", panels);
        assertEquals(0, actual.size());
    }

    @Test
    void shouldFindTracking() throws DataAccessException {
        List<SolarPanel> actual = repository.findTracking();
        assertEquals(3, actual.size());
    }

    @Test
    void shouldAdd() throws DataAccessException {
        SolarPanel panel = new SolarPanel();
        panel.setSection("Far Hill");
        panel.setRow(2);
        panel.setColumn(2);
        panel.setMaterial(MaterialType.CADMIUM_TELLURIDE);
        panel.setYearInstalled(2020);
        panel.setTracking(false);

        SolarPanel actual = repository.add(panel);

        assertNotNull(actual);
        assertEquals(7, actual.getPanelId());
    }

    @Test
    void shouldUpdate() throws DataAccessException {
        SolarPanel panel = new SolarPanel();
        panel.setPanelId(4);
        panel.setYearInstalled(2020);
        panel.setMaterial(MaterialType.MULTICRYSTALLINE_SILICON);
        panel.setTracking(true);

        boolean success = repository.update(panel);
        assertTrue(success);

        SolarPanel actual = repository.findById(4);
        assertNotNull(actual);
        assertEquals(2020, actual.getYearInstalled());
        assertEquals(MaterialType.MULTICRYSTALLINE_SILICON, actual.getMaterial());
        assertTrue(actual.isTracking());

    }

    @Test
    void shouldNotUpdateNonExisting() throws DataAccessException {
        assertFalse(repository.update(repository.findById(4000)));
    }

    @Test
    void shouldDeleteById () throws DataAccessException {
        boolean success = repository.deleteById(2);
        assertTrue(success);
    }

    @Test
    void shouldNotDeleteNonExisting() throws DataAccessException {
        boolean success = repository.deleteById(4000);
        assertFalse(success);
    }

}