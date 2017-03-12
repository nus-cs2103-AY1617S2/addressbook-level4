package seedu.toluist.storage;

import java.util.Optional;

import javafx.util.Pair;
import seedu.toluist.model.TodoList;

/**
 * Interface for TodoListStorage that saves/loads TodoList data
 */
public interface TodoListStorage {
    /**
     * Serialize a todolist to the disk
     * @param todoList todolist to be saved
     * @return true if the saving was successful, false otherwise
     */
    boolean save(TodoList todoList);

    /**
     * Load todo list data from disk
     * @return Optional.of(todoList) if the todo list can be loaded, Optional.empty() otherwise
     */
    Optional<TodoList> load();

    /**
     * Move the todo list data to somewhere else
     * @param todoListStoragePath
     * @return true if the moving was successful, false otherwise
     */
    boolean move(String todoListStoragePath);

    /**
     * Undo the todolist by a number of times
     * @param times number of times to undo
     * @return Pair of todolist, the resulting todo list and an integer denoting the number of actual undo times
     */
    Pair<TodoList, Integer> undo(int times);

    /**
     * Redo the todolist by a number of times
     * @param times number of times to redo
     * @return Pair of todolist, the resulting todo list and an integer denoting the number of actual redo times
     */
    Pair<TodoList, Integer> redo(int times);
}
