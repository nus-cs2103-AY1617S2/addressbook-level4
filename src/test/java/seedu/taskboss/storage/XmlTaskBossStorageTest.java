package seedu.taskboss.storage;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.taskboss.commons.exceptions.DataConversionException;
import seedu.taskboss.commons.util.FileUtil;
import seedu.taskboss.model.ReadOnlyTaskBoss;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.testutil.TypicalTestTasks;

public class XmlTaskBossStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskBossStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskBoss(null);
    }

    private java.util.Optional<ReadOnlyTaskBoss> readTaskBoss(String filePath) throws Exception {
        return new XmlTaskBossStorage(filePath).readTaskBoss(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskBoss("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskBoss("NotXmlFormatTaskBoss.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveTaskBoss_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTaskBoss.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskBoss original = td.getTypicalTaskBoss();
        XmlTaskBossStorage xmlTaskBossStorage = new XmlTaskBossStorage(filePath);

        //Save in new file and read back
        xmlTaskBossStorage.saveTaskBoss(original, filePath);
        ReadOnlyTaskBoss readBack = xmlTaskBossStorage.readTaskBoss(filePath).get();
        assertEquals(original, new TaskBoss(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.taskH));
        original.removeTask(new Task(td.taskA));
        xmlTaskBossStorage.saveTaskBoss(original, filePath);
        readBack = xmlTaskBossStorage.readTaskBoss(filePath).get();
        assertEquals(original, new TaskBoss(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.taskI));
        xmlTaskBossStorage.saveTaskBoss(original); //file path not specified
        readBack = xmlTaskBossStorage.readTaskBoss().get(); //file path not specified
        assertEquals(original, new TaskBoss(readBack));

    }

    @Test
    public void saveTaskBoss_nullTaskBoss_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskBoss(null, "SomeFile.xml");
    }

    private void saveTaskBoss(ReadOnlyTaskBoss taskBoss, String filePath) throws IOException {
        new XmlTaskBossStorage(filePath).saveTaskBoss(taskBoss, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskBoss_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskBoss(new TaskBoss(), null);
    }


}
