//@@author A0163559U
package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.task.TestApp;
import seedu.task.commons.core.Config;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.FileUtil;
import seedu.task.logic.commands.SaveCommand;
import seedu.task.storage.StorageManager;
import seedu.task.testutil.TestUtil;

public class SaveCommandTest extends AddressBookGuiTest {
    private static final String TEST_FOLDER = FileUtil.getPath("./src/test/data/SaveCommandTest/");
    private static final String TEST_CONFIG_PATH = FileUtil.getPath("./");
    private static final String TEST_CONFIG = "config.json";
    private static final String TEST_XML = "changeme.xml";
    private static final String TEST_SAMPLE_DATA_PATH = TestUtil.getFilePathInSandboxFolder(TEST_XML);

    private static final String SAVE_COMMAND = "save ";
    private String[] saveFiles = { "blooper", "taskmanager.xml", "data/taskmanager.xml", "data/taskmanager",
            "taskmanager", "secret_folder/secret_tasks.xml", "secret_folder/secret_tasks" };
    private String[] saveFiles_Windows = { "blooper", "taskmanager.xml", "data\\taskmanager.xml", "data\\taskmanager",
            "taskmanager", "secret_folder\\secret_tasks.xml", "secret_folder\\secret_tasks" };
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void reset_config() throws DataConversionException, IOException {
        // System.out.println("Executing JUnit before!");
        TestUtil.createDataFileWithSampleData(TEST_SAMPLE_DATA_PATH);
        TestUtil.createDataFileWithSampleData(TestApp.SAVE_LOCATION_FOR_TESTING);

        Optional<Config> opConfig = readConfig(TEST_CONFIG);
        if (opConfig.isPresent()) {
            Config config = opConfig.get();
            config.setTaskManagerFilePath(TEST_FOLDER + TEST_XML);
            saveConfig(config, TEST_CONFIG);
            System.out.println("Reset TaskManagerFilePath to " + config.getTaskManagerFilePath());
        }
        ((StorageManager) storage).updateXmlTaskListStorage(new File(TEST_SAMPLE_DATA_PATH));
    }

    @Test
    public void save_absolute_success() throws IllegalValueException, DataConversionException, IOException {
        //This test involves saving files with and without extensions, with and without creating new folders
        //However, all files are prefixed with a specified test folder
        String[] files = chooseTestStringsByOS();
        for (String saveFile : files) {
            System.out.println("Testing " + saveFile + "...");
            assertSaveSuccess(TEST_FOLDER + saveFile);
        }
    }

    private String[] chooseTestStringsByOS() {
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            return saveFiles_Windows;
        } else {
            return saveFiles;
        }
    }

    @Test
    public void save_relative_success() throws IllegalValueException, DataConversionException, IOException {
        //This test is similar to the previous, but does not specify the test path.
        String[] files = chooseTestStringsByOS();

        for (String saveFile : files) {
            System.out.println("Testing " + saveFile + "...");
            assertSaveSuccess(saveFile);
        }
    }

    @Test
    public void save_nullFile_illegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(SaveCommand.MESSAGE_NULL_SAVE_LOCATION);
        SaveCommand sc = new SaveCommand(null);
    }

    @Test
    public void save_emptyString_illegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(SaveCommand.MESSAGE_NULL_SAVE_LOCATION);
        SaveCommand sc = new SaveCommand("");
    }

    @Test
    public void save_isDirectory_illegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(SaveCommand.MESSAGE_DIRECTORY_SAVE_LOCATION);
        SaveCommand sc = new SaveCommand(TEST_FOLDER);
    }

    private void assertSaveSuccess(String expectedFilePath) throws DataConversionException, IOException {
        System.out.println("before save: " + getFilePathFromConfig());

        commandBox.runCommand(SAVE_COMMAND + expectedFilePath);

        // confirm config file is updated properly

        String actualFilePath = getFilePathFromConfig();
        System.out.println("after save: " + actualFilePath);
        System.out.println("expected: " + expectedFilePath);
        assertEquals(actualFilePath, expectedFilePath);

        // compare old and new task lists
        File expectedFile = new File(TEST_SAMPLE_DATA_PATH);
        File actualFile = new File(expectedFilePath);
        boolean filesMatch = FileUtil.isFileContentSame(expectedFile, actualFile);
        assertTrue(filesMatch);
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
