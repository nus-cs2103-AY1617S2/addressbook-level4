package seedu.address.logic.undo;

import javafx.collections.ObservableList;
import seedu.address.model.datastructure.UndoPair;
import seedu.address.model.label.Label;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0162877N
/**
 * Interface for undo command
 */
public interface Undo {

    /**
     * Undoes an command executed previously
     * @return UndoPair which contains the previous tasks and labels
     */
    UndoPair<ObservableList<ReadOnlyTask>, ObservableList<Label>> getUndoData();

    /**
     * Add old task state and old label state into undo storage history
     * @param currentTaskState - before execution
     * @param currentLabel - before execution
     */
    void addStorageHistory(ObservableList<ReadOnlyTask> currentTaskState, ObservableList<Label> currentLabelState);

    /**
     * Return true if the undo data structure is empty and false otherwise
     */
    boolean isEmpty();

    /**
     * Clears the data in the undo data structure
     */
    void clear();
}
