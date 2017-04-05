package seedu.whatsleft.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.whatsleft.commons.core.Config;
import seedu.whatsleft.commons.exceptions.DataConversionException;
import seedu.whatsleft.commons.util.FileUtil;

//@@author A0121668A
public class JsonUserConfigStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/JsonUserConfigStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readUserConfig_nullFilePath_assertionFailure() throws DataConversionException {
        thrown.expect(AssertionError.class);
        readUserConfig(null);
    }

    private Optional<Config> readUserConfig(String userConfigFileInTestDataFolder) throws DataConversionException {
        String configFilePath = addToTestDataPathIfNotNull(userConfigFileInTestDataFolder);
        return new JsonUserConfigStorage(configFilePath).readUserConfig(configFilePath);
    }

    @Test
    public void readUserConfig_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readUserConfig("NonExistentFile.json").isPresent());
    }

    private String addToTestDataPathIfNotNull(String userConfigFileInTestDataFolder) {
        return userConfigFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + userConfigFileInTestDataFolder
                : null;
    }

    @Test
    public void readUserConfig_fileInOrder_successfullyRead() throws DataConversionException {
        Config expected = new Config();
        expected.setAppTitle("TestApp");
        expected.setWhatsLeftName("myTestWhatsLeft");
        Config actual = readUserConfig("TypicalConfig.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readUserConfig_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        Config expected = new Config();
        expected.setAppTitle("TestApp");
        expected.setWhatsLeftName("myTestWhatsLeft");
        Config actual = readUserConfig("ExtraValuesUserPref.json").get();

        assertEquals(expected, actual);
    }

    @Test
    public void saveConfig_nullConfig_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserConfig(null, "SomeFile.json");
    }

    @Test
    public void saveUserConfig_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserConfig(new Config(), null);
    }

    private void saveUserConfig(Config userConfig, String prefsFileInTestDataFolder) throws IOException {
        new JsonUserConfigStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder))
                .saveUserConfig(userConfig);
    }

    @Test
    public void saveUserConfig_allInOrder_success() throws DataConversionException, IOException {

        Config original = new Config();
        original.setAppTitle("TestApp");
        original.setWhatsLeftName("myTestWhatsLeft");

        String configFilePath = testFolder.getRoot() + File.separator + "TempConfig.json";
        JsonUserConfigStorage jsonUserConfigStorage = new JsonUserConfigStorage(configFilePath);

        //Try writing when the file doesn't exist
        jsonUserConfigStorage.saveUserConfig(original);
        Config readBack = jsonUserConfigStorage.readUserConfig().get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setAppTitle("newTitle");
        original.setWhatsLeftName("newName");
        jsonUserConfigStorage.saveUserConfig(original);
        readBack = jsonUserConfigStorage.readUserConfig().get();
        assertEquals(original, readBack);
    }
}
