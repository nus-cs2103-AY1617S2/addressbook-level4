//@@author A0142255M
package guitests;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.NextCommand;
import seedu.tache.logic.commands.PrevCommand;

public class PrevAndNextCommandTest extends TaskManagerGuiTest {

    @Test
    public void prev_success() {
        commandBox.runCommand("prev");
        assertResultMessage(PrevCommand.MESSAGE_SUCCESS);

        commandBox.runCommand("p");
        assertResultMessage(PrevCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void next_success() {
        commandBox.runCommand("next");
        assertResultMessage(NextCommand.MESSAGE_SUCCESS);

        commandBox.runCommand("n");
        assertResultMessage(NextCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void prevAndNext_invalidCommand_failure() {
        commandBox.runCommand("prv");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("ne");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

}
