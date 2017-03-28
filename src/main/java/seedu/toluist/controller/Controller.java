//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;

import seedu.toluist.ui.UiStore;


/**
 * Abstract Controller class
 * Controllers are in charge of receiving the input from the UI,
 * modifies the models as appropriate, and render the updated UI subsequently
 */
public abstract class Controller {
    /**
     * UiStore to store data to be used by Ui
     */
    protected final UiStore uiStore = UiStore.getInstance();

    /**
     * Given a command string, execute the command
     * and modifies the data appropriately. Also optionally
     * update the UI
     * @param command
     */
    public abstract void execute(String command);

    /**
     * Given command string, tokenize the string into
     * a dictionary of tokens
     * @param command
     * @return
     */
    public abstract HashMap<String, String> tokenize(String command);

    /**
     * Check if Controller can handle this command
     * @param command
     * @return
     */
    public abstract boolean matchesCommand(String command);

    /**
     * Returns command word(s) used by controller
     */
    public static String[] getCommandWords() {
        return new String[] {};
    }
}
