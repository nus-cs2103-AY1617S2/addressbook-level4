package seedu.whatsleft.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.whatsleft.commons.events.model.WhatsLeftChangedEvent;
import seedu.whatsleft.commons.events.storage.DataSavingExceptionEvent;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;
import seedu.whatsleft.model.UserPrefs;
import seedu.whatsleft.model.WhatsLeft;
import seedu.whatsleft.testutil.EventsCollector;
import seedu.whatsleft.testutil.TypicalTestEvents;

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
    public void whatsLeftReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlWhatsLeftStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlWhatsLeftStorageTest} class.
         */
        WhatsLeft original = new TypicalTestEvents().getTypicalWhatsLeft();
        storageManager.saveWhatsLeft(original);
        ReadOnlyWhatsLeft retrieved = storageManager.readWhatsLeft().get();
        assertEquals(original, new WhatsLeft(retrieved));
    }

    @Test
    public void getWhatsLeftFilePath() {
        assertNotNull(storageManager.getWhatsLeftFilePath());
    }

    @Test
    public void handleWhatsLeftChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlWhatsLeftStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleWhatsLeftChangedEvent(new WhatsLeftChangedEvent(new WhatsLeft()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlWhatsLeftStorageExceptionThrowingStub extends XmlWhatsLeftStorage {

        public XmlWhatsLeftStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveWhatsLeft(ReadOnlyWhatsLeft whatsLeft, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
