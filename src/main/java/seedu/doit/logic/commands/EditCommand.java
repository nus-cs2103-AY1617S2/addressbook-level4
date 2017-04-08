package seedu.doit.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.doit.commons.core.EventsCenter;
import seedu.doit.commons.core.Messages;
import seedu.doit.commons.events.ui.JumpToListRequestEvent;
import seedu.doit.commons.util.CollectionUtil;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.item.Description;
import seedu.doit.model.item.EndTime;
import seedu.doit.model.item.Name;
import seedu.doit.model.item.Priority;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.StartTime;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueTaskList;
import seedu.doit.model.tag.UniqueTagList;

//@@author A0146809W

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_PARAMETER = "INDEX [s/START TIME] [e/END TIME] [p/PRIORITY] [#TAG]бн";
    public static final String COMMAND_RESULT = "Edits exisitng task with new details";
    public static final String COMMAND_EXAMPLE = "edit 1 s/9pm e/11pm p/high t/CS1010";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_RESULT + "Parameters: " + COMMAND_PARAMETER
            + "\nExample: " + COMMAND_EXAMPLE;

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final int filteredTaskListIndex;
    private EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor    details to edit the task with
     */

    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
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
        StartTime updatedStartTime = editTaskDescriptor.getStartTime().orElseGet(taskToEdit::getStartTime);
        EndTime updatedDeadline = editTaskDescriptor.getDeadline().orElseGet(taskToEdit::getDeadline);
        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedName, updatedPriority, updatedStartTime,
            updatedDeadline, updatedDescription, updatedTags);
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyTask> lastShownTaskList = this.model.getFilteredTaskList();


        if (this.filteredTaskListIndex >= lastShownTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } else {
            ReadOnlyTask taskToEdit = lastShownTaskList.get(this.filteredTaskListIndex);
            assert taskToEdit != null;
            Task editedTask = createEditedTask(taskToEdit, this.editTaskDescriptor);

            try {
                this.model.updateTask(this.filteredTaskListIndex, editedTask);
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            }
            this.model.updateFilteredListToShowAll();
            EventsCenter.getInstance().post(new JumpToListRequestEvent(
                    this.model.getFilteredTaskList().indexOf(editedTask)));
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));

        }
    }



    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        protected Optional<Name> name = Optional.empty();
        protected Optional<Priority> priority = Optional.empty();
        protected Optional<Description> description = Optional.empty();
        protected Optional<UniqueTagList> tags = Optional.empty();
        protected Optional<EndTime> deadline = Optional.empty();
        private Optional<StartTime> startTime = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.priority = toCopy.getPriority();
            this.description = toCopy.getDescription();
            this.tags = toCopy.getTags();
            this.deadline = toCopy.getDeadline();
            this.startTime = toCopy.getStartTime();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.priority, this.description, this.tags,
                this.deadline, this.startTime);
        }

        public Optional<Name> getName() {
            return this.name;
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Priority> getPriority() {
            return this.priority;
        }

        public void setPriority(Optional<Priority> priority) {
            assert priority != null;
            this.priority = priority;
        }

        public Optional<Description> getDescription() {
            return this.description;
        }

        public void setDescription(Optional<Description> description) {
            assert description != null;
            this.description = description;
        }

        public Optional<UniqueTagList> getTags() {
            return this.tags;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<StartTime> getStartTime() {
            return this.startTime;
        }

        public void setStartTime(Optional<StartTime> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<EndTime> getDeadline() {
            return this.deadline;
        }

        public void setDeadline(Optional<EndTime> deadline) {
            assert deadline != null;
            this.deadline = deadline;
        }
    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getParameter() {
        return COMMAND_PARAMETER;
    }

    public static String getResult() {
        return COMMAND_RESULT;
    }

    public static String getExample() {
        return COMMAND_EXAMPLE;
    }

}
