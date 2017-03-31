//@@author A0162011A
package guitests;

import org.junit.Test;

/**
 * Gui tests for history command
 */
public class HelpCommandTest extends ToLuistGuiTest {
    @Test
    public void viewGeneralHelp() {
        String command = "help";
        commandBox.runCommand(command);
        assertResultMessage("Displaying general help.");
    }

    @Test
    public void viewAddHelp() {
        String command = "help add";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "add"));
    }

    @Test
    public void viewAliasHelp() {
        String command = "help alias";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "alias"));
    }

    @Test
    public void viewClearHelp() {
        String command = "help clear";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "clear"));
    }

    @Test
    public void viewDeleteHelp() {
        String command = "help delete";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "delete"));
    }

    @Test
    public void viewExitHelp() {
        String command = "help exit";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "exit"));
    }

    @Test
    public void viewFindHelp() {
        String command = "help find";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "find"));
    }

    @Test
    public void viewHelpHelp() {
        String command = "help help";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "help"));
    }

    @Test
    public void viewHistoryHelp() {
        String command = "help history";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "history"));
    }

    @Test
    public void viewLoadHelp() {
        String command = "help load";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "load"));
    }

    @Test
    public void viewMarkHelp() {
        String command = "help mark";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "mark"));
    }

    @Test
    public void viewRedoHelp() {
        String command = "help redo";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "redo"));
    }

    @Test
    public void viewSaveHelp() {
        String command = "help save";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "save"));
    }

    @Test
    public void viewSwitchHelp() {
        String command = "help switch";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "switch"));
    }

    @Test
    public void viewTagHelp() {
        String command = "help tag";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "tag"));
    }

    @Test
    public void viewUnaliasHelp() {
        String command = "help unalias";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "unalias"));
    }

    @Test
    public void viewUndoHelp() {
        String command = "help undo";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "undo"));
    }

    @Test
    public void viewUntagHelp() {
        String command = "help untag";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "untag"));
    }

    @Test
    public void viewUpdateHelp() {
        String command = "help update";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "update"));
    }

    @Test
    public void viewViewAliasHelp() {
        String command = "help viewalias";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "viewalias"));
    }

    @Test
    public void viewInvalidHelp() {
        String command = "help asdfasdfsadfaf";
        commandBox.runCommand(command);
        assertResultMessage("Sorry, that command does not exist.\nPlease type help for available commands.");
    }

    @Test
    public void viewCaseInsensitiveHelp() {
        String command = "HeLp AdD";
        commandBox.runCommand(command);
        assertResultMessage(String.format("Displaying detailed help for %s.", "AdD"));
    }
}
