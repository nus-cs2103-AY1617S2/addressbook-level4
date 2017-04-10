//@@author A0142255M

package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.tache.logic.commands.LoadCommand;
import seedu.tache.logic.commands.SaveCommand;
import seedu.tache.testutil.TestUtil;

public class StatusBarFooterTest extends TaskManagerGuiTest {

    public final String saveFolder1 = TestUtil.SANDBOX_FOLDER + "saveTest1";
    public final String saveFolder2 = TestUtil.SANDBOX_FOLDER + "saveTest2";
    public final String fileName = "\\taskmanager.xml";

    @Test
    public void statusBarFooter_showSyncStatus_success() {
        // at startup
        assertEquals(statusBarFooter.getSyncStatus().getText(), "Not updated yet in this session");

        // data saved when task manager changed
        commandBox.runCommand("clear");
        String dataSavedIndicator = statusBarFooter.getSyncStatus().getText().substring(0, 14);
        assertEquals(dataSavedIndicator, "Last Updated: ");
    }

    @Test
    public void statusBarFooter_showSaveLocation_success() {
        // save command
        commandBox.runCommand(SaveCommand.COMMAND_WORD + " " + saveFolder1);
        String filePath = saveFolder1 + fileName;
        assertEquals(statusBarFooter.getSaveLocationStatus().getText(), "New Location: " + filePath);

        // load command
        commandBox.runCommand(LoadCommand.COMMAND_WORD + " " + saveFolder2 + fileName);
        filePath = saveFolder2 + fileName;
        assertEquals(statusBarFooter.getSaveLocationStatus().getText(), "New Location: " + filePath);
    }

}
