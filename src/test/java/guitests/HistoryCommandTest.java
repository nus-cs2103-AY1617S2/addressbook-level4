//@@author A0162011A
package guitests;

import org.junit.Test;

/**
 * Gui tests for history command
 */
public class HistoryCommandTest extends ToLuistGuiTest {
    String nonMutatingCommand = "history";
    String mutatingCommand = "add task";
    String historyCommand = "history";
    
    @Test
    public void viewHistoryWithoutAnythingElse() {
        commandBox.runCommand(historyCommand);
        assertResultMessage("\n0 commands displayed.");
    }

    @Test
    public void addMutatingCommandAndViewHistory() {
        String[] commandList = { mutatingCommand };
        runCommandsThenHistory(commandList);
        assertHistoryResultMessage( commandList);
    }

    @Test
    public void addNonMutatingCommandAndViewHistory() {
        String[] commandList = { nonMutatingCommand };
        runCommandsThenHistory(commandList);
        assertHistoryResultMessage( commandList);
    }

    @Test
    public void addMutatingAndNonMutatingCommandAndViewHistory() {
        String[] commandList = { mutatingCommand, nonMutatingCommand };
        runCommandsThenHistory(commandList);
        assertHistoryResultMessage( commandList);
    }

    @Test
    public void addNonMutatingAndMutatingCommandAndViewHistory() {
        String[] commandList = { nonMutatingCommand, mutatingCommand };
        runCommandsThenHistory(commandList);
        assertHistoryResultMessage( commandList);
    }

    @Test
    public void addMutatingCommandAndUndoAndViewHistory() {
        String command2 = "undo";;
        String[] commandList = { mutatingCommand, command2 };
        runCommandsThenHistory(commandList);
        assertHistoryResultMessage( commandList);
    }

    @Test
    public void addRandomGibberishAndViewHistory() {
        String command1 = "sadfkjhaslfhka";
        String command2 = "98371497632841892346";
        String[] commandList = { command1, command2 };
        runCommandsThenHistory(commandList);
        assertHistoryResultMessage( commandList);
    }
    
    private void assertHistoryResultMessage(String[] commandList) {
        String resultMessage = "";
        for (String command : commandList) {
            resultMessage += command;
            resultMessage += "\n";
        }
        resultMessage += commandList.length;
        resultMessage += " commands displayed.";
        assertResultMessage(resultMessage);
    }
    
    private void runCommandsThenHistory(String[] commandList) {
        for ( String command : commandList ) {
            commandBox.runCommand(command);
        }
        commandBox.runCommand(historyCommand);
    }
}
