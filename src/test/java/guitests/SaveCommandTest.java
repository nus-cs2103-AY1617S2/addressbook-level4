//@@author A0163559U
package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.task.commons.core.Config;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.commons.util.FileUtil;

public class SaveCommandTest extends AddressBookGuiTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/SaveCommandTest/");
    private static final String TEST_CONFIG_FOLDER = FileUtil.getPath("./");
    private static final String TEST_CONFIG = "config.json";
    private static final String INITIAL_CONFIG_FILE_PATH = "changeme.xml";

    private static final String saveCommand = "save ";

    @Before
    public void reset_config() throws DataConversionException, IOException {
        //System.out.println("Executing Junit before!");
        Optional<Config> opConfig = readConfig(TEST_CONFIG);
        if (opConfig.isPresent()) {
            Config config = opConfig.get();
            config.setTaskManagerFilePath(INITIAL_CONFIG_FILE_PATH);
            saveConfig(config, TEST_CONFIG);
            System.out.println("Reset TaskManagerFilePath to " +
                    config.getTaskManagerFilePath());
        } else {
            fail();
        }
    }

    @Test
    public void save_success() throws IllegalValueException, DataConversionException {
        String[] saveFiles = {"blooper", "taskmanager.xml", "data/taskmanager.xml",
                "data/taskmanager", "taskmanager"};
        // String saveFile = "data/taskmanager.xml";
        // String saveFile = "taskmanager";
        // String saveFile = "data/taskmanager";
        for (String saveFile : saveFiles) {
            System.out.println("Testing " + saveFile + "...");
            assertSaveSuccess(saveFile);
        }
    }

    private void assertSaveSuccess(String saveFile) throws DataConversionException {
        System.out.println("before save: " + getFilePathFromConfig());
        commandBox.runCommand(saveCommand + saveFile);

        //confirm config file is updated properly

        String actual = getFilePathFromConfig();
        System.out.println("after save: " + actual);
        System.out.println("expected: " + saveFile);
        assertEquals(actual, saveFile);

        //compare old and new task lists


    }

    private String getFilePathFromConfig() throws DataConversionException {
        Optional<Config> opConfig = readConfig(TEST_CONFIG);
        String configTaskManagerFilePath = "";
        if (opConfig.isPresent()) {
            Config config = opConfig.get();
            configTaskManagerFilePath = config.getTaskManagerFilePath();
        } else {
            fail();
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
        return configFileInTestDataFolder != null
                ? TEST_CONFIG_FOLDER + configFileInTestDataFolder
                        : null;
    }
}
