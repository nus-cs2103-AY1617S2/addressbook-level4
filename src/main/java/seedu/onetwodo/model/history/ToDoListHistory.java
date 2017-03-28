package seedu.onetwodo.model.history;

import seedu.onetwodo.commons.exceptions.EmptyHistoryException;
import seedu.onetwodo.model.ToDoList;

//@@author A0135739W
/**
 * The API of the Model component.
 */
public interface ToDoListHistory {
    void saveAsPreviousToDoList (ToDoList toDoList);
    void saveAsPreviousToDoListAndClearRedoHistory(ToDoList toDoList);
    void saveAsNextToDoList(ToDoList toDoList);
    ToDoList getPreviousToDoList() throws EmptyHistoryException;
    ToDoList getNextToDoList() throws EmptyHistoryException;
    boolean isUndoHistoryEmpty();
    boolean isRedoHistoryEmpty();
}
