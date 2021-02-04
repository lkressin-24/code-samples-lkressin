package learn.solar.data;

import learn.solar.models.MaterialType;
import learn.solar.models.SolarPanel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SolarPanelFileRepository  implements SolarPanelRepository {
    private final String filepath;
    private final String DELIMITER = "&&";

    public SolarPanelFileRepository (String filepath) {
        this.filepath = filepath;
    }

    @Override
    public List<SolarPanel> findAll() throws DataAccessException {
        ArrayList<SolarPanel> panels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                SolarPanel sp = deserialize(line);
                if (sp != null) {
                    panels.add(sp);
                }
            }
        } catch (FileNotFoundException ex) {
            // do nothing
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
        return panels;
    }

    @Override
    public List<SolarPanel> findBySection(String section, List<SolarPanel> panels) throws DataAccessException {
        ArrayList<SolarPanel> panelsInSection = new ArrayList<>();
        for (SolarPanel panel: panels) {
            if (panel.getSection().equalsIgnoreCase(section)) {
                panelsInSection.add(panel);
            }
        }
        return panelsInSection;
    }

    @Override
    public List<SolarPanel> findTracking() throws DataAccessException {
        ArrayList<SolarPanel> tracking = new ArrayList<>();
        for (SolarPanel panel: findAll()) {
            if (panel.isTracking()) {
                tracking.add(panel);
            }
        }
        return tracking;
    }

    @Override
    public SolarPanel findById(int panelId) throws DataAccessException {
        List<SolarPanel> all = findAll();
        for (SolarPanel panel: all) {
            if (panel.getPanelId() == panelId) {
                return panel;
            }
        }

        return null;
    }

    @Override
    public SolarPanel add(SolarPanel panel) throws DataAccessException {
        List<SolarPanel> all = findAll();
        panel.setPanelId(getNextId(all));
        all.add(panel);
        writeToFile(all);
        return panel;
    }

    @Override
    public boolean update(SolarPanel panel) throws DataAccessException {
        List<SolarPanel> all = findAll();

        if (panel == null) {
            return false;
        }

        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getPanelId() == panel.getPanelId()) {
                all.set(i, panel);
                writeToFile(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int panelId) throws DataAccessException {
        List<SolarPanel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getPanelId() == panelId) {
                all.remove(i);
                writeToFile(all);
                return true;
            }
        }
        return false;
    }

    //converts SolarPanel to line in file
    private String serialize(SolarPanel panel) {
        return String.format("%s&&%s&&%s&&%s&&%s&&%s&&%s",
                panel.getPanelId(),
                panel.getSection(),
                panel.getRow(),
                panel.getColumn(),
                panel.getYearInstalled(),
                panel.getMaterial(),
                panel.isTracking());
    }

    //converts line in file to SolarPanel
    private SolarPanel deserialize(String line) {
        String[] fields = line.split(DELIMITER);
        SolarPanel panel = new SolarPanel();
        if (fields.length == 7) {
            panel.setPanelId(Integer.parseInt(fields[0]));
            panel.setSection(fields[1]);
            panel.setRow(Integer.parseInt(fields[2]));
            panel.setColumn(Integer.parseInt(fields[3]));
            panel.setYearInstalled(Integer.parseInt(fields[4]));
            panel.setMaterial(MaterialType.valueOf(fields[5]));
            panel.setTracking(Boolean.parseBoolean(fields[6]));
        } else {
            return null;
        }

        return panel;
    }

    private int getNextId(List<SolarPanel> panels) {
        int nextId = 0;
        for (SolarPanel sp: panels) {
            nextId = Math.max(nextId, sp.getPanelId());
        }
        return nextId +1;
    }

    private void writeToFile(List<SolarPanel> panels) throws DataAccessException {
        try(PrintWriter writer = new PrintWriter(filepath)) {
            for (SolarPanel panel: panels) {
                writer.println(serialize(panel));
            }
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }


}
