package seedu.geekeep.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.geekeep.model.GeeKeep;
import seedu.geekeep.storage.XmlSerializableGeeKeep;
import seedu.geekeep.testutil.GeeKeepBuilder;
import seedu.geekeep.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validGeeKeep.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempGeeKeep.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, GeeKeep.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, GeeKeep.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, GeeKeep.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableGeeKeep dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableGeeKeep.class);
        assertEquals(9, dataFromFile.getTaskList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new GeeKeep());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new GeeKeep());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableGeeKeep dataToWrite = new XmlSerializableGeeKeep(new GeeKeep());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableGeeKeep dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableGeeKeep.class);
        assertEquals((new GeeKeep(dataToWrite)).toString(), (new GeeKeep(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        GeeKeepBuilder builder = new GeeKeepBuilder(new GeeKeep());
        dataToWrite = new XmlSerializableGeeKeep(
                builder.withTask(TestUtil.generateSampleTaskData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableGeeKeep.class);
        assertEquals((new GeeKeep(dataToWrite)).toString(), (new GeeKeep(dataFromFile)).toString());
    }
}
