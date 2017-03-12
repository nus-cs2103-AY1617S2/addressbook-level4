package seedu.toluist.commons.core;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.sun.glass.ui.monocle.util.C;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.toluist.commons.util.FileUtil;
import seedu.toluist.model.AliasTable;
import seedu.toluist.testutil.TestUtil;

import java.io.File;
import java.util.logging.Level;

public class ConfigTest {
    private static final String TEST_CONFIG_FILE_PATH = FileUtil.getPath("./src/test/data/ConfigTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void resetConfig() throws NoSuchFieldException, IllegalAccessException {
        TestUtil.resetSingleton(Config.class);
    }

    @Test
    public void defaultConfig_correctProperties() {
        Config config = new Config();
        assertSimilarToDefaultConfig(config);
    }

    @Test
    public void load_nonExistentFile_returnsDefaultConfig() {
        Config config = loadTestConfig(folder.getRoot().getPath());
        assertSimilarToDefaultConfig(config);
    }

    @Test
    public void load_emptyConfig_returnsDefaultConfig() {
        Config config = loadTestConfig("EmptyConfig.json");
        assertSimilarToDefaultConfig(config);
    }

    @Test
    public void load_notJsonFormat_returnsDefaultConfig() {
        Config config = loadTestConfig("NotJsonFormatConfig.json");
        assertSimilarToDefaultConfig(config);
    }

    @Test
    public void load_differentValues_useDifferentValues() {
        Config config = loadTestConfig("TypicalConfig.json");
        assertEquals(config, getTypicalConfig());
    }

    @Test
    public void save_fileThatCannotBeSaved_fail() {
        String path = folder.getRoot().getPath();
        Config.setConfigFilePath(path);
        assertFalse(getTypicalConfig().save());
    }

    @Test
    public void save_missingFile_createNewFile() {
        String missingFilePath = TestUtil.getFilePathInSandboxFolder("testConfig.json");
        File missingFile = new File(missingFilePath);

        // Remove this file if it exists
        FileUtil.removeFile(missingFile);
        assertFalse(FileUtil.isFileExists(missingFile));

        Config.setConfigFilePath(missingFilePath);
        assertTrue(getTypicalConfig().save());
        assertTrue(FileUtil.isFileExists(missingFile));
        assertEquals(getTypicalConfig(), Config.getInstance());
    }

    @Test
    public void save_existingFile_overWriteFile() {
        String filePath = TestUtil.getFilePathInSandboxFolder("testConfig.json");
        Config.setConfigFilePath(filePath);

        // Save a config first
        Config defaultConfig = new Config();
        defaultConfig.save();

        // Then save another config
        getTypicalConfig().save();

        // Remove this file if it exists
        assertNotEquals(defaultConfig, Config.getInstance());
        assertEquals(getTypicalConfig(), Config.getInstance());
    }

    private Config loadTestConfig(String testConfigFilePath) {
        Config.setConfigFilePath(TEST_CONFIG_FILE_PATH + "/" + testConfigFilePath);
        return Config.getInstance();
    }

    /**
     * Returns a typical config
     */
    private Config getTypicalConfig() {
        Config config = new Config();
        config.setTodoListFilePath("data/b.json");
        config.getAliasTable().setAlias("a", "add");
        return config;
    }

    /**
     * Assert that the config's properties are the default properties
     */
    private void assertSimilarToDefaultConfig(Config config) {
        assertEquals(config.getAppTitle(), Config.APP_NAME);
        assertEquals(config.getAliasTable(), new AliasTable());
        assertEquals(config.getLogLevel(), Level.INFO);
        assertEquals(config.getTodoListFilePath(), Config.DEFAULT_TODO_LIST_FILE_PATH);
    }
}
