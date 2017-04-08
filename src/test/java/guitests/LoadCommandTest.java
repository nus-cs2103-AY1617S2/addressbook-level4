//@@author A0163559U
package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import seedu.task.logic.commands.LoadCommand;
import seedu.task.model.task.Task;
import seedu.task.testutil.TestUtil;

public class LoadCommandTest extends AddressBookGuiTest {
    private static final String TEST_FOLDER = FileUtil.getPath("./src/test/data/LoadCommandTest/");
    private static final String TEST_CONFIG_PATH = FileUtil.getPath("./");
    private static final String TEST_CONFIG = "config.json";
    private static final String TEST_XML = "changeme.xml";
    private static final String TEST_SAMPLE_DATA_PATH = TestUtil.getFilePathInSandboxFolder(TEST_XML);
    private static final String TEST_XML_TO_LOAD = "loadme.xml";
    private static final String TEST_XML_TO_LOAD_PATH = TEST_FOLDER + TEST_XML_TO_LOAD;

    private static final String LOAD_COMMAND = "load ";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void reset_config() throws DataConversionException, IOException {
        //initialize sample data
        TestUtil.createDataFileWithSampleData(TEST_SAMPLE_DATA_PATH);

        //create slightly different sample data to later verify successful load
        ArrayList<Task> tasks = new ArrayList<Task>(Arrays.asList(TestUtil.SAMPLE_TASK_DATA));
        tasks.remove(0);
        TestUtil.createDataFileWithData(TestUtil.generateSampleStorageTaskManager(tasks), TEST_XML_TO_LOAD_PATH);

        Optional<Config> opConfig = readConfig(TEST_CONFIG);
        if (opConfig.isPresent()) {
            Config config = opConfig.get();
            config.setTaskManagerFilePath(TEST_FOLDER + TEST_XML);
            loadConfig(config, TEST_CONFIG);
            System.out.println("Reset TaskManagerFilePath to " + config.getTaskManagerFilePath());
        }
    }

    @Test
    public void load_success() throws IllegalValueException, DataConversionException, IOException {
        System.out.println("Testing " + TEST_XML_TO_LOAD_PATH + "...");
        assertLoadSuccess(TEST_XML_TO_LOAD_PATH);
    }

    @Test
    public void load_nullFile_illegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(LoadCommand.MESSAGE_NULL_LOAD_LOCATION);
        LoadCommand sc = new LoadCommand(null);
    }

    @Test
    public void load_emptyString_illegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(LoadCommand.MESSAGE_NULL_LOAD_LOCATION);
        LoadCommand sc = new LoadCommand("");
    }

    @Test
    public void load_isDirectory_illegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(LoadCommand.MESSAGE_DIRECTORY_LOAD_LOCATION);
        LoadCommand sc = new LoadCommand(TEST_FOLDER);
    }

    @Test
    public void load_nonExistentFile_illegalValueException() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        String nonExistentFile = "thisisnotafile";
        thrown.expectMessage(String.format(LoadCommand.MESSAGE_INVALID_LOAD_LOCATION, nonExistentFile));
        LoadCommand sc = new LoadCommand(nonExistentFile);
    }

    private void assertLoadSuccess(String expectedFilePath) throws DataConversionException, IOException {
        System.out.println("before load: " + getFilePathFromConfig());

        commandBox.runCommand(LOAD_COMMAND + expectedFilePath);

        // confirm config file is updated properly

        String actualFilePath = getFilePathFromConfig();
        System.out.println("after load: " + actualFilePath);
        System.out.println("expected: " + expectedFilePath);
        assertEquals(actualFilePath, expectedFilePath);

        // compare old and new task lists
        File expectedFile = new File(TEST_XML_TO_LOAD_PATH);
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

    private void loadConfig(Config config, String configFileInTestDataFolder) throws IOException {
        String configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        ConfigUtil.saveConfig(config, configFilePath);
    }

    private String addToTestDataPathIfNotNull(String configFileInTestDataFolder) {
        return configFileInTestDataFolder != null ? TEST_CONFIG_PATH + configFileInTestDataFolder : null;
    }
}
