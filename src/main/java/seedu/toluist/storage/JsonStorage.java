//@@author A0131125Y
package seedu.toluist.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayDeque;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

import javafx.util.Pair;
import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.exceptions.DataStorageException;
import seedu.toluist.commons.util.FileUtil;
import seedu.toluist.commons.util.JsonUtil;
import seedu.toluist.model.TodoList;

/**
 * JsonStorage saves/loads TodoList object to/from json file.
 */
public class JsonStorage implements TodoListStorage {
    private Config config = Config.getInstance();
    private ArrayDeque<String> historyStack = new ArrayDeque<>();
    private ArrayDeque<String> redoHistoryStack = new ArrayDeque<>();

    public boolean save(TodoList todoList) {
        return save(todoList, Config.getInstance().getTodoListFilePath());
    }

    public boolean save(TodoList todoList, String storagePath) {
        if (!saveNotAffectingHistory(todoList, storagePath)) {
            return false;
        }
        addToHistory(todoList);
        redoHistoryStack.clear();

        Config.getInstance().setTodoListFilePath(storagePath);
        return true;
    }

    public TodoList load() throws DataStorageException {
        return load(Config.getInstance().getTodoListFilePath());
    }


    public TodoList load(String storagePath) throws DataStorageException {
        try {
            String jsonString = FileUtil.readFromFile(new File(storagePath));
            // push todo list json string into historyStack if the stack is empty
            if (historyStack.isEmpty()) {
                addToHistory(jsonString);
            }
            TodoList todoList = JsonUtil.fromJsonString(jsonString, TodoList.class);

            Config.getInstance().setTodoListFilePath(storagePath);
            return todoList;
        } catch (IOException | InvalidPathException e) {
            throw new DataStorageException(e.getMessage());
        }
    }

    public boolean move(String newStoragePath) {
        String oldStoragePath = config.getTodoListFilePath();

        TodoList todoList;
        try {
            todoList = load();
        } catch (DataStorageException e) {
            return false;
        }

        if (!saveNotAffectingHistory(todoList, newStoragePath)) {
            return false;
        }

        FileUtil.removeFile(FileUtil.getFile(oldStoragePath));

        config.setTodoListFilePath(newStoragePath);
        return config.save();
    }

    public Pair<TodoList, Integer> undo(int times) {
        assert historyStack.size() >= 1;
        int steps = times;
        while (steps > 0 && historyStack.size() > 1) {
            redoHistoryStack.addLast(historyStack.pollLast());
            steps -= 1;
        }
        TodoList todoList = todoListFromJson(historyStack.peekLast()).get();
        // So as to not clear the redo history
        saveNotAffectingHistory(todoList, config.getTodoListFilePath());
        return new Pair<>(todoList, times - steps);
    }

    public Pair<TodoList, Integer> redo(int times) {
        int steps = times;
        while (steps > 0 && redoHistoryStack.size() > 0) {
            historyStack.addLast(redoHistoryStack.pollLast());
            steps -= 1;
        }

        TodoList todoList = todoListFromJson(historyStack.peekLast()).get();
        // So as to not clear the redo history
        saveNotAffectingHistory(todoList, config.getTodoListFilePath());
        return new Pair<>(todoList, times - steps);
    }

    /**
     * Add todolist data to history, making sure that no identical todo list data appear consecutively
     * in history
     * @param todoList TodoList object
     */
    public void addToHistory(TodoList todoList) {
        try {
            addToHistory(JsonUtil.toJsonString(todoList));
        } catch (JsonProcessingException e) {
            // Should not reach here
            e.printStackTrace();
        }
    }

    /**
     * Add json representation of todolist data to history, making sure that no identical todo list data
     * appear consecutively in history
     * @param jsonString json representation of todolist data
     */
    public void addToHistory(String jsonString) {
        if (historyStack.isEmpty() || (!historyStack.getLast().equals(jsonString))) {
            historyStack.addLast(jsonString);
        }
    }

    /**
     * Save todo list to data file, but does not add it to the history stack
     * @param todoList todo list data
     * @param storagePath data file path
     * @return true if saving succeeds, otherwise false
     */
    private boolean saveNotAffectingHistory(TodoList todoList, String storagePath) {
        try {
            String jsonString = JsonUtil.toJsonString(todoList);
            FileUtil.writeToFile(FileUtil.getFile(storagePath), jsonString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Get optional of TodoList from a json string
     * @param json json serialisation for todo list data
     * @return optional of todolist if the data can be deserialized successfully, otherwise Optional.empty()
     */
    private Optional<TodoList> todoListFromJson(String json) {
        try {
            TodoList todoList = JsonUtil.fromJsonString(json, TodoList.class);
            // Inject self as storage dependency
            todoList.setStorage(this);
            return Optional.of(todoList);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
