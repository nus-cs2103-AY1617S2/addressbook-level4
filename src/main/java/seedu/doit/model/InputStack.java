//@@author A0138909R
package seedu.doit.model;

import java.util.Stack;
import java.util.logging.Logger;

import seedu.doit.commons.core.LogsCenter;

public class InputStack {
    private static final String LOGGER_DOWN_DOWN_STACK_IS_EMPTY = "DOWN down stack is empty";
    private static final String LOGGER_UP_UP_STACK_IS_EMPTY = "UP up stack is empty";
    private static final String LOGGER_UP_DOWN_STACK_IS_EMPTY = "UP down stack is empty";
    private static final String LOGGER_UP_MAIN_STACK_IS_EMPTY = "UP main stack is empty";
    private static final String LOGGER_DOWNSTACK_CLEARED = "downstack cleared";
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

    /**
     * Adds a input string into the upStack
     *
     * @param input
     */

    public void addToUpStack(String input) {
        upStack.push(input);
    }

    /**
     * Adds a input string into the downStack
     *
     * @param input
     */
    public void addToDownStack(String input) {
        downStack.push(input);
    }

    /**
     * Adds a input string into the main stack This is used whenever a input is
     * parsed
     *
     * @param input
     */
    public void addToMainStack(String input) {
        if (!input.trim().isEmpty()) {
            mainStack.push(input);
            logger.info(LOGGER_DOWNSTACK_CLEARED);
            clearDownStack();
        }
    }

    /**
     * When up is pressed in command box if the main stack is empty means that
     * it just started and down stack is also empty if the down stack is empty
     * but main stack is not means that UP key is not triggered after an input
     * is parsed so we need to clone main stack into upstack for the possible
     * DOWN key when upstack is empty but main stack is not means that the user
     * have pressed up until all inputs have been pushed to down stack
     *
     * @return new input if stack is not empty else return the same input
     */
    public String pressedUp(String input) {
        if (mainStack.isEmpty()) {
            logger.info(LOGGER_UP_MAIN_STACK_IS_EMPTY);
            return input;
        } else if (downStack.isEmpty()) {
            logger.info(LOGGER_UP_DOWN_STACK_IS_EMPTY);
            upStack = (Stack<String>) mainStack.clone();
        }
        if (upStack.isEmpty()) {
            logger.info(LOGGER_UP_UP_STACK_IS_EMPTY);
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
     * When down is pressed in command box when down stack is empty means that
     * the user have pressed down until all inputs have been pushed to up stack
     *
     * @return new input if stack is not empty else return an empty string to
     *         allow user to type their new input
     */
    public String pressedDown(String input) {
        if (downStack.isEmpty()) {
            logger.info(LOGGER_DOWN_DOWN_STACK_IS_EMPTY);
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
