package seedu.doit.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.doit.commons.core.Config;
import seedu.doit.commons.events.model.TaskManagerChangedEvent;
import seedu.doit.commons.events.storage.DataSavingExceptionEvent;
import seedu.doit.model.ReadOnlyTaskManager;
import seedu.doit.model.TaskManager;
import seedu.doit.model.UserPrefs;
import seedu.doit.testutil.EventsCollector;
import seedu.doit.testutil.TypicalTestTasks;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private StorageManager storageManager;

    @Before
    public void setUp() {
        this.storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"), new Config());
    }

    private String getTempFilePath(String fileName) {
        return this.testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is
         * properly wired to the {@link JsonUserPrefsStorage} class. More
         * extensive testing of UserPref saving/reading is done in {@link
         * JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        this.storageManager.saveUserPrefs(original);
        UserPrefs retrieved = this.storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void taskManagerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is
         * properly wired to the {@link XmlTaskManagerStorage} class. More
         * extensive testing of UserPref saving/reading is done in {@link
         * XmlTaskManagerStorageTest} class.
         */
        TaskManager original = new TypicalTestTasks().getTypicalTaskManager();
        this.storageManager.saveTaskManager(original);
        ReadOnlyTaskManager retrieved = this.storageManager.readTaskManager().get();
        assertEquals(original, new TaskManager(retrieved));
    }

    @Test
    public void getTaskManagerFilePath() {
        assertNotNull(this.storageManager.getTaskManagerFilePath());
    }

    @Test
    public void handleTaskManagerChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that throws an
        // exception when the save method is called
        Storage storage = new StorageManager(new XmlTaskManagerStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"), new Config());
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTaskManagerChangedEvent(new TaskManagerChangedEvent(new TaskManager()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTaskManagerStorageExceptionThrowingStub extends XmlTaskManagerStorage {

        public XmlTaskManagerStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
