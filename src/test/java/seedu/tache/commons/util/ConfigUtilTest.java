package seedu.tache.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.tache.commons.core.Config;
import seedu.tache.commons.exceptions.DataConversionException;

public class ConfigUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ConfigUtilTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readNullAssertionFailure() throws DataConversionException {
        thrown.expect(AssertionError.class);
        read(null);
    }

    @Test
    public void readMissingFileEmptyResult() throws DataConversionException {
        assertFalse(read("NonExistentFile.json").isPresent());
    }

    @Test
    public void readNotJsonFormatExceptionThrown() throws DataConversionException {

        thrown.expect(DataConversionException.class);
        read("NotJsonFormatConfig.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readFileInOrderSuccessfullyRead() throws DataConversionException {

        Config expected = getTypicalConfig();

        Config actual = read("TypicalConfig.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readValuesMissingFromFileDefaultValuesUsed() throws DataConversionException {
        Config actual = read("EmptyConfig.json").get();
        assertEquals(new Config(), actual);
    }

    @Test
    public void readExtraValuesInFileExtraValuesIgnored() throws DataConversionException {
        Config expected = getTypicalConfig();
        Config actual = read("ExtraValuesConfig.json").get();

        assertEquals(expected, actual);
    }

    private Config getTypicalConfig() {
        Config config = new Config();
        config.setAppTitle("Typical App Title");
        config.setLogLevel(Level.INFO);
        config.setUserPrefsFilePath("C:\\preferences.json");
        config.setTaskManagerFilePath("taskmanager.xml");
        config.setTaskManagerName("TypicalTaskManagerName");
        return config;
    }

    private Optional<Config> read(String configFileInTestDataFolder) throws DataConversionException {
        String configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        return ConfigUtil.readConfig(configFilePath);
    }

    @Test
    public void saveNullConfigAssertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        save(null, "SomeFile.json");
        fail();
    }

    @Test
    public void saveNullFileAssertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        save(new Config(), null);
        fail();
    }

    @Test
    public void saveConfigAllInOrderSuccess() throws DataConversionException, IOException {
        Config original = getTypicalConfig();

        String configFilePath = testFolder.getRoot() + File.separator + "TempConfig.json";

        //Try writing when the file doesn't exist
        ConfigUtil.saveConfig(original, configFilePath);
        Config readBack = ConfigUtil.readConfig(configFilePath).get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setAppTitle("Updated Title");
        original.setLogLevel(Level.FINE);
        ConfigUtil.saveConfig(original, configFilePath);
        readBack = ConfigUtil.readConfig(configFilePath).get();
        assertEquals(original, readBack);
    }

    private void save(Config config, String configFileInTestDataFolder) throws IOException {
        String configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        ConfigUtil.saveConfig(config, configFilePath);
    }

    private String addToTestDataPathIfNotNull(String configFileInTestDataFolder) {
        return configFileInTestDataFolder != null
                                  ? TEST_DATA_FOLDER + configFileInTestDataFolder
                                  : null;
    }


}
