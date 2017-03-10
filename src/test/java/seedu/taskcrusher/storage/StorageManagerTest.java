package seedu.taskcrusher.storage;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.taskcrusher.commons.events.model.AddressBookChangedEvent;
import seedu.taskcrusher.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskcrusher.model.ReadOnlyUserInbox;
import seedu.taskcrusher.model.UserInbox;
import seedu.taskcrusher.model.UserPrefs;
import seedu.taskcrusher.storage.JsonUserPrefsStorage;
import seedu.taskcrusher.storage.Storage;
import seedu.taskcrusher.storage.StorageManager;
import seedu.taskcrusher.storage.XmlAddressBookStorage;
import seedu.taskcrusher.testutil.EventsCollector;
import seedu.taskcrusher.testutil.TypicalTestPersons;

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
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        UserInbox original = new TypicalTestPersons().getTypicalAddressBook();
        storageManager.saveUserInbox(original);
        ReadOnlyUserInbox retrieved = storageManager.readUserInbox().get();
        assertEquals(original, new UserInbox(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getUserInboxFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new UserInbox()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage {

        public XmlAddressBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveUserInbox(ReadOnlyUserInbox addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
