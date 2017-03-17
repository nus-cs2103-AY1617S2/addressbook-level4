package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.ToDoApp;
import seedu.address.storage.XmlSerializableToDoApp;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.ToDoAppBuilder;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validToDoApp.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempToDoApp.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(null, ToDoApp.class);
    }

    @Test
    public void getDataFromFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, ToDoApp.class);
    }

    @Test
    public void getDataFromFile_emptyFile_DataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, ToDoApp.class);
    }

//    @Test
//    public void getDataFromFile_validFile_validResult() throws Exception {
//        XmlSerializableToDoApp dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableToDoApp.class);
//        assertEquals(9, dataFromFile.getTaskList().size());
//        assertEquals(0, dataFromFile.getTagList().size());
//    }

    @Test
    public void saveDataToFile_nullFile_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(null, new ToDoApp());
    }

    @Test
    public void saveDataToFile_nullClass_AssertionError() throws Exception {
        thrown.expect(AssertionError.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_FileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new ToDoApp());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableToDoApp dataToWrite = new XmlSerializableToDoApp(new ToDoApp());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableToDoApp dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableToDoApp.class);
        assertEquals((new ToDoApp(dataToWrite)).toString(), (new ToDoApp(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        ToDoAppBuilder builder = new ToDoAppBuilder(new ToDoApp());
        dataToWrite = new XmlSerializableToDoApp(
                builder.withTask(TestUtil.generateSampleTaskData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableToDoApp.class);
        assertEquals((new ToDoApp(dataToWrite)).toString(), (new ToDoApp(dataFromFile)).toString());
    }
}
