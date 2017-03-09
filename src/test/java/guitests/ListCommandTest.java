package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

public class ListCommandTest extends TaskManagerGuiTest {

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
        
        commandBox.runCommand("List by christmas");
        assertTrue(taskListPanel.isListMatching(currentList));
        
        commandBox.runCommand("List by Mar 2018");
        assertTrue(taskListPanel.isListMatching(currentList));
        
        commandBox.runCommand("List by 01-01-2018");
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
    }
    
    private void assertListResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
