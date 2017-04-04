package seedu.todolist.commons.events.storage;

import seedu.todolist.commons.events.BaseEvent;
import seedu.todolist.model.ReadOnlyTodoList;

/*
 * Indicates the save file location has changed.
 */
public class SaveFilePathChangedEvent extends BaseEvent {
    public final String saveFilePath;
    public final ReadOnlyTodoList data;
    
    public SaveFilePathChangedEvent(String saveFilePath, ReadOnlyTodoList newData) {
        this.saveFilePath = saveFilePath;
        this.data = newData;
    }

    @Override
    public String toString() {
        return saveFilePath;
    }
}
