package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.task.logic.commands.AliasCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class AliasCommandTest extends TaskManagerGuiTest {

    @Test
    public void alias_invalidOriginalCommand_fail() {
        String alias = "create";

        String originalCommandInvalidRegex = "!@&!@%";
        commandBox.runCommand(AliasCommand.COMMAND_WORD + " " + alias + " for " + originalCommandInvalidRegex);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AliasCommand.MESSAGE_USAGE));

        String originalCommandNotExists = "adds";
        commandBox.runCommand(AliasCommand.COMMAND_WORD + " " + alias + " for " + originalCommandNotExists);
        assertResultMessage(AliasCommand.MESSAGE_INVALID_ORIGNINAL_COMMAND_STRING);
    }

    @Test
    public void alias_invalidAliasCommand_fail() {
        String alias = "!@&!@%";

        String original = "add";
        commandBox.runCommand(AliasCommand.COMMAND_WORD + " " + alias + " for " + original);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void alias_aliasEqualsOriginal_fail() {
        String original = "add";

        String alias = original;
        commandBox.runCommand(AliasCommand.COMMAND_WORD + " " + alias + " for " + original);
        assertResultMessage(AliasCommand.MESSAGE_ALIAS_EQUALS_ORIGINAL_NOT_ALLOWED);
    }

    @Test
    public void alias_allFieldsValid_success() {
        String original = "delete";

        String alias = "remove";
        commandBox.runCommand(AliasCommand.COMMAND_WORD + " " + alias + " for " + original);
        assertResultMessage(String.format(AliasCommand.MESSAGE_ADD_ALIAS_SUCCESS,
                alias,
                original));
        delete(alias);
    }

    @Test
    public void alias_aliasAnotherAlias_success() {
        String original = "delete";

        String alias = "remove";
        commandBox.runCommand(AliasCommand.COMMAND_WORD + " " + alias + " for " + original);
        assertResultMessage(String.format(AliasCommand.MESSAGE_ADD_ALIAS_SUCCESS,
                alias,
                original));

        String anotherAlias = "relinquish";
        commandBox.runCommand(AliasCommand.COMMAND_WORD + " " + anotherAlias + " for " + original);
        assertResultMessage(String.format(AliasCommand.MESSAGE_ADD_ALIAS_SUCCESS,
                anotherAlias,
                original));
        delete(anotherAlias);
    }

    private void delete(String commandWord) {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteSuccess(commandWord, targetIndex, currentList);
    }

    private void assertDeleteSuccess(String commandWord, int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand(commandWord + " " + targetIndexOneIndexed);
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }
}
