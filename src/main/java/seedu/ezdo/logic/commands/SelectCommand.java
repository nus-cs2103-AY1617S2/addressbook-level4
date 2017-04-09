package seedu.ezdo.logic.commands;

import java.util.ArrayList;

import seedu.ezdo.commons.core.EventsCenter;
import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.commons.core.UnmodifiableObservableList;
import seedu.ezdo.commons.events.ui.JumpToListRequestEvent;
import seedu.ezdo.commons.util.MultipleIndexCommandUtil;
import seedu.ezdo.logic.commands.exceptions.CommandException;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.model.todo.Task;

//@@author A0139177W
/**
 * Selects a task identified using its last displayed index from ezDo.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";
    private final ArrayList<Integer> targetIndexes;
    private final ArrayList<Task> tasksToToggle = new ArrayList<Task>();


    /**
     * Creates an arraylist of targetIndexes based on the total indexes quantity.
     * @param indexes
     */
    public SelectCommand(ArrayList<Integer> indexes) {
        this.targetIndexes = new ArrayList<Integer>(indexes);
    }

    /**
     * Executes the Select command.
     */
    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        checkValidIndexes(lastShownList);
        checkTasksDone(lastShownList);

        MultipleIndexCommandUtil.addTasksToList(tasksToToggle, lastShownList, targetIndexes);
        model.toggleTasksSelect(tasksToToggle);

        scrollToFinalTask();

        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndexes));

    }

    /** Scroll to the last task which was updated. **/
    private void scrollToFinalTask() {
        int offset = 1;
        int lastIndex = targetIndexes.size() - offset;
        int lastElementInTargetIndexes = targetIndexes.get(lastIndex);

        JumpToListRequestEvent scrollToTask = new JumpToListRequestEvent(lastElementInTargetIndexes - offset);
        EventsCenter.getInstance().post(scrollToTask);
    }

    /** checks if the tasks with the indexes specified are marked as done */
    private void checkTasksDone(UnmodifiableObservableList<ReadOnlyTask> lastShownList) throws CommandException {
        if (!MultipleIndexCommandUtil.isDone(lastShownList, targetIndexes)) {
            throw new CommandException(Messages.MESSAGE_TASK_DONE);
        }
    }

    /** checks if the indexes specified are all smaller than the size of the list and not 0 i.e. valid */
    private void checkValidIndexes(UnmodifiableObservableList<ReadOnlyTask> lastShownList) throws CommandException {
        if (!MultipleIndexCommandUtil.isIndexValid(lastShownList, targetIndexes)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

}
//@@author
