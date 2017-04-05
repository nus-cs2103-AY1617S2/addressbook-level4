package guitests;

import static typetask.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

import typetask.logic.commands.ListCommand;
import typetask.logic.commands.ListDoneCommand;
import typetask.logic.commands.ListPriorityCommand;
import typetask.logic.commands.ListTodayCommand;

//@@author A0144902L
public class ListCommandTest extends AddressBookGuiTest {

    //Checks if List shown has the same output as expected
    @Test
    public void list_notSupported_failure() {
        commandBox.runCommand("list");
        assertResultMessage(String.format(ListCommand.MESSAGE_SUCCESS, "All"));
        commandBox.runCommand("listdone");
        assertResultMessage(String.format(ListDoneCommand.MESSAGE_SUCCESS, "Completed"));
        commandBox.runCommand("list*");
        assertResultMessage(String.format(ListPriorityCommand.MESSAGE_SUCCESS, "priority"));
        commandBox.runCommand("listtoday");
        assertResultMessage(String.format(ListTodayCommand.MESSAGE_SUCCESS, "Today"));
        commandBox.runCommand("listHigh");
        assertResultMessage(String.format(MESSAGE_UNKNOWN_COMMAND, "Unknown"));
    }
}
