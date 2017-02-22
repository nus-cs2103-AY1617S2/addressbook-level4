package seedu.address.dispatcher;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.controller.AddTaskController;
import seedu.address.controller.AppController;
import seedu.address.controller.Controller;

import java.util.List;

/**
 * Created by louis on 21/2/17.
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
    private final Controller[] controllerList = getAllControllers();

    private CommandDispatcher() {}

    public void dispatch(String command) {
        final Controller controller = getBestFitController(command);
        final CommandResult feedbackToUser = controller.execute(command);
        eventsCenter.post(new NewResultAvailableEvent(feedbackToUser.getFeedbackToUser()));
    }

    private Controller getBestFitController(String command) {
        if (command == "") {
            return controllerList[0];
        }
        return controllerList[1];
    }

    private Controller[] getAllControllers() {
        return new Controller[] {
                new AppController(),
                new AddTaskController()
        };
    }
}
