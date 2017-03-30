package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.UniqueBookingList;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;

//@@author A0162877N
public class BookCommand extends Command {

    public static final String COMMAND_WORD = "book";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Books time slots for a task. "
            + "Parameters: TITLE [#LABEL] on DATE STARTTIME to ENDTIME, DATE STARTTIME to ENDTIME...\n"
            + "Example: " + COMMAND_WORD
            + " Meet John Doe #friends #owesMoney on 31 Mar 2017 2pm to 5pm,"
            + " 01 Oct 2017 2pm to 5pm, 30 Oct 2017 1pm to 2pm";
    public static final String MESSAGE_SUCCESS = "New booking added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOKING = "This booking already exists in the task manager";

    private final Task toAdd;

    public BookCommand(String title, Set<String> labels, String...dates)
            throws IllegalValueException, CommandException {
        final Set<Label> labelSet = new HashSet<>();
        final Set<Booking> bookingSet = new HashSet<>();
        for (String labelName : labels) {
            labelSet.add(new Label(labelName));
        }

        for (String bookingSlot : dates) {
            bookingSet.add(new Booking(bookingSlot));
        }

        this.toAdd = new Task(
                new Title(title),
                Optional.empty(),
                Optional.empty(),
                false,
                new UniqueLabelList(labelSet),
                new UniqueBookingList(bookingSet),
                false,
                Optional.empty()
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            saveCurrentState();
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKING);
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
