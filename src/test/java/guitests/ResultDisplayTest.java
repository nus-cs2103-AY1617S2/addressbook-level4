//@@author A0142255M
package guitests;

import org.junit.Test;

import seedu.tache.commons.core.Messages;

public class ResultDisplayTest extends TaskManagerGuiTest {

    @Test
    public void resultDisplay_blankOnStartup_success() {
        assertResultMessage("");
    }

    @Test
    public void resultDisplay_unknownCommand_failure() {
        commandBox.runCommand("yutyfcydvchui");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("");
        assertResultMessage(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
    }

}
