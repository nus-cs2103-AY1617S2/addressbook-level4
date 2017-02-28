package seedu.toluist.controller;

import seedu.toluist.commons.core.EventsCenter;
import seedu.toluist.commons.events.ui.ExitAppRequestEvent;
import seedu.toluist.dispatcher.CommandResult;

import java.util.logging.Logger;

/**
 * Handle exit command
 */
public class ExitController extends Controller {
    private static final String COMMAND_TEMPLATE = "^exit$";

    public CommandResult execute(String command) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult("");
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }
}
