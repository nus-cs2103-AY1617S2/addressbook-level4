package seedu.taskboss.storage;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.taskboss.commons.events.model.TaskBossChangedEvent;
import seedu.taskboss.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskboss.model.ReadOnlyTaskBoss;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.UserPrefs;
import seedu.taskboss.testutil.EventsCollector;
import seedu.taskboss.testutil.TypicalTestTasks;

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
    public void taskBossReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlTaskBossStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlTaskBossStorageTest} class.
         */
        TaskBoss original = new TypicalTestTasks().getTypicalTaskBoss();
        storageManager.saveTaskBoss(original);
        ReadOnlyTaskBoss retrieved = storageManager.readTaskBoss().get();
        assertEquals(original, new TaskBoss(retrieved));
    }

    @Test
    public void getTaskBossFilePath() {
        assertNotNull(storageManager.getTaskBossFilePath());
    }

    @Test
    public void handleTaskBossChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTaskBossStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTaskBossChangedEvent(new TaskBossChangedEvent(new TaskBoss()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTaskBossStorageExceptionThrowingStub extends XmlTaskBossStorage {

        public XmlTaskBossStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskBoss(ReadOnlyTaskBoss taskBoss, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
