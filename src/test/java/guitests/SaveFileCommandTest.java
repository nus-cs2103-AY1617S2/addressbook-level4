//@@author A0163720M
package guitests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import seedu.todolist.commons.core.Config;
import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.commons.util.ConfigUtil;
import seedu.todolist.testutil.TestUtil;

public class SaveFileCommandTest extends TodoListGuiTest {
    public static final String EXISTING_SAVE_FILE = "src/test/data/SaveFileTest/saveFileTest.xml";
    public static final String NON_EXISTING_SAVE_FILE = "src/test/data/SaveFileTest/doesnotexist.xml";

    /*
     * Change save file location given that the new file exists
     */
    @Test
    public void saveFile_validPath_success() {
        commandBox.runCommand(TestUtil.getSaveFileCommand(EXISTING_SAVE_FILE));

        try {
            Config config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();
            // If the save file had been successfully updated
            // the save file location in the config file should have updated too
            assertTrue(config.getTodoListFilePath().equals(EXISTING_SAVE_FILE));
        } catch (DataConversionException e) {
            e.printStackTrace();
        }
    }

    /*
     * Change save file location given that the new file does not exist
     * Save File command should create the new file and update the location
     */
    @Test
    public void saveFile_nonExistingPath_success() {
        try {
            // If the file already exists, delete it
            Path path = FileSystems.getDefault().getPath(NON_EXISTING_SAVE_FILE);
            Files.deleteIfExists(path);

            commandBox.runCommand(TestUtil.getSaveFileCommand(NON_EXISTING_SAVE_FILE));
            Config config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();

            assertTrue(config.getTodoListFilePath().equals(NON_EXISTING_SAVE_FILE));
        } catch (DataConversionException | IOException e) {
            e.printStackTrace();
        }
    }
}
//@@author
