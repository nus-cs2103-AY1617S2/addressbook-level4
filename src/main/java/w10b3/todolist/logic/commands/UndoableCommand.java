package w10b3.todolist.logic.commands;

import java.util.List;

import w10b3.todolist.model.ReadOnlyToDoList;

//@@author A0143648Y
public abstract class UndoableCommand extends Command {

    protected static List<ReadOnlyToDoList> previousToDoLists;
    protected static List<CommandResult> previousCommandResults;

    public abstract void updateUndoLists();
}
