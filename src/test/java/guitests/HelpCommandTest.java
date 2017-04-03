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
    public void viewAddHelp() {
        String command = "help add";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "add"));
    }

    @Test
    public void viewAliasHelp() {
        String command = "help alias";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "alias"));
    }

    @Test
    public void viewClearHelp() {
        String command = "help clear";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "clear"));
    }

    @Test
    public void viewDeleteHelp() {
        String command = "help delete";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "delete"));
    }

    @Test
    public void viewExitHelp() {
        String command = "help exit";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "exit"));
    }

    @Test
    public void viewFindHelp() {
        String command = "help find";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "find"));
    }

    @Test
    public void viewHelpHelp() {
        String command = "help help";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "help"));
    }

    @Test
    public void viewHistoryHelp() {
        String command = "help history";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "history"));
    }

    @Test
    public void viewLoadHelp() {
        String command = "help load";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "load"));
    }

    @Test
    public void viewMarkHelp() {
        String command = "help mark";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "mark"));
    }

    @Test
    public void viewRedoHelp() {
        String command = "help redo";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "redo"));
    }

    @Test
    public void viewSaveHelp() {
        String command = "help save";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "save"));
    }

    @Test
    public void viewSwitchHelp() {
        String command = "help switch";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "switch"));
    }

    @Test
    public void viewTagHelp() {
        String command = "help tag";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "tag"));
    }

    @Test
    public void viewUnaliasHelp() {
        String command = "help unalias";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "unalias"));
    }

    @Test
    public void viewUndoHelp() {
        String command = "help undo";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "undo"));
    }

    @Test
    public void viewUntagHelp() {
        String command = "help untag";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "untag"));
    }

    @Test
    public void viewUpdateHelp() {
        String command = "help update";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "update"));
    }

    @Test
    public void viewViewAliasHelp() {
        String command = "help viewalias";
        commandBox.runCommand(command);
        assertHelpHeading(String.format("Displaying detailed help for %s. Press any keys to go back.", "viewalias"));
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
