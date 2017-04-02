package guitests;

import org.junit.Test;

import seedu.task.logic.commands.ExportCommand;
import seedu.task.logic.commands.ImportCommand;

public class ImportCommandTest extends TaskManagerGuiTest {

    @Test
    public void importTest() {

        commandBox.runCommand("import invalid/path/name.ics");
        assertResultMessage(ImportCommand.MESSAGE_INVALID_FILE_PATH);

        String validFilePath = "test.ics";
        commandBox.runCommand("export " + validFilePath);
        assertResultMessage(String.format(ExportCommand.MESSAGE_SUCCESS, validFilePath));
        commandBox.runCommand("import " + validFilePath);
        assertResultMessage(String.format(ImportCommand.MESSAGE_SUCCESS, validFilePath));
    }
}
