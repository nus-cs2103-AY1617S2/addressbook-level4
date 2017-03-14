package seedu.ezdo.logic.commands;

import seedu.ezdo.commons.core.EventsCenter;
import seedu.ezdo.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class QuitCommand extends Command {

    public static final String COMMAND_WORD = "quit";
    public static final String SHORT_COMMAND_WORD = "q";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting EzDo as requested ...";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
