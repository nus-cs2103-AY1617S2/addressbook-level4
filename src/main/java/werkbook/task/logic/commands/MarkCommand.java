package werkbook.task.logic.commands;

import java.util.List;
import java.util.Optional;

import werkbook.task.commons.core.Messages;
import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.commons.util.CollectionUtil;
import werkbook.task.logic.commands.exceptions.CommandException;
import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.ReadOnlyTask;
import werkbook.task.model.task.Task;
import werkbook.task.model.task.UniqueTaskList;

/**
 * Marks an existing task in the task list as done.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks an existing task in task list as done "
            + "by the index number used in the last task listing. "
            + "Parameters: INDEX (must be a positive integer)" + "Example: " + COMMAND_WORD + " mark 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "%1$s marked as complete!";
    public static final String MESSAGE_TASK_ALREADY_COMPLETE = "This task is already complete";
    public static final String MESSAGE_TASK_NOT_FOUND = "This task cannot be found";

    private final int targetIndex;
    private final MarkTaskDescriptor markTaskDescriptor;

    /**
     * @param targetIndex the index of the task in the filtered task list to
     *            edit
     * @param markTaskDescriptor details to edit the task with
     */
    public MarkCommand(int targetIndex, MarkTaskDescriptor markTaskDescriptor) {
        assert targetIndex > 0;
        assert markTaskDescriptor != null;

        // converts targetIndex from one-based to zero-based.
        this.targetIndex = targetIndex - 1;

        this.markTaskDescriptor = new MarkTaskDescriptor(markTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex);
        String test = taskToMark.getTags().toSet().toArray()[0].toString().substring(1, 9);

        if (test.equals("Complete")) {
            System.out.println("This task is complete!");
            throw new CommandException(MESSAGE_TASK_ALREADY_COMPLETE);
        } else {

            Task editedTask = createMarkedTask(taskToMark, markTaskDescriptor);

            try {
                model.updateTask(targetIndex, editedTask);
            } catch (UniqueTaskList.DuplicateTaskException dpe) {
                throw new CommandException(MESSAGE_TASK_ALREADY_COMPLETE);
            }
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
        }
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code markTaskDescriptor}.
     */
    private static Task createMarkedTask(ReadOnlyTask taskToEdit, MarkTaskDescriptor markTaskDescriptor)
            throws CommandException {
        assert taskToEdit != null;

        UniqueTagList updatedTags = markTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        Task taskToReturn = null;

        try {
            taskToReturn = new Task(taskToEdit.getName(), taskToEdit.getDescription(), taskToEdit.getStartDateTime(),
                    taskToEdit.getEndDateTime(), updatedTags);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return taskToReturn;
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will
     * replace the corresponding field value of the task.
     */
    public static class MarkTaskDescriptor {
        private Optional<UniqueTagList> tags = Optional.empty();

        public MarkTaskDescriptor() {
        }

        public MarkTaskDescriptor(MarkTaskDescriptor toCopy) {
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.tags);
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }
}
