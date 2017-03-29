package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.logic.commands.ListCommand;
import seedu.address.testutil.TestTask;

//@@author A0162877N
public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    public void list_IsMutating() throws IllegalDateTimeValueException {
        ListCommand lc = new ListCommand();
        assertFalse(lc.isMutating());
    }

    @Test
    public void list_AllTask_ReturnTrue() {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("list");

        //No change should occur
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void list_TaskByDate_ReturnTrue() {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("list by 12-12-2017");
        //No change should occur
        assertTrue(taskListPanel.isListMatching(currentList));

        commandBox.runCommand("list by christmas");
        assertTrue(taskListPanel.isListMatching(currentList));

        commandBox.runCommand("list by Mar 2018");
        assertTrue(taskListPanel.isListMatching(currentList));

        commandBox.runCommand("list by 01-01-2018");
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void list_invalidCommand() {
        commandBox.runCommand("lis");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void list_TaskByDateTime_ReturnTrue() {
        TestTask[] currentList = td.getTypicalTasks();

        commandBox.runCommand("list by 12-12-2017 0000");

        assertTrue(taskListPanel.isListMatching(currentList)); // No change should occur

        commandBox.runCommand("list by ");

        assertTrue(taskListPanel.isListMatching(currentList)); // No change should occur

        assertListResult("list by 2301 10-11-2017", td.task6); // Only task 6 should appear
        assertListResult("list from 0000 10-11-2017 to 2359 10-11-2017", td.task6); // Only task 6 should appear

    }

    //@@author A0105287E
    @Test
    public void list_listCompletedTasks_success() {
        commandBox.runCommand("mark 1 completed"); //mark some tasks completed from the default list
        commandBox.runCommand("mark 3 completed");
        commandBox.runCommand("mark 5 completed");

        td.task1.setIsCompleted(true);
        td.task4.setIsCompleted(true);
        td.task7.setIsCompleted(true);

        commandBox.runCommand("list completed");

        assertTrue(taskListPanel.isListMatching(new TestTask[]{td.task7, td.task4, td.task1}));
    }

  //@@author A0105287E
    @Test
    public void list_listIncompleteTasks_success() {
        commandBox.runCommand("mark 1 completed"); //mark some tasks completed from the default list
        commandBox.runCommand("mark 3 completed");
        commandBox.runCommand("mark 5 completed");

        commandBox.runCommand("list incomplete");

        assertTrue(taskListPanel.isListMatching(new TestTask[]{td.task2, td.task3, td.task5, td.task6}));
    }

    private void assertListResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
