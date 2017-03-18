package seedu.ezdo.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.ezdo.model.todo.ReadOnlyTask;

/*
 * Checks for dates
 */
public class DateUtil {
    public static boolean isTaskDateValid(ReadOnlyTask task) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy H:mm");
        Date startDate = df.parse(task.getStartDate().toString());
        Date dueDate = df.parse(task.getDueDate().toString());
        if (task.getStartDate().toString().equals("") || task.getDueDate().toString().equals("")) {
            return true;
        }
        if (startDate.compareTo(dueDate) >= 0) {
            return false;
        }
        return true;
    }
}
