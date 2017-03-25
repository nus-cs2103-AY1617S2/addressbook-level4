package seedu.tasklist.model;


//@author A0139747N
/**
 * Creates a Pair object which will be used for undo/redo command.
 */
public class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}
