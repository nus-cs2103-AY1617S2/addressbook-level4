package seedu.onetwodo.logic.commands;

import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.events.ui.ShowHelpUGRequestEvent;

/**
 * Format command to display Userguide.
 */
public class HelpUGCommand extends Command {
    public static final String COMMAND_WORD = "helpug";
    public static final String COMMAND_WORD_FULL = "helpuserguide";
    public static final String COMMAND_WORD_SHORT = "ug";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n" + "Example: "
            + COMMAND_WORD;

    public static final String SHOWING_HELP_UG_MESSAGE = "Opened user guide.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpUGRequestEvent());
        return new CommandResult(SHOWING_HELP_UG_MESSAGE);
    }
}
