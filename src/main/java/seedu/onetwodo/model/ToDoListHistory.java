package seedu.onetwodo.model;

import seedu.onetwodo.commons.exceptions.EmptyHistoryException;

//@@author A0135739W
/**
 * The API of the Model component.
 */
public interface ToDoListHistory {
    void saveToDoList (ToDoList toDoList);
    ToDoList getMostRecentToDoList() throws EmptyHistoryException;
}
