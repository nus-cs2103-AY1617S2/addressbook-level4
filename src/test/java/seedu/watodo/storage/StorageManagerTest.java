package seedu.watodo.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.watodo.commons.events.model.TaskListChangedEvent;
import seedu.watodo.commons.events.storage.DataSavingExceptionEvent;
import seedu.watodo.model.ReadOnlyTaskManager;
import seedu.watodo.model.TaskManager;
import seedu.watodo.model.UserPrefs;
import seedu.watodo.testutil.EventsCollector;
import seedu.watodo.testutil.TypicalTestTasks;

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
    public void taskListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlTaskListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlTaskListStorageTest} class.
         */
        TaskManager original = new TypicalTestTasks().getTypicalTaskManager();
        storageManager.saveTaskList(original);
        ReadOnlyTaskManager retrieved = storageManager.readTaskList().get();
        assertEquals(original, new TaskManager(retrieved));
    }

    @Test
    public void getTaskListFilePath() {
        assertNotNull(storageManager.getTaskListFilePath());
    }

    @Test
    public void handleTaskListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTaskListStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTaskListChangedEvent(new TaskListChangedEvent(new TaskManager()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTaskListStorageExceptionThrowingStub extends XmlTaskListStorage {

        public XmlTaskListStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskList(ReadOnlyTaskManager taskList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
