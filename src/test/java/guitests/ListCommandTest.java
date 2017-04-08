package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.testutil.TestTask;

public class ListCommandTest extends TaskManagerGuiTest {

    // @@author A0141102H
    @Test
    public void listSpecificDateNonEmptyList() {
        assertListResult("LIST 01/03/17"); // no results
        assertListResult("LIST 03/03/17", td.eatBreakfast, td.doCS); // multiple
                                                                     // results
    }

    @Test
    public void listTodayDateNonEmptyList() {
        commandBox.runCommand("CLEAR");
        commandBox.runCommand("ADD testcase ON today");
        commandBox.runCommand("ADD testcase ON tmr");
        commandBox.runCommand("LIST today");
        assertListSize(1);
    }

    @Test
    public void listInvalidCommandFailure() {
        commandBox.runCommand("LIst");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void listCommand() {
        commandBox.runCommand("LIST");
        assertListSize(td.getTypicalTasks().length);
        assertTrue(eventTaskListPanel.isListMatching(td.getTypicalTasks()));
        assertTrue(deadlineTaskListPanel.isListMatching(td.getTypicalTasks()));
        assertTrue(floatingTaskListPanel.isListMatching(td.getTypicalTasks()));
    }

    private void assertListResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        String[] trimmedCommand = command.trim().split("\\s+");
        assertResultMessage("Listed all uncompleted tasks for [" + trimmedCommand[1] + "]");
        assertTrue(eventTaskListPanel.isListMatching(expectedHits));
        assertTrue(deadlineTaskListPanel.isListMatching(expectedHits));
        assertTrue(floatingTaskListPanel.isListMatching(expectedHits));
    }
}
