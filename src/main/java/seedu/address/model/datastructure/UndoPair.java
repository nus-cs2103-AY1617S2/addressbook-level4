package seedu.address.model.datastructure;

//@@author A0162877N
/**
 * This class stores the command and the list of task as a pair
 */
public class UndoPair<Command, Data> {
    private final Command left;
    private final Data right;

    public UndoPair(Command left, Data right) {
        this.left = left;
        this.right = right;
    }

    public Command getLeft() {
        return this.left;
    }

    public Data getRight() {
        return this.right;
    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UndoPair)) {
            return false;
        }
        UndoPair pair = (UndoPair) o;
        return this.left.equals(pair.getLeft()) && this.right.equals(pair.getRight());
    }
}
