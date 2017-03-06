package seedu.taskList.storage;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.taskList.commons.exceptions.DataConversionException;
import seedu.taskList.commons.util.FileUtil;
import seedu.taskList.model.TaskList;
import seedu.taskList.model.ReadOnlyTaskList;
import seedu.taskList.model.task.Person;
import seedu.taskList.storage.XmlTaskListStorage;
import seedu.taskList.testutil.TypicalTestPersons;

public class XmlAddressBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyTaskList> readAddressBook(String filePath) throws Exception {
        return new XmlTaskListStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestPersons td = new TypicalTestPersons();
        TaskList original = td.getTypicalAddressBook();
        XmlTaskListStorage xmlTaskListStorage = new XmlTaskListStorage(filePath);

        //Save in new file and read back
        xmlTaskListStorage.saveAddressBook(original, filePath);
        ReadOnlyTaskList readBack = xmlTaskListStorage.readAddressBook(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(new Person(td.hoon));
        original.removePerson(new Person(td.alice));
        xmlTaskListStorage.saveAddressBook(original, filePath);
        readBack = xmlTaskListStorage.readAddressBook(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Save and read without specifying file path
        original.addPerson(new Person(td.ida));
        xmlTaskListStorage.saveAddressBook(original); //file path not specified
        readBack = xmlTaskListStorage.readAddressBook().get(); //file path not specified
        assertEquals(original, new TaskList(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    private void saveAddressBook(ReadOnlyTaskList addressBook, String filePath) throws IOException {
        new XmlTaskListStorage(filePath).saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(new TaskList(), null);
    }


}
