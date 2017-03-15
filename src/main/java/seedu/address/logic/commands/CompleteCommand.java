package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.EditTaskDescriptor;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.UniqueTaskList;

/**
 * Change the task status to completed
 */

public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark the task identified by the index number."
            + "used in the last task lisitng as completed.\n"
            + "Parameter: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed task: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in the tag list";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";

    public final int targetIndex;
    public final EditTaskDescriptor completeTaskDescriptor;

    public CompleteCommand(int targetIndex) {
        assert targetIndex > 0;

        this.targetIndex = targetIndex - 1;
        this.completeTaskDescriptor = new EditTaskDescriptor();

        List<String> list = new ArrayList<>();
        list.add("complete");
        try {
            this.completeTaskDescriptor.setTags(parseTagsForComplete(ParserUtil.toSet(Optional.of(list))));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() <= targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex);
        Task completedTask = createCompletedTask(taskToComplete, completeTaskDescriptor);

        try {
            model.updateTask(targetIndex, completedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToComplete}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createCompletedTask(ReadOnlyTask taskToComplete,
            EditTaskDescriptor editTaskDescriptor) throws CommandException {
        assert taskToComplete != null;

        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToComplete::getDescription);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToComplete::getPriority);
        TaskDate updatedStartDate = editTaskDescriptor.getStartDate().orElseGet(taskToComplete::getStartDate);
        TaskDate updatedEndDate = editTaskDescriptor.getEndDate().orElseGet(taskToComplete::getStartDate);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToComplete::getTags);


        return new Task(updatedDescription, updatedPriority, updatedStartDate, updatedEndDate, updatedTags);
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueTagList> parseTagsForComplete(Collection<String> tags) throws IllegalValueException {
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
