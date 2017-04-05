package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_COMMAND_DOES_NOT_EXIST;

import org.junit.Test;

import guitests.guihandles.HelpFormatWindowHandle;
import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.EditCommand;
//@@author A0142487Y
public class HelpCommandTest extends TaskManagerGuiTest {

    @Test
    public void help_WrongHelpCommand() {
        commandBox.runCommand("hepl clear");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("manu delete");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void help_Command_success() {
        commandBox.runCommand("help add");
        assertResultMessage(AddCommand.MESSAGE_USAGE);
        commandBox.runCommand("man edit");
        assertResultMessage(EditCommand.MESSAGE_USAGE);

    }

    @Test
    public void help_Command_fail() {
        String incorrectCommandWord = "qweqw";
        commandBox.runCommand("help " + incorrectCommandWord);
        assertHelpFormatWindowOpen(mainMenu.openHelpFormatWindowUsingAccelerator());
        assertResultMessage(String.format(MESSAGE_COMMAND_DOES_NOT_EXIST, incorrectCommandWord));

        commandBox.runCommand("man " + incorrectCommandWord);
        assertHelpFormatWindowOpen(mainMenu.openHelpFormatWindowUsingAccelerator());
        assertResultMessage(String.format(MESSAGE_COMMAND_DOES_NOT_EXIST, incorrectCommandWord));
    }
  //@@author 
  //@@author A0142487Y--reused
    private void assertHelpFormatWindowOpen(HelpFormatWindowHandle helpFormatWindowHandle) {
        assertTrue(helpFormatWindowHandle.isWindowOpen());
        helpFormatWindowHandle.closeWindow();
    }
}
