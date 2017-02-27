package seedu.address.dispatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.controller.ListController;
import seedu.address.controller.Controller;
import seedu.address.controller.StoreController;
import seedu.address.controller.TaskController;

public class CommandDispatcher implements Dispatcher {

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
                .orElse(new ListController()); // fail-safe
    }

    private Collection<Controller> getAllControllers() {
        return new ArrayList<>(Arrays.asList(new Controller[] {
            new TaskController(),
            new StoreController(),
            new ListController()
        }));
    }
}
