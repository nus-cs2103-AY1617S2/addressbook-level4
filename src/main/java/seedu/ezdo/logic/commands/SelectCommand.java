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
    private final ArrayList<Task> tasksToToggle;

    public SelectCommand(ArrayList<Integer> indexes) {
        this.targetIndexes = new ArrayList<Integer>(indexes);
        this.tasksToToggle = new ArrayList<Task>();
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (!MultipleIndexCommandUtil.isIndexValid(lastShownList, targetIndexes)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if (!MultipleIndexCommandUtil.isDone(lastShownList, targetIndexes)) {
            throw new CommandException(Messages.MESSAGE_TASK_DONE);
        }

        MultipleIndexCommandUtil.addTasksToList(tasksToToggle, lastShownList, targetIndexes);
        model.toggleTasksSelect(tasksToToggle);

        int offset = 1;
        int lastIndex = targetIndexes.size() - offset;
        int lastElementInTargetIndexes = targetIndexes.get(lastIndex);

        JumpToListRequestEvent scrollToTask = new JumpToListRequestEvent(lastElementInTargetIndexes - offset);
        EventsCenter.getInstance().post(scrollToTask);

        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndexes));

    }

}
