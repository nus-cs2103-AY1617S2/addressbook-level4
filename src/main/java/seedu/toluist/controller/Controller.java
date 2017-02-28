package seedu.toluist.controller;

import java.util.HashMap;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.storage.JsonStorage;
import seedu.toluist.storage.Storage;
import seedu.toluist.ui.UiManager;
import seedu.toluist.ui.UiStore;


/**
 * Abstract Controller class
 * Controllers are in charge of receiving the input from the UI,
 * modifies the models as appropriate, and render the updated UI subsequently
 */
public abstract class Controller {

    /**
     * Persistent storage to interact with the models
     */
    protected final Storage storage = JsonStorage.getInstance();

    /**
     * Default renderer used to update the UI
     */
    protected final UiManager renderer = UiManager.getInstance();

    /**
     * UiStore to store data to be used by Ui
     */
    protected final UiStore uiStore = UiStore.getInstance();

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
