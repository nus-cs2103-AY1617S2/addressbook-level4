package seedu.address.storage;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.ToDoAppChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.ReadOnlyToDoApp;
import seedu.address.model.ToDoApp;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.EventsCollector;
import seedu.address.testutil.TypicalTestTasks;

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
         * {@link XmlToDoAppStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlToDoAppStorageTest} class.
         */
        ToDoApp original = new TypicalTestTasks().getTypicalToDoApp();
        storageManager.saveToDoApp(original);
        ReadOnlyToDoApp retrieved = storageManager.readToDoApp().get();
        assertEquals(original, new ToDoApp(retrieved));
    }

    @Test
    public void getToDoAppFilePath() {
        assertNotNull(storageManager.getToDoAppFilePath());
    }

    @Test
    public void handleToDoAppChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlToDoAppStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleToDoAppChangedEvent(new ToDoAppChangedEvent(new ToDoApp()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlToDoAppStorageExceptionThrowingStub extends XmlToDoAppStorage {

        public XmlToDoAppStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveToDoApp(ReadOnlyToDoApp addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
