package seedu.watodo.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.watodo.commons.exceptions.DataConversionException;
import seedu.watodo.commons.util.FileUtil;
import seedu.watodo.model.ReadOnlyTaskManager;
import seedu.watodo.model.TaskManager;
import seedu.watodo.model.task.Task;
import seedu.watodo.testutil.TypicalTestTasks;

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

    private java.util.Optional<ReadOnlyTaskManager> readTaskList(String filePath) throws Exception {
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
        TaskManager original = td.getTypicalTaskManagerWithoutTime();
        XmlTaskListStorage xmlTaskListStorage = new XmlTaskListStorage(filePath);

        //Save in new file and read back
        xmlTaskListStorage.saveTaskList(original, filePath);
        ReadOnlyTaskManager readBack = xmlTaskListStorage.readTaskList(filePath).get();
        assertEquals(original, new TaskManager(readBack));


        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.drum));
        original.removeTask(new Task(td.play));
        xmlTaskListStorage.saveTaskList(original, filePath);
        readBack = xmlTaskListStorage.readTaskList(filePath).get();

        assertEquals(original, new TaskManager(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.play));
        xmlTaskListStorage.saveTaskList(original); //file path not specified
        readBack = xmlTaskListStorage.readTaskList().get(); //file path not specified
        assertEquals(original, new TaskManager(readBack));

    }

    @Test
    public void saveTaskList_nullTaskList_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskList(null, "SomeFile.xml");
    }

    private void saveTaskList(ReadOnlyTaskManager taskList, String filePath) throws IOException {
        new XmlTaskListStorage(filePath).saveTaskList(taskList, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskList_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskList(new TaskManager(), null);
    }


}
