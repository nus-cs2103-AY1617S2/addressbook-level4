package seedu.address.dispatcher;

/**
 * Dispatcher is the bridge between the UI input & Controller
 * It acts like a Router in a MVC. From the input, it deduces what is the appropriate
 * the controller to dispatch the command to
 */
public interface Dispatcher {
    void dispatch(String command);
}
