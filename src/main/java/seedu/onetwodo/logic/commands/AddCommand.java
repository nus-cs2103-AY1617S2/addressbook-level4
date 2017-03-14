package seedu.onetwodo.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.tag.Tag;
import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Description;
import seedu.onetwodo.model.task.Name;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.StartDate;
import seedu.onetwodo.model.task.UniqueTaskList;

/**
 * Adds a person to the todo list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    // TODO: Update add messages when add more options
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the todo list. "
            + "Parameters: NAME  s/START_DATE  e/END_DATE  d/DESCRIPTION  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Take a nap s/03 Mar 2017 17:00 e/03 Mar 2017 21:00 d/tonight don't need to sleep alr t/nap t/habbit";

    public static final String MESSAGE_MISSING_DATE = "A task with a start date needs an end date.";
    public static final String MESSAGE_SUCCESS = "Added task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the todo list";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startDate, String endDate, String description, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new StartDate(startDate),
                new EndDate(endDate),
                new Description(description),
                new UniqueTagList(tagSet)
        );
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
