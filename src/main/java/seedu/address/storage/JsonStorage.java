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

    public boolean save(TodoList todoList) {
        return save(todoList, storagePath);
    }

    public Optional<TodoList> load() {
        try {
            final String jsonString = FileUtil.readFromFile(getStorageFile());
            return Optional.of(JsonUtil.fromJsonString(jsonString, TodoList.class));
        } catch (IOException e) {
            return null;
        }
    }

    public boolean move(TodoList todoList, String storagePath) {
        String oldStoragePath = storagePath;
        if (!save(todoList, storagePath)) {
            return false;
        }
        setStoragePath(storagePath);
        FileUtil.removeFile(FileUtil.getFile(oldStoragePath));
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

    private File getStorageFile() {
        return new File(storagePath);
    }

    private void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }
}
