package werkbook.task.storage;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import werkbook.task.commons.exceptions.DataConversionException;
import werkbook.task.commons.util.FileUtil;
import werkbook.task.model.ReadOnlyTaskList;
import werkbook.task.model.TaskList;
import werkbook.task.model.task.Task;
import werkbook.task.testutil.TypicalTestTasks;

public class XmlTaskListStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskListStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskList_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskList(null);
    }

    private java.util.Optional<ReadOnlyTaskList> readTaskList(String filePath) throws Exception {
        return new XmlTaskListStorage(filePath).readTaskList(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskList("NotXmlFormatTaskList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveTaskList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTaskList.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskList original = td.getTypicalTaskList();
        XmlTaskListStorage xmlTaskListStorage = new XmlTaskListStorage(filePath);

        //Save in new file and read back
        xmlTaskListStorage.saveTaskList(original, filePath);
        ReadOnlyTaskList readBack = xmlTaskListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.hoon));
        original.removeTask(new Task(td.alice));
        xmlTaskListStorage.saveTaskList(original, filePath);
        readBack = xmlTaskListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskList(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.ida));
        xmlTaskListStorage.saveTaskList(original); //file path not specified
        readBack = xmlTaskListStorage.readTaskList().get(); //file path not specified
        assertEquals(original, new TaskList(readBack));

    }

    @Test
    public void saveTaskList_nullTaskList_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskList(null, "SomeFile.xml");
    }

    private void saveTaskList(ReadOnlyTaskList taskList, String filePath) throws IOException {
        new XmlTaskListStorage(filePath).saveTaskList(taskList, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskList_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskList(new TaskList(), null);
    }


}
