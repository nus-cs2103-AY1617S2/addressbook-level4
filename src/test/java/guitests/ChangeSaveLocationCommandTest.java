package guitests;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.ChangeSaveLocationCommand;

public class ChangeSaveLocationCommandTest extends TaskManagerGuiTest {

    public static final String INVALID_SAVE_LOCATION = "Invalid input for save location";

    // @@author A0142418L
    @Test
    public void changeSaveLocationInvalidCommand() {
        commandBox.runCommand("SAVEdata");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void changeSaveLocationInvalidSaveLocation() {
        commandBox.runCommand("SAVE hello");
        assertResultMessage(INVALID_SAVE_LOCATION + "\n" + ChangeSaveLocationCommand.MESSAGE_USAGE);
    }

    @Test
    public void changeSaveLocationInvalidCommandFormat() {
        commandBox.runCommand("SAVE");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeSaveLocationCommand.MESSAGE_USAGE));
    }

}
