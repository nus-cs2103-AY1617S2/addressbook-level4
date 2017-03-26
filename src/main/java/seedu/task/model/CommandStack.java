package seedu.task.model;

import java.util.Stack;

public class CommandStack {
    private Stack<String> commandStack = new Stack<String>();

    public void pushCommand(String command) {
        commandStack.push(command);
    }

    public String popCommand() {
        return commandStack.pop();
    }

    public boolean getCommandHistoryStatus() {
        return commandStack.empty();
    }
}
