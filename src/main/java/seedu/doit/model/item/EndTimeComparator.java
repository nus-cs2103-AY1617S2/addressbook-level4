package seedu.doit.model.item;

public class EndTimeComparator implements TaskComparator {

    @Override
    public int compare(Task t1, Task t2) {
        return compareDone(t1, t2);
    }

    /**
     * Compares the current task with another Task other.
     * The current item is considered to be less than the other item
     * if it is done and the other is not done.
     */
    private int compareDone(ReadOnlyTask curr, ReadOnlyTask other) {
        if ((curr.getIsDone() == true) && (other.getIsDone() == true)) {
            compareItems(curr, other);
        } else if ((curr.getIsDone() == true) && (other.getIsDone() == false)) {
            return 1;
        } else if ((curr.getIsDone() == false) && (other.getIsDone() == true)) {
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

        if (curr.isTask() && other.isTask()) {
            return compareName(curr, other);
        } else if (curr.isTask() && other.isEvent()) {
            return -1;
        } else if (curr.isTask() && other.isFloatingTask()) {
            return -1;
        }

        if (curr.isEvent() && other.isEvent()) {
            return compareStartTime(curr, other);
        } else if (curr.isEvent() && other.isTask()) {
            return 1;
        } else if (curr.isEvent() && other.isFloatingTask()) {
            return -1;
        }

        if (curr.isFloatingTask() && other.isFloatingTask()) {
            return compareName(curr, other);
        } else if (curr.isFloatingTask() && other.isTask()) {
            return 1;
        } else if (curr.isFloatingTask() && other.isEvent()) {
            return 1;
        }

        // Should never reach this
        return 0;
    }

    private int compareStartTime(ReadOnlyTask curr, ReadOnlyTask other) {
        return curr.getEndTime().compareTo(other.getEndTime());
    }

    private int compareName(ReadOnlyTask curr, ReadOnlyTask other) {
        return curr.getName().toString().compareToIgnoreCase(other.getName().toString());
    }

}
