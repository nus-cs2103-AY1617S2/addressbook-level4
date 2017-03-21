package seedu.toluist.dispatcher;

/**
 * Dispatcher is the bridge between the UI input & Controller
 * It acts like a Router in a MVC. From the input, it deduces what is the appropriate
 * the controller to dispatch the command to
 */
public abstract class Dispatcher {
    public abstract void dispatch(String command);
}
