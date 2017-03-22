package guitests;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandTest extends AddressBookGuiTest {

    @Test
    public void export() {

        commandBox.runCommand("export invalid/path/name.ics");
        assertResultMessage(ExportCommand.MESSAGE_INVALID_FILE_PATH);

        String validFilePath = "test.ics";
        commandBox.runCommand("export " + validFilePath);
        assertResultMessage(String.format(ExportCommand.MESSAGE_SUCCESS, validFilePath));
    }
}
