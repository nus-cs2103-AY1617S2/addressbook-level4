package seedu.taskmanager.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.util.CollectionUtil;
// import seedu.taskmanager.model.person.Address;
import seedu.taskmanager.model.task.Date;
import seedu.taskmanager.model.task.TaskName;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.Time;
// import seedu.taskmanager.model.task.EndTime;
// import seedu.taskmanager.model.task.Deadline;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.UniqueTaskList;
// import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.logic.commands.exceptions.CommandException;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [TASK] TO [DATE] FROM [STARTTIME] TO [ENDTIME]\n"
            + "Example: " + COMMAND_WORD + " 1 ON 04/03/17 FROM 1630 TO 1830";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

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

        TaskName updatedName = editTaskDescriptor.getTaskName().orElseGet(taskToEdit::getTaskName);
        Time updatedTime = editTaskDescriptor.getTime().orElseGet(taskToEdit::getTime);
        Date updatedDate = editTaskDescriptor.getDate().orElseGet(taskToEdit::getDate);
//        EndTime updatedEndTime = editTaskDescriptor.getEndTime().orElseGet(taskToEdit::getEndTime);
//        Deadline updatedDeadline = editTaskDescriptor.getDeadline().orElseGet(taskToEdit::getDeadline);
//        Address updatedAddress = editPersonDescriptor.getAddress().orElseGet(personToEdit::getAddress);
//        UniqueTagList updatedTags = editPersonDescriptor.getTags().orElseGet(personToEdit::getTags);

        return new Task(updatedName, updatedTime, updatedDate/*, updatedEndTime, updatedDeadline, updatedAddress, updatedTags*/);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<TaskName> taskname = Optional.empty();
        private Optional<Time> time = Optional.empty();
        private Optional<Date> date = Optional.empty();
//        private Optional<EndTime> endtime = Optional.empty();
 //       private Optional<Deadline> deadline = Optional<T>.empty();
 //       private Optional<Address> address = Optional.empty();
 //       private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.taskname = toCopy.getTaskName();
            this.time = toCopy.getTime();
            this.date = toCopy.getDate();
//            this.endtime = toCopy.getEndTime();
//            this.deadline = toCopy.getDeadline);
 //           this.address = toCopy.getAddress();
 //           this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.taskname, this.time, this.date
            		                           /*this.endtime, this.deadline , this.address, this.tags*/);
        }

        public void setTaskName(Optional<TaskName> taskname) {
            assert taskname != null;
            this.taskname = taskname;
        }

        public Optional<TaskName> getTaskName() {
            return taskname;
        }

        public void setTime(Optional<Time> time) {
            assert time != null;
            this.time = time;
        }

        public Optional<Time> getTime() {
            return time;
        }

        public void setDate(Optional<Date> date) {
            assert date != null;
            this.date = date;
        }

        public Optional<Date> getDate() {
            return date;
        }

/*        public void setEndTime(Optional<EndTime> endtime) {
        	assert endtime != null;
        	this.endtime = endtime;
        }

        public Optional<EndTime> getEndTime() {
        	return endtime;
        }

        public void setDeadline(Optional<Deadline> deadline) {
        	assert deadline != null;
        	this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
        	return deadline;
        }
*/
/*        public void setAddress(Optional<Address> address) {
            assert address != null;
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return address;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        } */
    }
}
