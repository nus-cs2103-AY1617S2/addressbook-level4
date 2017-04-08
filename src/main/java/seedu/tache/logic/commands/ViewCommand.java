//@@author A0142255M

package seedu.tache.logic.commands;

import seedu.tache.commons.core.EventsCenter;
import seedu.tache.commons.events.ui.CalendarViewRequestEvent;

/**
 * Shows events (timed tasks) in the calendar in a day / week / month view.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String SHORT_COMMAND_WORD = "v";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Navigates calendar to a day / week / month view.\n"
            + "Example: " + COMMAND_WORD + "week";

    public static final String MESSAGE_SUCCESS = "Calendar view switched to %s view.";

    private String view;

    public ViewCommand(String view) {
        assert view != null;
        this.view = view;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new CalendarViewRequestEvent(view));
        return new CommandResult(String.format(MESSAGE_SUCCESS, view));
    }

}

