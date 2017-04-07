package project.taskcrusher.storage;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import project.taskcrusher.commons.exceptions.DataConversionException;
import project.taskcrusher.commons.util.FileUtil;
import project.taskcrusher.model.ReadOnlyUserInbox;
import project.taskcrusher.model.UserInbox;
//import project.taskcrusher.model.task.Task;
//import project.taskcrusher.testutil.TypicalTestTasks;

public class XmlAddressBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyUserInbox> readAddressBook(String filePath) throws Exception {
        return new XmlUserInboxStorage(filePath).readUserInbox(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

//    @Test
//    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
//        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
//        TypicalTestTasks td = new TypicalTestTasks();
//        UserInbox original = td.getTypicalUserInbox();
//        XmlUserInboxStorage xmlAddressBookStorage = new XmlUserInboxStorage(filePath);
//
//        //Save in new file and read back
//        xmlAddressBookStorage.saveUserInbox(original, filePath);
//        ReadOnlyUserInbox readBack = xmlAddressBookStorage.readUserInbox(filePath).get();
////        assertEquals(original, new UserInbox(readBack));
//
//        //Modify data, overwrite exiting file, and read back
//        //original.addTask(new Task(td.shopping));
//        original.removeTask(new Task(td.assignment));
//        xmlAddressBookStorage.saveUserInbox(original, filePath);
//        readBack = xmlAddressBookStorage.readUserInbox(filePath).get();
//        assertEquals(original, new UserInbox(readBack));
//
//        //Save and read without specifying file path
//        //original.addTask(new Task(td.phoneCall));
//        xmlAddressBookStorage.saveUserInbox(original); //file path not specified
//        readBack = xmlAddressBookStorage.readUserInbox().get(); //file path not specified
//        assertEquals(original, new UserInbox(readBack));
//
//    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    private void saveAddressBook(ReadOnlyUserInbox addressBook, String filePath) throws IOException {
        new XmlUserInboxStorage(filePath).saveUserInbox(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(new UserInbox(), null);
    }


}
