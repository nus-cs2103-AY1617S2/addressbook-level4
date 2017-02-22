package seedu.address.controller;

import seedu.address.ui.UiManager;
import seedu.address.dispatcher.CommandResult;

import java.util.HashMap;

/**
 * Created by louis on 21/2/17.
 */
public abstract class Controller {
    final UiManager renderer = UiManager.getInstance();

    abstract public CommandResult execute(String commandArgs);

    public HashMap<String, String> tokenize(String commandArgs) {
        return new HashMap<>();
    }

    public String getCommandWord() {
        return new String("");
    }
}
