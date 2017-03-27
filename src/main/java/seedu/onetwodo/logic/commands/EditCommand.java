package seedu.onetwodo.logic.commands;

import java.util.Optional;

import javafx.collections.transformation.FilteredList;
import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.commons.events.ui.DeselectCardsEvent;
import seedu.onetwodo.commons.util.CollectionUtil;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.Description;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Name;
import seedu.onetwodo.model.task.Priority;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.StartDate;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.TaskAttributesChecker;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the todo task.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive prefix integer) [NAME] "
            + "[s/START_DATE] [e/END_DATE] [p/PRIORITY] [d/DESCRIPTION ] [t/TAG]...\n" + "Example: " + COMMAND_WORD
            + " e1 s/tmr 9:00am d/beware of dogs";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task Result: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the todo list.";
    public static final String MESSAGE_TYPE_ERROR = "Task is invalid.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;
    private final TaskType taskType;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     * @param editTaskDescriptor
     *            details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor, char taskType) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
        this.taskType = TaskType.getTaskTypeFromChar(taskType);
    }

    @Override
    public CommandResult execute() throws CommandException {
        FilteredList<ReadOnlyTask> lastShownList = model.getFilteredByDoneFindType(taskType);
        if (lastShownList.size() < filteredTaskListIndex || taskType == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex - 1);
        
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        int internalIdx = model.getFilteredTaskList().indexOf(taskToEdit);

        // Throw CommandException if edited task is invalid
        TaskAttributesChecker.validateEditedAttributes(editedTask);
        try {
            // Not using model.updateTask as it does not trigger observers
            // model.updateTask(filteredTaskListIndex, editedTask);
            model.deleteTask(taskToEdit);
            model.addTask(internalIdx, editedTask);
            jumpToNewTask(editedTask);
            EventsCenter.getInstance().post(new DeselectCardsEvent());
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
        	try {
            model.addTask(internalIdx, (Task) taskToEdit);
        	} catch (UniqueTaskList.DuplicateTaskException dpe2) {
        		throw new CommandException(MESSAGE_DUPLICATE_TASK);
        	}
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (UniqueTaskList.TaskNotFoundException tne) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        StartDate updatedStartDate = editTaskDescriptor.getStartDate().orElseGet(taskToEdit::getStartDate);
        EndDate updatedEndDate = editTaskDescriptor.getDate().orElseGet(taskToEdit::getEndDate);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        Task edited = new Task(updatedName, updatedStartDate, updatedEndDate,
                updatedPriority, updatedDescription, updatedTags);
        if (taskToEdit.getDoneStatus()) {
            edited.setDone();
        }
        return edited;
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<StartDate> startDate = Optional.empty();
        private Optional<EndDate> endDate = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<Description> description = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.startDate = toCopy.getStartDate();
            this.endDate = toCopy.getDate();
            this.priority = toCopy.getPriority();
            this.description = toCopy.getDescription();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.startDate, this.endDate, this.priority, this.description,
                    this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setTime(Optional<StartDate> startDate) {
            assert startDate != null;
            this.startDate = startDate;
        }

        public Optional<StartDate> getStartDate() {
            return startDate;
        }

        public void setDate(Optional<EndDate> endDate) {
            assert endDate != null;
            this.endDate = endDate;
        }

        public Optional<EndDate> getDate() {
            return endDate;
        }

        public void setPriority(Optional<Priority> priority) {
            assert priority != null;
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
