//@@author A0142255M
package guitests;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.NextCommand;
import seedu.tache.logic.commands.PrevCommand;

public class PrevAndNextCommandTest extends TaskManagerGuiTest {

    @Test
    public void prev() {
        commandBox.runCommand(PrevCommand.COMMAND_WORD);
        assertResultMessage(PrevCommand.MESSAGE_SUCCESS);

        commandBox.runCommand(PrevCommand.SHORT_COMMAND_WORD);
        assertResultMessage(PrevCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void next() {
        commandBox.runCommand(NextCommand.COMMAND_WORD);
        assertResultMessage(NextCommand.MESSAGE_SUCCESS);

        commandBox.runCommand(NextCommand.SHORT_COMMAND_WORD);
        assertResultMessage(NextCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void prevAndNext_additionalParameters_success() {
        commandBox.runCommand(PrevCommand.COMMAND_WORD + " month");
        assertResultMessage(PrevCommand.MESSAGE_SUCCESS);

        commandBox.runCommand(NextCommand.COMMAND_WORD + " prev next next");
        assertResultMessage(NextCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void prevAndNext_invalidCommand_failure() {
        commandBox.runCommand("previous");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("ne");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

}
