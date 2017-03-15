package seedu.toluist.storage;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import seedu.toluist.TestApp;
import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.util.FileUtil;
import seedu.toluist.commons.util.JsonUtil;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TestUtil;
import seedu.toluist.testutil.TypicalTestTodoLists;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for JsonStorage
 */
public class JsonStorageTest {
    private static final String TEST_STORAGE_FILE_PATH =
            FileUtil.getPath("./src/test/data/StorageTest/");

    private JsonStorage storage;
    private TodoList todoList = new TypicalTestTodoLists().getTypicalTodoList();
    private TodoList anotherTodoList;
    private int zillion = 10000000;

    @Before
    public void setUp() {
        storage = new JsonStorage();
        anotherTodoList = new TodoList();
        anotherTodoList.add(new Task("it's a task"));
    }

    @Test
    public void load_emptyData() {
        Optional<TodoList> todoListOptional = loadDataFromPath(TEST_STORAGE_FILE_PATH + "EmptyData.json");
        assertTrue(todoListOptional.isPresent());
        assertEquals(todoListOptional.get(), new TodoList());
    }

    @Test
    public void load_invalidJson() {
        Optional<TodoList> todoListOptional = loadDataFromPath(TEST_STORAGE_FILE_PATH + "NotJsonFormatData.json");
        assertFalse(todoListOptional.isPresent());
    }

    @Test
    public void load_typicalData() {
        Optional<TodoList> todoListOptional = loadDataFromPath(TEST_STORAGE_FILE_PATH + "TypicalData.json");
        assertTrue(todoListOptional.isPresent());
        assertEquals(todoListOptional.get(), todoList);
    }

    @Test
    public void save_invalidPath() {
        assertFalse(saveDataToPath(new TodoList(), "////sfsfsf"));
    }

    @Test
    public void save_nonExistingFile() throws IOException {
        String path = TestUtil.getFilePathInSandboxFolder("non_existing.json");
        FileUtil.removeFile(new File(path));
        assertTrue(saveDataToPath(todoList, path));
        assertEquals(JsonUtil.toJsonString(todoList), FileUtil.readFromFile(new File(path)));
    }

    @Test
    public void save_overwriteFile() throws IOException {
        String path = TestUtil.getFilePathInSandboxFolder("existing.json");
        FileUtil.createFile(new File(path));
        assertTrue(saveDataToPath(todoList, path));
        assertEquals(JsonUtil.toJsonString(todoList), FileUtil.readFromFile(new File(path)));
    }

    @Test
    public void move_toInvalidPath() throws IOException {
        String oldPath = TestUtil.getFilePathInSandboxFolder("old.json");
        saveDataToPath(todoList, oldPath);
        assertEquals(JsonUtil.toJsonString(todoList), FileUtil.readFromFile(new File(oldPath)));

        String newPath = "///fff";
        assertFalse(storage.move(newPath));
        assertEquals(Config.getInstance().getTodoListFilePath(), oldPath);
        assertEquals(JsonUtil.toJsonString(todoList), FileUtil.readFromFile(new File(oldPath)));
    }

    @Test
    public void move_toValidPath() throws IOException {
        String oldPath = TestUtil.getFilePathInSandboxFolder("old.json");
        saveDataToPath(todoList, oldPath);
        assertEquals(JsonUtil.toJsonString(todoList), FileUtil.readFromFile(new File(oldPath)));

        String newPath = TestUtil.getFilePathInSandboxFolder("new.json");
        assertTrue(storage.move(newPath));
        assertEquals(Config.getInstance().getTodoListFilePath(), newPath);
        assertEquals(JsonUtil.toJsonString(todoList), FileUtil.readFromFile(new File(newPath)));
        assertFalse(FileUtil.isFileExists(new File(oldPath)));
    }

    @Test
    public void undo_nothingToUndo() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        Pair<TodoList, Integer> undoedData = storage.undo(1);
        assertEquals(undoedData.getKey(), new TodoList());
        assertEquals(undoedData.getValue().longValue(), 0);
    }

    @Test
    public void undo_multipleOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(todoList);
        storage.save(anotherTodoList);

        Pair<TodoList, Integer> undoedData = storage.undo(1);
        assertEquals(undoedData.getKey(), todoList);
        assertEquals(undoedData.getValue().longValue(), 1);

        undoedData = storage.undo(1);
        assertEquals(undoedData.getKey(), new TodoList());
        assertEquals(undoedData.getValue().longValue(), 1);
    }

    @Test
    public void undo_multipleTimesInOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(todoList);
        storage.save(anotherTodoList);

        Pair<TodoList, Integer> undoedData = storage.undo(2);
        assertEquals(undoedData.getKey(), new TodoList());
        assertEquals(undoedData.getValue().longValue(), 2);
    }

    @Test
    public void undo_zillionTimesInOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(todoList);
        storage.save(anotherTodoList);

        Pair<TodoList, Integer> undoedData = storage.undo(zillion);
        assertEquals(undoedData.getKey(), new TodoList());
        assertEquals(undoedData.getValue().longValue(), 2);
    }

    @Test
    public void redo_nothingToRedo() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        Pair<TodoList, Integer> undoedData = storage.undo(1);
        assertEquals(undoedData.getKey(), new TodoList());
        assertEquals(undoedData.getValue().longValue(), 0);
    }

    @Test
    public void redo_multipleOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(todoList);
        storage.save(anotherTodoList);
        storage.undo(2);

        Pair<TodoList, Integer> redoedData = storage.redo(1);
        assertEquals(redoedData.getKey(), todoList);
        assertEquals(redoedData.getValue().longValue(), 1);

        redoedData = storage.redo(1);
        assertEquals(redoedData.getKey(), anotherTodoList);
        assertEquals(redoedData.getValue().longValue(), 1);
    }

    @Test
    public void redo_multipleTimesInOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(todoList);
        storage.save(anotherTodoList);
        storage.undo(2);

        Pair<TodoList, Integer> redoedData = storage.redo(2);
        assertEquals(redoedData.getKey(), anotherTodoList);
        assertEquals(redoedData.getValue().longValue(), 2);
    }

    @Test
    public void redo_zillionTimesInOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(todoList);
        storage.save(anotherTodoList);
        storage.undo(2);

        Pair<TodoList, Integer> redoedData = storage.redo(zillion);
        assertEquals(redoedData.getKey(), anotherTodoList);
        assertEquals(redoedData.getValue().longValue(), 2);
    }

    @Test
    public void redo_afterSave() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(todoList);
        storage.undo(1);
        storage.save(anotherTodoList);

        Pair<TodoList, Integer> redoedData = storage.redo(1);
        assertEquals(redoedData.getKey(), anotherTodoList);
        assertEquals(redoedData.getValue().longValue(), 0);
    }

    /**
     * Helper method to load data from a path
     * @param path new todo list data path
     * @return Optional of todo list
     */
    private Optional<TodoList> loadDataFromPath(String path) {
        Config.getInstance().setTodoListFilePath(path);
        return storage.load();
    }

    /**
     * Helper method to save data to a path
     * @param todoList todolist data
     * @param path new todo list data path
     * @return Optional of todo list
     */
    private boolean saveDataToPath(TodoList todoList, String path) {
        Config.getInstance().setTodoListFilePath(path);
        return storage.save(todoList);
    }
}
