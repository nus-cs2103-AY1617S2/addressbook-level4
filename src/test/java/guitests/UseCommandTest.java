package guitests;

import org.junit.Test;

import seedu.task.logic.commands.UseCommand;

public class UseCommandTest extends TaskManagerGuiTest {

    @Test
    public void use() {

        commandBox.runCommand("cd invalid/path/name.invalid");
        assertResultMessage(String.format(UseCommand.MESSAGE_INVALID_FILE_PATH, UseCommand.MESSAGE_USAGE));

        String validStorageFilePath = "test.xml";
        commandBox.runCommand("cd " + validStorageFilePath);
        assertResultMessage(String.format(UseCommand.MESSAGE_SUCCESS_STORAGE_FILE_PATH, validStorageFilePath));

        String validFilePath = "test.json";
        commandBox.runCommand("cd " + validFilePath);
        assertResultMessage(String.format(UseCommand.MESSAGE_SUCCESS_USER_PREFS_FILE_PATH, validFilePath));
    }
}
