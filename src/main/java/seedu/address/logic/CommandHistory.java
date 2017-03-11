package seedu.address.logic;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Class to keep track of previous command executions
 */
public class CommandHistory {
    private LinkedList<String> history;
    private ListIterator<String> cursor;
    private boolean hasDirection = false;
    private boolean isTraversingBack = true;

    public CommandHistory() {
        history = new LinkedList<String>();
        resetIterator();
    }

    /**
     * Adds a command which has been executed previously
     * @param command - previously executed
     */
    public void addCommand(String command) {
        history.addFirst(command);
        resetIterator();
    }

    /**
     * Returns a previously executed command
     */
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

    /**
     * Returns the next command (if any) if the user has previously iterated through his commands before
     */
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
