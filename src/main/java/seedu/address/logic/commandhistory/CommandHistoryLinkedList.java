package seedu.address.logic.commandhistory;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Class to keep track of previous command executions
 */
public class CommandHistoryLinkedList implements CommandHistory {
    private LinkedList<String> history;
    private ListIterator<String> cursor;
    private boolean hasDirection = false;
    private boolean isTraversingBack = true;

    public CommandHistoryLinkedList() {
        history = new LinkedList<String>();
        resetIterator();
    }

    @Override
    public void addCommand(String command) {
        history.addFirst(command);
        resetIterator();
    }

    @Override
    public String previous() {
        if (cursor.hasNext()) {
            if (hasDirection && !isTraversingBack) {
                cursor.next();
            }

            if (cursor.hasNext()) {
                hasDirection = true;
                isTraversingBack = true;
                return cursor.next();
            }
        }
        return null;
    }

    @Override
    public String next() {
        if (cursor.hasPrevious()) {
            if (hasDirection && isTraversingBack) {
                cursor.previous();
            }
            if (cursor.hasPrevious()) {
                hasDirection = true;
                isTraversingBack = false;
                return cursor.previous();
            } else if (hasDirection && isTraversingBack) {
                //Reset the cursor to where it was before
                cursor.next();
            }
        }
        resetIterator();
        return null;
    }

    /**
     * Brings cursor back to the front
     */
    public void resetIterator() {
        cursor = history.listIterator(0);
        hasDirection = false;
    }
}
