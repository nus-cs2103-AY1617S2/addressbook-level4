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
        assertResultMessage("\n" + command + "\n2 commands displayed.");
    }

    @Test
    public void addMutatingCommandAndViewHistory() {
        String command1 = "add task";
        commandBox.runCommand(command1);
        String command2 = "history";
        commandBox.runCommand(command2);
        assertResultMessage("\n" + command1 + "\n" + command2 + "\n3 commands displayed.");
    }

    @Test
    public void addNonMutatingCommandAndViewHistory() {
        String command1 = "history";
        commandBox.runCommand(command1);
        String command2 = "history";
        commandBox.runCommand(command2);
        assertResultMessage("\n" + command1 + "\n" + command2 + "\n3 commands displayed.");
    }

    @Test
    public void addMutatingAndNonMutatingCommandAndViewHistory() {
        String command1 = "add task";
        commandBox.runCommand(command1);
        String command2 = "history";
        commandBox.runCommand(command2);
        String command3 = "history";
        commandBox.runCommand(command3);
        assertResultMessage("\n" + command1 + "\n" + command2 + "\n" + command3 + "\n4 commands displayed.");
    }

    @Test
    public void addNonMutatingAndMutatingCommandAndViewHistory() {
        String command1 = "history";
        commandBox.runCommand(command1);
        String command2 = "add task";
        commandBox.runCommand(command2);
        String command3 = "history";
        commandBox.runCommand(command3);
        assertResultMessage("\n" + command1 + "\n" + command2 + "\n" + command3 + "\n4 commands displayed.");
    }
}
