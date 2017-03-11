package seedu.typed.logic.parser;

import java.util.Optional;

import seedu.typed.commons.exceptions.IllegalValueException;

/**
 * Validates format of parsed date. Empty dates are considered valid.
 * @author Peixuan
 *
 */

public abstract class ArgumentValidator {
    protected String validationRegex;
    protected String messageConstraints;

    protected Optional<String> arg;
    protected String validArg;

    /**
     * Validates the given argument
     * @param arg
     * @throws IllegalValueException if given argument is not of a valid format
     */
    public void validate(Optional<String> arg) throws IllegalValueException {
        if (arg.isPresent()) {
            isValidArg(arg.get().trim());
        }
    }

    /**
     * Returns true if a given argument is in a valid format.
     */
    public void isValidArg(String arg) throws IllegalValueException {
        if (!arg.matches(validationRegex)) {
            throw new IllegalValueException(messageConstraints);
        }
        validArg = arg;
    }

    public String getValidArg() {
        return validArg;
    }

}

