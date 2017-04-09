package seedu.today.logic.commands;

import seedu.today.commons.core.EventsCenter;
import seedu.today.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting TODAY as requested ...";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "Exiting TODAY...";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, MESSAGE_SUCCESS_STATUS_BAR);
    }

}
