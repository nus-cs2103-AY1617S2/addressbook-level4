//@@author A0139248X
package seedu.ezdo.model;

import java.util.EmptyStackException;

/**
 * Array-based implementation for a stack with fixed size. Used for undo & redo stacks.
 * If stack goes past max capacity, the oldest item that was pushed is replaced.
 */
public class FixedStack<T> {

    private static final int STARTING_INDEX = -1;

    private int index;
    private T[] array;

    public FixedStack(int capacity) {
        array = (T[]) new Object[capacity];
        index = STARTING_INDEX;
    }

    public void push(T item) {
        index = (index + 1) % ModelManager.STACK_CAPACITY; // wraps around
        array[index] = item;
    }

    public T pop() throws EmptyStackException {
        if (index == STARTING_INDEX || array[index] == null) {
            throw new EmptyStackException();
        }
        T item = array[index];
        array[index] = null;
        if (index == 0) { // top of stack is 0, need to wrap around
            index = array.length - 1;
        } else {
            index = index - 1;
        }
        return item;
    }

    public boolean isEmpty() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                return false;
            }
        }
        return true;
    }

    public void clear() {
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        index = STARTING_INDEX;
    }
}
