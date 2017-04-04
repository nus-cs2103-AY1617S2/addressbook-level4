package seedu.doit.model;

import java.util.Stack;
import java.util.logging.Logger;

import seedu.doit.commons.core.LogsCenter;

public class InputStack {
    private static final Stack<String> mainStack = new Stack<String>();
    private static Stack<String> upStack = new Stack<String>();
    private static final Stack<String> downStack = new Stack<String>();
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

    public void addToUpStack(String in) {
        upStack.push(in);
    }

    public void addToDownStack(String in) {
        downStack.push(in);
    }

    public void addToMainStack(String in) {
        mainStack.push(in);
        clearDownStack();
    }

    /**
     * When up is pressed in command box
     *
     * @return new input if stack is not empty else return the same input
     */
    public String pressedUp(String input) {
        if (mainStack.isEmpty()) {
            return input;
        } else if (downStack.isEmpty()) {
            upStack = (Stack<String>) mainStack.clone();
        }
        if (upStack.isEmpty()) {
            return input;
        }
        if (!input.trim().isEmpty()) {
            addToDownStack(input);
        }
        String newInput = upStack.pop();
        return newInput;
    }

    /**
     * When down is pressed in command box
     *
     * @return new input if stack is not empty else return the same input
     */
    public String pressedDown(String input) {
        if (downStack.isEmpty()) {
            return input;
        }
        upStack.push(input);
        String newInput = upStack.pop();
        return newInput;
    }

    /**
     * Clears Down stack
     */
    public void clearDownStack() {
        downStack.clear();
    }

    /**
     * Clears Up stack
     */
    public void clearUpStack() {
        upStack.clear();
    }
}
