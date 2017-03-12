package seedu.toluist.controller;

import java.util.HashMap;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;


/**
 * Abstract Controller class
 * Controllers are in charge of receiving the input from the UI,
 * modifies the models as appropriate, and render the updated UI subsequently
 */
public abstract class Controller {

    protected final Ui renderer;

    /**
     * UiStore to store data to be used by Ui
     */
    protected final UiStore uiStore = UiStore.getInstance();

    public Controller(Ui renderer) {
        this.renderer = renderer;
    }

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

    public static String[] getCommandWords() {
        return new String[] {};
    }
}
