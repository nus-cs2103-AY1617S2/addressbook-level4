package onlythree.imanager.commons.core;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.TimeZone;

//@@author A0140023E
/**
 * Date-time formats used for different purposes
 */
public class DateTimeFormats {
    /**
     * A compact format for CLI display
     */
    public static final DateTimeFormatter CLI_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
    /**
     * Format that is based on the user's locale that is reasonably compact
     */
    public static final DateTimeFormatter LOCALIZED_FORMAT =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    /**
     * Full format that preserves all information about a date-time
     */
    public static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * Format that can be used for testing. Works with Natty by converting the date-time to the
     * local date-time and omitting the time-zone information as Natty do not handle all time-zones correctly.
     * Milliseconds and nanoseconds are ignored as they are ignored by Natty.
     **/
    public static final DateTimeFormatter TEST_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * The system default time-zone.
     * @see ZoneId#systemDefault()
     * @see TimeZone#getDefault()
     */
    public static final ZoneId SYSTEM_TIME_ZONE = ZoneId.systemDefault();
}
