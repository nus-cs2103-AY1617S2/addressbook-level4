package seedu.toluist.storage;

import java.util.Optional;

import javafx.util.Pair;
import seedu.toluist.model.TodoList;

/**
 * Interface for Storage that saves/loads TodoList data
 */
public interface Storage {
    boolean save(TodoList todoList);

    Optional<TodoList> load();

    boolean move(String path);

    String getStoragePath();

    Pair<TodoList, Integer> undo(int times);

    Pair<TodoList, Integer> redo(int times);
}
