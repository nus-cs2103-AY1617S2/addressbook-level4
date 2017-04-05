//@@author A0142255M

package seedu.tache.logic.commands;

import seedu.tache.commons.core.EventsCenter;
import seedu.tache.commons.events.ui.CalendarPreviousRequestEvent;

/**
 * Displays the previous day / week/ month at the calendar.
 */
public class PrevCommand extends Command {

    public static final String COMMAND_WORD = "prev";
    public static final String SHORT_COMMAND_WORD = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" +
            "Displays the previous day/week/month at the calendar.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Previous day/week/month displayed at the calendar.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new CalendarPreviousRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}

