package guitests;

import static org.junit.Assert.assertEquals;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.ChangeSaveLocationCommand;
import seedu.taskmanager.storage.XmlTaskManagerStorage;

public class ChangeSaveLocationCommandTest extends TaskManagerGuiTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private String temporaryFilePath = "";

    @Before
    public void setUp() {
        temporaryFilePath = testFolder.getRoot().getPath();
    }

    public static final String INVALID_SAVE_LOCATION = "Invalid input for save location";

    // @@author A0142418L
    @Test
    public void changeSaveLocation_invalidCommand() {
        commandBox.runCommand("SAVEdata");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void changeSaveLocation_invalidSaveLocation() {
        commandBox.runCommand("SAVE hello");
        assertResultMessage(INVALID_SAVE_LOCATION + "\n" + ChangeSaveLocationCommand.MESSAGE_USAGE);
    }

    @Test
    public void changeSaveLocation_invalidCommandFormat() {
        commandBox.runCommand("SAVE");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeSaveLocationCommand.MESSAGE_USAGE));
    }

    @Test
    public void changeSaveLocation_validSaveLocation() {
        commandBox.runCommand("SAVE " + temporaryFilePath);
        String fileLocation = "Save location changed to: " + temporaryFilePath + "\\" + "taskmanager.xml";
        assertResultMessage(fileLocation);
        assertEquals(temporaryFilePath + "\\" + "taskmanager.xml", XmlTaskManagerStorage.giveTaskManagerFilePath());
        commandBox.runCommand("SAVE data");
    }

}
