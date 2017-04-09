package seedu.doit.logic.commands;

import static seedu.doit.logic.commands.CommandResult.tasksToString;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.doit.commons.core.Messages;
import seedu.doit.commons.core.UnmodifiableObservableList;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.UniqueTaskList.TaskNotFoundException;

//@@author A0146809W
/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_PARAMETER = "INDEX";
    public static final String COMMAND_RESULT = "Deletes task at the specified index";
    public static final String COMMAND_EXAMPLE = "delete 3";
    public static final String MESSAGE_USAGE = COMMAND_WORD

        + ": Deletes the task identified by the index number used in the last task list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task(s): %1$s";

    private Set<Integer> targetIndexes;
    private HashSet<ReadOnlyTask> tasksToDeleteSet = new HashSet<>();


    public DeleteCommand(Set<Integer> targetIndexes) {
        this.targetIndexes = targetIndexes;
        this.targetIndexes = this.targetIndexes.stream().sorted().collect(Collectors.toSet());
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = this.model.getFilteredTaskList();

        if (isAnyInvalidIndex(lastShownTaskList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        for (int index: this.targetIndexes) {
            ReadOnlyTask taskToBeDeleted = lastShownTaskList.get(index - 1);
            this.tasksToDeleteSet.add(taskToBeDeleted);
        }

        try {
            this.model.deleteTasks(this.tasksToDeleteSet);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS,
            tasksToString(tasksToDeleteSet, targetIndexes)));
    }
    /**
     *
     * Checks if any index is invalid
     */
    private boolean isAnyInvalidIndex(UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList) {
        boolean test = this.targetIndexes.stream().anyMatch(index -> index < 0 || index > lastShownTaskList.size());
        return test;
    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getParameter() {
        return COMMAND_PARAMETER;
    }

    public static String getResult() {
        return COMMAND_RESULT;
    }

    public static String getExample() {
        return COMMAND_EXAMPLE;
    }
}
