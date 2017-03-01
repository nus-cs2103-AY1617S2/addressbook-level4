package seedu.toluist.dispatcher;

import seedu.toluist.ui.Ui;

/**
 * Dispatcher is the bridge between the UI input & Controller
 * It acts like a Router in a MVC. From the input, it deduces what is the appropriate
 * the controller to dispatch the command to
 */
public abstract class Dispatcher {
    public abstract void dispatch(Ui renderer, String command);
}
