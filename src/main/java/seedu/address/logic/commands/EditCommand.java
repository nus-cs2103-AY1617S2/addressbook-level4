package seedu.taskboss.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.commons.util.CollectionUtil;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.model.task.Info;
import seedu.taskboss.model.task.Date;
import seedu.taskboss.model.task.Time;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.Category;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.UniqueTaskList;
import seedu.taskboss.model.task.PriorityLevel;

/**
 * Edits the details of an existing task in TaskBoss.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task "
            + "by the index number used in the task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [i/INFO] [sd/START_DATE] [ed/END_DATE] [st/START_TIME] [et/END_TIME] [c/CATEGORY] [p/PRIORITY_LEVEL]\n"
            + "Example: " + COMMAND_WORD + " edit 1 i/Use Stack et/23:59";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in TaskBoss.";

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

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

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
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Info updatedInfo = editTaskDescriptor.getInfo().orElseGet(taskToEdit::getInfo);
        Date updatedDate = editTaskDescriptor.getDate().orElseGet(taskToEdit::getDate);
        Time updatedTime = editTaskDescriptor.getTime().orElseGet(taskToEdit::getTime);
        Category updatedCategory = editTaskDescriptor.getCategory().orElseGet(taskToEdit::getCategory);
        PriorityLevel updatedPriorityLevel = editTaskDescriptor.getPriorityLevel().orElseGet(taskToEdit::getPriorityLevel);

        return new Task(updatedInfo, updatedDate, updatedTime, updatedCategory, updatedPriorityLevel);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Info> info = Optional.empty();
        private Optional<Date> date = Optional.empty();
        private Optional<Time> time = Optional.empty();
        private Optional<Category> category = Optional.empty();
        private Optional<PriorityLevel> prioritylevel = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.info = toCopy.getInfo();
            this.date = toCopy.getDate();
            this.time = toCopy.getTime();
            this.category = toCopy.getCategory();
            this.prioritylevel = toCopy.getPriorityLevel();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.info, this.date, this.time, this.category, this.prioritylevel);
        }

        public void setInfo(Optional<Info> info) {
            assert info != null;
            this.info = info;
        }

        public Optional<Info> getInfo() {
            return info;
        }

        public void setDate(Optional<Date> date) {
            assert date != null;
            this.date = date;
        }

        public Optional<Date> getDate() {
            return date;
        }

        public void setTime(Optional<Time> time) {
            assert time != null;
            this.time = time;
        }

        public Optional<Time> getTime() {
            return time;
        }

        public void setCategory(Optional<Category> category) {
            assert category != null;
            this.category = category;
        }

        public Optional<Category> getCategory() {
            return category;
        }

        public void setPriorityLevel(Optional<PriorityLevel> prioritylevel) {
            assert prioritylevel != null;
            this.prioritylevel = prioritylevel;
        }

        public Optional<PriorityLevel> getPriorityLevel() {
            return prioritylevel;
        }
    }
}
