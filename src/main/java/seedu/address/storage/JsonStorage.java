package seedu.address.storage;

import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TodoList;

import java.io.File;
import java.io.IOException;

/**
 * Created by louis on 22/2/17.
 */
public class JsonStorage implements Storage {
    private static final String STORAGE_FILE_PATH = "todolist.json";

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
