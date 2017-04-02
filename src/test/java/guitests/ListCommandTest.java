//@@author A0139539R
package guitests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandTest extends AddressBookGuiTest {

    @Test
    public void list_notSupported_failure() {
        //End of state stack
        commandBox.runCommand("list");
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS, ListCommand.COMPARATOR_NAME_PRIORITY));
        commandBox.runCommand("list date");
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS, ListCommand.COMPARATOR_NAME_DATE));
        commandBox.runCommand("list priority");
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS, ListCommand.COMPARATOR_NAME_PRIORITY));
        commandBox.runCommand("list title");
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS, ListCommand.COMPARATOR_NAME_TITLE));
        commandBox.runCommand("list tag");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}

