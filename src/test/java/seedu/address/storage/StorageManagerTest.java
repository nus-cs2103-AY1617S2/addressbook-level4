package seedu.address.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.TodoListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.ReadOnlyTodoList;
import seedu.address.model.TodoList;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.EventsCollector;
import seedu.address.testutil.TypicalTestTodos;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setUp() {
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"));
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
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlTodoListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlTodoListStorageTest} class.
         */
        TodoList original = new TypicalTestTodos().getTypicalTodoList();
        storageManager.saveTodoList(original);
        ReadOnlyTodoList retrieved = storageManager.readTodoList().get();
        assertEquals(original, new TodoList(retrieved));
    }

    @Test
    public void getTodoListFilePath() {
        assertNotNull(storageManager.getTodoListFilePath());
    }

    @Test
    public void handleTodoListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTodoListStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTodoListChangedEvent(new TodoListChangedEvent(new TodoList()));
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
        public void saveTodoList(ReadOnlyTodoList addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
