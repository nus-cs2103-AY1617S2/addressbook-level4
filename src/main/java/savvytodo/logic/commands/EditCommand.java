package savvytodo.logic.commands;

import java.util.List;

import savvytodo.commons.core.Messages;
import savvytodo.logic.commands.exceptions.CommandException;
import savvytodo.logic.parser.CommandTaskDescriptor;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.DateTime;
import savvytodo.model.task.Description;
import savvytodo.model.task.Location;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.ReadOnlyTask;
import savvytodo.model.task.Recurrence;
import savvytodo.model.task.Status;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList;

//@@author A0140016B
/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the at least one details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[NAME] [dt/START_DATE = END_DATE] [l/LOCATION] [p/PRIORITY_LEVEL] "
            + "[r/RECURRING_TYPE NUMBER_OF_RECURRENCE] [c/CATEGORY] [d/DESCRIPTION]...\n"
            + "Example: " + COMMAND_WORD + " 1 c/CS2103 d/Complete group project component";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final int filteredTaskListIndex;
    private final CommandTaskDescriptor cmdTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, CommandTaskDescriptor cmdTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert cmdTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.cmdTaskDescriptor = new CommandTaskDescriptor(cmdTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size() || filteredTaskListIndex < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, cmdTaskDescriptor);

        try {
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code cmdTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit, CommandTaskDescriptor cmdTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = cmdTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Priority updatedPriority = cmdTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        Description updatedDescription = cmdTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        Location updatedLocation = cmdTaskDescriptor.getLocation().orElseGet(taskToEdit::getLocation);
        UniqueCategoryList updatedCategories = cmdTaskDescriptor.getCategories().orElseGet(taskToEdit::getCategories);
        DateTime updatedDateTime = cmdTaskDescriptor.getDateTime().orElseGet(taskToEdit::getDateTime);
        Recurrence updatedRecurrence = cmdTaskDescriptor.getRecurrence().orElseGet(taskToEdit::getRecurrence);
        Status updatedStatus = cmdTaskDescriptor.getStatus().orElseGet(taskToEdit::isCompleted);

        return new Task(updatedName, updatedPriority, updatedDescription, updatedLocation, updatedCategories,
                updatedDateTime, updatedRecurrence, updatedStatus);
    }

}
//@@author A0140016B
