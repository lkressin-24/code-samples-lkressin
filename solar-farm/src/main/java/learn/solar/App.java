package learn.solar;

import learn.solar.data.SolarPanelFileRepository;
import learn.solar.domain.SolarPanelService;
import learn.solar.ui.Controller;
import learn.solar.ui.View;

public class App {
    public static void main(String[] args) {
        SolarPanelFileRepository repository = new SolarPanelFileRepository("./data/solar-panels.txt");
        View view = new View();
        SolarPanelService service = new SolarPanelService(repository);
        Controller controller = new Controller(service, view);

        controller.run();
    }
}
