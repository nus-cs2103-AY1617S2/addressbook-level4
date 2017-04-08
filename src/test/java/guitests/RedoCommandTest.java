package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.whatsleft.logic.commands.RedoCommand;
import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0121668A
public class RedoCommandTest extends WhatsLeftGuiTest {
    @Test
    public void redoPreviousFinishedTaskSuccess() {
        // finish one task
        TestEvent[] currentEventList = te.getTypicalEvents();
        TestEvent[] filteredPastEventList = TestUtil.filterPastExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        TestTask[] filteredTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        TestTask[] redoList = TestUtil.getTasksFromListByIndex(filteredTaskList, 1, 4);

        //finishes 3 tasks
        setUpRedoTest();

        /** Redo a task at the end of the completed list */
        assertRedoFinishedTaskSuccess("3", redoList, filteredPastEventList);

        /** Redo a task at the start of the completed list */
        assertRedoFinishedTaskSuccess("1", redoList, filteredPastEventList);

        /** Redo a task at the end of the completed list */
        assertRedoFinishedTaskSuccess("2", redoList, filteredPastEventList);
    }

    @Test
    public void redoPreviousFinishedTasksFailure() {
        TestTask[] currentTaskList = tt.getTypicalTasks();
        TestTask[] filteredTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        TestTask[] redoList = TestUtil.getTasksFromListByIndex(filteredTaskList, 1, 4);

      //finishes 3 tasks
        setUpRedoTest();
        /** index out of range */
        assertRedoCommandFailure("redo " + redoList.length + 1 , "The Task index provided is invalid");
        /** empty index */
        assertRedoCommandFailure("redo", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        /** string as index */
        assertRedoCommandFailure("redo ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
    }
    /**
     * Assert Redo Command with invalid input fails
     * @param command
     * @param expected
     */
    private void assertRedoCommandFailure(String command, String expected) {
        commandBox.runCommand(command);
        assertResultMessage(expected);
    }


    /**
     * Assert redo a previously finished task is successful
     *
     * @param taskIndex
     *            to finish
     * @param tasklist
     * @param eventslist
     */
    private void assertRedoFinishedTaskSuccess(String taskIndex, TestTask[] tasklist, TestEvent[] eventlist) {
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(tasklist, Integer.parseInt(taskIndex));

        commandBox.runCommand("redo " + taskIndex);

        assertTrue(taskListPanel.isListMatching(expectedRemainder));
        assertTrue(eventListPanel.isListMatching(eventlist));
        commandBox.runCommand("undo");
    }

    /**
     * finish 3 tasks and show the completed tasks for redoCommand test
     */
    private void setUpRedoTest() {
        commandBox.runCommand("finish 1");
        commandBox.runCommand("finish 1");
        commandBox.runCommand("finish 1");
        commandBox.runCommand("show com");
    }
}
