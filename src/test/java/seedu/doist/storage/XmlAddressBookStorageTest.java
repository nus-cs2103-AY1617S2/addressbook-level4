package seedu.doist.storage;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.doist.commons.exceptions.DataConversionException;
import seedu.doist.commons.util.FileUtil;
import seedu.doist.model.ReadOnlyTodoList;
import seedu.doist.model.TodoList;
import seedu.doist.model.task.Task;
import seedu.doist.testutil.TypicalTestTasks;

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

    private java.util.Optional<ReadOnlyTodoList> readAddressBook(String filePath) throws Exception {
        return new XmlTodoListStorage(filePath).readTodoList(addToTestDataPathIfNotNull(filePath));
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
        TypicalTestTasks td = new TypicalTestTasks();
        TodoList original = td.getTypicalAddressBook();
        XmlTodoListStorage xmlAddressBookStorage = new XmlTodoListStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveTodoList(original, filePath);
        ReadOnlyTodoList readBack = xmlAddressBookStorage.readTodoList(filePath).get();
        assertEquals(original, new TodoList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.email));
        original.removeTask(new Task(td.laundry));
        xmlAddressBookStorage.saveTodoList(original, filePath);
        readBack = xmlAddressBookStorage.readTodoList(filePath).get();
        assertEquals(original, new TodoList(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.exercise));
        xmlAddressBookStorage.saveTodoList(original); //file path not specified
        readBack = xmlAddressBookStorage.readTodoList().get(); //file path not specified
        assertEquals(original, new TodoList(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    private void saveAddressBook(ReadOnlyTodoList addressBook, String filePath) throws IOException {
        new XmlTodoListStorage(filePath).saveTodoList(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(new TodoList(), null);
    }


}
