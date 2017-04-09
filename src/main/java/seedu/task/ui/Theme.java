package seedu.task.ui;
//@@author A0142487Y
public enum Theme {
    Default("Default"), Dark("Dark"), Light("Light");

    private final String themeDescription;

    private Theme(String description) {
        themeDescription = description;
    }

    /**
     * Returns a {@code Theme enum} for the input {@code themeName} if there is match,
     * Returns a null if there is not a match.
     */
    public static Theme getTheme(String themeName) {
        // TODO Auto-generated method stub
        for (Theme t : Theme.values()) {
            if (t.themeDescription.equalsIgnoreCase(themeName.trim())) {
                return t;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Theme is : ").append(themeDescription).append(".");
        return sb.toString();
    }
}
