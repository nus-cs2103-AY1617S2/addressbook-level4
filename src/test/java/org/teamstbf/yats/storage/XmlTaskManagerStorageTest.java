package org.teamstbf.yats.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.teamstbf.yats.commons.exceptions.DataConversionException;
import org.teamstbf.yats.commons.util.FileUtil;
import org.teamstbf.yats.model.ReadOnlyTaskManager;
import org.teamstbf.yats.model.TaskManager;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.testutil.TypicalTestEvents;

public class XmlTaskManagerStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskManagerStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskManager_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskManager(null);
    }

    private java.util.Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws Exception {
        return new XmlTaskManagerStorage(filePath).readTaskManager(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER + prefsFileInTestDataFolder : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskManager("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskManager("NotXmlFormatAddressBook.xml");

        /*
         * IMPORTANT: Any code below an exception-throwing line (like the one
         * above) will be ignored. That means you should not have more than one
         * exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestEvents td = new TypicalTestEvents();
        TaskManager original = td.getTypicalTaskManager();
        XmlTaskManagerStorage xmlAddressBookStorage = new XmlTaskManagerStorage(filePath);

        // Save in new file and read back
        xmlAddressBookStorage.saveTaskManager(original, filePath);
        ReadOnlyTaskManager readBack = xmlAddressBookStorage.readTaskManager(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addEvent(new Event(td.abdicate));
        original.removeEvent(new Event(td.abdicate));
        xmlAddressBookStorage.saveTaskManager(original, filePath);
        readBack = xmlAddressBookStorage.readTaskManager(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        // Save and read without specifying file path
        original.addEvent(new Event(td.boop));
        xmlAddressBookStorage.saveTaskManager(original); // file path not
                                                         // specified
        readBack = xmlAddressBookStorage.readTaskManager().get(); // file path
                                                                  // not
                                                                  // specified
        assertEquals(original, new TaskManager(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    private void saveAddressBook(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
        new XmlTaskManagerStorage(filePath).saveTaskManager(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(new TaskManager(), null);
    }

}
