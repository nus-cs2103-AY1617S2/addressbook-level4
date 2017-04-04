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
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.Event;
import seedu.address.testutil.TypicalTestEvents;

public class XmlWhatsLeftStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlWhatsLeftStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readWhatsLeft_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readWhatsLeft(null);
    }

    private java.util.Optional<ReadOnlyWhatsLeft> readWhatsLeft(String filePath) throws Exception {
        return new XmlWhatsLeftStorage(filePath).readWhatsLeft(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readWhatsLeft("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readWhatsLeft("NotXmlFormatWhatsLeft.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveWhatsLeft_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempWhatsLeft.xml";
        TypicalTestEvents te = new TypicalTestEvents();
        WhatsLeft original = te.getTypicalWhatsLeft();
        XmlWhatsLeftStorage xmlWhatsLeftStorage = new XmlWhatsLeftStorage(filePath);

        //Save in new file and read back
        xmlWhatsLeftStorage.saveWhatsLeft(original, filePath);
        ReadOnlyWhatsLeft readBack = xmlWhatsLeftStorage.readWhatsLeft(filePath).get();
        assertEquals(original, new WhatsLeft(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addEvent(new Event(te.consultation));
        original.removeEvent(new Event(te.tutorial));
        xmlWhatsLeftStorage.saveWhatsLeft(original, filePath);
        readBack = xmlWhatsLeftStorage.readWhatsLeft(filePath).get();
        assertEquals(original, new WhatsLeft(readBack));

        //Save and read without specifying file path
        original.addEvent(new Event(te.workshop));
        xmlWhatsLeftStorage.saveWhatsLeft(original); //file path not specified
        readBack = xmlWhatsLeftStorage.readWhatsLeft().get(); //file path not specified
        assertEquals(original, new WhatsLeft(readBack));
    }

    @Test
    public void saveWhatsLeft_nullWhatsLeft_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveWhatsLeft(null, "SomeFile.xml");
    }

    private void saveWhatsLeft(ReadOnlyWhatsLeft addressBook, String filePath) throws IOException {
        new XmlWhatsLeftStorage(filePath).saveWhatsLeft(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveWhatsLeft_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveWhatsLeft(new WhatsLeft(), null);
    }


}
