package seedu.task.logic.commands;

import static seedu.task.commons.core.Messages.MESSSAGE_INVALID_TIMING_ORDER;

import java.util.List;

import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalTimingOrderException;
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
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_WORD_REC = "editthis";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "[DESCRIPTION] [p/PRIORITY] [sd/START_DATE] [ed/END_DATE] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/2 sd/13/03/2017";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;
    private boolean isSpecific;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered person list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor, boolean isSpecific) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredPersonListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
        this.isSpecific = isSpecific;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task newTask = null;

        if (isSpecific) {
            if (taskToEdit.getOccurrenceIndexList().size() == 0) {
                taskToEdit.getOccurrenceIndexList().add(0);
            }
            int occurrenceIndex = taskToEdit.getOccurrenceIndexList().get(0);
            RecurringFrequency freq = null;
            try {
                freq = new RecurringFrequency(RecurringFrequency.NULL_FREQUENCY);
            } catch (IllegalValueException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            newTask = new Task(
                    taskToEdit.getDescription(),
                    taskToEdit.getPriority(),
                    taskToEdit.getOccurrences().get(occurrenceIndex).getStartTiming(),
                    taskToEdit.getOccurrences().get(occurrenceIndex).getEndTiming(),
                    taskToEdit.getTags(),
                    false,
                    freq);
            newTask.getStartTiming().setTiming(newTask.getStartTiming().toString());
            newTask.getEndTiming().setTiming(newTask.getEndTiming().toString());
            taskToEdit.removeOccurrence(occurrenceIndex);
            try {
                model.addTask(newTask);
            } catch (DuplicateTaskException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        Task editedTask;
        try {
            if (isSpecific) {
                editedTask = createEditedTask(newTask, editTaskDescriptor);
            } else {
                editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
            }
        } catch (IllegalTimingOrderException e) {
            throw new CommandException(MESSSAGE_INVALID_TIMING_ORDER);
        }

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
     * @throws IllegalTimingOrderException
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
            EditTaskDescriptor editTaskDescriptor) throws IllegalTimingOrderException {
        assert taskToEdit != null;

        Description updatedDescription = editTaskDescriptor.getDescription().orElseGet(taskToEdit::getDescription);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        Timing updatedStartDate = editTaskDescriptor.getStartTiming().orElseGet(taskToEdit::getStartTiming);
        Timing updatedEndDate = editTaskDescriptor.getEndTiming().orElseGet(taskToEdit::getEndTiming);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        boolean updatedRecurring = editTaskDescriptor.isRecurring().orElseGet(taskToEdit::isRecurring);
        RecurringFrequency updatedFrequency = editTaskDescriptor.getFrequency().orElseGet(taskToEdit::getFrequency);

        updatedStartDate.setTiming(updatedStartDate.toString());
        updatedEndDate.setTiming(updatedEndDate.toString());

        if (!Timing.checkTimingOrder(updatedStartDate, updatedEndDate)) {
            throw new IllegalTimingOrderException(MESSSAGE_INVALID_TIMING_ORDER);
        }

        return new Task(updatedDescription, updatedPriority, updatedStartDate,
                updatedEndDate, updatedTags, updatedRecurring, updatedFrequency);
    }
}
