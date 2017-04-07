package project.taskcrusher.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import project.taskcrusher.commons.exceptions.DataConversionException;
import project.taskcrusher.commons.util.FileUtil;
import project.taskcrusher.model.ReadOnlyUserInbox;
import project.taskcrusher.model.UserInbox;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.testutil.TypicalTestUserInbox;

public class XmlUserInboxStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readUserInbox_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readUserInbox(null);
    }

    private java.util.Optional<ReadOnlyUserInbox> readUserInbox(String filePath) throws Exception {
        return new XmlUserInboxStorage(filePath).readUserInbox(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readUserInbox("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        File notXmlFormattedfile = new File(addToTestDataPathIfNotNull("NotXmlFormatUserInbox.xml"));
        FileUtil.createIfMissing(notXmlFormattedfile);
        thrown.expect(DataConversionException.class);
        readUserInbox("NotXmlFormatUserInbox.xml");
    }

    @Test
    public void readAndSaveUserInbox_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempUserInbox.xml";
        TypicalTestUserInbox testUserInbox = new TypicalTestUserInbox();
        UserInbox original = testUserInbox.getTypicalUserInbox();
        XmlUserInboxStorage xmlUserInboxStorage = new XmlUserInboxStorage(filePath);

        //Save in new file and read back
        xmlUserInboxStorage.saveUserInbox(original, filePath);
        ReadOnlyUserInbox readBack = xmlUserInboxStorage.readUserInbox(filePath).get();
        assertEquals(original, new UserInbox(readBack));

        //Modify data, overwrite exiting file, and read back
        original.removeTask(new Task(testUserInbox.assignment));
        xmlUserInboxStorage.saveUserInbox(original, filePath);
        readBack = xmlUserInboxStorage.readUserInbox(filePath).get();
        assertEquals(original, new UserInbox(readBack));

        //Check it stores events properly
        original.removeEvent(new Event(testUserInbox.islandTrip));
        xmlUserInboxStorage.saveUserInbox(original, filePath);
        readBack = xmlUserInboxStorage.readUserInbox(filePath).get();
        assertEquals(original, new UserInbox(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(testUserInbox.notAddedYetQuiz));
        xmlUserInboxStorage.saveUserInbox(original); //file path not specified
        readBack = xmlUserInboxStorage.readUserInbox().get(); //file path not specified
        assertEquals(original, new UserInbox(readBack));
    }

    @Test
    public void saveUserInbox_nullUserInbox_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserInbox(null, "SomeFile.xml");
    }

    private void saveUserInbox(ReadOnlyUserInbox addressBook, String filePath) throws IOException {
        new XmlUserInboxStorage(filePath).saveUserInbox(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveUserInbox_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserInbox(new UserInbox(), null);
    }


}
