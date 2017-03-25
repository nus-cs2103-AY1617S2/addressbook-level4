//@@author A0131125Y
package seedu.toluist.storage;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import javafx.util.Pair;
import seedu.toluist.TestApp;
import seedu.toluist.commons.core.Config;
import seedu.toluist.commons.exceptions.DataStorageException;
import seedu.toluist.commons.util.FileUtil;
import seedu.toluist.commons.util.JsonUtil;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.testutil.TestUtil;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Tests for JsonStorage
 */
public class JsonStorageTest {
    private static final String TEST_STORAGE_FILE_PATH =
            FileUtil.getPath("./src/test/data/StorageTest/");
    private static final String EMPTY_JSON_DATA_FILE_PATH = TEST_STORAGE_FILE_PATH + "EmptyData.json";
    private static final String INVALID_JSON_DATA_FILE_PATH = TEST_STORAGE_FILE_PATH + "NotJsonFormatData.json";
    private static final String TYPICAL_JSON_DATA_FILE_PATH = TEST_STORAGE_FILE_PATH + "TypicalData.json";
    private static final String NON_EXISTING_FILE_PATH = TestUtil.getFilePathInSandboxFolder("non_existing.json");
    private static final String EXISTING_FILE_PATH = TestUtil.getFilePathInSandboxFolder("existing.json");
    private static final String INVALID_FILE_PATH = "////sfsfsf";
    private static final int ZILLION = 10000000;

    private JsonStorage storage;
    private TodoList emptyTodoList = new TodoList();
    private TodoList typicalTodoList = new TypicalTestTodoLists().getTypicalTodoList();
    private TodoList anotherTodoList;

    @Before
    public void setUp() {
        Config.getInstance().setTodoListFilePath(TestApp.SAVE_LOCATION_FOR_TESTING);
        storage = new JsonStorage();
        anotherTodoList = new TodoList();
        anotherTodoList.add(new Task("it's a task"));

        emptyTodoList.setStorage(storage);
        typicalTodoList.setStorage(storage);
        anotherTodoList.setStorage(storage);
    }

    @Test
    public void load_emptyData() {
        TodoList todoList = null;
        try {
            todoList = loadDataFromPath(EMPTY_JSON_DATA_FILE_PATH);
        } catch (DataStorageException e) {
            fail("Should not throw exception");
        }
        assertEquals(todoList, new TodoList());
    }

    @Test
    public void load_invalidJson() {
        try {
            loadDataFromPath(INVALID_JSON_DATA_FILE_PATH);
            fail("Should not reach here");
        } catch (DataStorageException e) {
            // good to go
        }
    }

    @Test
    public void load_typicalData() {
        TodoList todoList = null;
        try {
            todoList = loadDataFromPath(TYPICAL_JSON_DATA_FILE_PATH);
        } catch (DataStorageException e) {
            fail("Should not throw exception");
        }
        assertEquals(todoList, typicalTodoList);
    }

    @Test
    public void load_withPathEmptyData() throws DataStorageException {
        TodoList todoList = null;
        try {
            todoList = loadDataFromPath(EMPTY_JSON_DATA_FILE_PATH);
        } catch (DataStorageException e) {
            fail("Should not throw exception");
        }
        assertEquals(todoList, new TodoList());
        assertEquals(Config.getInstance().getTodoListFilePath(), EMPTY_JSON_DATA_FILE_PATH);
    }

    @Test
    public void load_withPathInvalidJson() {
        try {
            storage.load(INVALID_JSON_DATA_FILE_PATH);
            fail("Should not reach here");
        } catch (DataStorageException e) {
            // good to go
            assertEquals(Config.getInstance().getTodoListFilePath(), TestApp.SAVE_LOCATION_FOR_TESTING);
        }
    }

    @Test
    public void load_WithPathTypicalData() {
        TodoList todoList = null;
        try {
            todoList = loadDataFromPath(TYPICAL_JSON_DATA_FILE_PATH);
        } catch (DataStorageException e) {
            fail("Should not throw exception");
        }
        assertEquals(todoList, typicalTodoList);
        assertEquals(Config.getInstance().getTodoListFilePath(), TYPICAL_JSON_DATA_FILE_PATH);
    }

    @Test
    public void save_invalidPath() {
        assertFalse(saveDataToPath(new TodoList(), INVALID_FILE_PATH));
    }

    @Test
    public void save_nonExistingFile() throws DataStorageException, IOException {
        String path = NON_EXISTING_FILE_PATH;
        FileUtil.removeFile(new File(path));
        assertTrue(saveDataToPath(typicalTodoList, path));
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(path)));
    }

    @Test
    public void save_overwriteFile() throws DataStorageException, IOException {
        String path = EXISTING_FILE_PATH;
        FileUtil.createFile(new File(path));
        assertTrue(saveDataToPath(typicalTodoList, path));
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(path)));
    }

    @Test
    public void save_WithPathInvalidPath() {
        assertFalse(storage.save(new TodoList(), INVALID_FILE_PATH));
        assertEquals(Config.getInstance().getTodoListFilePath(), TestApp.SAVE_LOCATION_FOR_TESTING);
    }

    @Test
    public void save_WithPathNonExistingFile() throws DataStorageException, IOException {
        String path = NON_EXISTING_FILE_PATH;
        FileUtil.removeFile(new File(path));
        assertTrue(storage.save(typicalTodoList, path));
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(path)));
        assertEquals(Config.getInstance().getTodoListFilePath(), path);
    }

    @Test
    public void save_WithPathOverwriteFile() throws DataStorageException, IOException {
        String path = EXISTING_FILE_PATH;
        FileUtil.createFile(new File(path));
        assertTrue(storage.save(typicalTodoList, path));
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(path)));
        assertEquals(Config.getInstance().getTodoListFilePath(), path);
    }

    @Test
    public void move_toInvalidPath() throws DataStorageException, IOException {
        String oldPath = TestUtil.getFilePathInSandboxFolder("old.json");
        saveDataToPath(typicalTodoList, oldPath);
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(oldPath)));

        String newPath = "///fff";
        assertFalse(storage.move(newPath));
        assertEquals(Config.getInstance().getTodoListFilePath(), oldPath);
        assertEquals(JsonUtil.toJsonString(typicalTodoList), FileUtil.readFromFile(new File(oldPath)));
    }

    @Test
    public void move_toValidPath() throws DataStorageException, IOException {
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
    public void undo_IdenticalDataSavedMultipleTimes() {
        saveDataToPath(new TodoList(), TestApp.SAVE_LOCATION_FOR_TESTING);
        storage.save(typicalTodoList);
        storage.save(typicalTodoList);

        assertValidUndoRedoData(storage.undo(ZILLION), new TodoList(), 1);
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
     * Helper method to set storage path in config and load data from config-defined path
     * @param path new todo list data path
     * @return Optional of todo list
     * @throws DataStorageException
     */
    private TodoList loadDataFromPath(String path) throws DataStorageException {
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
