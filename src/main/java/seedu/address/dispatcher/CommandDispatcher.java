package seedu.address.dispatcher;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.controller.AddTaskController;

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

    private CommandDispatcher() {}

    public void dispatch(String command) {
        final CommandResult feedbackToUser = new AddTaskController().execute(command);
        eventsCenter.post(new NewResultAvailableEvent(feedbackToUser.getFeedbackToUser()));
    }
}
