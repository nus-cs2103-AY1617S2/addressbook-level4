package seedu.address.logic.commands;

import java.util.List;
//import java.util.Optional;

//import seedu.address.commons.util.CollectionUtil;
//import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;

import seedu.address.logic.commands.exceptions.CommandException;

//import seedu.address.model.tag.UniqueTagList;
//import seedu.address.model.task.Deadline;
//import seedu.address.model.task.Instruction;
//import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
//import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;


/**
 * Edits the details of an existing task in the instruction book.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";
    public static final String COMMAND_WORD_FIRST_ALTERNATIVE = "finish";
    public static final String COMMAND_WORD_SECOND_ALTERNATIVE = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task as complete "
            + "by the index number used in the last task listing. "
            + "Existing priority level will be changed to completed"
            + "Parameters: [LIST_NAME] INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " floating 1";

    public static final String MESSAGE_MARK_COMPLETE_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the instruction book.";

    private final int filteredTaskListIndex;
    private final String targetList;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public CompleteCommand(String targetList, int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
        this.targetList = targetList;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = getTargetTaskList(targetList);

        validateTargetIndex(filteredTaskListIndex, lastShownList);

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = new Task(taskToEdit);
        editedTask.setAsCompleted();
        try {
            model.updateTask(targetList, filteredTaskListIndex, editedTask);
            highlight(editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(String.format(MESSAGE_MARK_COMPLETE_TASK_SUCCESS, editedTask));
    }
}
