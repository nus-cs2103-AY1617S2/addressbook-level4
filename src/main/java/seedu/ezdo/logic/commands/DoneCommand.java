package seedu.ezdo.logic.commands;

import java.util.ArrayList;

import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.commons.util.MultipleIndexCommandUtil;
import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Task;
//@@author A0141010L
/**
 * Marks a task as identified using its last displayed index from ezDo as done
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";
    public static final String SHORT_COMMAND_WORD = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as done\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Done task: %1$s";
    public static final String MESSAGE_UNDONE_TASK_SUCCESS = "Undone task: %1$s";
    public static final String MESSAGE_DONE_LISTED = "Done tasks listed";

    private final ArrayList<Integer> targetIndexes;
    private final ArrayList<Task> tasksToToggle;
    private final boolean requestToViewDoneOnly;

    public DoneCommand(ArrayList<Integer> indexes) {
        this.targetIndexes = new ArrayList<Integer>(indexes);
        this.requestToViewDoneOnly = false;
        this.tasksToToggle = new ArrayList<Task>();
    }

    public DoneCommand() {
        this.targetIndexes = null;
        this.requestToViewDoneOnly = true;
        this.tasksToToggle = null;
    }
  //@@author A0139248X
    /**
     * Executes the done command.
     *
     * @throws CommandException if any index is invalid
     */
    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (requestToViewDoneOnly) {
            model.updateFilteredDoneList();
            return new CommandResult(MESSAGE_DONE_LISTED);
        }
        if (!MultipleIndexCommandUtil.isIndexValid(lastShownList, targetIndexes)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        MultipleIndexCommandUtil.addTasksToList(tasksToToggle, lastShownList, targetIndexes);
        boolean isDone = model.toggleTasksDone(tasksToToggle);
        if (isDone) {
            return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, tasksToToggle));
        }
        return new CommandResult(String.format(MESSAGE_UNDONE_TASK_SUCCESS, tasksToToggle));
    }
}
