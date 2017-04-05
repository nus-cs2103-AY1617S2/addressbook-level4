//@@author A0139343E
package seedu.onetwodo.model.task;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.parser.DateTimeParser;

/**
 * Represents a Task's end date in the toDo list.
 */
public class EndDate extends Date {

    /**
     * Create a end date.
     *
     * @throws IllegalValueException if given date toDo string is invalid.
     */
    public EndDate(String dateString) throws IllegalValueException {
        super(dateString);
        assert dateString != null;
        LocalDateTime newDefaultDateTime = defaultDateTime.withHour(23).withMinute(59);
        String trimmedInput = dateString.trim();
        this.localDateTime = DateTimeParser.parseDateTime(trimmedInput, newDefaultDateTime);
        this.value = createDisplayValue(localDateTime);
    }

    public EndDate(LocalDateTime localDateTime) {
        super(localDateTime);
        this.value = createDisplayValue(Optional.of(localDateTime));
    }

    /**
     *
     * @param localDateTime input optional LocalDateTime
     * @return String to be displayed to user.
     */
    @Override
    public String createDisplayValue(Optional<LocalDateTime> localDateTime) {
        if (!localDateTime.isPresent()) {
            return "";
        } else {
            return localDateTime.get().toLocalDate().toString() + " "
                    + localDateTime.get().toLocalTime().toString();
        }
    }
}
