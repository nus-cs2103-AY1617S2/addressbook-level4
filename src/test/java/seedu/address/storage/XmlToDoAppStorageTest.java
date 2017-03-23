package seedu.address.storage;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyToDoApp;
import seedu.address.model.ToDoApp;
import seedu.address.model.person.Task;
import seedu.address.testutil.TypicalTestTasks;

public class XmlToDoAppStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlToDoAppStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readToDoApp_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readToDoApp(null);
    }

    private java.util.Optional<ReadOnlyToDoApp> readToDoApp(String filePath) throws Exception {
        return new XmlToDoAppStorage(filePath).readToDoApp(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readToDoApp("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readToDoApp("NotXmlFormatToDoApp.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveToDoApp_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempToDoApp.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        ToDoApp original = td.getTypicalToDoApp();
        XmlToDoAppStorage xmlToDoAppStorage = new XmlToDoAppStorage(filePath);

        //Save in new file and read back
        xmlToDoAppStorage.saveToDoApp(original, filePath);
        ReadOnlyToDoApp readBack = xmlToDoAppStorage.readToDoApp(filePath).get();
        assertEquals(original, new ToDoApp(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.hoon));
        original.removeTask(new Task(td.alice));
        xmlToDoAppStorage.saveToDoApp(original, filePath);
        readBack = xmlToDoAppStorage.readToDoApp(filePath).get();
        assertEquals(original, new ToDoApp(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.ida));
        xmlToDoAppStorage.saveToDoApp(original); //file path not specified
        readBack = xmlToDoAppStorage.readToDoApp().get(); //file path not specified
        assertEquals(original, new ToDoApp(readBack));

    }

    @Test
    public void saveToDoApp_nullToDoApp_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveToDoApp(null, "SomeFile.xml");
    }

    private void saveToDoApp(ReadOnlyToDoApp addressBook, String filePath) throws IOException {
        new XmlToDoAppStorage(filePath).saveToDoApp(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveToDoApp_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveToDoApp(new ToDoApp(), null);
    }


}
