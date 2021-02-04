package learn.solar.ui;

import learn.solar.domain.SolarPanelResult;
import learn.solar.models.MaterialType;
import learn.solar.models.SolarPanel;

import java.util.*;

public class View {

    private Scanner console = new Scanner(System.in);

    public MenuOption selectMenuOption() { //prints menu, prompts for selection, and returns selection
        printHeader("Main Menu");

        MenuOption[] options = MenuOption.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%s. %s%n", i + 1, options[i].getDisplayText());
        }

        String selection = String.format("Select [%s-%s]: ", 1, options.length);
        String required = "Menu Option";
        int value = readIntInRange(selection, required, 1, options.length);
        return options[value - 1];
    }

    public void printHeader(String header) {
        System.out.println();
        System.out.println(header);
        System.out.println("=".repeat(header.length()));
    }

    public void printResultMessage(SolarPanelResult result, SolarPanel panel, String action) {
        if (result.isSuccess()) {
            printHeader("Success");
            System.out.printf("Panel %s-%s-%s %s.%n", panel.getSection(), panel.getRow(), panel.getColumn(), action);
        } else {
            printHeader("Error");
            for (String error: result.getErrorMessages()) {
                System.out.println(error);
            }
        }


    }

    public List<String> getSections(List<SolarPanel> panels) {
        HashSet<String> possibleSections = new HashSet<>();
        for (SolarPanel sp: panels) {
            possibleSections.add(sp.getSection());
        }

        ArrayList<String> sections = new ArrayList<>();
        for (String section: possibleSections) {
            sections.add(section);
        }

        return sections;
    }

    public String chooseSection(List<String> sections) {
        int sectionCount = 1;
        if (sections.size() == 0) {
            return "No Sections Found.";
        }

        for (String section: sections) {
            System.out.printf("%s. %s%n", sectionCount, section);
            sectionCount++;
        }

        String selection = String.format("Select [%s-%s]: ", 1, sections.size());
        String required = "Section Selection";
        int value = readIntInRange(selection, required, 1, sections.size());
        return sections.get(value - 1);
    }

    public void printPanels(List<SolarPanel> panels) {
        if (panels == null || panels.size() == 0) {
            System.out.println();
            System.out.println("No panels found.");
        } else {
            for (SolarPanel sp: panels) {
                System.out.printf("Panel Id: %s, Row: %s, Column: %s, Year Installed: %s, Material: %s, %s%n",
                        sp.getPanelId(),
                        sp.getRow(),
                        sp.getColumn(),
                        sp.getYearInstalled(),
                        sp.getMaterial().toString(),
                        sp.isTracking()? "Sun-tracking" : "No Sun-tracking");
            }
        }
    }

    public SolarPanel makeSolarPanel() {
        SolarPanel panel = new SolarPanel();
        panel.setSection(readRequiredString("Section: ", "Section"));
        panel.setRow(readIntInRange("Row: ", "Row", 1, 250));
        panel.setColumn(readIntInRange("Column: ", "Column", 1, 250));
        panel.setYearInstalled(readInt("Year Installed: ", "Installation Year"));
        panel.setMaterial(selectMaterial(List.of(MaterialType.values())));
        panel.setTracking(readTracking("Sun Tracking? [y or n]: ", "Tracking"));

        return panel;
    }

    public SolarPanel update(SolarPanel panel) {
        String section = readString("Section (" + panel.getSection() + "): ");
        if (section.trim().length() > 0) {
            panel.setSection(section);
        }

        String row = readString("Row (" + panel.getRow() + "): ");
        if (row.trim().length() > 0) {
            panel.setRow(Integer.parseInt(row));
        }

        String column = readString("Column (" + panel.getColumn() + "): ");
        if (column.trim().length() > 0) {
            panel.setColumn(Integer.parseInt(column));
        }

        String year = readString("Year Installed (" + panel.getYearInstalled() + "): ");
        if (year.trim().length() > 0) {
            panel.setYearInstalled(Integer.parseInt(year));
        }

        String changeMaterial = readString("Change Material? (" + panel.getMaterial().toString() + ") [y or n]: ");
        if (changeMaterial.charAt(0) == 'y') {
            MaterialType material = selectMaterial(List.of(MaterialType.values()));
            panel.setMaterial(material);
        }

        String tracking = readString("Tracking [y or n]: ");
        if (tracking.charAt(0) == 'y') {
            panel.setTracking(true);
        } else {
            panel.setTracking(false);
        }

        return panel;
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return console.nextLine();
    }

    private String readRequiredString(String prompt, String requiredValueName) {
        String read;
        do {
            read = readString(prompt);
            if (read.trim().length() == 0) {
                System.out.println(requiredValueName + " is required.");
            }
        } while (read.trim().length() == 0);
        return read;
    }

    public int readInt(String prompt, String requiredValueName) {
        String input = null;
        int read = 0;
        boolean isNumber = false;
        do {
            try{
                input = readRequiredString(prompt, requiredValueName);
                read = Integer.parseInt(input);
                isNumber = true;
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a valid number.%n", input);
            }
        } while (!isNumber);

        return read;
    }

    private int readIntInRange(String prompt, String requiredValueName, int min, int max) {
        int read;
        do {
            read = readInt(prompt, requiredValueName);
            if (read < min || read > max) {
                System.out.printf("Value must be between %s and %s.%n", min, max);
            }
        } while (read < min || read > max);

        return read;
    }

    private boolean readTracking(String prompt, String requiredValueName) {
        String read = readRequiredString(prompt, requiredValueName);

        return read.charAt(0) == 'y';
    }

    private MaterialType selectMaterial(List<MaterialType> types) {
        int typeCount = 1;

        System.out.println("Material: ");
        for (MaterialType type: types) {
            System.out.printf("%s. %s%n", typeCount, type.toString());
            typeCount++;
        }

        String selection = String.format("Select [%s-%s]: ", 1, types.size());
        String required = "Material Selection";
        int value = readIntInRange(selection, required, 1, types.size());
        return types.get(value - 1);
    }





}
