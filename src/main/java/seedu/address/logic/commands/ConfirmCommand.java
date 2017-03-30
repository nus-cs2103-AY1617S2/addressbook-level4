package seedu.address.logic.commands;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.UniqueBookingList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

//@@author A0162877N
public class ConfirmCommand extends Command {

    public static final String COMMAND_WORD = "confirm";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Confirms the bookings of the task identified "
            + "by the index number used in the last task listing. "
            + "Other booking slots will be removed.\n"
            + "Parameters: INDEX (index of the task in the current task list and must be a positive integer)\n"
            + "Parameters: INDEX (index of the time slot to confirm and must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 1";
    public static final String MESSAGE_NO_SUCH_BOOKING = "Index provided is invalid.\n" + MESSAGE_USAGE;
    public static final String MESSAGE_TASK_NO_BOOKING = "This task does not have bookings to confirm.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Confirmed Task: %1$s";

    private final int filteredTaskListIndex;
    private final int bookingSlotIndex;

    public ConfirmCommand(int filteredTaskListIndex, int bookingSlotIndex) {
        assert filteredTaskListIndex > 0;
        assert bookingSlotIndex > 0;
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
        this.bookingSlotIndex = bookingSlotIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = null;
        try {
            taskToEdit = lastShownList.get(filteredTaskListIndex);
            Task editedTask = confirmTaskBooking(taskToEdit, bookingSlotIndex);
            saveCurrentState();
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        } catch (IllegalDateTimeValueException e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     * @throws IllegalDateTimeValueException
     * @throws IllegalValueException
     * @throws CommandException
     */
    private static Task confirmTaskBooking(ReadOnlyTask taskToEdit, int bookingSlotIndex)
            throws IllegalValueException, IllegalDateTimeValueException, CommandException {
        assert taskToEdit != null;
        if (!taskToEdit.getBookings().isEmpty()) {
            ObservableList<Booking> bookingList = taskToEdit.getBookings().asObservableList();
            System.out.println(bookingList.size());
            if (bookingSlotIndex >= bookingList.size()) {
                throw new CommandException(MESSAGE_NO_SUCH_BOOKING);
            }
            Booking booking = bookingList.get(bookingSlotIndex);
            Optional<Deadline> updatedStartTime = Optional.ofNullable(
                    new Deadline(booking.getBookingStartDate().toString()));
            Optional<Deadline> updatedEndTime = Optional.ofNullable(
                    new Deadline(booking.getBookingEndDate().toString()));
            return new Task(taskToEdit.getTitle(),
                    updatedStartTime,
                    updatedEndTime,
                    taskToEdit.isCompleted(),
                    taskToEdit.getLabels(),
                    new UniqueBookingList(),
                    false,
                    Optional.empty());
        } else {
            throw new CommandException(MESSAGE_TASK_NO_BOOKING);
        }
    }

    /**
     * Save the data in task manager if command is mutating the data
     */
    public void saveCurrentState() {
        if (isMutating()) {
            try {
                LogicManager.undoCommandHistory.addStorageHistory(model.getTaskManager().getImmutableTaskList(),
                        model.getTaskManager().getImmutableLabelList());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isMutating() {
        return true;
    }

}
