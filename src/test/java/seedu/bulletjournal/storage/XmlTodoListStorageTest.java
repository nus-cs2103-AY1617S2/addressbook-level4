package seedu.bulletjournal.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.bulletjournal.commons.exceptions.DataConversionException;
import seedu.bulletjournal.commons.util.FileUtil;
import seedu.bulletjournal.model.ReadOnlyTodoList;
import seedu.bulletjournal.model.TodoList;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.testutil.TypicalTestTasks;

public class XmlTodoListStorageTest {
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
        return new XmlTodoListStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER + prefsFileInTestDataFolder : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /*
         * IMPORTANT: Any code below an exception-throwing line (like the one
         * above) will be ignored. That means you should not have more than one
         * exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TodoList original = td.getTypicalTodoList();
        XmlTodoListStorage xmlTodoListStorage = new XmlTodoListStorage(filePath);

        // Save in new file and read back
        xmlTodoListStorage.saveTodoList(original, filePath);
        ReadOnlyTodoList readBack = xmlTodoListStorage.readAddressBook(filePath).get();
        assertEquals(original, new TodoList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.hangclothes));
        original.removeTask(new Task(td.assignment));
        xmlTodoListStorage.saveTodoList(original, filePath);
        readBack = xmlTodoListStorage.readAddressBook(filePath).get();
        assertEquals(original, new TodoList(readBack));

        // Save and read without specifying file path
        original.addTask(new Task(td.interviewprep));
        xmlTodoListStorage.saveTodoList(original); // file path not specified
        readBack = xmlTodoListStorage.readAddressBook().get(); // file path not
                                                               // specified
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
