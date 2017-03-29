package guitests;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.testutil.TestUtil;

public class SaveToCommandTest extends TaskManagerGuiTest {
    private static final String TEST_SAVE_LOCATION = FileUtil.getPath("./src/test/data/SaveToCommandTest/");
    private static final File newTestStorage = new File(TEST_SAVE_LOCATION + "taskmanager.xml");

    @Test
    public void saveTo() throws IllegalArgumentException, IllegalValueException {
        // verify that 'saveto' command doesn't affect list
        assertTrue(futureTaskListPanel.isListMatching(td.getTypicalTasks()));
        assertSaveToSuccess();
        assertTrue(futureTaskListPanel.isListMatching(td.getTypicalTasks()));

        // verify other commands can work after a saveto command
        commandBox.runCommand(td.hoon.getAddCommand());
        assertTrue(futureTaskListPanel.isListMatching(TestUtil.addTasksToList(td.getTypicalTasks(), td.hoon)));
        commandBox.runCommand("delete F1");
        assertListSize(td.getTypicalTasks().length);

        assertTrue(newTestStorage.delete());
        assertTrue(newTestStorage.getParentFile().delete());
    }

    private void assertSaveToSuccess() {
        if (FileUtil.isFileExists(newTestStorage)) {
            FileUtil.deleteFile(newTestStorage);
        }

        commandBox.runCommand("saveto " + TEST_SAVE_LOCATION);
        assertTrue(FileUtil.isFileExists(newTestStorage));
    }

}
