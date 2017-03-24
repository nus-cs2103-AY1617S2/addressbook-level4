package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * @author ryuus
 * Represents a Task's instruction in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 *
 */
public class Instruction {

    public static final String MESSAGE_INSTRUCTION_CONSTRAINTS =
            "Task instructions can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String INSTRUCTION_VALIDATION_REGEX = "[^\\s].*";

    public static final String DEFAULT_VALUE = "nothing yet";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Instruction(String instruction) throws IllegalValueException {
        assert instruction != null;
        if (!isValidInstruction(instruction)) {
            throw new IllegalValueException(MESSAGE_INSTRUCTION_CONSTRAINTS);
        }
        this.value = instruction;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidInstruction(String test) {
        return test.matches(INSTRUCTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Instruction // instanceof handles nulls
                && this.value.equals(((Instruction) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
