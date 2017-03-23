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
    public void saveToDoList(ToDoList toDoList) {
        ToDoList copiedCurrentToDoList = new ToDoList(toDoList);
        previousToDoLists.push(copiedCurrentToDoList);
    }

    @Override
    public ToDoList getPreviousToDoList() throws EmptyHistoryException {
        if (!previousToDoLists.isEmpty()) {
            return previousToDoLists.pop();
        } else {
            throw new EmptyHistoryException("OneTwoDo ccannot be undone anymore");
        }
    }

    @Override
    public ToDoList getNextToDoList() throws EmptyHistoryException {
        if (!previousToDoLists.isEmpty()) {
            return previousToDoLists.pop();
        } else {
            throw new EmptyHistoryException("OneTwoDo ccannot be undone anymore");
        }
    }

}
