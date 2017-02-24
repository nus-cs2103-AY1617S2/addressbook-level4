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
     * Given a command argument string, execute the command
     * and modifies the data appropriately. Also optionally
     * update the UI
     * @param commandArgs
     * @return
     */
    public abstract CommandResult execute(String commandArgs);

    /**
     * Given command argument strings, tokenize the string into
     * a dictionary of tokens
     * @param commandArgs
     * @return
     */
    protected HashMap<String, String> tokenize(String commandArgs) {
        return new HashMap<>();
    }

    /**
     * Get signature command phrase for the Controller
     * @return
     */
    public String getCommandWord() {
        return new String("");
    }
}
