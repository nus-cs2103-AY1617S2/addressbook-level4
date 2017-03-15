package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "ADD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: TITLE by DEADLINE  [#LABEL]...\n" + "Example: " + COMMAND_WORD
            + " Meet John Doe by 31-10-2017 #friends #owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Creates an AddCommand with no date using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String title, Set<String> labels) throws IllegalValueException {
        final Set<Label> labelSet = new HashSet<>();
        for (String labelName : labels) {
            labelSet.add(new Label(labelName));
        }

        this.toAdd = new Task(
                new Title(title),
                Optional.empty(),
                Optional.empty(),
                false,
                new UniqueLabelList(labelSet)
        );
    }

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     * @throws IllegalDateTimeValueException
     *             if deadline values are invalid
     */
    public AddCommand(String title, String deadline, Set<String> labels)
            throws IllegalValueException, IllegalDateTimeValueException {
        final Set<Label> labelSet = new HashSet<>();
        for (String labelName : labels) {
            labelSet.add(new Label(labelName));
        }

        this.toAdd = new Task(
                new Title(title),
                Optional.empty(),
                Optional.ofNullable(new Deadline(deadline)),
                false,
                new UniqueLabelList(labelSet)
        );
    }

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws IllegalDateTimeValueException if deadline values are invalid
     */
    public AddCommand(String title, String startDate, String deadline, Set<String> labels)
            throws IllegalValueException, IllegalDateTimeValueException {
        final Set<Label> labelSet = new HashSet<>();
        for (String labelName : labels) {
            labelSet.add(new Label(labelName));
        }

        this.toAdd = new Task(
                new Title(title),
                Optional.ofNullable(new Deadline(startDate)),
                Optional.ofNullable(new Deadline(deadline)),
                false,
                new UniqueLabelList(labelSet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            saveCurrentState();
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    /**
     * Save the data in task manager if command is mutating the data
     */
    public void saveCurrentState() {
        if (isMutating()) {
            try {
                LogicManager.undoCommandHistory.addStorageHistory(model.getRawTaskManager().getImmutableTaskList(),
                        model.getRawTaskManager().getImmutableLabelList());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isMutating() {
        return true;
    }

}
