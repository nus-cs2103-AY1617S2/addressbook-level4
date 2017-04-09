//@@author A0148087W
package seedu.opus.ui;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.opus.commons.core.LogsCenter;

//@@author A0148087W
/**
 * Stores and manage user inputs in ui.CommandBox
 */
public class UserInputHistory {

    private LinkedList<String> userInputHistory;
    private ListIterator<String> iterator;
    private final Logger logger = LogsCenter.getLogger(UserInputHistory.class);
    private String current;

    public UserInputHistory() {
        this.userInputHistory = new LinkedList<String>();
        resetIterator();
    }

    /**
     * Captures user input and reset iterator to include new input
     * @param input - User input String
     */
    public void saveUserInput(String input) {
        if (input.isEmpty()) {
            return;
        }
        userInputHistory.addFirst(input.trim());
        resetIterator();
        logger.info("Capturing user input: " + input);
    }

    /**
     * Rebuilds the iterator and reset current iteration
     */
    public void resetIterator() {
        iterator = userInputHistory.listIterator();
        current = null;
    }

    /**
     * Returns the previous user input relative to current history iteration
     * @return Previous user input Optional<String> if available, null otherwise
     */
    public Optional<String> getPreviousUserInput() {
        assert iterator != null;
        if (!iterator.hasNext()) {
            current = null;
            return Optional.empty();
        }

        String previousInput = iterator.next();

        if (!previousInput.equals(current)) {
            current = previousInput;
        } else if (iterator.hasNext()) {
            current = iterator.next();
        } else {
            current = null;
        }
        return Optional.ofNullable(current);
    }

    /**
     * Returns the preceding user input relative to current history iteration
     * @return Preceding user input Optional<String> if available, null otherwise
     */
    public Optional<String> getPrecedingUserInput() {
        assert iterator != null;
        if (!iterator.hasPrevious()) {
            current = null;
            return Optional.empty();
        }

        String precedingInput = iterator.previous();

        if (!precedingInput.equals(current)) {
            current = precedingInput;
        } else if (iterator.hasPrevious()) {
            current = iterator.previous();
        } else {
            current = null;
        }
        return Optional.ofNullable(current);
    }
}
