package seedu.address.commons.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0163848R
public class DateUtil {
    public static Date parse(String rawDate) throws IllegalValueException {
        try {
            Parser parser = new Parser();
            List<DateGroup> groups = parser.parse(rawDate);
            Date date = groups.get(0).getDates().get(0);
            return date;
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalValueException(seedu.address.model.task.Date.MESSAGE_DATE_CONSTRAINTS);
        }
    }
    
    public static Date add(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
}
