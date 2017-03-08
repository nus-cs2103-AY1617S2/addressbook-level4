package seedu.ezdo.storage;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.ezdo.commons.exceptions.DataConversionException;
import seedu.ezdo.commons.util.FileUtil;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.ReadOnlyEzDo;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.testutil.TypicalTestTasks;

public class XmlEzDoStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlEzDoStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEzDo_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readEzDo(null);
    }

    private java.util.Optional<ReadOnlyEzDo> readEzDo(String filePath) throws Exception {
        return new XmlEzDoStorage(filePath).readEzDo(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEzDo("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readEzDo("NotXmlFormatEzDo.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveEzDo_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        EzDo original = td.getTypicalEzDo();
        XmlEzDoStorage xmlEzDoStorage = new XmlEzDoStorage(filePath);

        //Save in new file and read back
        xmlEzDoStorage.saveEzDo(original, filePath);
        ReadOnlyEzDo readBack = xmlEzDoStorage.readEzDo(filePath).get();
        assertEquals(original, new EzDo(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.hoon));
        original.removeTask(new Task(td.alice));
        xmlEzDoStorage.saveEzDo(original, filePath);
        readBack = xmlEzDoStorage.readEzDo(filePath).get();
        assertEquals(original, new EzDo(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.ida));
        xmlEzDoStorage.saveEzDo(original); //file path not specified
        readBack = xmlEzDoStorage.readEzDo().get(); //file path not specified
        assertEquals(original, new EzDo(readBack));

    }

    @Test
    public void saveEzDo_nullEzDo_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveEzDo(null, "SomeFile.xml");
    }

    private void saveEzDo(ReadOnlyEzDo ezDo, String filePath) throws IOException {
        new XmlEzDoStorage(filePath).saveEzDo(ezDo, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveEzDo_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveEzDo(new EzDo(), null);
    }


}
