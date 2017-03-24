package seedu.toluist.dispatcher;

import java.util.SortedSet;

/**
 * Dispatcher is the bridge between the UI input & Controller
 * It acts like a Router in a MVC. From the input, it deduces what is the appropriate
 * the controller to dispatch the command to
 */
public abstract class Dispatcher {

    /**
     * Dispatch a command from the Ui to a suitable Controller
     * The command will be saved in the dispatcher's command history
     * @param command
     */
    public abstract void dispatchRecordingHistory(String command);

    /**
     * Dispatch a command from the Ui to a suitable Controller.
     * @param command command to be executed
     */
    public abstract void dispatch(String command);

    /**
     * Returns set of possible command extensions from a command
     * @param command a command
     */
    public abstract SortedSet<String> getPredictedCommands(String command);
}
