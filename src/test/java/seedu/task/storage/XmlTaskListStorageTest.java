package seedu.task.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.util.FileUtil;
import seedu.task.model.ReadOnlyTaskList;
import seedu.task.model.TaskList;
import seedu.task.model.task.Task;
import seedu.task.testutil.TypicalTestTasks;

public class XmlTaskListStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskListStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskListNullFilePathAssertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        java.util.Optional<ReadOnlyTaskList> tl = readTaskList(null);
        assertNull(tl);
    }

    private java.util.Optional<ReadOnlyTaskList> readTaskList(String filePath) throws Exception {
        return new XmlTaskListStorage(filePath).readTaskList(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER + prefsFileInTestDataFolder : null;
    }

    @Test
    public void readMissingFileEmptyResult() throws Exception {
        assertFalse(readTaskList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void readNotXmlFormatExceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskList("NotXmlFormatTaskList.xml");
        fail();

        //          * IMPORTANT: Any code below an exception-throwing line (like the one
        //          * above) will be ignored. That means you should not have more than one
        //          * exception test in one method

    }

    @Test
    public void readAndSaveTaskListAllInOrderSuccess() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTaskList.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskList original = td.getTypicalTaskList();
        XmlTaskListStorage xmlTaskListStorage = new XmlTaskListStorage(filePath);

        // Save in new file and read back
        xmlTaskListStorage.saveTaskList(original, filePath);
        ReadOnlyTaskList readBack = xmlTaskListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.hoon));
        original.removeTask(new Task(td.fiona));
        xmlTaskListStorage.saveTaskList(original, filePath);
        readBack = xmlTaskListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        // Save and read without specifying file path
        original.addTask(new Task(td.ida));
        xmlTaskListStorage.saveTaskList(original); // file path not specified
        readBack = xmlTaskListStorage.readTaskList().get(); // file path not
        // specified
        assertEquals(original, new TaskList(readBack));

    }

    @Test
    public void saveTaskListNullTaskListAssertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskList(null, "SomeFile.xml");
        fail();
    }

    private void saveTaskList(ReadOnlyTaskList taskList, String filePath) throws IOException {
        new XmlTaskListStorage(filePath).saveTaskList(taskList, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskListNullFilePathAssertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskList(new TaskList(), null);
        fail();
    }

}
