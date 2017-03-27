//@@author A0163720M
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.todolist.commons.core.Config;
import seedu.todolist.commons.exceptions.DataConversionException;
import seedu.todolist.commons.util.ConfigUtil;
import seedu.todolist.testutil.TestUtil;

public class SaveFileCommandTest extends TodoListGuiTest {

    @Test
    public void saveFileTest() {
        commandBox.runCommand(TestUtil.getSaveFileCommand());

        try {
            Config config;
            config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();
            // If the save file had been successfully updated
            // the save file location in the config file should have updated too
            assertTrue(config.getTodoListFilePath().equals(TestUtil.SAVE_FILE_TEST));
        } catch (DataConversionException e) {
            e.printStackTrace();
        }
    }
}
//@@author
