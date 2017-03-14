package seedu.doit.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.doit.commons.core.Messages;
import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.item.Description;
import seedu.doit.model.item.EndTime;
import seedu.doit.model.item.Event;
import seedu.doit.model.item.FloatingTask;
import seedu.doit.model.item.Name;
import seedu.doit.model.item.Priority;
import seedu.doit.model.item.ReadOnlyEvent;
import seedu.doit.model.item.ReadOnlyFloatingTask;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.StartTime;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueEventList;
import seedu.doit.model.item.UniqueFloatingTaskList;
import seedu.doit.model.item.UniqueTaskList;
import seedu.doit.model.tag.UniqueTagList;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
        + "by the index number used in the last task list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) [TASK NAME] [p/PRIORITY] [e/END DATE] "
        + "[d/ADDITIONAL DESCRIPTION] [t/TAG]...\n"
        + "Example: " + COMMAND_WORD + " 1 p/1 e/15-3-2020 23:59";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final int filteredTaskListIndex;
    private EditEventDescriptor editEventDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor    details to edit the task with
     */

    public EditCommand(int filteredTaskListIndex, EditEventDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
        this.editEventDescriptor = new EditEventDescriptor(editTaskDescriptor);
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                         EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;
        assert editTaskDescriptor != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        EndTime updatedDeadline = editTaskDescriptor.getDeadline().orElseGet(taskToEdit::getEndTime);
        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedName, updatedPriority, updatedDeadline, updatedDescription, updatedTags);
    }

    private static FloatingTask createEditedFloatingTask(ReadOnlyFloatingTask taskToEdit,
                                                         EditFloatingTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new FloatingTask(updatedName, updatedPriority, updatedDescription, updatedTags);
    }

    private static Event createEditedEvent(ReadOnlyEvent taskToEdit,
                                           EditEventDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        StartTime updatedStartTime = editTaskDescriptor.getStartTime().orElseGet(taskToEdit::getStartTime);
        EndTime updatedDeadline = editTaskDescriptor.getDeadline().orElseGet(taskToEdit::getEndTime);
        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Event(updatedName, updatedPriority, updatedStartTime, updatedDeadline,
            updatedDescription, updatedTags);
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();
        List<ReadOnlyFloatingTask> lastShownFloatingTaskList = model.getFilteredFloatingTaskList();
        List<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();

        int taskSize = lastShownTaskList.size();
        int taskAndEventSize = taskSize + lastShownEventList.size();
        int totalSize = taskAndEventSize + lastShownFloatingTaskList.size();


        if (filteredTaskListIndex >= totalSize) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if (filteredTaskListIndex < taskSize) {
            ReadOnlyTask taskToEdit = lastShownTaskList.get(filteredTaskListIndex);
            assert taskToEdit != null;
            Task editedTask = createEditedTask(taskToEdit, editEventDescriptor);

            try {
                model.updateTask(filteredTaskListIndex, editedTask);
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));

        } else if (filteredTaskListIndex >= taskSize && filteredTaskListIndex < taskAndEventSize) {
            ReadOnlyEvent taskToEdit = lastShownEventList.get(filteredTaskListIndex - taskSize);
            Event editedEvent = createEditedEvent(taskToEdit, editEventDescriptor);

            try {
                model.updateEvent(filteredTaskListIndex - taskSize, editedEvent);
            } catch (UniqueEventList.DuplicateEventException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));

        } else if (filteredTaskListIndex >= taskAndEventSize && filteredTaskListIndex < totalSize) {
            ReadOnlyFloatingTask taskToEdit = lastShownFloatingTaskList.get(filteredTaskListIndex - taskAndEventSize);
            FloatingTask editedFloatingTask = createEditedFloatingTask(taskToEdit, editEventDescriptor);

            try {
                model.updateFloatingTask(filteredTaskListIndex - taskAndEventSize,
                    editedFloatingTask);
            } catch (UniqueFloatingTaskList.DuplicateFloatingTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
        } else {
            return null;
            // should not happen
        }
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditFloatingTaskDescriptor {
        protected Optional<Name> name = Optional.empty();
        protected Optional<Priority> priority = Optional.empty();
        protected Optional<Description> description = Optional.empty();
        protected Optional<UniqueTagList> tags = Optional.empty();

        public EditFloatingTaskDescriptor() {
        }

        public EditFloatingTaskDescriptor(EditFloatingTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.priority = toCopy.getPriority();
            this.description = toCopy.getDescription();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.priority, this.description, this.tags);
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

        public void setPriority(Optional<Priority> priority) {
            assert priority != null;
            this.priority = priority;
        }

        public Optional<Description> getDescription() {
            return description;
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor extends EditFloatingTaskDescriptor {
        protected Optional<EndTime> deadline = Optional.empty();

        public EditTaskDescriptor() {
            super();
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            super(toCopy);
            this.deadline = toCopy.getDeadline();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.deadline) || super.isAnyFieldEdited();
        }

        public Optional<EndTime> getDeadline() {
            return deadline;
        }

        public void setDeadline(Optional<EndTime> deadline) {
            assert deadline != null;
            this.deadline = deadline;
        }
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor extends EditTaskDescriptor {
        private Optional<StartTime> startTime = Optional.empty();

        public EditEventDescriptor() {
            super();
        }

        public EditEventDescriptor(EditEventDescriptor toCopy) {
            super(toCopy);
            this.startTime = toCopy.getStartTime();
        }

        public Optional<StartTime> getStartTime() {
            return startTime;
        }

        public void setStartTime(Optional<StartTime> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.startTime) || super.isAnyFieldEdited();
        }
    }


}
