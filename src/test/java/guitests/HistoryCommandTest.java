package guitests;

import org.junit.Test;

/**
 * Gui tests for history command
 */
public class HistoryCommandTest extends ToLuistGuiTest{
    @Test
    public void viewHistory() {
        String command = "history";
        commandBox.runCommand(command);
        assertResultMessage("\nhistory\n2 commands displayed.");
    }
    
    @Test
    public void addTaskAndViewHistory() {
        String command1 = "add task";
        commandBox.runCommand(command1);
        String command2 = "history";
        commandBox.runCommand(command2);
        assertResultMessage("\nadd\nhistory\n2 commands displayed.");
    }
}
