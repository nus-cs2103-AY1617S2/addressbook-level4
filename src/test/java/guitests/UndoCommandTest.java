package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.testutil.TestTask;

public class UndoCommandTest extends GeeKeepGuiTest {

    @Test
    public void undo() {
        // add one task
        TestTask[] currentList = td.getTypicalTasks();
        String command = td.meeting.getAddCommand();
        assertUndoSuccess(command, currentList);

        command = "delete 1";
        assertUndoSuccess(command, currentList);

        command = "clear";
        assertUndoSuccess(command, currentList);

        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    private void assertUndoSuccess(String command, TestTask[] currentList) {
        commandBox.runCommand(command);
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));
    }

}
