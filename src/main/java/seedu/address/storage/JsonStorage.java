package seedu.address.storage;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TodoList;

/**
 * JsonStorage saves TodoList object to json file.
 */
public class JsonStorage implements Storage {
    private static final String STORAGE_FILE_PATH = "data/todolist.json";

    public void save(TodoList todoList) throws IOException {
        final File storageFile = getStorageFile();
        final String jsonString = JsonUtil.toJsonString(todoList);
        FileUtil.writeToFile(storageFile, jsonString);
    }

    public TodoList load() throws IOException {
        final String jsonString = FileUtil.readFromFile(getStorageFile());
        return JsonUtil.fromJsonString(jsonString, TodoList.class);
    }

    private File getStorageFile() {
        return new File(STORAGE_FILE_PATH);
    }
}
