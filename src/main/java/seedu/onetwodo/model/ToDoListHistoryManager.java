package seedu.onetwodo.model;

import java.util.Stack;

import seedu.onetwodo.commons.exceptions.EmptyHistoryException;

//@@author A0135739W
/**
 * Represents the saved history of ToDoLists.
 */
public class ToDoListHistoryManager implements ToDoListHistory {

    private Stack<ToDoList> previousToDoLists;
    private Stack<ToDoList> nextToDoLists;

    public ToDoListHistoryManager () {
        this.previousToDoLists = new Stack<ToDoList>();
        this.nextToDoLists = new Stack<ToDoList>();
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

    @Override
    public boolean isUndoHistoryEmpty() {
        return previousToDoLists.empty();
    }

    @Override
    public boolean isRedoHistoryEmpty() {
        return nextToDoLists.empty();
    }
}
