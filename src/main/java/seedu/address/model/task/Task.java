package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a Task
 */
public class Task {

    public String description;

    /**
     * To be used with json deserialisation
     */
    public Task() {}

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Task(String description) throws IllegalValueException {
        assert description != null;
        String trimmedDescription = description.trim();
        this.description = trimmedDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.description.equals(((Task) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return description;
    }

}
