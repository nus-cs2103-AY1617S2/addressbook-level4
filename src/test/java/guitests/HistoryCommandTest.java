//@@author A0162011A
package guitests;

import org.junit.Test;

/**
 * Gui tests for history command
 */
public class HistoryCommandTest extends ToLuistGuiTest {
    @Test
    public void viewHistoryWithoutAnythingElse() {
        String command = "history";
        commandBox.runCommand(command);
        assertResultMessage("\n0 commands displayed.");
    }

    @Test
    public void addMutatingCommandAndViewHistory() {
        String command1 = "add task";
        commandBox.runCommand(command1);
        String command2 = "history";
        commandBox.runCommand(command2);
        assertResultMessage(command1 + "\n1 command displayed.");
    }

    @Test
    public void addNonMutatingCommandAndViewHistory() {
        String command1 = "history";
        commandBox.runCommand(command1);
        String command2 = "history";
        commandBox.runCommand(command2);
        assertResultMessage(command1 + "\n1 command displayed.");
    }

    @Test
    public void addMutatingAndNonMutatingCommandAndViewHistory() {
        String command1 = "add task";
        commandBox.runCommand(command1);
        String command2 = "history";
        commandBox.runCommand(command2);
        String command3 = "history";
        commandBox.runCommand(command3);
        assertResultMessage(command1 + "\n" + command2 + "\n2 commands displayed.");
    }

    @Test
    public void addNonMutatingAndMutatingCommandAndViewHistory() {
        String command1 = "history";
        commandBox.runCommand(command1);
        String command2 = "add task";
        commandBox.runCommand(command2);
        String command3 = "history";
        commandBox.runCommand(command3);
        assertResultMessage(command1 + "\n" + command2 + "\n2 commands displayed.");
    }

    @Test
    public void addMutatingCommandAndUndoAndViewHistory() {
        String command1 = "add task";
        commandBox.runCommand(command1);
        String command2 = "undo";
        commandBox.runCommand(command2);
        String command3 = "history";
        commandBox.runCommand(command3);
        assertResultMessage(command1 + "\n" + command2 + "\n2 commands displayed.");
    }

    @Test
    public void addRandomGibberishAndViewHistory() {
        String command1 = "sadfkjhaslfhka";
        commandBox.runCommand(command1);
        String command2 = "98371497632841892346";
        commandBox.runCommand(command2);
        String command3 = "history";
        commandBox.runCommand(command3);
        assertResultMessage(command1 + "\n" + command2 + "\n2 commands displayed.");
    }
}
