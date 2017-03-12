package seedu.tasklist.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.exceptions.CommandException;
import seedu.tasklist.model.tag.Tag;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.Status;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: TASK NAME c/COMMENT  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Do this c/updated comment here t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    public static final int DEADLINE_SIZE = 1;
    public static final int EVENT_SIZE = 2;

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, Optional<List<Date>> dates, Optional<String> comment,
            Optional<String> priority, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        //Checks if it is a FloatingTask
        if (!isDatePresent(dates)) {
            this.toAdd = new FloatingTask(
                    new Name(name),
                    new Comment(comment),
                    new Priority(priority),
                    new Status(),
                    new UniqueTagList(tagSet)
            );
        } else {
            List<Date> dateList = dates.get();
            if (isDeadline(dateList)) {
                this.toAdd = new DeadlineTask(
                        new Name(name),
                        new Comment(comment),
                        new Priority(priority),
                        new Status(),
                        getDeadline(dateList),
                        new UniqueTagList(tagSet)
                        );
            } else if (isEvent(dateList)) {
                this.toAdd = new EventTask(
                        new Name(name),
                        new Comment(comment),
                        new Priority(priority),
                        new Status(),
                        getStartDate(dateList),
                        getEndDate(dateList),
                        new UniqueTagList(tagSet)
                        );
            } else {
                //Should never reach here. Temporary this until replaced with exception
                this.toAdd = null;
            }
        }
    }

    /**
     * Returns true if dates are present. Used to check for FloatingTask
     */
    public boolean isDatePresent(Optional<List<Date>> dates) {
        return dates.isPresent();
    }

    /**
     * Returns true if task added is a Deadline
     */
    public boolean isDeadline(List<Date> dates) {
        int dateListSize = dates.size();
        return (dateListSize == DEADLINE_SIZE);
    }

    /**
     * Returns true if task added is an Event
     */
    public boolean isEvent(List<Date> dates) {
        int dateListSize = dates.size();
        return (dateListSize == EVENT_SIZE);
    }

    /**
     * Returns Deadline
     */
    public Date getDeadline(List<Date> dates) {
        return dates.get(0);
    }

    /**
     * Returns Starting Date for an event
     */
    public Date getStartDate(List<Date> dates) {
        return dates.get(0);
    }

    /**
     * Returns End Date for an event
     */
    public Date getEndDate(List<Date> dates) {
        return dates.get(1);
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
