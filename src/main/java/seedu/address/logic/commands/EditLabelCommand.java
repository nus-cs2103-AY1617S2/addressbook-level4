package seedu.address.logic.commands;

import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0140042A
/**
 * Edits a label in all tasks that exists in Task Manager
 */
public class EditLabelCommand extends Command {

    public static final String COMMAND_WORD = "editlabel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a label to another label \n"
            + "Existing label will be overwritten by the new label.\n"
            + "Parameters: LABEL_TO_EDIT NEW_LABEL \n"
            + "Example: " + COMMAND_WORD + " school schoolwork";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Labels changed from %1$s to %2$s";
    public static final String MESSAGE_LABEL_NOT_EXIST = "Specified label does not exist in any task saved";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_LABEL_INVALID = "Label name is invalid";

    private Label labelToChange;
    private Label newLabel;

    public EditLabelCommand(String labelToChange, String newLabel) throws IllegalValueException {
        this.labelToChange = new Label(labelToChange);
        this.newLabel = new Label(newLabel);
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredListToShowAll();
        List<ReadOnlyTask> allTaskList = model.getFilteredTaskList();
        boolean labelExist = replaceLabelInTasks(allTaskList);

        if (!labelExist) {
            throw new CommandException(MESSAGE_LABEL_NOT_EXIST);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, labelToChange, newLabel));
    }

    /**
     * Replaces a specific label in all tasks
     * @param allTaskList
     * @return true if the specified label exists
     */
    private boolean replaceLabelInTasks(List<ReadOnlyTask> allTaskList) throws CommandException {
        boolean labelExist = false;
        for (int i = 0; i < allTaskList.size(); i++) {
            Task task = new Task(allTaskList.get(i));
            UniqueLabelList labels = task.getLabels();
            if (labels.contains(labelToChange)) {
                Set<Label> labelSet = labels.toSet();
                labelSet.remove(labelToChange);
                labelSet.add(newLabel);
                task.setLabels(new UniqueLabelList(labelSet));

                labelExist = true;

                try {
                    saveCurrentState();
                    model.updateTask(i, task);
                } catch (DuplicateTaskException dpe) {
                    throw new CommandException(MESSAGE_DUPLICATE_TASK);
                }
            }
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

    @Override
    public boolean isMutating() {
        return true;
    }

}
