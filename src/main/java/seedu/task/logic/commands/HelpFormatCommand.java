package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ShowHelpFormatRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpFormatCommand extends Command {

    public static final String COMMAND_WORD_1 = "helpf";
    public static final String COMMAND_WORD_2 = "helpformat";
    public static final String COMMAND_WORD_3 = "summary";
    public static final String COMMAND_WORD_4 = "hf";


    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Shows summary of commands.\n"
            + "Example: " + COMMAND_WORD_1;

    public static final String SHOWING_HELP_SUMMARY_MESSAGE = "Opened summary window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpFormatRequestEvent());
        return new CommandResult(SHOWING_HELP_SUMMARY_MESSAGE);
    }
}
