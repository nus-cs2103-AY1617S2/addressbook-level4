//@@author A0142255M
package guitests;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.HelpCommand;

public class ResultDisplayTest extends TaskManagerGuiTest {

    @Test
    public void resultDisplay_blankOnStartup_success() {
        assertResultMessage("");
    }

    @Test
    public void resultDisplay_unknownCommand_failure() {
        commandBox.runCommand("yutyfcydvchui");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void resultDisplay_invalidCommand_failure() {
        commandBox.runCommand("");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

}
