package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.testutil.TestUtil;

public class SaveToCommandTest extends TaskManagerGuiTest {
    private static final String TEST_SAVE_LOCATION = FileUtil.getPath("./src/test/data/SaveToCommandTest/");
    private static final File newTestStorage = new File(TEST_SAVE_LOCATION + "taskmanager.xml");

    @Test
    public void saveTo() {
        //verify that 'save to' command doesn't affect list
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertSaveToSuccess();
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));

        //verify other commands can work after a save to command
        commandBox.runCommand(td.hoon.getAddCommand());
        assertTrue(taskListPanel.isListMatching(TestUtil.addTasksToList(td.getTypicalTasks(), td.hoon)));
        commandBox.runCommand("delete 1");
        assertListSize(td.getTypicalTasks().length);
    }

    private void assertSaveToSuccess() {
        if (FileUtil.isFileExists(newTestStorage)) {
            FileUtil.deleteFile(newTestStorage);
        }

        commandBox.runCommand("save to " + TEST_SAVE_LOCATION);
        assertTrue(FileUtil.isFileExists(newTestStorage));
    }
}
