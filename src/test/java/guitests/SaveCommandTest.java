//@@author A0163559U
package guitests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.task.commons.core.Config;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.FileUtil;
import seedu.task.logic.commands.SaveCommand;
import seedu.task.testutil.TestUtil;

public class SaveCommandTest extends AddressBookGuiTest {
    private static final String TEST_FOLDER = FileUtil.getPath("./src/test/data/SaveCommandTest/");
    private static final String TEST_CONFIG_PATH = FileUtil.getPath("./");
    private static final String TEST_CONFIG = "config.json";
    private static final String TEST_XML = "changeme.xml";

    private static final String SAVE_COMMAND = "save ";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void reset_config() throws DataConversionException, IOException {
        // System.out.println("Executing JUnit before!");
        String sampleDataFilePath = TestUtil.getFilePathInSandboxFolder(TEST_XML);
        TestUtil.createDataFileWithSampleData(sampleDataFilePath);
        Optional<Config> opConfig = readConfig(TEST_CONFIG);
        if (opConfig.isPresent()) {
            Config config = opConfig.get();
            config.setTaskManagerFilePath(TEST_FOLDER + TEST_XML);
            saveConfig(config, TEST_CONFIG);
            System.out.println("Reset TaskManagerFilePath to " + config.getTaskManagerFilePath());
        }
    }

    @Test
    public void save_success() throws IllegalValueException, DataConversionException {
        //This test involves saving files with and without extensions, with and without creating new folders
        //However, all files are prefixed with a specified test folder
        String[] saveFiles = { "blooper", "taskmanager.xml", "data/taskmanager.xml", "data/taskmanager",
                "taskmanager", "secret_folder/secret_tasks.xml", "secret_folder/secret_tasks" };
        for (String saveFile : saveFiles) {
            System.out.println("Testing " + saveFile + "...");
            assertSaveSuccess(saveFile);
        }
    }

    @Test
    public void save_nullFile_illegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        SaveCommand sc = new SaveCommand(null);
    }

    @Test
    public void save_emptyString_illegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        SaveCommand sc = new SaveCommand("");
    }

    private void assertSaveSuccess(String saveFile) throws DataConversionException {
        System.out.println("before save: " + getFilePathFromConfig());
        saveFile = TEST_FOLDER + saveFile;
        commandBox.runCommand(SAVE_COMMAND + saveFile);

        // confirm config file is updated properly

        String actual = getFilePathFromConfig();
        System.out.println("after save: " + actual);
        System.out.println("expected: " + saveFile);
        assertEquals(actual, saveFile);

        // compare old and new task lists

    }

    private String getFilePathFromConfig() throws DataConversionException {
        Optional<Config> opConfig = readConfig(TEST_CONFIG);
        String configTaskManagerFilePath = "";
        if (opConfig.isPresent()) {
            Config config = opConfig.get();
            configTaskManagerFilePath = config.getTaskManagerFilePath();
        }
        return configTaskManagerFilePath;
    }

    private Optional<Config> readConfig(String configFileInTestDataFolder) throws DataConversionException {
        String configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        return ConfigUtil.readConfig(configFilePath);
    }

    private void saveConfig(Config config, String configFileInTestDataFolder) throws IOException {
        String configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        ConfigUtil.saveConfig(config, configFilePath);
    }

    private String addToTestDataPathIfNotNull(String configFileInTestDataFolder) {
        return configFileInTestDataFolder != null ? TEST_CONFIG_PATH + configFileInTestDataFolder : null;
    }
}
