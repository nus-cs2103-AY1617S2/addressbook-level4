package seedu.tache.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.tache.commons.exceptions.DataConversionException;
import seedu.tache.commons.util.FileUtil;
import seedu.tache.model.ReadOnlyTaskManager;
import seedu.tache.model.TaskManager;
import seedu.tache.model.task.Task;
import seedu.tache.testutil.TypicalTestTasks;

public class XmlTaskManagerStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskManagerStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskManagerNullFilePathAssertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskManager(null);
        fail();
    }

    private java.util.Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws Exception {
        return new XmlTaskManagerStorage(filePath).readTaskManager(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void readMissingFileEmptyResult() throws Exception {
        assertFalse(readTaskManager("NonExistentFile.xml").isPresent());
    }

    @Test
    public void readNotXmlFormatExceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskManager("NotXmlFormatTaskManager.xml");
        fail();
        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveTaskManagerAllInOrderSuccess() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTaskManager.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskManager original = td.getTypicalTaskManager();
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(filePath);

        //Save in new file and read back
        xmlTaskManagerStorage.saveTaskManager(original, filePath);
        ReadOnlyTaskManager readBack = xmlTaskManagerStorage.readTaskManager(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.getFit));
        original.removeTask(new Task(td.eggsAndBread));
        xmlTaskManagerStorage.saveTaskManager(original, filePath);
        readBack = xmlTaskManagerStorage.readTaskManager(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.findGirlfriend));
        xmlTaskManagerStorage.saveTaskManager(original); //file path not specified
        readBack = xmlTaskManagerStorage.readTaskManager().get(); //file path not specified
        assertEquals(original, new TaskManager(readBack));

    }

    @Test
    public void saveTaskManagerNullTaskManagerAssertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskManager(null, "SomeFile.xml");
        fail();
    }

    private void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        new XmlTaskManagerStorage(filePath).saveTaskManager(taskManager, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskManagerNullFilePathAssertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskManager(new TaskManager(), null);
        fail();
    }

}
