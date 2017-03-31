package seedu.task.ui;
//@@author A0142487Y
public enum Theme {
    Default("Default"), Dark("Dark"), Light("Light");

    private final String themeDescription;

    private Theme(String description) {
        themeDescription = description;
    }

    public static Theme getTheme(String themeName) {
        // TODO Auto-generated method stub
        for (Theme t : Theme.values()) {
            if (t.toString().equalsIgnoreCase(themeName.trim())) {
                return t;
            }
        }
        return Default;
    }

    public static String valueOf(Theme theme) {
        return theme.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Theme is : ").append(themeDescription).append(".");
        return sb.toString();
    }

}
