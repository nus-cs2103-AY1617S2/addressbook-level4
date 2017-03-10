package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Instruction;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;


/**
 * Redo a task in Burdens.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo previous undone action ";
    
    public static final String MESSAGE_SUCCESS = "Redone";
    public static final String MESSAGE_DUPLICATE_TASK = "No previous command/invalid action";

    private final Task toAdd;

    /**
     * Creates a Redo Command using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public RedoCommand(String title, Optional<Deadline> date, String priority, String instruction, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        toAdd = null;
        //Where is the hashset stored?
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
