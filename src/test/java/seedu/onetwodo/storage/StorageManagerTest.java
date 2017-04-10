package seedu.onetwodo.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.onetwodo.commons.events.model.ToDoListChangedEvent;
import seedu.onetwodo.commons.events.storage.DataSavingExceptionEvent;
import seedu.onetwodo.model.ReadOnlyToDoList;
import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.model.UserPrefs;
import seedu.onetwodo.testutil.EventsCollector;
import seedu.onetwodo.testutil.TypicalTestTasks;

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
    public void toDoListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlToDoListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlToDoListStorageTest} class.
         */
        ToDoList original = new TypicalTestTasks().getTypicalToDoList();
        storageManager.saveToDoList(original);
        ReadOnlyToDoList retrieved = storageManager.readToDoList().get();
        assertEquals(original, new ToDoList(retrieved));
    }

    @Test
    public void getToDoListFilePath() {
        assertNotNull(storageManager.getToDoListFilePath());
    }

    @Test
    public void handleToDoListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlToDoListStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleToDoListChangedEvent(new ToDoListChangedEvent(new ToDoList()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlToDoListStorageExceptionThrowingStub extends XmlToDoListStorage {

        public XmlToDoListStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveToDoList(ReadOnlyToDoList toDoList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
