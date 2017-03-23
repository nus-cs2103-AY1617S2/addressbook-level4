package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Completion;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Start;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Adds a task to the ToDoApp.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the ToDoApp. "
            + "Parameters: NAME [d/DEADLINE] [p/PRIORITY] [t/TAG] [n/NOTES]...\n"
            + "Example: " + COMMAND_WORD
            + " Buy Printer";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the ToDoApp";

    private final Task toAdd;
    private final int idx; // Optional adding of index

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String start, String deadline,
                        Integer priority, Set<String> tags, String notes, String completion, int idx)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Start(start),
                new Deadline(deadline),
                new Priority(priority),
                new UniqueTagList(tagSet),
                new Notes(notes),
                new Completion(completion)
        );
        this.idx = idx;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            if (this.idx >= 0) {
                model.addTask(toAdd, idx);
            } else {
                model.addTask(toAdd);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
