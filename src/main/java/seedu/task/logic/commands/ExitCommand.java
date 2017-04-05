package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD_1 = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting KIT as requested ...";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + "Exits KIT.\n"
            + "Example: " + COMMAND_WORD_1;

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
