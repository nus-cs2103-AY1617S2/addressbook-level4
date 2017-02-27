package seedu.toluist.dispatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import seedu.toluist.commons.core.EventsCenter;
import seedu.toluist.commons.events.ui.NewResultAvailableEvent;
import seedu.toluist.controller.Controller;
import seedu.toluist.controller.ListController;
import seedu.toluist.controller.RedoController;
import seedu.toluist.controller.StoreController;
import seedu.toluist.controller.TaskController;
import seedu.toluist.controller.UndoController;

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
            new UndoController(),
            new RedoController(),
            new ListController()
        }));
    }
}
