package guitests;

import org.junit.Test;

import seedu.doist.logic.commands.AliasCommand;
import seedu.doist.logic.parser.AliasCommandParser;

//@@author A0147980U
public class AliasCommandTest extends DoistGUITest {

    @Test
    public void testInputAliasSuccess() {
        assertAliasSuccessfullyAdded("addalias", "add");
    }

    @Test
    public void testInputAliasIsDefaultCommandWord() {
        commandBox.runCommand("alias add \\for edit");
        assertResultMessage(String.format(AliasCommand.MESSAGE_ALIAS_IS_DEFAULT_COMMAND_WORD, "add"));
    }

    @Test
    public void testCommandWordNotSpecified() {
        commandBox.runCommand("alias a");
        assertResultMessage(String.format(AliasCommandParser.MESSAGE_COMMAND_WORD_NOT_SPECIFIED,
                AliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void testInputAliasFormatInvalid() {
        commandBox.runCommand("alias add a task \\for add");
        assertResultMessage(String.format(AliasCommandParser.MESSAGE_ALIAS_FORMAT_INVALID,
                AliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void testDefaultCommandWordNotExist() {
        commandBox.runCommand("alias newAlias \\for notExistCommand");
        assertResultMessage(String.format(AliasCommand.MESSAGE_COMMAND_WORD_NOT_EXIST, "notExistCommand"));
    }

    private void assertViewAliasContains(String alias) {
        commandBox.runCommand("view_alias");
        assert resultDisplay.getText().contains(alias);
    }

    private void assertAliasSuccessfullyAdded(String alias, String commandWord) {
        commandBox.runCommand("alias " + alias + " \\for " + commandWord);
        assertResultMessage(String.format(AliasCommand.MESSAGE_SUCCESS, alias, commandWord));
        assertViewAliasContains(alias);
    }
}
