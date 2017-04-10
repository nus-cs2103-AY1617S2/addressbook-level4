package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tache.logic.commands.ClearCommand;

public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() {
        System.out.println(taskListPanel.getTask(4));
        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.getFit.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.getFit));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    //@@author A0142255M
    public void clear_invalidCommand_failure() {
        commandBox.runCommand("clearrrrrrr");
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
    }

    public void clear_additionalParameters_success() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD + " my entire task list");
        assertClearCommandSuccess();
    }
    //@@author

    private void assertClearCommandSuccess() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
    }
}
