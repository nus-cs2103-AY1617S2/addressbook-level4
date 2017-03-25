package seedu.opus.ui;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.opus.commons.core.LogsCenter;

public class UserInputHistory {

    private LinkedList<String> commandHistory;
    private ListIterator<String> iterator;
    private final Logger logger = LogsCenter.getLogger(UserInputHistory.class);
    private String current;

    public UserInputHistory() {
        this.commandHistory = new LinkedList<String>();
        resetIterator();
    }

    public void saveUserInput(String input) {
        commandHistory.addFirst(input);
        resetIterator();
        logger.info("Capturing user input: " + input);
    }

    public void resetIterator() {
        iterator = commandHistory.listIterator();
        current = null;
    }

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
