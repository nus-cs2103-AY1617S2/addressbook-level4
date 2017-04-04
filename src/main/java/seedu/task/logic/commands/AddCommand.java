package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Instruction;
import seedu.task.model.task.Priority;
import seedu.task.model.task.Task;
import seedu.task.model.task.Title;
import seedu.task.model.task.UniqueTaskList;


/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TITLE [for: DATE] [priority: PRIORITY] [note: INSTRUCTION] [#TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Buy groceries for: today priority: 3 note: eggs x3, bread x2 #chores #everydayToDos";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(
            String title,
            Optional<Deadline> date,
            Optional<Priority> priority,
            Optional<Instruction> instruction,
            Set<String> tags
    ) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        Deadline finalDeadline = date.isPresent()
                ? date.get()
                        : new Deadline(Deadline.DATE_STRING_DEFAULT_VALUE);
        Priority finalPriority = priority.isPresent()
                ? priority.get()
                        : new Priority(Priority.PRIORITY_LEVEL_DEFAULT_VALUE);
        Instruction finalInstruction = instruction.isPresent()
                ? instruction.get()
                        : new Instruction(Instruction.DEFAULT_VALUE);
        this.toAdd = new Task(
                new Title(title),
                finalDeadline,
                finalPriority,
                finalInstruction,
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            highlight(toAdd);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
