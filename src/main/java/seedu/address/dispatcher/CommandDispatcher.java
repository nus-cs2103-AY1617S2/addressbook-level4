package seedu.address.dispatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.controller.AddTaskController;
import seedu.address.controller.AppController;
import seedu.address.controller.Controller;
import seedu.address.controller.DeleteTaskController;
import seedu.address.controller.UpdateTaskController;

/**
 * CommandDispatcher is the bridge between the UI input & Controller
 * It acts like a Router in a MVC. From the input, it deduces what is the appropriate
 * the controller to dispatch the command to
 */
public class CommandDispatcher {

    private static CommandDispatcher instance;
    private static final String WHITE_SPACE = "\\s+";

    public static CommandDispatcher getInstance() {
        if (instance == null) {
            instance = new CommandDispatcher();
        }
        return instance;
    }

    private final EventsCenter eventsCenter = EventsCenter.getInstance();
    private final Collection<Controller> controllerCollection = getAllControllers();

    private CommandDispatcher() {}

    public void dispatch(String command) {
        final Controller controller = getBestFitController(command);
        final CommandResult feedbackToUser = controller.execute(getArgs(command));
        eventsCenter.post(new NewResultAvailableEvent(feedbackToUser.getFeedbackToUser()));
    }

    private Controller getBestFitController(String command) {
        final String preamble = getPreamble(command);
        return controllerCollection
                .stream()
                .filter(controller -> controller.getCommandWord().equals(preamble))
                .findFirst()
                .orElse(new AppController()); // fail-safe
    }

    private String getPreamble(String command) {
        return command.split(WHITE_SPACE)[0];
    }

    private String getArgs(String command) {
        final ArrayList<String> commandParts = new ArrayList<>(Arrays.asList(command.split(WHITE_SPACE)));
        commandParts.remove(0);
        return String.join(" ", commandParts);
    }

    private Collection<Controller> getAllControllers() {
        return new ArrayList<>(Arrays.asList(new Controller[] {
            new AppController(),
            new AddTaskController(),
            new DeleteTaskController(),
            new UpdateTaskController()
        }));
    }
}
