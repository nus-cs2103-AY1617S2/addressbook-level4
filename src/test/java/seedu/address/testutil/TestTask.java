package seedu.address.testutil;

import java.util.Optional;

import seedu.address.model.booking.UniqueBookingList;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Recurrence;
import seedu.address.model.task.Title;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Title title;
    private Optional<Deadline> startTime;
    private Optional<Deadline> deadline;
    private UniqueLabelList labels;
    private Boolean isCompleted;
    private UniqueBookingList bookings;
    private Boolean isRecurring;
    private Optional<Recurrence> recurrence;

    public TestTask() {
        labels = new UniqueLabelList();
        bookings = new UniqueBookingList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.title = taskToCopy.getTitle();
        this.startTime = taskToCopy.getStartTime();
        this.deadline = taskToCopy.getDeadline();
        this.labels = taskToCopy.getLabels();
        this.isCompleted = taskToCopy.isCompleted();
        this.bookings = taskToCopy.getBookings();
        this.isRecurring = taskToCopy.isRecurring();
        this.recurrence = taskToCopy.getRecurrence();
    }

    public void setTitle(Title name) {
        this.title = name;
    }

    public void setDeadline(Optional<Deadline> deadline) {
        this.deadline = deadline;
    }

    public void setLabels(UniqueLabelList labels) {
        this.labels = labels;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Optional<Deadline> getDeadline() {
        return deadline == null ? Optional.empty() : deadline;
    }

    @Override
    public UniqueLabelList getLabels() {
        return labels;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@Author A0105287E
    /**
     * Returns an AddCommand that can be used to create this task.
     */
    public String getAddCommand() {
        String addCommand;
        if (this.getStartTime().isPresent() && this.getDeadline().isPresent()) {
            addCommand = getAddCommandWithInterval();
        } else if (this.getDeadline().isPresent()) {
            addCommand = getAddCommandWithDeadline();
        } else {
            addCommand = getAddCommandWithoutDate();
        }
        return addCommand;
    }

    //@@Author A0105287E
    private String getAddCommandWithoutDate() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().title + " ");
        this.getLabels().asObservableList().stream().forEach(s -> sb.append("#" + s.labelName + " "));
        return sb.toString();
    }

    //@@Author A0105287E
    private String getAddCommandWithDeadline() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().title + " ");
        sb.append(" by " + this.getDeadline().get().toString() + " ");
        this.getLabels().asObservableList().stream().forEach(s -> sb.append("#" + s.labelName + " "));
        return sb.toString();
    }

  //@@Author A0105287E
    private String getAddCommandWithInterval() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().title + " ");
        sb.append(" from " + this.getStartTime().get().toString() + " ");
        sb.append(" to " + this.getDeadline().get().toString() + " ");
        this.getLabels().asObservableList().stream().forEach(s -> sb.append("#" + s.labelName + " "));
        return sb.toString();
    }

    public void setStartTime(Optional<Deadline> startTime) {
        this.startTime = startTime;
    }

    @Override
    public Optional<Deadline> getStartTime() {
        return startTime == null ? Optional.empty() : startTime;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public Boolean isCompleted() {
        return isCompleted == null ? Boolean.FALSE : isCompleted;
    }

    @Override
    public UniqueBookingList getBookings() {
        return bookings;
    }

    public void setBookings(UniqueBookingList uniqueBookingList) {
        bookings = uniqueBookingList;
    }

    @Override
    public Boolean isRecurring() {
        return isRecurring == null ? Boolean.FALSE : isRecurring;
    }

    @Override
    public Optional<Recurrence> getRecurrence() {
        return recurrence == null ? Optional.empty() : recurrence;
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public void setRecurrence (Optional<Recurrence> recurrence) {
        this.recurrence = recurrence;
    }
}
