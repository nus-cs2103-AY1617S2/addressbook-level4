package seedu.onetwodo.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.model.DoneStatus;
import seedu.onetwodo.model.tag.Tag;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Priority;
import seedu.onetwodo.model.task.StartDate;

/**
 * Lists all tasks in the todo list to the user.
 */
public class ListCommand extends Command {

    private static final String DATES_ARE_INVALID = "Dates are invalid";
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks by done status"
            + "Parameters: DONE_STATUS [DATE] [PRIORITY] [t/TAG]...\n";

    private DoneStatus doneStatus;
    private EndDate before;
    private StartDate after;
    private Priority priority;
    private Set<Tag> tagSet;

    public ListCommand(String doneString, String beforeDate, String afterDate,
            String priorityString, Set<String> tags) throws IllegalValueException {
        assert doneString != null;
        switch (doneString) {
        case DoneStatus.DONE_STRING: // view done tasks
            doneStatus = DoneStatus.DONE;
            break;
        case DoneStatus.UNDONE_STRING:
            doneStatus = DoneStatus.UNDONE;
            break;
        case DoneStatus.ALL_STRING: // view all tasks
            doneStatus = DoneStatus.ALL;
            break;
        }
        before = new EndDate(beforeDate);
        after = new StartDate(afterDate);
        if (before.hasDate() && after.hasDate() && before.getLocalDateTime().isBefore(after.getLocalDateTime())) {
            throw new IllegalValueException(DATES_ARE_INVALID);
        }
        priority = new Priority(priorityString);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.tagSet = tagSet;
    }

    @Override
    public CommandResult execute() {
        switch (doneStatus) {
        case ALL:
            model.setDoneStatus(DoneStatus.ALL);
            break;
        case DONE:
            model.setDoneStatus(DoneStatus.DONE);
            break;
        case UNDONE:
        default:
            model.setDoneStatus(DoneStatus.UNDONE);
        }
        model.resetSearchStrings();
        model.updateByDoneDatePriorityTags(before, after, priority, tagSet);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
