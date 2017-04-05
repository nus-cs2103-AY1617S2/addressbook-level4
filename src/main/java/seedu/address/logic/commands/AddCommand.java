package seedu.address.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

//@@author A0144422R
/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a task to the task manager. "
            + "Parameters: NAME [t/TAG]...\n" + "Example: " + COMMAND_WORD
            + " CS2103 Refactoring Task t/CS2103";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_SUCCESS_STATUS_BAR = "New task added successfully.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String tags[]) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName.trim()));
        }
        this.toAdd = new FloatingTask(new Name(name), new UniqueTagList(tagSet),
                false, false);
        this.toAdd.setAnimation(true);
    }

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, Date deadline, String tags[])
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName.trim()));
        }
        System.out.print("deadline: " + deadline);
        this.toAdd = new DeadlineTask(new Name(name), new UniqueTagList(tagSet),
                deadline, false, false);
        this.toAdd.setAnimation(true);
    }

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, Date deadline, Date startingTime,
            String tags[]) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName.trim()));
        }
        this.toAdd = new EventTask(new Name(name), new UniqueTagList(tagSet),
                deadline, startingTime, false, false);
        this.toAdd.setAnimation(true);
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd),
                    MESSAGE_SUCCESS_STATUS_BAR);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }

    // For testing
    public Task getTask() {
        return toAdd;
    }

}
