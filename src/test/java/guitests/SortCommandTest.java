package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.logic.commands.SortCommand;
import seedu.tasklist.testutil.TestTask;

public class SortCommandTest extends TaskListGuiTest {


    @Test
    public void sort_invalidParameter_errorMessageShown() {
        String command = "sort a";
        commandBox.runCommand(command);
        assertResultMessage(String.format(SortCommand.MESSAGE_FAILURE));
    }

    @Test
    public void sort_moreThanOneParameter_errorMessageShown() {
        String command = "sort n p";
        commandBox.runCommand(command);
        assertResultMessage(String.format(SortCommand.MESSAGE_FAILURE));
    }

    @Test
    public void sort_invalidCommand_errorMessageShown() {
        commandBox.runCommand("sorts newTask");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void sort() {
        //sort by name
        TestTask[] expectedListName = {td.groceries, td.tutorial, td.homework, td.drink, td.cs2103T, td.java};
        String commandName = "sort n";
        commandBox.runCommand(commandName);
        assertSortSuccess(expectedListName);

        //sort by date
        TestTask[] expectedListDate = {td.tutorial, td.cs2103T, td.groceries, td.homework, td.drink, td.java };
        String commandDate = "sort d";
        commandBox.runCommand(commandDate);
        assertResultMessage(SortCommand.MESSAGE_SUCCESS);
        assertSortSuccess(expectedListDate);

        //sort by start priority
        TestTask[] expectedListPriority = {td.tutorial, td.drink, td.java, td.cs2103T, td.groceries, td.homework};
        String commandPriority = "sort p";
        commandBox.runCommand(commandPriority);
        assertResultMessage(SortCommand.MESSAGE_SUCCESS);
        assertSortSuccess(expectedListPriority);
    }

    private void assertSortSuccess(TestTask... expectedList) {
        //confirm list contains all tasks in the correct sorted order
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
