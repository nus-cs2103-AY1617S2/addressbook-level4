package seedu.address.dispatcher;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.controller.*;

import javax.naming.ldap.Control;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by louis on 21/2/17.
 */
public class CommandDispatcher {

    private static CommandDispatcher instance;
    private static String WHITE_SPACE = "\\s+";

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
