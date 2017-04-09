//@@author A0162011A
package seedu.toluist.dispatcher;

import java.util.ArrayList;

import seedu.toluist.commons.util.StringUtil;

public class CommandHistoryList {
    private ArrayList<String> commandHistory;
    private int historyPointer = 0;
    private String currentCommand;

    public CommandHistoryList() {
        commandHistory = new ArrayList();
        currentCommand = StringUtil.EMPTY_STRING;
    }

    public ArrayList<String> getCommandHistory() {
        return commandHistory;
    }

    public void recordCommand(String command) {
        commandHistory.add(command);
        historyPointer = commandHistory.size();
    }

    public String movePointerDown() {
        if (historyPointer != commandHistory.size()) {
            historyPointer++;
        }

        if (historyPointer == commandHistory.size()) {
            return currentCommand;
        }
        return commandHistory.get(historyPointer);
    }

    public String movePointerUp(String currentCommand) {
        if (historyPointer == commandHistory.size()) {
            this.currentCommand = currentCommand;
        }
        if (commandHistory.size() == 0) {
            return this.currentCommand;
        }

        if (historyPointer != 0) {
            historyPointer--;
        }
        return commandHistory.get(historyPointer);
    }
}
