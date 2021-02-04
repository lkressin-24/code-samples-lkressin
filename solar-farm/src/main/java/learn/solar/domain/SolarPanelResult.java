package learn.solar.domain;

import learn.solar.models.SolarPanel;

import java.util.ArrayList;
import java.util.List;

public class SolarPanelResult {
    private ArrayList<String> messages = new ArrayList<>();
    private SolarPanel panel;

    public SolarPanel getPanel() {return panel;}

    public void setPanel(SolarPanel panel) {this.panel = panel;}

    public List<String> getErrorMessages() {
        return new ArrayList<>(messages);
    }

    public void addErrorMessage(String message) {messages.add(message);}

    public boolean isSuccess() {
        return messages.size() == 0;
    }


}
