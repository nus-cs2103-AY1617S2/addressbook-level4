package seedu.address.storage;

import seedu.address.model.TodoList;

import java.io.IOException;

/**
 * Created by louis on 22/2/17.
 */
public interface Storage {
    public void save(TodoList todoList) throws IOException;

    public TodoList load() throws IOException;
}
