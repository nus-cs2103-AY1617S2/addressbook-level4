package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.UniqueBookingList;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Recurrence;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = false)
    private String deadline;
    @XmlElement(required = false)
    private String startTime;
    @XmlElement(required = true)
    private boolean isCompleted;
    @XmlElement(required = true)
    private boolean isRecurring;
    @XmlElement(required = false)
    private String recurrence;

    @XmlElement
    private List<XmlAdaptedLabel> labeled = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedBooking> bookingSlots = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        assert(source != null);
        title = source.getTitle().title;
        //TODO: fix for tests!
        if (source.getStartTime().isPresent()) {
            startTime = source.getStartTime().get().toString();
        }
        if (source.getDeadline().isPresent()) {
            deadline = source.getDeadline().get().toString();
        }
        isCompleted = source.isCompleted();
        isRecurring = source.isRecurring();
        if (source.getRecurrence().isPresent()) {
            recurrence = source.getRecurrence().get().toString();
        }
        labeled = new ArrayList<>();
        bookingSlots = new ArrayList<>();
        for (Label label : source.getLabels()) {
            labeled.add(new XmlAdaptedLabel(label));
        }
        for (Booking booking : source.getBookings()) {
            bookingSlots.add(new XmlAdaptedBooking(booking));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     * @throws CommandException
     */
    public Task toModelType() throws IllegalValueException, IllegalDateTimeValueException, CommandException {
        final List<Label> taskLabels = new ArrayList<>();
        final Optional<Deadline> startTime;
        final Optional<Deadline> deadline;
        final Optional<Recurrence> recurrence;
        final List<Booking> taskBookings = new ArrayList<>();
        for (XmlAdaptedLabel label : labeled) {
            taskLabels.add(label.toModelType());
        }
        for (XmlAdaptedBooking booking : bookingSlots) {
            taskBookings.add(booking.toModelType());
        }
        final Title title = new Title(this.title);
        if (this.startTime != null) {
            startTime = Optional.ofNullable(new Deadline(this.startTime));
        } else {
            startTime = Optional.empty();
        }
        if (this.deadline != null) {
            deadline = Optional.ofNullable(new Deadline(this.deadline));
        } else {
            deadline = Optional.empty();
        }
        if (this.recurrence != null) {
            recurrence = Optional.ofNullable(new Recurrence(this.recurrence));
        } else {
            recurrence = Optional.empty();
        }
        final UniqueLabelList labels = new UniqueLabelList(taskLabels);
        final UniqueBookingList bookings = new UniqueBookingList(taskBookings);
        return new Task(title, startTime, deadline, isCompleted, labels, bookings, isRecurring, recurrence);
    }
}
