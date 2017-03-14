package seedu.ezdo.model;

import java.util.EmptyStackException;

/*
 * Array-based implementation for a stack with fixed size. Used for undo & redo stacks.
 * If stack goes past max capacity, the oldest item to be pushed is replaced.
 */
public class FixedStack<Item> {
    private int index;
    private Item[] array;

    public FixedStack(int capacity) {
        array = (Item[]) new Object[capacity];
        index = -1;
    }

    public void push(Item item) {
        index = (index + 1) % 5; // wraps around
        array[index] = item;
    }

    public Item pop() throws EmptyStackException {
        if (index == -1 || array[index] == null) {
            throw new EmptyStackException();
        }
        Item item = array[index];
        array[index] = null;
        if (index == 0) {
            index = array.length - 1;
        } else {
            index = index - 1;
        }
        return item;
    }

    public boolean isEmpty() {
        return array.length == 0;
    }
}
