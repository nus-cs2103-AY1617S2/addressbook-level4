package seedu.taskboss.logic.commands;

import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String COMMAND_WORD_SHORT = "x";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting TaskBoss as requested ...";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
