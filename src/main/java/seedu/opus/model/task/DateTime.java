package seedu.opus.model.task;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import com.joestelmach.natty.Parser;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.logic.parser.DateTimeParser;

public class DateTime {

    /*
     * Represents a Task's dateTime in the task manager.
     * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
     */

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Task date time should be in the format of [Day]/[Month]/[Year] [Hour]:[Minute]";

    public final LocalDateTime dateTime;
    private static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    private static Parser parser = new Parser();
    private Clock clock;

    /**
     * Validates given dateTime.
     *
     * @throws IllegalValueException if given dateTime is invalid.
     */
    public DateTime(LocalDateTime dateTime) throws IllegalValueException {
        assert dateTime != null;

        this.clock = Clock.systemDefaultZone();
        this.dateTime = dateTime;
    }

    public DateTime(String dateTime) throws IllegalValueException {
        assert dateTime != null;
        this.clock = Clock.systemDefaultZone();
        this.dateTime =  DateTimeParser.parse(dateTime)
                .orElseThrow(() -> new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS));
    }

    /**
     * Checks if the DateTime is in the current week.
     *
     * @return true if the DateTime is in the current week
     */
    public boolean isCurrentWeek() {
        LocalDateTime now = LocalDateTime.now(clock);

        // 1 for Monday, 7 for Sunday
        int dayOfCurrentWeek = now.getDayOfWeek().getValue();

        LocalDateTime startOfWeek = now.minusDays(dayOfCurrentWeek);
        LocalDateTime endOfWeek = now.plusDays(7 - dayOfCurrentWeek);

        return dateTime.isAfter(startOfWeek) && dateTime.isBefore(endOfWeek);
    }

    /**
     * Returns true if a given date is a valid task dateTime.
     * @throws IllegalValueException
     */
    public static boolean isValidDateTime(String test) {
        return DateTimeParser.parse(test).isPresent();
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    public String toString() {
        return formatter.format(dateTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.dateTime.equals(((DateTime) other).dateTime)); // state check

    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
