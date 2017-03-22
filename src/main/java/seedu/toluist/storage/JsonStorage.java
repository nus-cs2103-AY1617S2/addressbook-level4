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

    public JsonStorage() {}

    public boolean save(TodoList todoList) {
        return save(todoList, Config.getInstance().getTodoListFilePath());
    }

    public boolean save(TodoList todoList, String storagePath) {
        if (!saveNotAffectingHistory(todoList, storagePath)) {
            return false;
        }
        // push current todo list json string into historyStack if the stack is empty

        try {
            historyStack.addLast(JsonUtil.toJsonString(todoList));
        } catch (JsonProcessingException e) {
            // Should not reach here
            e.printStackTrace();
        }
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
                historyStack.addLast(jsonString);
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

    private boolean saveNotAffectingHistory(TodoList todoList, String storagePath) {
        try {
            String jsonString = JsonUtil.toJsonString(todoList);
            FileUtil.writeToFile(FileUtil.getFile(storagePath), jsonString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

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
