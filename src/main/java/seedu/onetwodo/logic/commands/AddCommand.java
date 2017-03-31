package seedu.onetwodo.logic.commands;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.DoneStatus;
import seedu.onetwodo.model.tag.Tag;
import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.Description;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Name;
import seedu.onetwodo.model.task.Priority;
import seedu.onetwodo.model.task.Recurring;
import seedu.onetwodo.model.task.StartDate;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.TaskAttributesChecker;
import seedu.onetwodo.model.task.UniqueTaskList;

/**
 * Adds a person to the todo list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the todo list. "
            + "Parameters: NAME  s/START_DATE  e/END_DATE r/RECUR p/PRIORITY d/DESCRIPTION  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " Take nap s/03-03-2018 17:00 e/03-03-2018 21:00 r/daily"
            + "d/tonight don't need to sleep already t/nap t/habbit";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the todo list";

    private final Task toAdd;
    private final LocalDateTime dateCreated;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String startDate, String endDate, String recur,
            String priority, String description, Set<String> tags) throws IllegalValueException {

        dateCreated = LocalDateTime.now().withSecond(0).withNano(0);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(new Name(name), new StartDate(startDate), new EndDate(endDate),
                new Recurring(recur), new Priority(priority),
                new Description(description), new UniqueTagList(tagSet));
        TaskAttributesChecker.checkValidAttributes(toAdd, dateCreated);
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            handleDoneStatus();
            model.jumpToNewTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

    private void handleDoneStatus() {
        if (model.getDoneStatus() == DoneStatus.DONE) {
            model.setDoneStatus(DoneStatus.UNDONE);
        }
        model.updateByDoneStatus();
    }

}
