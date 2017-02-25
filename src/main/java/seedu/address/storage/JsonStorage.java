package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Optional;

import javafx.util.Pair;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TodoList;

/**
 * JsonStorage saves TodoList object to json file.
 */
public class JsonStorage implements Storage {
    private static JsonStorage instance;

    private String storagePath = "data/todolist.json";
    private ArrayDeque<String> historyStack = new ArrayDeque<>();
    private ArrayDeque<String> redoHistoryStack = new ArrayDeque<>();

    public static JsonStorage getInstance() {
        if (instance == null) {
            instance = new JsonStorage();
        }
        return instance;
    }

    public Optional<TodoList> load() {
        try {
            String jsonString = getDataJson().get();
            // addLast this to undo history if there's nothing here yet
            // This will be the very initial state
            if (historyStack.isEmpty()) {
                historyStack.addLast(jsonString);
            }
            return Optional.of(JsonUtil.fromJsonString(jsonString, TodoList.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean move(String storagePath) {
        String oldStoragePath = getStoragePath();

        Optional<TodoList> todoList = load();
        if (!todoList.isPresent()) {
            return false;
        }

        if (!save(todoList.get(), storagePath)) {
            return false;
        }
        setStoragePath(storagePath);
        FileUtil.removeFile(FileUtil.getFile(oldStoragePath));
        return true;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public boolean save(TodoList todoList) {
        if (!save(todoList, storagePath)) {
            return false;
        }
        try {
            historyStack.addLast(JsonUtil.toJsonString(todoList));
            redoHistoryStack.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean save(TodoList todoList, String storagePath) {
        try {
            final String jsonString = JsonUtil.toJsonString(todoList);
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
        int steps = times;
        while (steps > 0 && historyStack.size() > 1) {
            redoHistoryStack.addLast(historyStack.pollLast());
            steps -= 1;
        }
        TodoList todoList = todoListFromJson(historyStack.peekLast()).get();
        // So as to not clear the redo history
        save(todoList, getStoragePath());
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
        save(todoList, getStoragePath());
        return new Pair<>(todoList, times - steps);
    }

    private Optional<TodoList> todoListFromJson(String json) {
        try {
            return Optional.of(JsonUtil.fromJsonString(json, TodoList.class));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Optional<String> getDataJson() {
        try {
            return Optional.of(FileUtil.readFromFile(getStorageFile()));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private File getStorageFile() {
        return new File(getStoragePath());
    }

    private void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
