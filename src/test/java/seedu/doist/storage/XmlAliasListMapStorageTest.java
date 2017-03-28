package seedu.doist.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.doist.commons.exceptions.DataConversionException;
import seedu.doist.commons.util.FileUtil;
import seedu.doist.model.AliasListMap;
import seedu.doist.model.ReadOnlyAliasListMap;

public class XmlAliasListMapStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAliasListMapStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAliasListMap_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readAliasListMap(null);
    }

    private java.util.Optional<ReadOnlyAliasListMap> readAliasListMap(String filePath) throws Exception {
        return new XmlAliasListMapStorage(filePath).readAliasListMap(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAliasListMap("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAliasListMap("NotXmlFormatAliasListMap.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAliasListMap.xml";
        AliasListMap original = new AliasListMap();
        XmlAliasListMapStorage xmlAliasListMapStorage = new XmlAliasListMapStorage(filePath);

        //Save in new file and read back
        xmlAliasListMapStorage.saveAliasListMap(original, filePath);
        ReadOnlyAliasListMap readBack = xmlAliasListMapStorage.readAliasListMap(filePath).get();
        assertEquals(original, new AliasListMap(readBack));

        //Modify data, overwrite exiting file, and read back
        original.setAlias("hello", "add");
        xmlAliasListMapStorage.saveAliasListMap(original, filePath);
        readBack = xmlAliasListMapStorage.readAliasListMap(filePath).get();
        assertEquals(original, new AliasListMap(readBack));

        //Save and read without specifying file path
        original.setAlias("goodbye", "add");
        xmlAliasListMapStorage.saveAliasListMap(original); //file path not specified
        readBack = xmlAliasListMapStorage.readAliasListMap().get(); //file path not specified
        assertEquals(original, new AliasListMap(readBack));

    }

    @Test
    public void saveAliasListMap_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAliasListMap(null, "SomeFile.xml");
    }

    private void saveAliasListMap(ReadOnlyAliasListMap aliasListMap, String filePath) throws IOException {
        new XmlAliasListMapStorage(filePath).saveAliasListMap(aliasListMap, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAliasListMap_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAliasListMap(new AliasListMap(), null);
    }


}
