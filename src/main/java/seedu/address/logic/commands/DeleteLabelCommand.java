package seedu.address.logic.commands;

import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.undo.UndoManager;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0140042A
/**
 * Edits a label in all tasks that exists in task manager
 */
public class DeleteLabelCommand extends Command {

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Label %1$s deleted from all tasks";
    public static final String MESSAGE_LABEL_NOT_EXIST = "Specified label does not exist in any task saved";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_LABEL_INVALID = "Label name is invalid";

    private Label labelToDelete;

    public DeleteLabelCommand(String labelToDelete) throws IllegalValueException {
        this.labelToDelete = new Label(labelToDelete);
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredListToShowAll();
        List<ReadOnlyTask> allTaskList = model.getFilteredTaskList();
        boolean labelExist = deleteLabelInTasks(allTaskList);

        if (!labelExist) {
            throw new CommandException(MESSAGE_LABEL_NOT_EXIST);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, labelToDelete));
    }

    /**
     * Deletes a specific label in all tasks
     * @param allTaskList
     * @return true if the specified label exists
     */
    private boolean deleteLabelInTasks(List<ReadOnlyTask> allTaskList) throws CommandException {
        boolean labelExist = false;
        saveCurrentState();
        for (int i = 0; i < allTaskList.size(); i++) {
            Task task = new Task(allTaskList.get(i));
            UniqueLabelList labels = task.getLabels();
            if (labels.contains(labelToDelete)) {
                Set<Label> labelSet = labels.toSet();
                labelSet.remove(labelToDelete);
                task.setLabels(new UniqueLabelList(labelSet));

                labelExist = true;

                try {
                    model.updateTask(i, task);
                } catch (DuplicateTaskException dpe) {
                    throw new CommandException(MESSAGE_DUPLICATE_TASK);
                }
            }
        }

        if (!labelExist) {
            deleteCurrentState();
        }

        return labelExist;
    }

    /**
     * Save the data in task manager if command is mutating the data
     */
    public void saveCurrentState() {
        if (isMutating()) {
            try {
                LogicManager.undoCommandHistory.addStorageHistory(model.getTaskManager().getImmutableTaskList(),
                        model.getTaskManager().getImmutableLabelList());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes the data in task manager if command is mutating the data
     */
    public void deleteCurrentState() {
        UndoManager.getInstance().getUndoData();
    }

    @Override
    public boolean isMutating() {
        return true;
    }

}
