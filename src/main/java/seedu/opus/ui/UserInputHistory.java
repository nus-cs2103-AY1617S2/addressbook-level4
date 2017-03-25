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
        userInputHistory.addFirst(input);
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
        String previousInput;
        if (!iterator.hasNext()) {
            return Optional.empty();
        }
        previousInput = iterator.next();
        if (!previousInput.equals(current)) {
            current = previousInput;
            return Optional.ofNullable(current);
        } else {
            current = iterator.next();
            return Optional.ofNullable(current);
        }
    }

    /**
     * Returns the preceding user input relative to current history iteration
     * @return Preceding user input Optional<String> if available, null otherwise
     */
    public Optional<String> getPrecedingUserInput() {
        String precedingInput = null;
        if (!iterator.hasPrevious()) {
            return Optional.empty();
        }
        precedingInput = iterator.previous();
        if (!precedingInput.equals(current)) {
            current = precedingInput;
            return Optional.ofNullable(current);
        } else {
            current = iterator.previous();
            return Optional.ofNullable(current);
        }
    }
}
