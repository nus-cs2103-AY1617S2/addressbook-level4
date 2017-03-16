package guitests;

import org.junit.Test;

import seedu.doist.logic.commands.AliasCommand;
import seedu.doist.logic.parser.AliasCommandParser;



public class AliasCommandTest extends DoistGUITest {
    @Test
    public void testInputAliasIsDefaultCommandWord() {
        commandBox.runCommand("alias add \\for edit");
        assertResultMessage(String.format(AliasCommand.MESSAGE_ALIAS_IS_DEFAULT_COMMAND_WORD, "add"));
    }

    @Test
    public void testCommandWordNotSpecified() {
        commandBox.runCommand("alias a");
        assertResultMessage(AliasCommandParser.MESSAGE_COMMAND_WORD_NOT_SPECIFIED);
    }

    @Test
    public void testInputAliasFormatInvalid() {
        commandBox.runCommand("alias add a task \\for add");
        assertResultMessage(AliasCommandParser.MESSAGE_ALIAS_FORMAT_INVALID);
    }

    @Test
    public void testDefaultCommandWordNotExist() {
        commandBox.runCommand("alias newAlias \\for notExistCommand");
        assertResultMessage(String.format(AliasCommand.MESSAGE_COMMAND_WORD_NOT_EXIST, "notExistCommand"));
    }
}
