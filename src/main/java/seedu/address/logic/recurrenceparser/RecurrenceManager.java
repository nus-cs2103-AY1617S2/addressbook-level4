package seedu.address.logic.recurrenceparser;

import java.util.Calendar;
import java.util.Date;

import seedu.address.model.task.Recurrence;

//@@author A0105287E
/**
 *
 * Parses string into interval and returns the next date for a task
 *
 */
public class RecurrenceManager implements RecurrenceParser {

    public static final String MESSAGE_INVALID_INTERVAL = "Invalid interval entered.";

    @Override
    public Date getNextDate(Date oldDate, Recurrence recurrence) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(oldDate);
        cal.add(recurrence.interval, recurrence.value);
        return cal.getTime();
    }

    @Override
    public int getInterval(String input) {
        int interval;
        switch (input) {
        case "year": case "years":
            interval = Calendar.YEAR;
            break;
        case "month": case "months":
            interval = Calendar.MONTH;
            break;
        case "day": case "days":
            interval = Calendar.DATE;
            break;
        case "hour": case "hours":
            interval = Calendar.HOUR;
            break;
        case "minutes": case "minute":
            interval = Calendar.MINUTE;
            break;
        case "seconds": case "second":
            interval = Calendar.SECOND;
            break;
        default:
            throw new IllegalArgumentException(MESSAGE_INVALID_INTERVAL);
        }
        return interval;
    }


}
