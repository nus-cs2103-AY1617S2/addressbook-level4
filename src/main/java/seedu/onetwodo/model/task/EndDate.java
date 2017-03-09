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
    public EndDate(String input) throws IllegalValueException {
        super(input);
        defaultDateTime.withHour(23).withMinute(59).withSecond(59);
        String trimmedInput = input.trim();
        this.localDateTime = DateTimeParser.parse(trimmedInput, defaultDateTime);
        this.value = createDisplayValue(localDateTime);
    }
    
    /**
     * 
     * @param localDateTime input optional LocalDateTime
     * @return String to be displayed to user.
     */
    @Override
    public String createDisplayValue(Optional<LocalDateTime> localDateTime) {
        if(!localDateTime.isPresent()) {
            return "";
        } else {
            return "end: " + localDateTime.get().toLocalDate().toString() 
                    + " " + localDateTime.get().toLocalTime().toString();
        }
    }
    
}
