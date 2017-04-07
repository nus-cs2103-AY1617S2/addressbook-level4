package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartEndDateTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: NAME  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " exam from 10 Jan 8pm to 10 Jan 10pm t/CS2010";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    //@@author A0140023E
    /**
     * Creates an AddCommand using raw values except {@code deadline} and {@code startEndDateTime}
     * that must be pre-initialized.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String nameArgs, Optional<Deadline> deadline,
            Optional<StartEndDateTime> startEndDateTime, Set<String> tags) throws IllegalValueException {
        toAdd = new Task(new Name(nameArgs), deadline, startEndDateTime, initTagList(tags));
    }

    /**
     * Returns initialized tags as a {@link UniqueTagList}
     * @throws IllegalValueException if there is a tag name that is invalid in the given tags set
     */
    private UniqueTagList initTagList(Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }


    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            //@@author A0148052L
            model.pushCommand(COMMAND_WORD);
            model.pushStatus(model.getTaskList());
            //@@author
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

}
