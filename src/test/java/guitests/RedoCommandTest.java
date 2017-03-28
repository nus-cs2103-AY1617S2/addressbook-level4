package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.testutil.TestTask;
import seedu.geekeep.testutil.TestUtil;

public class RedoCommandTest extends GeeKeepGuiTest {

    @Test
    public void redo() {
        // add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.meeting;
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertRedoSuccess(currentList);

        int targetIndexInOneIndexedFormat = 1;
        TestUtil.removeTaskFromList(currentList, targetIndexInOneIndexedFormat);
        commandBox.runCommand("delete" + targetIndexInOneIndexedFormat);
        assertRedoSuccess(currentList);

        currentList = new TestTask[0];
        commandBox.runCommand("clear");
        assertRedoSuccess(currentList);

        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    private void assertRedoSuccess(TestTask[] currentList) {
        commandBox.runCommand("undo");
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(currentList));

    }

}
