package seedu.onetwodo.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.model.DoneStatus;
import seedu.onetwodo.model.SortOrder;
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
    public static final String MESSAGE_LIST_DONE_SUCCESS = "Listed completed tasks";
    public static final String MESSAGE_LIST_UNDONE_SUCCESS = "Listed incompleted tasks";
    public static final String MESSAGE_LIST_ALL_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks by done status"
            + "Parameters: [DONE_STATUS] [DATE] [p/PRIORITY] [t/TAG]...\n";

    private DoneStatus doneStatus;
    private EndDate before;
    private StartDate after;
    private Priority priority;
    private Set<Tag> tagSet;
    private SortOrder sortOrder;

    public ListCommand(String doneString, String beforeDate, String afterDate,
            String priorityString, Set<String> tags, String order) throws IllegalValueException {
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
        sortOrder = SortOrder.getSortOrder(order);
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
        String feecbackMessageToReturn;
        switch (doneStatus) {
        case ALL:
            model.setDoneStatus(DoneStatus.ALL);
            feecbackMessageToReturn = MESSAGE_LIST_ALL_SUCCESS;
            break;
        case DONE:
            model.setDoneStatus(DoneStatus.DONE);
            feecbackMessageToReturn = MESSAGE_LIST_DONE_SUCCESS;
            break;
        case UNDONE:
        default:
            model.setDoneStatus(DoneStatus.UNDONE);
            feecbackMessageToReturn = MESSAGE_LIST_UNDONE_SUCCESS;
        }
        if (sortOrder != SortOrder.NONE) {
            model.sortBy(sortOrder);
        }
        model.resetSearchStrings();
        model.updateByDoneDatePriorityTags(before, after, priority, tagSet);
        return new CommandResult(feecbackMessageToReturn);
    }
}
