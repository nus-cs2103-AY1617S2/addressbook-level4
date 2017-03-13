package seedu.doit.model;

import java.util.Objects;

import seedu.doit.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;

    public UserPrefs() {
        this.setGuiSettings(700, 1100, 200, 8);
    }

    public GuiSettings getGuiSettings() {
        return this.guiSettings == null ? new GuiSettings() : this.guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        this.guiSettings = new GuiSettings(width, height, x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(this.guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.guiSettings);
    }

    @Override
    public String toString() {
        return this.guiSettings.toString();
    }

}
