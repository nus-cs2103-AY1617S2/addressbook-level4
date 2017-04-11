package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.logic.commands.RedoCommand;
//@@author A0163845X

public class RedoCommandTest extends TaskManagerGuiTest {

    @Test
    public void redo() {
        try {
            setup();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        commandBox.runCommand("clear");
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_FAIL);
        for (int i = 0; i < 11; i++) {
            assertTrue(i == taskListPanel.getNumberOfTasks());
            commandBox.runCommand("add " + i);
        }
        for (int i = 11; i > 1; i--) {
            assertTrue(i == taskListPanel.getNumberOfTasks());
            commandBox.runCommand("undo");
        }
        for (int i = 1; i < 11; i++) {
            assertTrue(i == taskListPanel.getNumberOfTasks());
            commandBox.runCommand("redo");
        }
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_FAIL);

    }
}
