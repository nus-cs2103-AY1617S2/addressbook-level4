package seedu.task.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.Model;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.CompletionStatus;
import seedu.task.model.task.EndTime;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

//@@author A0146789H
/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: DESCRIPTION from START_DATE to END_DATE #TAGS\n"
            + "Example: " + COMMAND_WORD
            + " Do CS2103 tutorial from 03/06/17 to 03/08/17 #CS2103 #uni";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private Task toAdd;

    public AddCommand(){
    }

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, Date startDate, Date endDate, boolean completionStatus, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new StartTime(startDate),
                new EndTime(endDate),
                new CompletionStatus(completionStatus),
                new UniqueTagList(tagSet)
                );
    }

    //@@author A0146789H
    /**
     * @return the task to be added
     */
    public Task getToAdd() {
        return toAdd;
    }

    //@@author
    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        this.toAdd = new Task(
                new Name(name),
                new StartTime(null),
                new EndTime(null),
                new CompletionStatus(false),
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

    public CommandResult executeUndo(Task previousTask, Model model) throws CommandException {
        try {
            model.addTaskUndo(previousTask);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
