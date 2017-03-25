package seedu.geekeep.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.geekeep.commons.exceptions.DataConversionException;
import seedu.geekeep.commons.util.FileUtil;
import seedu.geekeep.model.GeeKeep;
import seedu.geekeep.model.ReadOnlyGeeKeep;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.testutil.TypicalTestTasks;

public class XmlGeeKeepStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlGeeKeepStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readGeeKeep_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readGeeKeep(null);
    }

    private java.util.Optional<ReadOnlyGeeKeep> readGeeKeep(String filePath) throws Exception {
        return new XmlGeeKeepStorage(filePath).readGeeKeep(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readGeeKeep("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readGeeKeep("NotXmlFormatGeeKeep.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveGeeKeep_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempGeeKeep.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        GeeKeep original = td.getTypicalGeeKeep();
        XmlGeeKeepStorage xmlGeeKeepStorage = new XmlGeeKeepStorage(filePath);

        //Save in new file and read back
        xmlGeeKeepStorage.saveGeeKeep(original, filePath);
        ReadOnlyGeeKeep readBack = xmlGeeKeepStorage.readGeeKeep(filePath).get();
        assertEquals(original, new GeeKeep(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.hoon));
        original.removeTask(new Task(td.alice));

        xmlGeeKeepStorage.saveGeeKeep(original, filePath);
        readBack = xmlGeeKeepStorage.readGeeKeep(filePath).get();

        assertEquals(original, new GeeKeep(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.ida));
        xmlGeeKeepStorage.saveGeeKeep(original); //file path not specified
        readBack = xmlGeeKeepStorage.readGeeKeep().get(); //file path not specified
        assertEquals(original, new GeeKeep(readBack));

    }

    @Test
    public void saveGeeKeep_nullGeeKeep_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveGeeKeep(null, "SomeFile.xml");
    }

    private void saveGeeKeep(ReadOnlyGeeKeep geeKeep, String filePath) throws IOException {
        new XmlGeeKeepStorage(filePath).saveGeeKeep(geeKeep, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveGeeKeep_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveGeeKeep(new GeeKeep(), null);
    }


}
