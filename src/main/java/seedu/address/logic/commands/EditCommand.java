package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

	public static final String COMMAND_WORD = "edit";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
			+ "by the index number used in the last task list. "
			+ "Existing values will be overwritten by the input values.\n"
			+ "Parameters: INDEX (must be a positive integer) [NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS ] [t/TAG]...\n"
			+ "Example: " + COMMAND_WORD + " 1 p/91234567 e/johndoe@yahoo.com";

	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
	public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

	private final int filteredTaskListIndex;
	private final EditTaskDescriptor editTaskDescriptor;

	/**
	 * @param filteredTaskListIndex
	 *            the index of the task in the filtered task list to edit
	 * @param editTaskDescriptor
	 *            details to edit the task with
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
		List<ReadOnlyTask> lastShownList = this.model.getFilteredTaskList();

		if (this.filteredTaskListIndex >= lastShownList.size()) {
			throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyTask taskToEdit = lastShownList.get(this.filteredTaskListIndex);
		Task editedTask = createEditedTask(taskToEdit, this.editTaskDescriptor);

		try {
			this.model.updateTask(this.filteredTaskListIndex, editedTask);
		} catch (UniqueTaskList.DuplicateTaskException dpe) {
			throw new CommandException(MESSAGE_DUPLICATE_TASK);
		}
		this.model.updateFilteredListToShowAll();
		return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
	}

	/**
	 * Creates and returns a {@code Task} with the details of {@code taskToEdit}
	 * edited with {@code editTaskDescriptor}.
	 */
	private static Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor) {
		assert taskToEdit != null;

		Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
		Priority updatedPhone = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
		Deadline updatedEmail = editTaskDescriptor.getDeadline().orElseGet(taskToEdit::getDeadline);
		Description updatedAddress = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
		UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

		return new Task(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
	}

	/**
	 * Stores the details to edit the task with. Each non-empty field value will
	 * replace the corresponding field value of the task.
	 */
	public static class EditTaskDescriptor {
		private Optional<Name> name = Optional.empty();
		private Optional<Priority> priority = Optional.empty();
		private Optional<Deadline> deadline = Optional.empty();
		private Optional<Description> description = Optional.empty();
		private Optional<UniqueTagList> tags = Optional.empty();

		public EditTaskDescriptor() {
		}

		public EditTaskDescriptor(EditTaskDescriptor toCopy) {
			this.name = toCopy.getName();
			this.priority = toCopy.getPriority();
			this.deadline = toCopy.getDeadline();
			this.description = toCopy.getDescription();
			this.tags = toCopy.getTags();
		}

		/**
		 * Returns true if at least one field is edited.
		 */
		public boolean isAnyFieldEdited() {
			return CollectionUtil.isAnyPresent(this.name, this.priority, this.deadline, this.description, this.tags);
		}

		public void setName(Optional<Name> name) {
			assert name != null;
			this.name = name;
		}

		public Optional<Name> getName() {
			return this.name;
		}

		public void setPhone(Optional<Priority> priority) {
			assert priority != null;
			this.priority = priority;
		}

		public Optional<Priority> getPriority() {
			return this.priority;
		}

		public void setEmail(Optional<Deadline> deadline) {
			assert deadline != null;
			this.deadline = deadline;
		}

		public Optional<Deadline> getDeadline() {
			return this.deadline;
		}

		public void setAddress(Optional<Description> description) {
			assert description != null;
			this.description = description;
		}

		public Optional<Description> getDescription() {
			return this.description;
		}

		public void setTags(Optional<UniqueTagList> tags) {
			assert tags != null;
			this.tags = tags;
		}

		public Optional<UniqueTagList> getTags() {
			return this.tags;
		}
	}
}
