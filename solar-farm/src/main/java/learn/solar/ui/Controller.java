package learn.solar.ui;

import learn.solar.data.DataAccessException;
import learn.solar.domain.SolarPanelResult;
import learn.solar.domain.SolarPanelService;
import learn.solar.models.SolarPanel;

import java.util.List;

public class Controller {
    private final SolarPanelService service;
    private final View view;

    public Controller(SolarPanelService service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        view.printHeader("Welcome to Solar Farm");
        try {
            MenuOption selection;
            do {
                selection = view.selectMenuOption();
                switch (selection) {
                    case FIND_BY_SECTION:
                        displayPanelsBySection();
                        break;
                    case ADD:
                        addSolarPanel();
                        break;
                    case UPDATE:
                        updateSolarPanel();
                        break;
                    case REMOVE:
                        removeSolarPanel();
                        break;
                }
            } while (selection != MenuOption.EXIT);
        } catch (DataAccessException ex) {
            view.printHeader("Fatal Error: " + ex.getMessage());
        }
    }

    private void displayPanelsBySection() throws DataAccessException {
        view.printHeader(MenuOption.FIND_BY_SECTION.getDisplayText());
        List<SolarPanel> panels = service.findAll();
        List<SolarPanel> panelsInSection = service.findBySection(view.chooseSection(view.getSections(panels)), panels);
        view.printPanels(panelsInSection);
    }

    private void addSolarPanel() throws DataAccessException{
        view.printHeader(MenuOption.ADD.getDisplayText());
        SolarPanel panel = view.makeSolarPanel();
        SolarPanelResult result = service.add(panel);
        view.printResultMessage(result, panel, "added");
    }

    private void updateSolarPanel() throws DataAccessException{
        view.printHeader(MenuOption.UPDATE.getDisplayText());

        List<SolarPanel> panels = service.findAll();
        List<SolarPanel> panelsToChoose = service.findBySection(view.chooseSection(view.getSections(panels)), panels);
        view.printPanels(panelsToChoose);

        if (panelsToChoose.size() != 0) {
            int panelId = view.readInt("Solar Panel Id: ", "Solar Panel Id");
            SolarPanel panel = view.update(service.findById(panelId));
            SolarPanelResult result = service.update(panel);
            view.printResultMessage(result, panel, "updated");
        }

    }

    private void removeSolarPanel() throws DataAccessException{
        view.printHeader(MenuOption.REMOVE.getDisplayText());

        List<SolarPanel> panels = service.findAll();
        List<SolarPanel> panelsToChoose = service.findBySection(view.chooseSection(view.getSections(panels)), panels);
        view.printPanels(panelsToChoose);

        if (panelsToChoose.size() != 0) {
            int panelId = view.readInt("Solar Panel Id: ", "Solar Panel Id");
            SolarPanel panel = service.findById(panelId);
            SolarPanelResult result = service.deleteById(panelId);
            view.printResultMessage(result, panel, "removed");
        }
    }
}
