package seedu.doist.logic.commands;

import java.util.Stack;

/**
 * Class to help maintain a stack of commands that have been entered by the user
 * @author Ram
 *
 */

public class CommandHistory {

    /**
     * history is the main stack that contains the commands (as strings) entered
     * overflow acts as a buffer, when history is popped off but needs to be refilled
     * current is the command viewed by the user
     */
    private static Stack<String> history = new Stack<String>();
    private static Stack<String> overflow = new Stack<String>();
    private static String current = null;

    public CommandHistory() {
        history = new Stack<String>();
        overflow = new Stack<String>();
        current = null;
    }


    /**
     * Method that pushes new command onto the top of the stack
     * @param command
     * Returns a boolean if successful
     */
    public static boolean addCommandHistory(String command) {
        return history.add(command);
    }


    /**
     * Method that returns the previous command entered by user
     * Returns null if history is empty
     */
    public static String getPreviousCommand() {
        if (history.isEmpty()) {
            return current;
        } else {
            if (current != null) {
                overflow.push(current);
            }
            current = history.pop();
            return current;
        }
    }

    /**
     * Method that returns the next command entered by the user, if it exists
     * Returns null if overflow is empty
     */
    public static String getNextCommand() {
        if (overflow.isEmpty()) {
            if (current != null) {
                history.add(current);
            }
            current = null;
            return current;
        } else {
            history.add(current);
            current = overflow.pop();
            return current;
        }
    }

    public static void restore() {
        if (current != null) {
            history.push(current);
            while (!overflow.isEmpty()) {
                history.push(overflow.pop());
            }
            current = null;
        }
    }

}
