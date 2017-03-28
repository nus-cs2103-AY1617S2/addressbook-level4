//@@author A0139161J
package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
public class UncompleteCommand extends Command {
    public static final String COMMAND_WORD = "uncomplete";

    public static final String MESSAGE_SUCCESS = "Task uncompleted: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task as uncompleted, identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final int indexToUncomplete;

    public UncompleteCommand(int targetIndex) {
        indexToUncomplete = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;

        UnmodifiableObservableList<ReadOnlyTask> list =
                (UnmodifiableObservableList<ReadOnlyTask>) model.getCompletedTaskList();

        if (list.size() < indexToUncomplete) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUncomplete = list.get(indexToUncomplete - 1);
        Task task = new Task(taskToUncomplete);
        task.setParserInfo("uncomplete");
        try {
            model.uncompleteTask(task);
            return new CommandResult(String.format(MESSAGE_SUCCESS, task));
        } catch (DuplicateTaskException | TaskNotFoundException e) {
            assert false : "Not possible";
            throw new CommandException("Unable to uncomplete specified task");
        }
    }
}
