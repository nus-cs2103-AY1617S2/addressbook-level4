package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TodoList;

/**
 * JsonStorage saves TodoList object to json file.
 */
public class JsonStorage implements Storage {
    private static JsonStorage instance;

    private String storagePath = "data/todolist.json";
    private ArrayList<String> undoHistory = new ArrayList<>();
    private int redoIndex = 0;

    public static JsonStorage getInstance() {
        if (instance == null) {
            instance = new JsonStorage();
        }
        return instance;
    }

    public Optional<TodoList> load() {
        try {
            String jsonString = getDataJson().get();
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
        return save(todoList, storagePath);
    }

    private boolean save(TodoList todoList, String storagePath) {
        try {
            final String jsonString = JsonUtil.toJsonString(todoList);
            Optional<String> previousTodoJson = getDataJson();
            FileUtil.writeToFile(FileUtil.getFile(storagePath), jsonString);
            if (previousTodoJson.isPresent()) {
                undoHistory.add(previousTodoJson.get());
            }
            clearRedo();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Assume that the serialized history in undoHistory & redoHistory are reliable
     * @param times
     * @return
     */
    public Optional<TodoList> undo(int times) {
        int undoHistoryIndex = Math.max(0, undoHistory.size() - times);
        return getUndoedTodoList(undoHistoryIndex);
    }

    public Optional<TodoList> redo(int times) {
        int undoHistoryIndex = Math.min(undoHistory.size() + redoIndex + times, undoHistory.size());
        return getUndoedTodoList(undoHistoryIndex);
    }

    private void clearRedo() {
        while (redoIndex < 0) {
            undoHistory.remove(undoHistory.size() - 1);
            redoIndex += 1;
        }
    }

    private Optional<TodoList> getUndoedTodoList(int undoHistoryIndex) {
        try {
            TodoList todoList = JsonUtil.fromJsonString(undoHistory.get(undoHistoryIndex), TodoList.class);
            redoIndex = undoHistoryIndex - undoHistory.size();
            return Optional.of(todoList);
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
