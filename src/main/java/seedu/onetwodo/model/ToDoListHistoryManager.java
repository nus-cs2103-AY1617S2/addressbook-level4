package seedu.onetwodo.model;

import java.util.Stack;

import seedu.onetwodo.commons.exceptions.EmptyHistoryException;
import seedu.onetwodo.model.task.ReadOnlyTask;

//@@author A0135739W
/**
 * Represents the saved history of ToDoLists.
 */
public class ToDoListHistoryManager implements ToDoListHistory {
    private static final String COMMAND_FORMATTER = " %1$s";

    private Stack<ToDoList> previousToDoLists;
    private Stack<ToDoList> nextToDoLists;
    private Stack<String> counterCommandHistory;

    public ToDoListHistoryManager () {
        this.previousToDoLists = new Stack<ToDoList>();
        this.nextToDoLists = new Stack<ToDoList>();
        this.counterCommandHistory = new Stack<String>();
    }

    public void saveUndoInformationAndClearRedoHistory(String counterCommandWord, ReadOnlyTask task,
            ToDoList toDoList) {
        String counterCommandWithFormatter = counterCommandWord.concat(COMMAND_FORMATTER);
        counterCommandHistory.push(String.format(counterCommandWithFormatter, task));
        previousToDoLists.push(toDoList);
        nextToDoLists.clear();
    }

    //This overloaded method is specially for clear command.
    public void saveUndoInformationAndClearRedoHistory(ToDoList toDoList) {
        counterCommandHistory.push("Restore OneTwoDo");
        previousToDoLists.push(toDoList);
        nextToDoLists.clear();
    }

    @Override
    public void saveAsPreviousToDoList(ToDoList toDoList) {
        ToDoList copiedCurrentToDoList = new ToDoList(toDoList);
        previousToDoLists.push(copiedCurrentToDoList);
    }

    @Override
    public void saveAsPreviousToDoListAndClearRedoHistory(ToDoList toDoList) {
        previousToDoLists.push(toDoList);
        nextToDoLists.clear();
    }

    @Override
    public void saveAsNextToDoList(ToDoList toDoList) {
        ToDoList copiedCurrentToDoList = new ToDoList(toDoList);
        nextToDoLists.push(copiedCurrentToDoList);
    }

    @Override
    public ToDoList getPreviousToDoList() throws EmptyHistoryException {
        if (!isUndoHistoryEmpty()) {
            return previousToDoLists.pop();
        } else {
            throw new EmptyHistoryException("OneTwoDo cannot be undone anymore");
        }
    }

    @Override
    public ToDoList getNextToDoList() throws EmptyHistoryException {
        if (!isRedoHistoryEmpty()) {
            return nextToDoLists.pop();
        } else {
            throw new EmptyHistoryException("OneTwoDo cannot be redone anymore");
        }
    }

    public String getPreviousCounterCommand() {
        return counterCommandHistory.pop();
    }

    @Override
    public boolean isUndoHistoryEmpty() {
        return previousToDoLists.empty();
    }

    @Override
    public boolean isRedoHistoryEmpty() {
        return nextToDoLists.empty();
    }
}
