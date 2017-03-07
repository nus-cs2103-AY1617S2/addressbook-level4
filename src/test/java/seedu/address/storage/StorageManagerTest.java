package seedu.address.storage;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

<<<<<<< HEAD
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.UserPrefs;
=======
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285
import seedu.address.testutil.EventsCollector;
import seedu.address.testutil.TypicalTestPersons;
import seedu.task.model.TaskManager;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.storage.DataSavingExceptionEvent;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.UserPrefs;

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
<<<<<<< HEAD
        AddressBook original = new TypicalTestPersons().getTypicalAddressBook();
        storageManager.saveTaskManager(original);
        ReadOnlyTaskManager retrieved = storageManager.readTaskManager().get();
        assertEquals(original, new AddressBook(retrieved));
=======
        TaskManager original = new TypicalTestPersons().getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyTaskManager retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new TaskManager(retrieved));
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getTaskManagerFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
<<<<<<< HEAD
        storage.handleTaskManagerChangedEvent(new TaskManagerChangedEvent(new AddressBook()));
=======
        storage.handleAddressBookChangedEvent(new TaskManagerChangedEvent(new TaskManager()));
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlTaskManagerStorage {

        public XmlAddressBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
<<<<<<< HEAD
        public void saveTaskManager(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
=======
        public void saveAddressBook(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285
            throw new IOException("dummy exception");
        }
    }


}
