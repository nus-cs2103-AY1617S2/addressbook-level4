package seedu.todolist.storage;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.commons.util.FileUtil;
import seedu.todolist.model.ReadOnlyToDoList;
import seedu.todolist.model.ToDoList;
import seedu.todolist.model.task.Task;
import seedu.todolist.testutil.TypicalTestTasks;

public class XmlToDoListStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readToDoList_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readToDoList(null);
    }

    private java.util.Optional<ReadOnlyToDoList> readToDoList(String filePath) throws Exception {
        return new XmlToDoListStorage(filePath).readToDoList(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                        : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readToDoList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readToDoList("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveToDoList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempToDoList.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        ToDoList original = td.getTypicalToDoList();
        XmlToDoListStorage xmlToDoListStorage = new XmlToDoListStorage(filePath);

        //Save in new file and read back
        xmlToDoListStorage.saveToDoList(original, filePath);
        ReadOnlyToDoList readBack = xmlToDoListStorage.readToDoList(filePath).get();
        assertEquals(original, new ToDoList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.hoon));
        original.removeTask(new Task(td.alice));
        xmlToDoListStorage.saveToDoList(original, filePath);
        readBack = xmlToDoListStorage.readToDoList(filePath).get();
        assertEquals(original, new ToDoList(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.ida));
        xmlToDoListStorage.saveToDoList(original); //file path not specified
        readBack = xmlToDoListStorage.readToDoList().get(); //file path not specified
        assertEquals(original, new ToDoList(readBack));

    }

    @Test
    public void saveToDoList_nullToDoList_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveToDoList(null, "SomeFile.xml");
    }

    private void saveToDoList(ReadOnlyToDoList toDoList, String filePath) throws IOException {
        new XmlToDoListStorage(filePath).saveToDoList(toDoList, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveToDoList_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveToDoList(new ToDoList(), null);
    }


}
