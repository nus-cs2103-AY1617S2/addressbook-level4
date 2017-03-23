package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Completion;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Start;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.tag.UniqueTagList;

/**
 * Marks the details of an existing person in the address book.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified "
            + "by the index number used in the last task listing as completed. "
            + "Marking will set completion to true.\n" + "Parameters: INDEX (must be a positive integer) " + "[NAME]\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s";
    public static final String MESSAGE_NOT_MARKED = "At least one field to mark must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the ToDoApp.";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex
     *            the index of the person in the filtered person list to mark
     * @param markTaskDescriptor
     *            details to mark the person with
     */
    public MarkCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(filteredTaskListIndex);
        Task markedTask = createMarkedTask(taskToMark);

        try {
            model.updateTask(filteredTaskListIndex, markedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToMark}
     * marked with {@code markTaskDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createMarkedTask(ReadOnlyTask taskToMark) throws IllegalValueException {
        assert taskToMark != null;

        return new Task(taskToMark.getName(), taskToMark.getStart(), taskToMark.getDeadline(), taskToMark.getPriority(),
                taskToMark.getTags(), taskToMark.getNotes(), new Completion("true"));
    }

    /**
     * Stores the details to mark the person with. Each non-empty field value
     * will replace the corresponding field value of the person.
     */
    public static class MarkTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Start> start = Optional.empty();
        private Optional<Deadline> deadline = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();
        private Optional<Notes> notes = Optional.empty();
        private Optional<Completion> completion = Optional.empty();

        public MarkTaskDescriptor() {
        }

        public MarkTaskDescriptor(MarkTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.start = toCopy.getStart();
            this.deadline = toCopy.getDeadline();
            this.priority = toCopy.getPriority();
            this.tags = toCopy.getTags();
            this.notes = toCopy.getNotes();
            this.completion = toCopy.getCompletion();
        }

        /**
         * Returns true if at least one field is marked.
         */
        public boolean isAnyFieldMarked() {
            return CollectionUtil.isAnyPresent(this.name, this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setStart(Optional<Start> start) {
            assert start != null;
            this.start = start;
        }

        public Optional<Start> getStart() {
            return start;
        }

        public void setDeadline(Optional<Deadline> deadline) {
            assert deadline != null;
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return deadline;
        }

        public void setPriority(Optional<Priority> priority) {
            assert priority != null;
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        public void setNotes(Optional<Notes> notes) {
            assert notes != null;
            this.notes = notes;
        }

        public Optional<Notes> getNotes() {
            return notes;
        }

        public void setCompletion(Optional<Completion> completion) {
            assert completion != null;
            this.completion = completion;
        }

        public Optional<Completion> getCompletion() {
            return completion;
        }
    }
}
