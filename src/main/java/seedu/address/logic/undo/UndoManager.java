package seedu.address.logic.undo;

import java.util.LinkedList;

import javafx.collections.ObservableList;
import seedu.address.model.datastructure.UndoPair;
import seedu.address.model.label.Label;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0162877N
/**
 * A singleton UndoManager that implements a linkedlist of undoPair objects which essentially stores the data
 */
public class UndoManager implements Undo {

    private static UndoManager instance = null;
    private LinkedList<UndoPair<ObservableList<ReadOnlyTask>, ObservableList<Label>>> storageHistory;

    private UndoManager() {
        storageHistory = new LinkedList<UndoPair<ObservableList<ReadOnlyTask>, ObservableList<Label>>>();
    }

    public static UndoManager getInstance() {
        if (instance == null) {
            instance = new UndoManager();
        }
        return instance;
    }

    public void addStorageHistory(ObservableList<ReadOnlyTask> currentTasks, ObservableList<Label> currentLabels) {
        this.storageHistory.addLast(
                new UndoPair<ObservableList<ReadOnlyTask>,
                ObservableList<Label>>(currentTasks, currentLabels));
    }

    public boolean isEmpty() {
        return storageHistory.isEmpty();
    }

    public void clear() {
        storageHistory.clear();
    }

    @Override
    public UndoPair<ObservableList<ReadOnlyTask>, ObservableList<Label>> getUndoData() {
        return storageHistory.removeLast();
    }

}
