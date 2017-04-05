//@@author A0162011A
package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.input.KeyCode;

/**
 * Gui tests for history command
 */
public class HelpCommandTest extends ToLuistGuiTest {
    @Test
    public void viewGeneralHelp() {
        String command = "help";
        commandBox.runCommand(command);
        assertHelpHeading("Displaying general help. Press any keys to go back.");
    }

    @Test
    public void viewDetailedHelp() {
        String[] commandList = { "add", "alias", "clear", "delete",
                                 "help", "find", "exit",
                                 "history", "load", "mark",
                                 "redo", "save", "switch",
                                 "tag", "unalias", "undo",
                                 "untag", "update", "viewalias" };
        for (String command : commandList) {
            commandBox.runCommand("help " + command);
            assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", command));
        }
    }

    @Test
    public void viewInvalidHelp() {
        String command = "help asdfasdfsadfaf";
        commandBox.runCommand(command);
        assertResultMessage("Sorry, that command does not exist.\nPlease type help for available commands.");
        assertFalse(helpView.isVisible());
    }

    @Test
    public void viewCaseInsensitiveHelp() {
        String command = "HeLp AdD";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.",
                "add"));
    }

    private void assertHelpHeading(String expected) {
        assertTrue(helpView.isVisible());
        assertEquals(helpView.getHeading(), expected);
        mainGui.press(KeyCode.R);
        assertFalse(helpView.isVisible());
    }
}
