package learn.solar.domain;

import learn.solar.data.DataAccessException;
import learn.solar.data.SolarPanelRepositoryDouble;
import learn.solar.models.MaterialType;
import learn.solar.models.SolarPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SolarPanelServiceTest {

    SolarPanelService service;

    @BeforeEach
    void setup() {
        SolarPanelRepositoryDouble repository = new SolarPanelRepositoryDouble();
        service = new SolarPanelService(repository);
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        List<SolarPanel> panels = service.findAll();
        assertNotNull(panels);
        assertEquals(6, panels.size());
    }

    @Test
    void shouldFindByType() throws DataAccessException {
        List<SolarPanel> actual = service.findBySection("Test Section 2", service.findAll());
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void shouldFindTracking() throws DataAccessException {
        List<SolarPanel> actual = service.findTracking();
        assertNotNull(actual);
        assertEquals(4, actual.size());
    }

    @Test
    void shouldFindById() throws DataAccessException {
        SolarPanel actual = service.findById(5);
        assertEquals("Test Section 2", actual.getSection());
        assertEquals(2019, actual.getYearInstalled());
    }

    @Test
    void shouldAdd() throws DataAccessException {
        SolarPanelResult result = service.add(new SolarPanel(7, "Test Section 4", 1, 1, 2017, MaterialType.CADMIUM_TELLURIDE, false));

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddNull() throws DataAccessException {
        SolarPanelResult result = service.add(null);

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddWithoutSection() throws DataAccessException {
        SolarPanel panel = new SolarPanel();
        panel.setRow(2);
        panel.setColumn(2);
        panel.setMaterial(MaterialType.AMORPHOUS_SILICON);
        panel.setYearInstalled(2016);
        panel.setTracking(true);

        SolarPanelResult result = service.add(panel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddDuplicate() throws DataAccessException {
        SolarPanelResult result = service.add(new SolarPanel(1, "Test Section 1", 1, 1, 2020, MaterialType.MULTICRYSTALLINE_SILICON, true));

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddImpossibleRow() throws DataAccessException {
        SolarPanel panel = new SolarPanel();
        panel.setSection("Test Section 4");
        panel.setRow(2000);
        panel.setColumn(2);
        panel.setMaterial(MaterialType.AMORPHOUS_SILICON);
        panel.setYearInstalled(2016);
        panel.setTracking(true);

        SolarPanelResult result = service.add(panel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddImpossibleColumn() throws DataAccessException {
        SolarPanel panel = new SolarPanel();
        panel.setSection("Test Section 4");
        panel.setRow(2);
        panel.setColumn(2000);
        panel.setMaterial(MaterialType.AMORPHOUS_SILICON);
        panel.setYearInstalled(2016);
        panel.setTracking(true);

        SolarPanelResult result = service.add(panel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddFutureYear() throws DataAccessException {
        SolarPanel panel = new SolarPanel();
        panel.setSection("Test Section 4");
        panel.setRow(2);
        panel.setColumn(2);
        panel.setMaterial(MaterialType.AMORPHOUS_SILICON);
        panel.setYearInstalled(2044);
        panel.setTracking(true);

        SolarPanelResult result = service.add(panel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddWithoutMaterial() throws DataAccessException {
        SolarPanel panel = new SolarPanel();
        panel.setSection("Test Section 4");
        panel.setRow(2);
        panel.setColumn(2);
        panel.setYearInstalled(2016);
        panel.setTracking(true);

        SolarPanelResult result = service.add(panel);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldUpdate() throws DataAccessException {
        SolarPanelResult result = service.update(new SolarPanel(1, "Test Section 5", 6, 9, 2020, MaterialType.MULTICRYSTALLINE_SILICON, true));

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateDuplicate() throws DataAccessException {
        SolarPanelResult result = service.update(new SolarPanel(2, "Test Section 2", 1, 1, 2019, MaterialType.CADMIUM_TELLURIDE, false));

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotNonExisting() throws DataAccessException {
        SolarPanel panel = new SolarPanel();
        panel.setPanelId(2000);
        SolarPanelResult result = service.update(panel);

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldDeleteById() throws DataAccessException {
        SolarPanelResult result = service.deleteById(5);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteNonExisting() throws DataAccessException {
        SolarPanelResult result = service.deleteById(5000);

        assertFalse(result.isSuccess());
    }

}
