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
    private static final int ZILLION = 10000000;

    private JsonStorage storage;
    private TodoList emptyTodoList = new TodoList();
    private TodoList typicalTodoList = new TypicalTestTodoLists().getTypicalTodoList();
    private TodoList anotherTodoList;

    @Before
    public void setUp() {
        storage = new JsonStorage();
        anotherTodoList = new TodoList();
        anotherTodoList.add(new Task("it's a task"));

        emptyTodoList.setStorage(storage);
        typicalTodoList.setStorage(storage);
        anotherTodoList.setStorage(storage);
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
        assertEquals(todoListOptional.get(), typicalTodoList);
    }

    @Test
    public void save_invalidPath() {
        assertFalse(saveDataToPath(new TodoList(), "////sfsfsf"));
    }

    @Test
    public void save_nonExistingFile() throws IOException {
        String path = TestUtil.getFilePathInSandboxFolder("non_existing.json");
        FileUtil.removeFile(new File(path));
        assertTrue(saveDataToPath(typicalTodoList, path));
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(path)));
    }

    @Test
    public void save_overwriteFile() throws IOException {
        String path = TestUtil.getFilePathInSandboxFolder("existing.json");
        FileUtil.createFile(new File(path));
        assertTrue(saveDataToPath(typicalTodoList, path));
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(path)));
    }

    @Test
    public void move_toInvalidPath() throws IOException {
        String oldPath = TestUtil.getFilePathInSandboxFolder("old.json");
        saveDataToPath(typicalTodoList, oldPath);
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(oldPath)));

        String newPath = "///fff";
        assertFalse(storage.move(newPath));
        assertEquals(Config.getInstance().getTodoListFilePath(), oldPath);
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(oldPath)));
    }

    @Test
    public void move_toValidPath() throws IOException {
        String oldPath = TestUtil.getFilePathInSandboxFolder("old.json");
        saveDataToPath(typicalTodoList, oldPath);
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(oldPath)));

        String newPath = TestUtil.getFilePathInSandboxFolder("new.json");
        assertTrue(storage.move(newPath));
        assertEquals(Config.getInstance().getTodoListFilePath(), newPath);
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(newPath)));
        assertFalse(FileUtil.isFileExists(new File(oldPath)));
    }

    @Test
    public void undo_nothingToUndo() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        assertValidUndoRedoData(storage.undo(1), new TodoList(), 0);
    }

    @Test
    public void undo_multipleOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(typicalTodoList);
        storage.save(anotherTodoList);

        assertValidUndoRedoData(storage.undo(1), typicalTodoList, 1);
        assertValidUndoRedoData(storage.undo(1), new TodoList(), 1);
    }

    @Test
    public void undo_multipleTimesInOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(typicalTodoList);
        storage.save(anotherTodoList);

        assertValidUndoRedoData(storage.undo(2), new TodoList(), 2);
    }

    @Test
    public void undo_zillionTimesInOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(typicalTodoList);
        storage.save(anotherTodoList);

        assertValidUndoRedoData(storage.undo(ZILLION), new TodoList(), 2);
    }

    @Test
    public void redo_nothingToRedo() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        assertValidUndoRedoData(storage.redo(1), new TodoList(), 0);
    }

    @Test
    public void redo_multipleOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(typicalTodoList);
        storage.save(anotherTodoList);
        storage.undo(2);

        assertValidUndoRedoData(storage.redo(1), typicalTodoList, 1);
        assertValidUndoRedoData(storage.redo(1), anotherTodoList, 1);
    }

    @Test
    public void redo_multipleTimesInOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(typicalTodoList);
        storage.save(anotherTodoList);
        storage.undo(2);

        assertValidUndoRedoData(storage.redo(2), anotherTodoList, 2);
    }

    @Test
    public void redo_zillionTimesInOnce() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(typicalTodoList);
        storage.save(anotherTodoList);
        storage.undo(2);

        assertValidUndoRedoData(storage.redo(ZILLION), anotherTodoList, 2);
    }

    @Test
    public void redo_afterSave() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(typicalTodoList);
        storage.undo(1);
        storage.save(anotherTodoList);

        assertValidUndoRedoData(storage.redo(1), anotherTodoList, 0);
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

    /**
     * Helper method to verify if the undo/redo data is correct
     * @param data undo/redo data
     * @param expectedTodoList todolist expected
     * @param expectedUndoRedoTimes number of undo/redo times expected
     */
    private void assertValidUndoRedoData(Pair<TodoList, Integer> data, TodoList expectedTodoList, long
            expectedUndoRedoTimes) {
        assertEquals(data.getKey(), expectedTodoList);
        assertEquals(data.getKey().getStorage(), storage);
        assertEquals(data.getValue().longValue(), expectedUndoRedoTimes);
    }
}
