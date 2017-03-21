package seedu.todolist.logic;

import seedu.todolist.logic.commands.Command;
import seedu.todolist.model.ReadOnlyToDoList;

public class CommandAndState {
    Command command;
    ReadOnlyToDoList beforeState;
    ReadOnlyToDoList afterState;


    public CommandAndState(Command command, ReadOnlyToDoList beforeState, ReadOnlyToDoList afterState) {
        this.command = command;
        this.beforeState = beforeState;
        this.afterState = afterState;
    }

    public Command getCommand() {
        return command;
    }

    public ReadOnlyToDoList getStateBeforeCommand() {
        return beforeState;
    }

    public ReadOnlyToDoList getStateAfterCommand() {
        return afterState;
    }

    public void setCurrentState(ReadOnlyToDoList afterState) {
        this.afterState = afterState;
    }





}
