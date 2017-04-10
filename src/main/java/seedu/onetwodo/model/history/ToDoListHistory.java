package seedu.onetwodo.model.history;

import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.model.task.ReadOnlyTask;

//@@author A0135739W
/**
 * The API of the ToDoListHistory.
 */
public interface ToDoListHistory {

    /**
     * saves undo information and clears the redo stack
     * @param commandWord the string representation of the command
     * @param taskBeforeEdit the task before edit, involved in the command
     * @param taskAfterEdit the task after edit, involved in the command
     * @param the current toDoList to be saved for undo
     */
    void saveUndoInformationAndClearRedoHistory(String commandWord, ReadOnlyTask taskBeforeEdit,
            ReadOnlyTask taskAfterEdit, ToDoList toDoList);
    /**
     * saves undo information and clears the redo stack
     * @param commandWord the string representation of the command
     * @param task the task involved in the command
     * @param the current toDoList to be saved for undo
     */
    void saveUndoInformationAndClearRedoHistory(String commandWord, ReadOnlyTask task, ToDoList toDoList);

    /**
     * saves undo information and clears the redo stack
     * @param commandWord the string representation of the command
     * @param toDoList the current toDoList to be saved for undo
     */
    void saveUndoInformationAndClearRedoHistory(String commandWord, ToDoList toDoList);

    /**
     * saves undo information
     * @param toDoList the current toDoList to be saved for undo
     */
    void saveUndoInformation(ToDoList toDoList);

    /**
     * saves redo information
     * @param toDoList the current toDoList to be saved in redo stack
     */
    void saveRedoInformation(ToDoList toDoList);

    /**
     * returns the latest previous toDoList
     * @return ToDoList the latest previous toDoList from the undo stack
     */
    ToDoList getPreviousToDoList();

    /**
     * returns the latest next toDoList
     * @return ToDoList the latest next toDoList from the redo stack
     */
    ToDoList getNextToDoList();

    /**
     * returns the feedback message for undo and transfer to information to redo stack
     * @return String the latest previous toDoList from the undo stack
     */
    String getUndoFeedbackMessageAndTransferToRedo();

    /**
     * returns the feedback message for redo and transfer to information to undo stack
     * @return String the latest previous toDoList from the undo stack
     */
    String getRedoFeedbackMessageAndTransferToUndo();

    /**
     * checks if the undo history is empty
     * @return true if undo history is empty
     */
    boolean hasUndoHistory();

    /**
     * checks if the redo history is empty
     * @return true if redo history is empty
     */
    boolean hasRedoHistory();
}
