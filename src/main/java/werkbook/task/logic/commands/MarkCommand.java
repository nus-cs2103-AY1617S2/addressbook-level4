package werkbook.task.logic.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import werkbook.task.commons.core.Messages;
import werkbook.task.commons.exceptions.IllegalValueException;
import werkbook.task.commons.util.CollectionUtil;
import werkbook.task.logic.commands.exceptions.CommandException;
import werkbook.task.logic.parser.ParserUtil;
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
            + "Parameters: INDEX (must be a positive integer)" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked %1$s as complete!";
    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmark %1$s as complete!";
    public static final String MESSAGE_TASK_NOT_FOUND = "This task cannot be found";

    private final int targetIndex;
    private MarkTaskDescriptor markTaskDescriptor;

    /**
     * @param targetIndex the index of the task in the filtered task list to
     *            edit
     * @param markTaskDescriptor details to edit the task with
     */
    public MarkCommand(int targetIndex) {
        assert targetIndex > 0;

        // converts targetIndex from one-based to zero-based.
        this.targetIndex = targetIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        // Checks the task's completion status and set to the opposite
        ReadOnlyTask taskToMark = lastShownList.get(targetIndex);
        Object[] tagArray = taskToMark.getTags().toSet().toArray();

        String taskStatus = tagArray[0].toString().substring(1, 9);
        taskStatus = taskStatus.equals("Complete") ? "Incomplete" : "Complete";

        // Create a new list of tags
        ArrayList<String> newTags = new ArrayList<String>();
        newTags.add(taskStatus);

        for (int i = 1; i < tagArray.length; i++) {
            // Remove brackets
            newTags.add(tagArray[i].toString().substring(1, tagArray[i].toString().length() - 1));
        }

        markTaskDescriptor = new MarkTaskDescriptor();
        String statusMessage = taskStatus.equals("Complete") ? MESSAGE_MARK_TASK_SUCCESS
                : MESSAGE_UNMARK_TASK_SUCCESS;

        try {
            markTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(Optional.of(newTags))));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }

        Task markedTask = createMarkedTask(taskToMark, markTaskDescriptor);

        try {
            model.updateTask(targetIndex, markedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            dpe.printStackTrace();
        }

        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(statusMessage, taskToMark.getName()));
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
            taskToReturn = new Task(taskToEdit.getName(), taskToEdit.getDescription(),
                    taskToEdit.getStartDateTime(), taskToEdit.getEndDateTime(), updatedTags);
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

    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
