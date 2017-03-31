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
 * Unmarks the details of an existing person in the address book.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks the task identified "
            + "by the index number used in the last task listing as not completed. "
            + "Unmarking will set completion to false.\n" + "Parameters: INDEX (must be a positive integer) "
            + "[NAME]\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmarked Task: %1$s";
    public static final String MESSAGE_NOT_UNMARKED = "At least one field to mark must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the ToDoApp.";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex the index of the person in the filtered
     *            person list to mark
     * @param markTaskDescriptor details to mark the person with
     */
    public UnmarkCommand(int filteredTaskListIndex) {
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

        ReadOnlyTask taskToUnmark = lastShownList.get(filteredTaskListIndex);
        Task markedTask = createUnmarkedTask(taskToUnmark);

        try {
            model.updateTask(filteredTaskListIndex, markedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
    }

    /**
     * Creates and returns a {@code Task} with the details of
     * {@code taskToUnmark} marked with {@code markTaskDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createUnmarkedTask(ReadOnlyTask taskToUnmark) throws IllegalValueException {
        assert taskToUnmark != null;

        return new Task(taskToUnmark.getName(), taskToUnmark.getStart(), taskToUnmark.getDeadline(),
                taskToUnmark.getPriority(), taskToUnmark.getTags(), taskToUnmark.getNotes(), new Completion("false"));
    }

    /**
     * Stores the details to mark the person with. Each non-empty field value
     * will replace the corresponding field value of the person.
     */
    public static class UnmarkTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Start> start = Optional.empty();
        private Optional<Deadline> deadline = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();
        private Optional<Notes> notes = Optional.empty();
        private Optional<Completion> completion = Optional.empty();

        public UnmarkTaskDescriptor() {
        }

        public UnmarkTaskDescriptor(UnmarkTaskDescriptor toCopy) {
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
        public boolean isAnyFieldUnmarked() {
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
