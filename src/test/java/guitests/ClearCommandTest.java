//@@author A0146809W
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doit.logic.commands.ClearCommand;
import seedu.doit.logic.commands.DeleteCommand;

public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() {

        // verify a non-empty list can be cleared
        assertAllPanelsMatch(this.td.getTypicalTasks());
        assertClearCommandSuccess();

        // verify other commands can work after a clear command
        this.commandBox.runCommand(this.td.hoon.getAddCommand());
        assertTrue(this.taskListPanel.isListMatching(this.td.hoon));
        this.commandBox.runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertListSize(0);

        // verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        this.commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertResultMessage("All tasks has been cleared!");
    }
}
