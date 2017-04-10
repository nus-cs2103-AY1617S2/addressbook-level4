package seedu.task.model;

import java.util.Objects;

import seedu.task.commons.core.GuiSettings;
import seedu.task.ui.Theme;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    public Theme theme;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    // public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
    // updateLastUsedGuiSetting(guiSettings, this.theme);
    // }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings, Theme... theme) {
        this.guiSettings = guiSettings;
        if (theme.length != 0) {
            this.theme = theme[0];
        }
    }

    // Default UserPrefs has the Theme.Default.
    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
        this.theme = Theme.Default;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    // @@author A0142487Y
    public void setTheme(String themeName) {
        this.theme = Theme.getTheme(themeName);
    }

    public Theme getTheme() {
        return this.theme;
    }

    // @@author
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { // this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(guiSettings.toString());
        sb.append(theme.toString());
        return sb.toString();
    }

}
