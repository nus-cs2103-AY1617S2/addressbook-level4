package seedu.doist.logic.commands;

import seedu.doist.commons.core.EventsCenter;
import seedu.doist.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String DEFAULT_COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = DEFAULT_COMMAND_WORD
            + ": Shows program usage instructions.\n"
            + "Example: " + DEFAULT_COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
