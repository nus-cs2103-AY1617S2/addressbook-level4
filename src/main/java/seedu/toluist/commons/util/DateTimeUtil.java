package seedu.toluist.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represent date nicely in human-readable format.
 */
public class DateTimeUtil {
    public static String toString(LocalDateTime dateTime) {
        String stringDateTime = dateTime.format(DateTimeFormatter.ofPattern("d MMM uuuu, h:m a"));
        return stringDateTime;
    }
}
