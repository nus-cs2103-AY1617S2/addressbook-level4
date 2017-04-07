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
        assertTodayFutureListsMatching(todayList, futureList);
        assertSaveToSuccess();
        assertTodayFutureListsMatching(todayList, futureList);

        // verify other commands can work after a saveto command
        commandBox.runCommand(TestUtil.makeAddCommandString(td.extraFloat));
        futureList = TestUtil.addTasksToList(futureList, td.extraFloat, 1);
        assertTodayFutureListsMatching(todayList, futureList);
        commandBox.runCommand("delete F2");
        assertFutureListSize(td.getFutureListTasks().length);

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
