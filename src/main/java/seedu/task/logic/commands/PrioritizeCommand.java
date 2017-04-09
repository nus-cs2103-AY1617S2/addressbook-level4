//@@author A0113795Y
package seedu.task.logic.commands;

import java.util.Optional;
import java.util.logging.Logger;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Description;
import seedu.task.model.task.EditTaskDescriptor;
import seedu.task.model.task.Priority;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.RecurringFrequency;
import seedu.task.model.task.Task;
import seedu.task.model.task.Timing;
import seedu.task.model.task.UniqueTaskList;

public class PrioritizeCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(PrioritizeCommand.class);
    public static final String COMMAND_WORD = "prioritize";
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should be between 1-3";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the priority of the task identified "
            + "by the index number used in the last task lisitng as completed.\n"
            + "Parameter: INDEX (must be a positive integer) NEW_PRIORITY_LEVEL (integer from 1 to 3)\n"
            + "Example: " + COMMAND_WORD + " 2" + " 1";

    public static final String MESSAGE_PRIORITIZE_TASK_SUCCESS = "Prioritized task: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in the tag list";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String MESSAGE_NULL_TIMING =
            "Both the start and end timings must be specified for a recurring task";

    public final int targetIndex;
    public final EditTaskDescriptor prioritizeTaskDescriptor;

    public PrioritizeCommand(int targetIndex, Priority newPriority) {
        assert targetIndex > 0;

        this.targetIndex = targetIndex - 1;
        this.prioritizeTaskDescriptor = new EditTaskDescriptor();
        this.prioritizeTaskDescriptor.setPriority(Optional.of(newPriority));
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() <= targetIndex) {
            logger.info(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex);
        Task completedTask = createPrioritizedTask(taskToComplete, prioritizeTaskDescriptor);

        try {
            model.updateTask(targetIndex, completedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            logger.info(MESSAGE_DUPLICATE_TASK);
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.updateFilteredListToShowAll();
        logger.info(String.format(MESSAGE_PRIORITIZE_TASK_SUCCESS, taskToComplete));
        return new CommandResult(String.format(MESSAGE_PRIORITIZE_TASK_SUCCESS, taskToComplete));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToComplete}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createPrioritizedTask(ReadOnlyTask taskToPrioritize,
            EditTaskDescriptor editTaskDescriptor) throws CommandException {
        assert taskToPrioritize != null;

        Description updatedDescription = editTaskDescriptor.getDescription()
                .orElseGet(taskToPrioritize::getDescription);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToPrioritize::getPriority);
        Timing updatedStartDate = editTaskDescriptor.getStartTiming().orElseGet(taskToPrioritize::getStartTiming);
        Timing updatedEndDate = editTaskDescriptor.getEndTiming().orElseGet(taskToPrioritize::getEndTiming);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToPrioritize::getTags);
        boolean updatedRecurring = editTaskDescriptor.isRecurring().orElseGet(taskToPrioritize::isRecurring);
        RecurringFrequency updatedFrequency = editTaskDescriptor.getFrequency()
                .orElseGet(taskToPrioritize::getFrequency);

        try {
            return new Task(updatedDescription, updatedPriority, updatedStartDate,
                    updatedEndDate, updatedTags, updatedRecurring, updatedFrequency);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_NULL_TIMING);
        }
    }
}
