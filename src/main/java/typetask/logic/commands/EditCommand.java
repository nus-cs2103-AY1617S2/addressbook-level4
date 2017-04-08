package typetask.logic.commands;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import typetask.commons.core.Messages;
import typetask.commons.util.CollectionUtil;
import typetask.logic.commands.exceptions.CommandException;
import typetask.logic.parser.DateParser;
import typetask.model.task.DueDate;
import typetask.model.task.Name;
import typetask.model.task.Priority;
import typetask.model.task.ReadOnlyTask;
import typetask.model.task.Task;
//@@author A0139926R
/**
 * Edits the details of an existing task in the TaskManager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX [NAME] [by:DATE] [@TIME] [from:DATE] [to:DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 by: next week 11pm";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_INVALID_DATE = "Please check your dates again."
            + "Start date cannot be after End date. End date cannot be before Start date.";
    public static final String MESSAGE_MISSING_END_DATE = "There is no End date for the task."
            + "Please provide an End date as well.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }
    //@@author A0139926R
    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        //Checks for valid schedule after editing before updating taskManager
        if (!editedTask.getDate().value.equals("")) {
            if (editedTask.getEndDate().value.equals("")) {
                return new CommandResult(String.format(MESSAGE_MISSING_END_DATE));
            } else {
                List<Date> startDate = DateParser.parse(editedTask.getDate().value);
                List<Date> endDate = DateParser.parse(editedTask.getEndDate().value);
                if (!DateParser.checkValidSchedule(startDate, endDate)) {
                    return new CommandResult(MESSAGE_INVALID_DATE);
                }
            }
        }
        model.storeTaskManager(COMMAND_WORD);
        model.updateTask(filteredTaskListIndex, editedTask);
        model.updateFilteredTaskList(false);
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }
  //@@author A0139926R
    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        DueDate updatedDate = editTaskDescriptor.getDate().orElseGet(taskToEdit::getDate);
        DueDate updatedEndDate = editTaskDescriptor.getEndDate().orElseGet(taskToEdit::getEndDate);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);

        return new Task(updatedName, updatedDate, updatedEndDate, false, updatedPriority);
    }
  //@@author A0139926R
    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<DueDate> date = Optional.empty();
        private Optional<DueDate> endDate = Optional.empty();
        private Optional<Priority> priority = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.date = toCopy.getDate();
            this.endDate = toCopy.getEndDate();
            this.priority = toCopy.getPriority();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.date,
                    this.endDate, this.priority);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setDate(Optional<DueDate> date) {
            assert date != null;
            this.date = date;
        }
        public void setEndDate(Optional<DueDate> endDate) {
            assert endDate != null;
            this.endDate = endDate;
        }

        public Optional<DueDate> getDate() {
            return date;
        }
        public Optional<DueDate> getEndDate() {
            return endDate;
        }
        //author A0144902L
        public void setPriority(Optional<Priority> priority) {
            assert priority != null;
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

    }
}
