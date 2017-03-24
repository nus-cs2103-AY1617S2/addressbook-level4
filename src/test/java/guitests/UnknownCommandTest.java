package guitests;

import org.junit.Test;

import seedu.toluist.commons.core.Messages;

/**
 * GuiTest for unknown command
 */
public class UnknownCommandTest extends ToLuistGuiTest {
    @Test
    public void unknownCommand() {
        commandBox.runCommand("alias unknown");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
}
