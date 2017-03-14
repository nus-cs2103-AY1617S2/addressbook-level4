package seedu.task.logic.commands;


import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ShowHelpFormatRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpFormatCommand extends Command {

    public static final String COMMAND_WORD = "helpf";
    public static final String COMMAND_WORD_FULL = "helpformat";
    public static final String COMMAND_WORD_SUMMARY = "summary";
    public static final String COMMAND_WORD_SHORT = "hf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows summary of commands.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_SUMMARY_MESSAGE = "Opened summary window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpFormatRequestEvent());
        return new CommandResult(SHOWING_HELP_SUMMARY_MESSAGE);
    }
}
