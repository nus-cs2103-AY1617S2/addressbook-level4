package seedu.address.storage;

import java.io.IOException;

import seedu.address.model.TodoList;

/**
 * Interface for Storage that saves/loads TodoList object
 */
public interface Storage {
    void save(TodoList todoList) throws IOException;

    TodoList load() throws IOException;
}
