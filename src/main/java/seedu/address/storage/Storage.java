package seedu.address.storage;

import java.util.Optional;

import seedu.address.model.TodoList;

/**
 * Interface for Storage that saves/loads TodoList data
 */
public interface Storage {
    boolean save(TodoList todoList);

    Optional<TodoList> load();

    boolean move(TodoList todoList, String path);
}
