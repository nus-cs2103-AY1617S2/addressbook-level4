//@@author A0139399J
package seedu.doit.model;

import seedu.doit.commons.core.CommandSettings;
import seedu.doit.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;

    private CommandSettings commandSettings;

    public UserPrefs() {
        this.setGuiSettings(1100, 650, 150, 40);
        this.commandSettings = CommandSettings.getInstance();
    }

    public GuiSettings getGuiSettings() {
        return this.guiSettings == null ? new GuiSettings() : this.guiSettings;
    }

    // @@author A0138909R
    public CommandSettings getCommandSettings() {
        if (this.commandSettings == null) {
            this.commandSettings = CommandSettings.getInstance();
        }
        CommandSettings.setInstance(this.commandSettings);
        return this.commandSettings;
    }

    // @@author
    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    // @@author A0138909R
    public void updateCommandSetting(CommandSettings commandSettings) {
        this.commandSettings = commandSettings;
    }

    // @@author
    public void setGuiSettings(double width, double height, int x, int y) {
        this.guiSettings = new GuiSettings(width, height, x, y);
    }

    // @@author A0138909R
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UserPrefs)) {
            return false;
        }
        UserPrefs other = (UserPrefs) obj;
        if (this.commandSettings == null) {
            if (other.commandSettings != null) {
                return false;
            }
        } else if (!this.commandSettings.equals(other.commandSettings)) {
            return false;
        }
        if (this.guiSettings == null) {
            if (other.guiSettings != null) {
                return false;
            }
        } else if (!this.guiSettings.equals(other.guiSettings)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.commandSettings == null) ? 0 : this.commandSettings.hashCode());
        result = (prime * result) + ((this.guiSettings == null) ? 0 : this.guiSettings.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return this.guiSettings.toString() + "\n" + this.getCommandSettings().toString();
    }
    // @@author
}
