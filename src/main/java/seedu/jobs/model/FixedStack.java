package seedu.jobs.model;

import java.util.Stack;

/**
 * Data structure to store modified tasks.
 * @author Yue
 *
 * @param <T>
 */
public class FixedStack<T> extends Stack<T> {

    private int maxSize = 10;

    public FixedStack() {
        super();
    }

    public FixedStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public T push(T object) {
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push(object);
    }
}
