package seedu.bulletjournal.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.bulletjournal.model.ReadOnlyTodoList;
import seedu.bulletjournal.model.TodoList;
import seedu.bulletjournal.model.UserPrefs;
import seedu.bulletjournal.testutil.TypicalTestTasks;

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
         * Note: This is an integration test that verifies the StorageManager is
         * properly wired to the {@link JsonUserPrefsStorage} class. More
         * extensive testing of UserPref saving/reading is done in {@link
         * JsonUserPrefsStorageTest} class.
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
         * Note: This is an integration test that verifies the StorageManager is
         * properly wired to the {@link XmlAddressBookStorage} class. More
         * extensive testing of UserPref saving/reading is done in {@link
         * XmlAddressBookStorageTest} class.
         */
        TodoList original = new TypicalTestTasks().getTypicalTodoList();
        storageManager.saveTodoList(original);
        ReadOnlyTodoList retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new TodoList(retrieved));
    }

    @Test
    public void getBulletJournalFilePath() {
        assertNotNull(storageManager.getBulletJournalFilePath());
    }

}
