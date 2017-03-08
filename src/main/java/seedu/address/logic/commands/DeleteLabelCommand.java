package seedu.address.logic.commands;

import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
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

    public static final String COMMAND_WORD = "deletelabel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a label \n"
            + "All labels with this label name will be deleted from its attached task.\n"
            + "Parameters: LABEL_TO_DELETE\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Label %1$s deleted from all tasks";
    public static final String MESSAGE_LABEL_NOT_EXIST = "Specified label does not exist in any task saved";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";
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

        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, labelToDelete));
    }

    /**
     * Deletes a specific label in all tasks
     * @param allTaskList
     * @return true if the specified label exists
     */
    private boolean deleteLabelInTasks(List<ReadOnlyTask> allTaskList) throws CommandException {
        boolean labelExist = false;
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
        return labelExist;
    }

}
