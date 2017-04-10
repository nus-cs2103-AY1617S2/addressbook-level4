package seedu.geekeep.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.geekeep.commons.events.model.GeeKeepChangedEvent;
import seedu.geekeep.commons.events.storage.DataSavingExceptionEvent;
import seedu.geekeep.model.GeeKeep;
import seedu.geekeep.model.ReadOnlyGeeKeep;
import seedu.geekeep.model.UserPrefs;
import seedu.geekeep.testutil.EventsCollector;
import seedu.geekeep.testutil.TypicalTestTasks;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setUp() {
        storageManager = new StorageManager(getTempFilePath("config"), getTempFilePath("ab"), getTempFilePath("prefs"));
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
    public void geeKeepReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlGeeKeepStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlGeeKeepStorageTest} class.
         */
        GeeKeep original = new TypicalTestTasks().getTypicalGeeKeep();
        storageManager.saveGeeKeep(original);
        ReadOnlyGeeKeep retrieved = storageManager.readGeeKeep().get();
        assertEquals(original, new GeeKeep(retrieved));
    }

    @Test
    public void getGeeKeepFilePath() {
        assertNotNull(storageManager.getGeeKeepFilePath());
    }

    @Test
    public void handleGeeKeepChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new JsonConfigStorage("dummy"),
                new XmlGeeKeepStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleGeeKeepChangedEvent(new GeeKeepChangedEvent(new GeeKeep()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlGeeKeepStorageExceptionThrowingStub extends XmlGeeKeepStorage {

        public XmlGeeKeepStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveGeeKeep(ReadOnlyGeeKeep geeKeep, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
