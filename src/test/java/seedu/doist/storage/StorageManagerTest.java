package seedu.doist.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.doist.commons.events.model.AliasListMapChangedEvent;
import seedu.doist.commons.events.model.TodoListChangedEvent;
import seedu.doist.commons.events.storage.DataSavingExceptionEvent;
import seedu.doist.model.AliasListMap;
import seedu.doist.model.ReadOnlyAliasListMap;
import seedu.doist.model.ReadOnlyTodoList;
import seedu.doist.model.TodoList;
import seedu.doist.model.UserPrefs;
import seedu.doist.testutil.EventsCollector;
import seedu.doist.testutil.TypicalTestTasks;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setUp() {
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("cd"), getTempFilePath("prefs"));
    }


    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void todoListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlTodoListStorage} class.
         * More extensive testing of TodoList saving/reading is done in {@link XmlTodoListStorageTest} class.
         */
        TodoList original = new TypicalTestTasks().getTypicalTodoList();
        storageManager.saveTodoList(original);
        ReadOnlyTodoList retrieved = storageManager.readTodoList().get();
        assertEquals(original, new TodoList(retrieved));
    }

    @Test
    public void aliasListMapReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAliasListMapStorage} class.
         * More extensive testing of AliasListMap saving/reading is done in {@link XmlAliasListMapStorageTest} class.
         */
        AliasListMap original = new AliasListMap();
        original.setAlias("hello", "add");
        storageManager.saveAliasListMap(original);
        ReadOnlyAliasListMap retrieved = storageManager.readAliasListMap().get();
        assertEquals(original, new AliasListMap(retrieved));
    }

    @Test
    public void getTodoListFilePath() {
        assertNotNull(storageManager.getTodoListFilePath());
    }

    @Test
    public void handleTodoListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTodoListStorageExceptionThrowingStub("dummy"),
                                             new XmlAliasListMapStorage("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTodoListChangedEvent(new TodoListChangedEvent(new TodoList()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleAliasListMapChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTodoListStorage("dummy"),
                                             new XmlAliasListMapStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleAliasListMapChangedEvent(new AliasListMapChangedEvent(new AliasListMap()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTodoListStorageExceptionThrowingStub extends XmlTodoListStorage {

        public XmlTodoListStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTodoList(ReadOnlyTodoList todoList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAliasListMapStorageExceptionThrowingStub extends XmlAliasListMapStorage {

        public XmlAliasListMapStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveAliasListMap(ReadOnlyAliasListMap todoList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
