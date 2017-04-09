package seedu.task.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.task.commons.core.Messages;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.logic.GlobalStack;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.Information;
import seedu.task.model.task.PriorityLevel;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[NAME] [p/PRIORITY] [i/INFORMATION] [d/DEADLINE ] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/4 d/123456";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task mananger.";

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
        Task originalTask = obtainTaskAtIndex(taskToEdit);
        originalTask.setIndex(filteredTaskListIndex);
        originalTask.setParserInfo("edit");
        editedTask.setIndex(filteredTaskListIndex);
        editedTask.setParserInfo("edit");
        try {
            model.updateTask(filteredTaskListIndex, editedTask);
            GlobalStack gStack = GlobalStack.getInstance();
            gStack.getUndoStack().push(editedTask);
            gStack.getUndoStack().push(originalTask);
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

        TaskName updatedTaskName = editTaskDescriptor.getTaskName().orElseGet(taskToEdit::getTaskName);
        Deadline updatedDeadline = editTaskDescriptor.getDeadline().orElseGet(taskToEdit::getDate);
        PriorityLevel updatedPriorityLevel = editTaskDescriptor.getPriorityLevel().orElseGet(taskToEdit::getPriority);
        Information updatedInformation = editTaskDescriptor.getInfo().orElseGet(taskToEdit::getInfo);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedTaskName, updatedDeadline, updatedPriorityLevel, updatedInformation, updatedTags);
    }

    private static Task obtainTaskAtIndex(ReadOnlyTask task) {
        assert task != null;

        TaskName taskName = task.getTaskName();
        Deadline deadline = task.getDate();
        PriorityLevel priorityLevel = task.getPriority();
        Information info = task.getInfo();
        UniqueTagList tags = task.getTags();

        return new Task (taskName, deadline, priorityLevel, info, tags);
    }
    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<TaskName> taskName = Optional.empty();
        private Optional<Deadline> deadline = Optional.empty();
        private Optional<PriorityLevel> priorityLevel = Optional.empty();
        private Optional<Information> info = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.taskName = toCopy.getTaskName();
            this.deadline = toCopy.getDeadline();
            this.priorityLevel = toCopy.getPriorityLevel();
            this.info = toCopy.getInfo();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.taskName, this.deadline, this.priorityLevel, this.info, this.tags);
        }

        public void setName(Optional<TaskName> name) {
            assert name != null;
            this.taskName = name;
        }

        public Optional<TaskName> getTaskName() {
            return taskName;
        }

        public void setDeadline(Optional<Deadline> deadline) {
            assert deadline != null;
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return deadline;
        }

        public void setPriorityLevel(Optional<PriorityLevel> priorityLevel) {
            assert priorityLevel != null;
            this.priorityLevel = priorityLevel;
        }

        public Optional<PriorityLevel> getPriorityLevel() {
            return priorityLevel;
        }

        public void setInfo(Optional<Information> info) {
            assert info != null;
            this.info = info;
        }

        public Optional<Information> getInfo() {
            return info;
        }

        public void setTags(Optional<seedu.task.model.tag.UniqueTagList> optional) {
            assert optional != null;
            this.tags = optional;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
