package seedu.address.logic.undo;

import javafx.collections.ObservableList;
import seedu.address.model.datastructure.UndoPair;
import seedu.address.model.label.Label;
import seedu.address.model.task.ReadOnlyTask;

public interface Changeable {

    /**
     * Undoes an command executed previously
     */
    public UndoPair<ObservableList<ReadOnlyTask>, ObservableList<Label>> getUndoData();
}
