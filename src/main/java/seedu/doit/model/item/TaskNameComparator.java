package seedu.doit.model.item;

public class TaskNameComparator implements TaskComparator {

    @Override
    public int compare(ReadOnlyTask t1, ReadOnlyTask t2) {
        return compareDone(t1, t2);
    }

    /**
     * Compares the current task with another Task other.
     * The current item is considered to be less than the other item
     * if it is done and the other is not done.
     */
    private int compareDone(ReadOnlyTask curr, ReadOnlyTask other) {
        if (curr.getIsDone() && !other.getIsDone()) {
            return 1;
        } else if (!curr.getIsDone() && other.getIsDone()) {
            return -1;
        }
        return compareItems(curr, other);
    }

    /**
     * Compares the current item with another item other. returns -1 if other
     * item is greater than current item return 0 is both items are equal return
     * 1 if other item is smaller than current item The ranking are as follows
     * from highest: 1) tasks 2) events 3) floating tasks If both have same
     * rankings, then compare names
     */
    private int compareItems(ReadOnlyTask curr, ReadOnlyTask other) {
        Integer currType = curr.getItemType();
        Integer otherType = other.getItemType();
        int compareInt = currType.compareTo(otherType);

        if (compareInt == 0) {
            return compareName(curr, other);
        }
        return compareInt;
    }

    private int compareName(ReadOnlyTask curr, ReadOnlyTask other) {
        return curr.getName().toString().compareToIgnoreCase(other.getName().toString());
    }
}
