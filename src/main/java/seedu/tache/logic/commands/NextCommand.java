//@@author A0142255M

package seedu.tache.logic.commands;

import seedu.tache.commons.core.EventsCenter;
import seedu.tache.commons.events.ui.CalendarNextRequestEvent;

/**
 * Displays the next day / week/ month at the calendar.
 */
public class NextCommand extends Command {

    public static final String COMMAND_WORD = "next";
    public static final String SHORT_COMMAND_WORD = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" +
            "Displays the next day/week/month at the calendar.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Next day/week/month displayed at the calendar.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new CalendarNextRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}

