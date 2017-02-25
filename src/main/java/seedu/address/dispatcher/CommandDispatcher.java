package seedu.address.dispatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.controller.AppController;
import seedu.address.controller.Controller;
import seedu.address.controller.StoreController;
import seedu.address.controller.TaskController;

/**
 * CommandDispatcher is the bridge between the UI input & Controller
 * It acts like a Router in a MVC. From the input, it deduces what is the appropriate
 * the controller to dispatch the command to
 */
public class CommandDispatcher {

    private static CommandDispatcher instance;

    public static CommandDispatcher getInstance() {
        if (instance == null) {
            instance = new CommandDispatcher();
        }
        return instance;
    }

    private final EventsCenter eventsCenter = EventsCenter.getInstance();

    private CommandDispatcher() {}

    public void dispatch(String command) {
        final Controller controller = getBestFitController(command);
        final CommandResult feedbackToUser = controller.execute(command);
        eventsCenter.post(new NewResultAvailableEvent(feedbackToUser.getFeedbackToUser()));
    }

    private Controller getBestFitController(String command) {
        return getAllControllers()
                .stream()
                .filter(controller -> controller.matchesCommand(command))
                .findFirst()
                .orElse(new AppController()); // fail-safe
    }

    private Collection<Controller> getAllControllers() {
        return new ArrayList<>(Arrays.asList(new Controller[] {
            new TaskController(),
            new StoreController(),
            new AppController()
        }));
    }
}
