package seedu.toluist.storage;

import java.io.IOException;
import java.util.Optional;

import javafx.util.Pair;
import seedu.toluist.model.TodoList;

/**
 * Interface for TodoListStorage that saves/loads TodoList data
 */
public interface TodoListStorage {
    /**
     * Serialize a todolist to the disk, using a default storage path
     * supplied by the singleton Config
     * @param todoList todolist to be saved
     * @return true if the saving was successful, false otherwise
     */
    boolean save(TodoList todoList);

    /**
     * Serialize a todolist to the disk at the specified storage path
     * If saving is successful, the todolist storage path in the config will be changed
     * accordingly
     * @param todoList todolist to be saved
     * @param todoListStoragePath storage path to save todo list data at
     * @return true if the saving was successful, false otherwise
     */
    boolean save(TodoList todoList, String todoListStoragePath);

    /**
     * Load todo list data from disk, using a default storage path
     * supplied by the singleton Config
     * @return todo list data
     * @throws IOException if data cannot be loaded
     */
    TodoList load() throws IOException;

    /**
     * Load todo list data from disk from the specified storage path
     * If loading is successful, the todolist storage path in the config will be changed
     * accordingly
     * @return todo list data
     * @param todoListStoragePath storage path to load todo list data from
     * @throws IOException if data cannot be loaded
     */
    TodoList load(String todoListStoragePath) throws IOException;

    /**
     * Move the todo list data to somewhere else
     * If moving is successful, the todolist storage path in the config will be changed
     * accordingly
     * @param todoListStoragePath storage path to move todo list data to
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
