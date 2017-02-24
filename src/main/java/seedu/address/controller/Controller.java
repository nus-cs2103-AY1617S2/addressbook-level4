package seedu.address.controller;

import java.util.HashMap;

import seedu.address.dispatcher.CommandResult;
import seedu.address.ui.UiManager;


/**
 * Abstract Controller class
 * Controllers are in charge of receiving the input from the UI,
 * modifies the models as appropriate, and render the updated UI subsequently
 */
public abstract class Controller {

    /**
     * Default renderer used to update the UI
     */
    protected final UiManager renderer = UiManager.getInstance();

    /**
     * Given a command string, execute the command
     * and modifies the data appropriately. Also optionally
     * update the UI
     * @param command
     * @return
     */
    public abstract CommandResult execute(String command);

    /**
     * Given command string, tokenize the string into
     * a dictionary of tokens
     * @param command
     * @return
     */
    protected HashMap<String, String> tokenize(String command) {
        return new HashMap<>();
    }

    /**
     * Check if Controller can handle this command
     * @param command
     * @return
     */
    public boolean matchesCommand(String command) {
        return true;
    }
}
