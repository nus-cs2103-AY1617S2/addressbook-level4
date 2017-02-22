package seedu.address.controller;

import seedu.address.ui.UiManager;
import seedu.address.dispatcher.CommandResult;

/**
 * Created by louis on 21/2/17.
 */
public abstract class Controller {
    final UiManager renderer = UiManager.getInstance();

    abstract CommandResult execute(String command);
}
