package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.UniqueBookingList;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Recurrence;
import seedu.address.model.task.Title;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withTitle(String title) throws IllegalValueException {
        this.task.setTitle(new Title(title));
        return this;
    }

    public TaskBuilder withLabels(String ... labels) throws IllegalValueException {
        task.setLabels(new UniqueLabelList());
        for (String label: labels) {
            task.getLabels().add(new Label(label));
        }
        return this;
    }

    //@@author A0162877N
    public TaskBuilder withBookings(String ... bookings) throws IllegalValueException, CommandException {
        task.setBookings(new UniqueBookingList());
        for (String booking: bookings) {
            task.getBookings().add(new Booking(booking));
        }
        return this;
    }

    public TaskBuilder withStartTime(String startTime) throws IllegalValueException, IllegalDateTimeValueException {
        if (startTime != null) {
            this.task.setStartTime(Optional.ofNullable(new Deadline(startTime)));
        } else {
            this.task.setStartTime(Optional.empty());
        }
        return this;
    }

    public TaskBuilder withDeadline(String deadline) throws IllegalValueException, IllegalDateTimeValueException {
        if (deadline != null) {
            this.task.setDeadline(Optional.ofNullable(new Deadline(deadline)));
        } else {
            this.task.setDeadline(Optional.empty());
        }
        return this;
    }

    public TaskBuilder withStatus(boolean isCompleted) {
        this.task.setIsCompleted(isCompleted);
        return this;
    }

    public TestTask build() {
        return this.task;
    }

    public TaskBuilder withRecurrenceStatus(boolean isRecurring) {
        this.task.setIsRecurring(isRecurring);
        return this;
    }

    public TaskBuilder withRecurrence(String recurrence) throws IllegalValueException {
        if (recurrence != null) {
            this.task.setRecurrence(Optional.ofNullable(new Recurrence(recurrence)));
        } else {
            this.task.setRecurrence(Optional.empty());
        }
        return this;
    }

}
