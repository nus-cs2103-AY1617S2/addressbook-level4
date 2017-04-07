//@@author A0139248X
package seedu.ezdo.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.ezdo.model.todo.ReadOnlyTask;

/**
 * Utility methods for dates
 */
public class DateUtil {

    private static final String COMPARE_DATE_STRINGS_ACCEPTED_FORMAT = "dd/MM/yyyy HH:mm";
    private static final String INVALID_DATE_FORMAT_MESSAGE = "The date format should not be invalid.";

    private static final int COMPARE_RESULT_LESS_THAN = -1;
    private static final int COMPARE_RESULT_MORE_THAN = 1;
    private static final int COMPARE_RESULT_EQUAL = 0;

    /**
     * Checks whether a task's dates are valid
     *
     * @return true if the start date is earlier than or equal to the due date OR if either date is empty
     * @throws ParseException if any task date cannot be parsed
     */
    public static boolean isTaskDateValid(ReadOnlyTask task) throws ParseException {
        assert task != null;
        String taskStartDate = task.getStartDate().toString();
        String taskDueDate = task.getDueDate().toString();
        final boolean isStartDateMissing = taskStartDate.isEmpty();
        final boolean isDueDateMissing = taskDueDate.isEmpty();
        if (isStartDateMissing || isDueDateMissing) {
            return true;
        }
        SimpleDateFormat df = new SimpleDateFormat(COMPARE_DATE_STRINGS_ACCEPTED_FORMAT);
        Date startDate = df.parse(taskStartDate);
        Date dueDate = df.parse(taskDueDate);
        return (startDate.compareTo(dueDate) <= 0);
    }


    //@@author A0138907W
    /**
     * Compares two dates strings. Both strings must be in the format dd/MM/yyyy hh:mm.
     * Empty strings are always considered to be of lower value than non-empty strings.
     *
     * @return An int representing the comparison result of the two date strings.
     * @throws ParseException if any of the date strings cannot be parsed.
     */
    public static int compareDateStrings(String dateString1, String dateString2, Boolean isSortedAscending) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(COMPARE_DATE_STRINGS_ACCEPTED_FORMAT);
        Date date1 = null;
        Date date2 = null;
        if (dateString1.isEmpty() || dateString2.isEmpty()) {
            return handleEmptyDates(dateString1, dateString2);
        }
        try {
            date1 = dateFormat.parse(dateString1);
            date2 = dateFormat.parse(dateString2);
        } catch (ParseException pe) {
            assert false : INVALID_DATE_FORMAT_MESSAGE;
        }
        int result = date1.compareTo(date2);

        // If the sort order is descending, return a negative value to invert the order.
        return (isSortedAscending) ? result : -result;
    }

    /**
     * Handle empty dates such that they are always considered lower in value, so that they show at the bottom of the
     * list.
     *
     * @return An int representing the comparison result of the two date strings.
     */
    private static int handleEmptyDates(String dateString1, String dateString2) {
        assert dateString1.isEmpty() || dateString2.isEmpty();

        if (dateString1.isEmpty() && dateString2.isEmpty()) {
            return COMPARE_RESULT_EQUAL;
        } else if (dateString1.isEmpty()) {
            return COMPARE_RESULT_MORE_THAN;
        } else {
            return COMPARE_RESULT_LESS_THAN;
        }
    }

}
