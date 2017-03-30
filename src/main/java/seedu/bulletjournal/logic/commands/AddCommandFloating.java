//@@author A0146738U

package seedu.bulletjournal.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.logic.commands.exceptions.CommandException;
import seedu.bulletjournal.model.tag.Tag;
import seedu.bulletjournal.model.tag.UniqueTagList;
import seedu.bulletjournal.model.task.BeginDate;
import seedu.bulletjournal.model.task.DueDate;
import seedu.bulletjournal.model.task.Status;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.model.task.TaskName;
import seedu.bulletjournal.model.task.UniqueTaskList;

/**
 * Adds a task to the address book.
 */
public class AddCommandFloating extends Command {

    public static final String COMMAND_WORD = "addf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a floating task to the todo list. "
            + "Parameters: TASKNAME s/STATUS [t/TAG]...\n" + "Example: " + COMMAND_WORD
            + " Join groups s/undone t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New floating task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This floating task already exists in the todo list";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommandFloating(String taskname, String duedate, String status, String begindate, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(new TaskName(taskname), new DueDate(duedate), new Status(status),
                new BeginDate(begindate), new UniqueTagList(tagSet));
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
