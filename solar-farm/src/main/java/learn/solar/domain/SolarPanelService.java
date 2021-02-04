package learn.solar.domain;

import learn.solar.data.DataAccessException;
import learn.solar.data.SolarPanelRepository;
import learn.solar.models.SolarPanel;

import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class SolarPanelService {

    private final SolarPanelRepository repository;

    public SolarPanelService(SolarPanelRepository repository) {
        this.repository = repository;
    }

    //1. read methods (find)
    public List<SolarPanel> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public List<SolarPanel> findBySection(String section, List<SolarPanel> panels) throws DataAccessException {
        return repository.findBySection(section, panels);
    }

    public List<SolarPanel> findTracking() throws DataAccessException {
        return repository.findTracking();
    }

    public SolarPanel findById(int panelId) throws DataAccessException {
        return repository.findById(panelId);
    }

    //2. create method (add)
    public SolarPanelResult add(SolarPanel panel) throws DataAccessException {
        SolarPanelResult result = validate(panel);
        if (!result.isSuccess()) {
            return result;
        }

        if (isRepeat(panel)) {
            result.addErrorMessage("Duplicate panel.");
            return result;
        }

        panel = repository.add(panel);
        result.setPanel(panel);
        return result;
    }

    //3. update method
    public SolarPanelResult update(SolarPanel panel) throws DataAccessException {
        SolarPanelResult result = validate(panel);

        if (isRepeat(panel)) {
            result.addErrorMessage("Duplicate panel.");
        }

        if (result.isSuccess()) {
            if (repository.update(panel)) {
                result.setPanel(panel);
            } else {
                result.addErrorMessage("Could not update.");
            }
        }
        return result;
    }

    //4. delete method
    public SolarPanelResult deleteById(int panelId) throws DataAccessException {
        SolarPanelResult result = new SolarPanelResult();

        if (!repository.deleteById(panelId)) {
            result.addErrorMessage("Could not delete");
            return result;
        }

        return result;
    }

    //5. validate inputs
    public SolarPanelResult validate(SolarPanel panel) {
        SolarPanelResult result = new SolarPanelResult();
        if (panel == null) {
            result.addErrorMessage("Solar panel cannot be null.");
            return result;
        }

        if (panel.getSection() == null || panel.getSection().trim().length() == 0) {
            result.addErrorMessage("Section name is required.");
        }

        if (panel.getRow() > 250 || panel.getRow() < 0) {
            result.addErrorMessage("Row must be positive integer no greater than 250.");
        }

        if (panel.getColumn() > 250 || panel.getRow() < 0) {
            result.addErrorMessage("Column must be positive integer no greater than 250.");
        }

        Year present = Year.now();
        if (panel.getYearInstalled() >= Integer.parseInt(present.toString())) {
            result.addErrorMessage("Year installed must be current or in the past.");
        }

        if (panel.getMaterial() == null) {
            result.addErrorMessage("Material selection is required.");
        }

        return result;
    }

    public boolean isRepeat(SolarPanel panel) throws DataAccessException {
        List<SolarPanel> all = repository.findAll();
        for (SolarPanel sp: all) {
            if (sp.getSection().equalsIgnoreCase(panel.getSection()) &&
                    sp.getRow() == panel.getRow() &&
                    sp.getColumn() == panel.getColumn()) {
                return true;
            }
        }

        return false;
    }


}
