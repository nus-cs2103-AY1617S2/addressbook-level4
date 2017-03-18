package seedu.address.model.task;

import java.util.Optional;

import seedu.address.model.label.UniqueLabelList;

public class Booking extends Task {

    public String bookingID;
    public boolean confirm;

    public Booking(ReadOnlyTask source) {
        super(source);
    }

    public Booking(Title title, Optional<Deadline> startTime, Optional<Deadline> deadline, boolean isCompleted,
            UniqueLabelList labels, String bookingID) {
        super(title, startTime, deadline, isCompleted, labels);
        this.bookingID = bookingID;
    }

    public String getBookingID() {
        return this.bookingID;
    }

    public boolean isConfirm() {
        return false;
    }

}
