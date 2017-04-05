//@@author A0113795Y
package seedu.task.logic.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.logic.parser.ParserUtil;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.EditTaskDescriptor;
import seedu.task.model.task.Priority;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.RecurringFrequency;
import seedu.task.model.task.Task;
import seedu.task.model.task.Timing;
import seedu.task.model.task.UniqueTaskList;

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

    //@@author A0164212U
    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() <= targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex);
        Task newTask = null;
        Task completedTask;

        try {
            if (taskToComplete.isRecurring()) {
                newTask = Task.extractOccurrence(taskToComplete);
                model.addTask(newTask);
                completedTask = createCompletedTask(newTask, completeTaskDescriptor);
                int newIndex = model.getFilteredTaskList().indexOf(newTask);
                model.updateTask(newIndex, completedTask);
                completedTask.setComplete();
                model.updateFilteredListToShowAll();
                return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, newTask));
            } else {
                completedTask = createCompletedTask(taskToComplete, completeTaskDescriptor);
                model.updateTask(targetIndex, completedTask);
                completedTask.setComplete();
                model.updateFilteredListToShowAll();
                return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete));
            }
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }
    //@@author A0113795Y
    /**
     * Creates and returns a {@code Task} with the details of {@code taskToComplete}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createCompletedTask(ReadOnlyTask taskToComplete,
            EditTaskDescriptor editTaskDescriptor) throws CommandException {
        assert taskToComplete != null;

        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToComplete::getDescription);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToComplete::getPriority);
        Timing updatedStartDate = editTaskDescriptor.getStartTiming().orElseGet(taskToComplete::getStartTiming);
        Timing updatedEndDate = editTaskDescriptor.getEndTiming().orElseGet(taskToComplete::getStartTiming);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToComplete::getTags);
        boolean updatedRecurring = editTaskDescriptor.isRecurring().orElseGet(taskToComplete::isRecurring);
        RecurringFrequency updatedFrequency = editTaskDescriptor.getFrequency().orElseGet(taskToComplete::getFrequency);

        updatedStartDate.setTiming(updatedStartDate.toString());
        updatedEndDate.setTiming(updatedEndDate.toString());

        return new Task(updatedDescription, updatedPriority, updatedStartDate,
                updatedEndDate, updatedTags, updatedRecurring, updatedFrequency);
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
