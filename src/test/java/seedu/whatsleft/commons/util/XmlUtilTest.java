package seedu.whatsleft.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.whatsleft.model.WhatsLeft;
import seedu.whatsleft.storage.XmlSerializableWhatsLeft;
import seedu.whatsleft.testutil.TestUtil;
import seedu.whatsleft.testutil.WhatsLeftBuilder;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validWhatsLeft.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempWhatsLeft.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, WhatsLeft.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, WhatsLeft.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, WhatsLeft.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableWhatsLeft dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableWhatsLeft.class);
        assertEquals(9, dataFromFile.getEventList().size());
        assertEquals(25, dataFromFile.getTagList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new WhatsLeft());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new WhatsLeft());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableWhatsLeft dataToWrite = new XmlSerializableWhatsLeft(new WhatsLeft());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableWhatsLeft dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableWhatsLeft.class);
        assertEquals((new WhatsLeft(dataToWrite)).toString(), (new WhatsLeft(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        WhatsLeftBuilder builder = new WhatsLeftBuilder(new WhatsLeft());
        dataToWrite = new XmlSerializableWhatsLeft(
                builder.withEvent(TestUtil.generateSampleEventData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableWhatsLeft.class);
        assertEquals((new WhatsLeft(dataToWrite)).toString(), (new WhatsLeft(dataFromFile)).toString());
    }
}
