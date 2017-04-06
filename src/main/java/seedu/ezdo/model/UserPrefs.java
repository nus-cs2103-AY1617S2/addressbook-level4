package seedu.ezdo.model;

import java.util.Objects;

import seedu.ezdo.commons.core.GuiSettings;
import seedu.ezdo.commons.exceptions.AliasAlreadyInUseException;
import seedu.ezdo.commons.exceptions.CommandDoesNotExistException;
import seedu.ezdo.logic.CommandAliases;
import seedu.ezdo.model.todo.UniqueTaskList;
import seedu.ezdo.model.todo.UniqueTaskList.SortCriteria;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;
    public SortCriteria sortCriteria;
    public Boolean isSortedAscending;
    public CommandAliases commandAliases;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
        this.sortCriteria = UniqueTaskList.SortCriteria.NAME;
        this.isSortedAscending = true;
        this.commandAliases = new CommandAliases();
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }
  //@@author A0139248X
    public void updateLastUsedSortCriteria(SortCriteria sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public SortCriteria getSortCriteria() {
        return sortCriteria;
    }

    //@@author A0138907W

    /**
     * Adds a command alias to user preferences.
     * @param command The command to alias.
     * @param alias The alias that will execute the given command.
     * @throws AliasAlreadyInUseException when the alias is already in use by an ezDo command.
     * @throws CommandDoesNotExistException if the given command does not exist and thus cannot be aliased.
     */
    public void addCommandAlias(String command, String alias) throws AliasAlreadyInUseException,
        CommandDoesNotExistException {
        commandAliases.addAlias(command, alias);
    }

    /**
     * Removes all previously-specified command aliases.
     */
    public void clearCommandAliases() {
        commandAliases.clearAliases();
    }

    /**
     * Gets the user's command aliases.
     */
    public CommandAliases getCommandAliases() {
        return commandAliases;
    }

    /**
     * Updates the sorting order according to {@code isSortedAscending}.
      * @param isSortedAscending A true value represents an ascending sort order, wheras a false value represents a descending
     *                           sort order.
     */
    public void updateLastUsedIsSortedAscending(Boolean isSortedAscending) {
        this.isSortedAscending = isSortedAscending;
    }

    /**
     * Get the current sort order.
     * @return true if the sort order is ascending. Returns false if the sort order is descending.
     */
    public boolean getIsSortedAscending() {
        return isSortedAscending;
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
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
        return guiSettings.toString();
    }

}
