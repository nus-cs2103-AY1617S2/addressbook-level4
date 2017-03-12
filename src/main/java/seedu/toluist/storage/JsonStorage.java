package seedu.toluist.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Optional;

import javafx.util.Pair;
import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.util.FileUtil;
import seedu.toluist.commons.util.JsonUtil;
import seedu.toluist.model.TodoList;

/**
 * JsonStorage saves TodoList object to json file.
 */
public class JsonStorage implements TodoListStorage {
    private Config config = Config.getInstance();
    private ArrayDeque<String> historyStack = new ArrayDeque<>();
    private ArrayDeque<String> redoHistoryStack = new ArrayDeque<>();

    public JsonStorage() {}

    public Optional<TodoList> load() {
        try {
            String jsonString = getDataJson().get();
            // push todo list json string into historyStack if the stack is empty
            if (historyStack.isEmpty()) {
                historyStack.addLast(jsonString);
            }
            return Optional.of(JsonUtil.fromJsonString(jsonString, TodoList.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean move(String newStoragePath) {
        String oldStoragePath = config.getTodoListFilePath();

        Optional<TodoList> todoListOptional = load();
        if (!todoListOptional.isPresent()) {
            return false;
        }

        if (!saveNotAffectingHistory(todoListOptional.get(), newStoragePath)) {
            return false;
        }

        FileUtil.removeFile(FileUtil.getFile(oldStoragePath));

        config.setTodoListFilePath(newStoragePath);
        return config.save();
    }

    public boolean save(TodoList todoList) {
        if (!saveNotAffectingHistory(todoList, config.getTodoListFilePath())) {
            return false;
        }
        // push current todo list json string into historyStack if the stack is empty
        try {
            historyStack.addLast(JsonUtil.toJsonString(todoList));
            redoHistoryStack.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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

    /**
     * Assume that the serialized history in historyStack & redoHistoryStack are reliable
     * @param times
     * @return
     */
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

    private Optional<TodoList> todoListFromJson(String json) {
        try {
            return Optional.of(JsonUtil.fromJsonString(json, TodoList.class));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Read the json data of the current todo list
     * @return Optional.of(jsonString) if the data can be read, Optional.empty() otherwise
     */
    private Optional<String> getDataJson() {
        File storageFile = new File(Config.getInstance().getTodoListFilePath());
        try {
            return Optional.of(FileUtil.readFromFile(storageFile));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
