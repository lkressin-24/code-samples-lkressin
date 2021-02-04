package learn.solar.data;

import learn.solar.models.SolarPanel;

import java.util.List;

public interface SolarPanelRepository {
    List<SolarPanel> findAll() throws DataAccessException;

    List<SolarPanel> findBySection(String section, List<SolarPanel> panels) throws DataAccessException;

    List<SolarPanel> findTracking() throws DataAccessException;

    SolarPanel findById(int panelId) throws DataAccessException;

    SolarPanel add(SolarPanel panel) throws DataAccessException;

    boolean update(SolarPanel panel) throws DataAccessException;

    boolean deleteById(int panelId) throws DataAccessException;
}
