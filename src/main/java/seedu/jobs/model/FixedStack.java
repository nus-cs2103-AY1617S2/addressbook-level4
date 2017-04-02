package seedu.jobs.model;

import java.util.Stack;

/**
 * Data structure to store modified tasks.
 * @author Yue
 *
 * @param <T>
 */
//@@author A0164440M
public class FixedStack<T> extends Stack<T> {

    private int maxSize = 10;

    public FixedStack() {
        super();
    }

    public FixedStack(int size) {
        super();
        this.maxSize = size;
    }

    // Fixed stack has fixed size which only stores the latest data.
    // The older date will be removed from fixed stack.
    @Override
    public T push(T object) {
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push(object);
    }

}
//@@author
