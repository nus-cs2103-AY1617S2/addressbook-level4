package seedu.address.model.task;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.booking.UniqueBookingList;
import seedu.address.model.label.UniqueLabelList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Title title;
    private Optional<Deadline> deadline;
    private Optional<Deadline> startTime;
    private Boolean isCompleted;
    private Optional<Recurrence> recurrence;
    private Boolean isRecurring;

    private UniqueLabelList labels;
    private UniqueBookingList bookings;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, Optional<Deadline> startTime, Optional<Deadline> deadline,
            boolean isCompleted, UniqueLabelList labels, boolean isRecurring, Optional<Recurrence> recurrence) {
        assert !CollectionUtil.isAnyNull(title, isCompleted, labels, isRecurring);
        this.title = title;
        this.deadline = deadline;
        this.startTime = startTime;
        this.isCompleted = isCompleted;
        this.isRecurring = isRecurring;
        this.recurrence = recurrence;
        this.labels = new UniqueLabelList(labels); // protect internal labels from changes in the arg list
        this.bookings = new UniqueBookingList();
    }

    /**
     * Every field except bookingList must be present and not null.
     */
    public Task(Title title, Optional<Deadline> startTime, Optional<Deadline> deadline,
            boolean isCompleted, UniqueLabelList labels, UniqueBookingList bookings, boolean isRecurring,
            Optional<Recurrence> recurrence) {
        assert !CollectionUtil.isAnyNull(title, isCompleted, labels);
        this.title = title;
        this.deadline = deadline;
        this.startTime = startTime;
        this.isCompleted = isCompleted;
        this.labels = new UniqueLabelList(labels); // protect internal labels from changes in the arg list
        this.bookings = new UniqueBookingList(bookings);
        this.isRecurring = isRecurring;
        this.recurrence = recurrence;
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getStartTime(), source.getDeadline()
                , source.isCompleted(), source.getLabels(), source.getBookings(), source.isRecurring(),
                source.getRecurrence());
    }

    public void setName(Title title) {
        assert title != null;
        this.title = title;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public void setStartTime(Optional<Deadline> startTime) {
        if (startTime.isPresent()) {
            this.startTime = startTime;
        } else {
            this.startTime = Optional.empty();
        }
    }

    @Override
    public Optional<Deadline> getStartTime() {
        return startTime == null ? Optional.empty() : startTime;
    }

    public void setDeadline(Optional<Deadline> deadline) {
        if (deadline.isPresent()) {
            this.deadline = deadline;
        } else {
            this.deadline = Optional.empty();
        }
    }

    @Override
    public Optional<Deadline> getDeadline() {
        return deadline == null ? Optional.empty() : deadline;
    }

    @Override
    public UniqueLabelList getLabels() {
        return new UniqueLabelList(labels);
    }

    /**
     * Replaces this 's labels with the labels in the argument label list.
     */
    public void setLabels(UniqueLabelList replacement) {
        labels.setLabels(replacement);
    }

    @Override
    public UniqueBookingList getBookings() {
        return new UniqueBookingList(bookings);
    }

    /**
     * Replaces this's bookings with the bookings in the argument booking list.
     */
    public void setBookings(UniqueBookingList replacement) {
        bookings.setBookings(replacement);
    }

    /**
     * Updates this  with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;
        this.setName(replacement.getTitle());
        this.setStartTime(replacement.getStartTime());
        this.setDeadline(replacement.getDeadline());
        this.setLabels(replacement.getLabels());
        this.setIsCompleted(replacement.isCompleted());
        this.setBookings(replacement.getBookings());
        this.setIsRecurring(replacement.isRecurring());
        this.setRecurrence(replacement.getRecurrence());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, startTime, deadline, labels);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public Boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public Boolean isRecurring() {
        return isRecurring;
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    @Override
    public Optional<Recurrence> getRecurrence() {
        return (recurrence == null) ? Optional.empty() : recurrence;
    }

    public void setRecurrence(Optional<Recurrence> recurrence) {
        if (recurrence.isPresent()) {
            this.recurrence = recurrence;
        } else {
            this.recurrence = Optional.empty();
        }
    }


}
