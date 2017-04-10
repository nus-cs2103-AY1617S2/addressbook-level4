package seedu.watodo.logic.commands;

import java.util.Optional;
import java.util.Set;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.logic.parser.ParserUtil;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.TaskType;
import seedu.watodo.model.task.UniqueTaskList;
import seedu.watodo.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.watodo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TASK [by/ DATETIME] [on/ DATETIME] [from/ START_DATETIME] [to/ END_DATE_TIME] [#TAG]...\n"
            + "Example: " + COMMAND_WORD + " read Lord of the rings by/ next thurs #personal";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private Task toAdd;

    private Task undoAdd;

    //@@author A0143076J
    /**
     * Creates an AddCommand using raw values and create a new Task according to the taskType
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String description, Optional<String> startDate, Optional<String> endDate,
            Set<String> tags, TaskType taskType) throws IllegalValueException {

        assert description != null;
        assert taskType.equals(TaskType.FLOAT) || taskType.equals(TaskType.DEADLINE) || taskType.equals(TaskType.EVENT);

        final UniqueTagList tagSet = ParserUtil.parseTags(tags);

        switch (taskType) {
        case FLOAT:
            this.toAdd = new Task(new Description(description), tagSet);
            break;
        case DEADLINE:
            this.toAdd = new Task(new Description(description), new DateTime(endDate.get()), tagSet);
            break;
        case EVENT:
            DateTime start = new DateTime(startDate.get());
            DateTime end = new DateTime(endDate.get());
            if (start.isLater(end)) { //checks if the end time is later than start time
                throw new IllegalValueException(DateTime.MESSAGE_DATETIME_START_LATER_THAN_END);
            }
            this.toAdd = new Task(new Description(description), start, end, tagSet);
            break;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            this.undoAdd = new Task(toAdd);
            model.addTask(toAdd);
            selectLastTask();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    //@@author A0139845R
    @Override
    public void unexecute() {
        assert model != null;
        try {
            model.updateFilteredListToShowAll();
            model.deleteTask(undoAdd);
        } catch (TaskNotFoundException e) {
            System.out.println(undoAdd);
        }
    }

    @Override
    public void redo() {
        assert model != null;
        try {
            model.updateFilteredListToShowAll();
            model.addTask(undoAdd);
            selectLastTask();
        } catch (DuplicateTaskException e) {

        }
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

}
