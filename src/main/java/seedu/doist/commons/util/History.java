package seedu.doist.commons.util;

import java.util.EmptyStackException;
import java.util.Stack;

public class History<State> {

    /**
     * history is the main stack that contains the past states
     * overflow acts as a buffer, when history is popped off but needs to be refilled
     */
    private Stack<State> history = new Stack<State>();
    private Stack<State> overflow = new Stack<State>();

    public History() {
        history = new Stack<State>();
        overflow = new Stack<State>();
    }


    /**
     * Method that pushes new state onto the top of the stack
     * @param state
     * Returns a boolean if successful
     */
    public boolean addToHistory(State state) {
        return history.add(state);
    }


    /**
     * Method that returns the previous state entered by user
     * Return null if history is empty
     */
    public State getPreviousState() {
        try {
            State previousState = history.pop();
            overflow.push(previousState);
            return previousState;
        } catch (EmptyStackException e) {
            return null;
        }
    }

    /**
     * Method that returns the next state entered by the user, if it exists
     * Returns null if overflow is empty
     */
    public State getNextState() {
        try {
            history.push(overflow.pop());
            return overflow.peek();
        } catch (EmptyStackException e) {
            return null;
        }
    }

    /**
     * Move the current state and everything in the overflow stack
     * back into history stack
     */
    public void restore() {
        while (!overflow.isEmpty()) {
            history.push(overflow.pop());
        }
    }

    /**
     * forget all the states in the overflow except the peek
     */
    public void forgetStatesAfter() {
        if (!overflow.isEmpty()) {
            history.push(overflow.pop());
        }
        overflow.clear();
    }

    public boolean isAtMostRecentState() {
        return overflow.isEmpty();
    }
}


