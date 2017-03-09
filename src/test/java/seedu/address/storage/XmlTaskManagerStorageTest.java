package seedu.address.storage;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.testutil.TypicalTestPersons;

public class XmlTaskManagerStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskManagerStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test(expected=AssertionError.class)
    public void readTaskManager_nullFilePath_assertionFailure() throws Exception {
        readTaskManager(null);
    }

    private java.util.Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws Exception {
        return new XmlAddressBookStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskManager("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskManager("NotXmlFormatTaskManager.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveTaskManager_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTaskManager.xml";
        TypicalTestPersons td = new TypicalTestPersons();
        TaskManager original = td.getTypicalAddressBook();
        XmlAddressBookStorage xmlTaskManagerStorage = new XmlAddressBookStorage(filePath);

        //Save in new file and read back
        xmlTaskManagerStorage.saveAddressBook(original, filePath);
        ReadOnlyTaskManager readBack = xmlTaskManagerStorage.readAddressBook(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.hoon));
        original.removeTask(new Task(td.alice));
        xmlTaskManagerStorage.saveAddressBook(original, filePath);
        readBack = xmlTaskManagerStorage.readAddressBook(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.ida));
        xmlTaskManagerStorage.saveAddressBook(original); //file path not specified
        readBack = xmlTaskManagerStorage.readAddressBook().get(); //file path not specified
        assertEquals(original, new TaskManager(readBack));

    }

    @Test(expected=AssertionError.class)
    public void saveTaskManager_nullTaskManager_assertionFailure() throws IOException {
        saveTaskManager(null, "SomeFile.xml");
    }

    private void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        new XmlAddressBookStorage(filePath).saveAddressBook(taskManager, addToTestDataPathIfNotNull(filePath));
    }

    @Test(expected=AssertionError.class)
    public void saveTaskManager_nullFilePath_assertionFailure() throws IOException {
        saveTaskManager(new TaskManager(), null);
    }


}
