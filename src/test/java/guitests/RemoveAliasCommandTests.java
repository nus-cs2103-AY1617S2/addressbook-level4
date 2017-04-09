package guitests;

import static seedu.doist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

import seedu.doist.logic.commands.RemoveAliasCommand;
import seedu.doist.logic.parser.RemoveAliasCommandParser;


//@@author A0147980U
public class RemoveAliasCommandTests extends DoistGUITest {
    @Test
    public void testRemoveAliasSuccessfully() {
        commandBox.runCommand("alias a \\for add");
        commandBox.runCommand("remove_alias a");
        assertResultMessage(String.format(RemoveAliasCommand.MESSAGE_SUCCESS, "a"));
    }

    @Test
    public void testRemoveDefaultCommandWord() {
        commandBox.runCommand("remove_alias delete");
        assertResultMessage(String.format(RemoveAliasCommand.MESSAGE_INPUT_NOT_ALIAS, "delete"));
    }

    @Test
    public void testInputIsEmptyString() {
        commandBox.runCommand("remove_alias ");
        assertResultMessage(RemoveAliasCommandParser.MESSAGE_ALIAS_NOT_SPECIFIED);
    }

    @Test
    public void testInputInvalidFormatAlias() {
        commandBox.runCommand("remove_alias a*&^");
        assertResultMessage(String.format(RemoveAliasCommandParser.MESSAGE_ALIAS_FORMAT_INVALID,
                RemoveAliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void testInputWithSpace() {
        commandBox.runCommand("remove_alias d el");
        assertResultMessage(String.format(RemoveAliasCommandParser.MESSAGE_ALIAS_FORMAT_INVALID,
                RemoveAliasCommand.MESSAGE_USAGE));
    }

    @Test
    public void testIncorrectCommandWord() {
        commandBox.runCommand("remov_alias update");
        assertResultMessage(MESSAGE_UNKNOWN_COMMAND);
    }
}
