//@@author A0139539R
package guitests;

import org.junit.Test;

import seedu.task.logic.commands.AliasCommand;
import seedu.task.logic.commands.ShowAliasCommand;

public class ShowAliasCommandTest extends TaskManagerGuiTest {

    @Test
    public void showAlias_noAlias_success() {
        String messageSuccess = "Listed all aliases:\n";
        commandBox.runCommand(ShowAliasCommand.COMMAND_WORD);
        assertResultMessage(messageSuccess);
    }

    @Test
    public void showAlias_validOriginalAlias_success() {
        String originalCommand = "add";
        String alias = "create";
        String expectedResult = "add: [create]\n";
        commandBox.runCommand(AliasCommand.COMMAND_WORD + " " + alias + " for " + originalCommand);
        commandBox.runCommand(ShowAliasCommand.COMMAND_WORD);
        assertResultMessage(String.format(ShowAliasCommand.MESSAGE_SUCCESS, expectedResult));
    }

    @Test
    public void showAlias_validAliastoAlias_success() {
        String originalCommand = "add";
        String firstAlias = "create";
        String secondAlias = "make";
        String firstExpectedResult = "add: [create]\n";
        String secondExpectedResult = "add: [create, make]\n";
        commandBox.runCommand(AliasCommand.COMMAND_WORD + " " + firstAlias + " for " + originalCommand);
        commandBox.runCommand(ShowAliasCommand.COMMAND_WORD);
        assertResultMessage(String.format(ShowAliasCommand.MESSAGE_SUCCESS, firstExpectedResult));

        commandBox.runCommand(AliasCommand.COMMAND_WORD + " " + secondAlias + " for " + firstAlias);
        commandBox.runCommand(ShowAliasCommand.COMMAND_WORD);
        assertResultMessage(String.format(ShowAliasCommand.MESSAGE_SUCCESS, secondExpectedResult));
    }

}
