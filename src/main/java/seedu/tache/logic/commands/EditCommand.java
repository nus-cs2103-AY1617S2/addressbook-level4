package seedu.tache.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.tache.commons.core.Messages;
import seedu.tache.commons.util.CollectionUtil;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.Date;
import seedu.tache.model.task.DetailedTask;
import seedu.tache.model.task.Duration;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.Time;
import seedu.tache.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last tasks listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer); [NAME;] [DATE;] [TIME;] [DURATION;] [TAG;]...\n"
            + "Example: " + COMMAND_WORD + " 1 Meeting; 10/11/2017; 3.30pm;";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

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

        if (taskToEdit instanceof DetailedTask) {
            DetailedTask temp = (DetailedTask) taskToEdit;
            Name updatedName = editTaskDescriptor.getName().orElseGet(temp::getName);
            Date updatedStartDate = editTaskDescriptor.getStartDate().orElseGet(temp::getStartDate);
            Date updateEndDate = editTaskDescriptor.getEndDate().orElseGet(temp::getEndDate);
            Time updateTime = editTaskDescriptor.getTime().orElseGet(temp::getTime);
            Duration updatedDuration = editTaskDescriptor.getDuration().orElseGet(temp::getDuration);
            UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
            return new DetailedTask(updatedName, updatedStartDate, updateEndDate, updateTime,
                                    updatedDuration, updatedTags);
        } else {
            Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
            UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
            return new Task(updatedName, updatedTags);
        }
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Date> startDate = Optional.empty();
        private Optional<Date> endDate = Optional.empty();
        private Optional<Time> time = Optional.empty();
        private Optional<Duration> duration = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.startDate = toCopy.getStartDate();
            this.endDate = toCopy.getEndDate();
            this.time = toCopy.getTime();
            this.duration = toCopy.getDuration();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.startDate, this.endDate,
                                               this.time, this.duration, this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setStartDate(Optional<Date> date) {
            assert date != null;
            this.startDate = date;
        }

        public Optional<Date> getStartDate() {
            return startDate;
        }

        public void setEndDate(Optional<Date> date) {
            assert date != null;
            this.endDate = date;
        }

        public Optional<Date> getEndDate() {
            return endDate;
        }

        public void setTime(Optional<Time> time) {
            assert time != null;
            this.time = time;
        }

        public Optional<Time> getTime() {
            return time;
        }

        public void setDuration(Optional<Duration> duration) {
            assert duration != null;
            this.duration = duration;
        }

        public Optional<Duration> getDuration() {
            return duration;
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
