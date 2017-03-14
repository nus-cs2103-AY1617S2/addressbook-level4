package seedu.ezdo.model;

import java.util.EmptyStackException;

/*
 * Array-based implementation for a stack with fixed size. Used for undo & redo stacks.
 * If stack goes past max capacity, the oldest item to be pushed is replaced.
 */
public class FixedStack<T> {
    private int index;
    private T[] array;

    public FixedStack(int capacity) {
        array = (T[]) new Object[capacity];
        index = -1;
    }

    public void push(T item) {
        index = (index + 1) % 5; // wraps around
        array[index] = item;
        System.out.println("item added at index: " + index);
    }

    public T pop() throws EmptyStackException {
        if (index == -1 || array[index] == null) {
            throw new EmptyStackException();
        }
        T item = array[index];
        array[index] = null;
        System.out.println(item + " at index " + index + " removed");
        if (index == 0) {
            index = array.length - 1;
            System.out.println("index now: " + index);
        } else {
            index = index - 1;
            System.out.println("index now: " + index);
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
        index = -1;
    }
}
