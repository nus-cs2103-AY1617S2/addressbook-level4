package seedu.address.model.booking;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.dateparser.DateTimeManager;
import seedu.address.logic.dateparser.DateTimeParser;

//@@author A0162877N
public class Booking {

    public static final String MESSAGE_BOOKING_CONSTRAINTS =
            "Input dates are in the wrong format, please try again!";
    public static DateTimeParser dtParser;

    public String bookingID;
    public boolean confirm;
    public Date startTime;
    public Date endTime;

    public Booking(String date) throws CommandException {
        dtParser = new DateTimeManager();
        if (isDateParseable(date)) {
            List<DateGroup> dateList = dtParser.parse(date);
            if (dateList.get(0).getDates().size() > 1) {
                this.startTime = dateList.get(0).getDates().get(0);
                this.endTime = dateList.get(0).getDates().get(1);
            } else {
                throw new CommandException(MESSAGE_BOOKING_CONSTRAINTS);
            }
        }
    }

    public Booking(Date startDate, Date endDate) {
        dtParser = new DateTimeManager();
        this.startTime = startDate;
        this.endTime = endDate;
    }

    public Date getBookingStartDate() {
        return this.startTime;
    }

    public Date getBookingEndDate() {
        return this.endTime;
    }

    public boolean isDateParseable(String date) {
        return !dtParser.parse(date).isEmpty();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return startTime.toString() + " to " + endTime.toString();
    }

}
