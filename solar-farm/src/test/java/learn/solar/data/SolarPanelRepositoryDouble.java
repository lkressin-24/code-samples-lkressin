package learn.solar.data;

import learn.solar.models.MaterialType;
import learn.solar.models.SolarPanel;

import java.util.ArrayList;
import java.util.List;

public class SolarPanelRepositoryDouble implements SolarPanelRepository {
    private ArrayList<SolarPanel> panels = new ArrayList<>();

    public SolarPanelRepositoryDouble() {
        panels.add(new SolarPanel(1, "Test Section 1", 1, 1, 2020, MaterialType.MULTICRYSTALLINE_SILICON, true));
        panels.add(new SolarPanel(2, "Test Section 2", 1, 1, 2019, MaterialType.CADMIUM_TELLURIDE, false));
        panels.add(new SolarPanel(3, "Test Section 3", 1,1, 2018, MaterialType.AMORPHOUS_SILICON, true));
        panels.add(new SolarPanel(4, "Test Section 1", 1, 2, 2020, MaterialType.MULTICRYSTALLINE_SILICON, true));
        panels.add(new SolarPanel(5, "Test Section 2", 1, 2, 2019, MaterialType.CADMIUM_TELLURIDE, false));
        panels.add(new SolarPanel(6, "Test Section 3", 1, 2, 2018, MaterialType.AMORPHOUS_SILICON, true));
    }

    @Override
    public List<SolarPanel> findAll() throws DataAccessException {
        return new ArrayList<>(panels);
    }

    @Override
    public List<SolarPanel> findBySection(String section, List<SolarPanel> panels) throws DataAccessException {
        ArrayList<SolarPanel> result = new ArrayList<>();
        for (SolarPanel sp: panels) {
            if (sp.getSection().equalsIgnoreCase(section)) {
                result.add(sp);
            }
        }
        return result;
    }

    @Override
    public List<SolarPanel> findTracking() throws DataAccessException {
        ArrayList<SolarPanel> result = new ArrayList<>();
        for (int i = 0; i < panels.size(); i++) {
            if (panels.get(i).isTracking()) {
                result.add(panels.get(i));
            }
        }
        return result;
    }

    @Override
    public SolarPanel findById(int panelId) throws DataAccessException {
        for (int i = 0; i < panels.size(); i++) {
            if (panels.get(i).getPanelId() == panelId) {
                return panels.get(i);
            }
        }
        return null;
    }

    @Override
    public SolarPanel add(SolarPanel panel) throws DataAccessException {
        return panel;
    }

    @Override
    public boolean update(SolarPanel panel) throws DataAccessException {
        return true;
    }

    @Override
    public boolean deleteById(int panelId) throws DataAccessException {
        return panelId == 5;
    }
}
