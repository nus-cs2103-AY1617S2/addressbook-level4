package guitests;

import org.junit.Test;

import seedu.doist.commons.core.Messages;
import seedu.doist.logic.commands.ResetAliasCommand;

//@@author A0147980U
public class ResetAliasCommandTest extends DoistGUITest {
    @Test
    public void testResetAliasSuccess() {
        commandBox.runCommand("reset_alias");
        assertResultMessage(ResetAliasCommand.MESSAGE_RESET_ALIAS_SUCCESS);

        commandBox.runCommand("reset_alias hello");
        assertResultMessage(ResetAliasCommand.MESSAGE_RESET_ALIAS_SUCCESS);
    }

    @Test
    public void testResetAliasWrongCommand() {
        //invalid command
        commandBox.runCommand("reset_aliass hello");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
}
