//@@author A0138909R
package seedu.doit.model;

import java.util.Stack;
import java.util.logging.Logger;

import seedu.doit.commons.core.LogsCenter;

public class InputStack {
    public static final String EMPTY_STRING = "";
    private static final Stack<String> mainStack = new Stack<String>();
    private static Stack<String> upStack = new Stack<String>();
    private static Stack<String> downStack = new Stack<String>();
    private static final Logger logger = LogsCenter.getLogger(InputStack.class);
    private static InputStack instance = null;

    protected InputStack() {
    }

    public static InputStack getInstance() {
        if (instance == null) {
            instance = new InputStack();
        }
        return instance;
    }

    public void addToUpStack(String input) {
        upStack.push(input);
    }

    public void addToDownStack(String input) {
        downStack.push(input);
    }

    public void addToMainStack(String input) {
        if (!input.trim().isEmpty()) {
            mainStack.push(input);
            logger.info("downstack cleared");
            clearDownStack();
        }
    }

    /**
     * When up is pressed in command box
     *
     * @return new input if stack is not empty else return the same input
     */
    public String pressedUp(String input) {
        if (mainStack.isEmpty()) {
            logger.info("UP main stack is empty");
            return input;
        } else if (downStack.isEmpty()) {
            logger.info("UP down stack is empty");
            upStack = (Stack<String>) mainStack.clone();
        }
        if (upStack.isEmpty()) {
            logger.info("UP up stack is empty");
            return input;
        }
        logger.info("UP adding (" + input + ") to downstack");
        addToDownStack(input);
        logger.info("UP up " + upStack.peek() + " down " + downStack.peek());
        String newInput = upStack.pop();
        logger.info("UP output (" + newInput + ")");
        return newInput;
    }

    /**
     * When down is pressed in command box
     *
     * @return new input if stack is not empty else return the same input
     */
    public String pressedDown(String input) {
        if (downStack.isEmpty()) {
            logger.info("DOWN down stack is empty");
            return EMPTY_STRING;
        }
        logger.info("DOWN adding (" + input + ") to upstack");
        addToUpStack(input);
        logger.info("DOWN up " + upStack.peek() + " down " + downStack.peek());
        String newInput = downStack.pop();
        logger.info("DOWN output (" + newInput + ")");
        return newInput;
    }

    /**
     * Clears Down stack
     */
    public void clearDownStack() {
        downStack.clear();
    }

}
