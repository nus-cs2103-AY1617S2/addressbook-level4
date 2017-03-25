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
    }

    public Optional<String> getPreviousUserInput() {
        String previousInput = null;
        if (iterator.hasNext()) {
            previousInput = iterator.next();
        }
        return Optional.ofNullable(previousInput);
    }

    public Optional<String> getPrecedingUserInput() {
        String precedingInput = null;
        if (iterator.hasPrevious()) {
            precedingInput = iterator.previous();
        }
        return Optional.ofNullable(precedingInput);
    }
}
