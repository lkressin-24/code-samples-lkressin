package learn.solar.ui;

public enum MenuOption {
    EXIT("Exit"),
    FIND_BY_SECTION("Find Panels By Section"),
    ADD("Add A Solar Panel"),
    UPDATE("Update A Solar Panel"),
    REMOVE("Remove A Solar Panel");

    private String displayText;

    MenuOption(String displayText) {this.displayText = displayText;}

    public String getDisplayText() {return displayText;}
}
