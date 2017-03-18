package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.EventsCenter;

import seedu.address.commons.events.ui.ChangeViewRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Status;

/**
 * Change the view of UI.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the view of UI."
        + "e.g. view [all|calendar|done|floating|overdue|today|tomorrow|future]";

    public static final String MESSAGE_SUCCESS = "View changed to %s";
    public static final String MESSAGE_ERROR = "Invalid input, allowed input: all|calendar|done|"
        + "floating|overdue|today|tomorrow|future";

    public static final String ALL = "All";
    public static final String CALENDAR = "Calendar";
    public static final String EMPTY = "";
    public static final int NOT_FOUND = -1;

    public static final String[] VIEW_GROUPS = {
        Status.DONE, Status.FLOATING, Status.OVERDUE, Status.TODAY, Status.TOMORROW, Status.IN_FUTURE,
        ALL, CALENDAR
    };

    public final ArrayList<String> viewGroups;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public ViewCommand(String[] params) throws IllegalValueException {
        this.viewGroups = new ArrayList<String>();
        for (String param : params) {
            if (param.equals(EMPTY)) {
                continue;
            }
            int index = getIndex(param);
            if (index == NOT_FOUND) {
                throw new IllegalValueException(MESSAGE_ERROR);
            }
            this.viewGroups.add(VIEW_GROUPS[index]);
        }
    }

    int getIndex(String param) {
        int index = 0;
        for (String viewGroup : VIEW_GROUPS) {
            if (param.toLowerCase().equals(viewGroup.toLowerCase())) {
                return index;
            }
            index++;
        }
        return NOT_FOUND;
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeViewRequestEvent(viewGroups));
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("|", viewGroups)));
    }

}
