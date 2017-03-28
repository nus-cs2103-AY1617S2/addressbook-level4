package seedu.task.logic;

import java.util.LinkedList;
import java.util.ListIterator;

//@@author A0141928B
public class CommandList {
    private static CommandList instance;
    //List of commands in reverse chronological order (most recent command first)
    private LinkedList<String> commandHistory;
    //To iterate through commandHistory
    public ListIterator<String> iterator;

    private CommandList() {
        commandHistory = new LinkedList<String>();
        iterator = commandHistory.listIterator();
    }

    public static CommandList getInstance() {
        if (instance == null) {
            instance = new CommandList();
        }
        return instance;
    }

    public void addToList(String command) {
        commandHistory.addFirst(command);
        resetIterator();
    }

    public void resetIterator() {
        iterator = commandHistory.listIterator();
    }
}
