package seedu.address.storage;

import java.io.File;
import java.io.IOException;
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

    public static JsonStorage getInstance() {
        if (instance == null) {
            instance = new JsonStorage();
        }
        return instance;
    }

    public Optional<TodoList> load() {
        try {
            final String jsonString = FileUtil.readFromFile(getStorageFile());
            return Optional.of(JsonUtil.fromJsonString(jsonString, TodoList.class));
        } catch (IOException e) {
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
            FileUtil.writeToFile(FileUtil.getFile(storagePath), jsonString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private File getStorageFile() {
        return new File(getStoragePath());
    }

    private void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
