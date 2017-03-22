package seedu.taskboss.logic.commands;


import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_WORD_SHORT = "h";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT
            + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD + " || " + COMMAND_WORD_SHORT;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
