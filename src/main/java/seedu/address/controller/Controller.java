package seedu.address.controller;

import seedu.address.ui.UiManager;

/**
 * Created by louis on 21/2/17.
 */
public abstract class Controller {
    final UiManager renderer = UiManager.getInstance();
    
    abstract void execute(String command);
}
