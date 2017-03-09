package seedu.typed.logic.parser;

import java.util.Optional;

/**
 * Validates format of parsed date. Empty dates are considered valid.
 * @author Peixuan
 *
 */

public class DateValidator extends ArgumentValidator {

    public DateValidator(Optional<String> date) {
        this.validationRegex = "\\d{2}\\/\\d{2}\\/\\d{4}";
        this.messageConstraints = "Task date should be in the format DD/MM/YYYY";
        this.arg = date;
    }

}

