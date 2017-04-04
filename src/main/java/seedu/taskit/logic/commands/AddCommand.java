// @@author A0163996J

package seedu.taskit.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.logic.commands.exceptions.CommandException;
import seedu.taskit.model.tag.Tag;
import seedu.taskit.model.tag.UniqueTagList;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.Priority;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.Title;
import seedu.taskit.model.task.UniqueTaskList;


/**
 * Add a new task to TaskIt
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to TaskIt. "
            + "Parameters: TITLE [tag TAG][from START to END][by End][priority PRIORITY]...\n"
            + "Example: " + COMMAND_WORD
            + " Finish SWE project by 11pm tag school priority high";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in TaskIt";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String title, String start, String end, String priority, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Title(title),
                new Date(start),
                new Date(end),
                new Priority(priority),
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

    //@@author A0141011J
    @Override
    public boolean isUndoable() {
        return true;
    }

}
