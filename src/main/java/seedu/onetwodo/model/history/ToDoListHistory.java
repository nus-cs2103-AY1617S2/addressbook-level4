package seedu.onetwodo.model.history;

import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.model.task.ReadOnlyTask;

//@@author A0135739W
/**
 * The API of the ToDoListHistory.
 */
public interface ToDoListHistory {
    void saveUndoInformationAndClearRedoHistory(String commandWord, ReadOnlyTask task, ToDoList toDoList);
    void saveUndoInformationAndClearRedoHistory(String commandWord, ToDoList toDoList);
    void saveUndoInformation(ToDoList toDoList);
    void saveRedoInformation(ToDoList toDoList);
    ToDoList getPreviousToDoList();
    ToDoList getNextToDoList();
    String getUndoFeedbackMessageAndTransferToRedo();
    String getRedoFeedbackMessageAndTransferToUndo();
    boolean hasUndoHistory();
    boolean hasRedoHistory();
}
