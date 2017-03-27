package guitests;

import static org.mockito.Matchers.any;
import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import seedu.ezdo.commons.util.FileUtil;
import seedu.ezdo.logic.commands.SaveCommand;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest({FileUtil.class})
public class SaveCommandTest extends EzDoGuiTest {

    private final String validDirectory = "./";
    private final String inexistentDirectory = "data/COWABUNGA";

    @Test
    public void save_validDirectory_success() {
        commandBox.runCommand("save " + validDirectory);
        assertResultMessage(String.format(SaveCommand.MESSAGE_SAVE_TASK_SUCCESS,
                validDirectory + SaveCommand.DATA_FILE_NAME));
    }

    @Test
    public void save_invalidFormat_failure() {
        commandBox.runCommand("save");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }

    @Test
    public void save_inexistentDirectory_failure() {
        commandBox.runCommand("save " + inexistentDirectory);
        assertResultMessage(String.format(SaveCommand.MESSAGE_DIRECTORY_PATH_DOES_NOT_EXIST));
    }

  /*  @Test
    public void save_validDirectory_noAdminPermissions_failure() throws Exception {
        PowerMockito.spy(FileUtil.class);
        PowerMockito.doThrow(new IOException()).when(FileUtil.class, "createIfMissing", any(File.class));
        commandBox.runCommand("save " + validDirectory);
        assertResultMessage(String.format(SaveCommand.MESSAGE_DIRECTORY_PATH_INVALID));
    } */
}
