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
    public void redoPreviousFinishedTask_validInput_Success() {
        // finish one task
        TestEvent[] currentEventList = te.getTypicalEvents();
        TestEvent[] filteredPastEventList = TestUtil.getPastTestEvents(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        TestTask[] filteredTaskList = TestUtil.getFilteredTestTasks(currentTaskList);
        TestTask[] redoList = TestUtil.getTasksFromListByIndex(filteredTaskList, 1, 4);

        //finishes 3 tasks
        setUpRedoTest();

        /** Redo a task at the end of the completed list */
        assertRedoSuccess("3", redoList, filteredPastEventList);

        /** Redo a task at the start of the completed list */
        assertRedoSuccess("1", redoList, filteredPastEventList);

        /** Redo a task at the end of the completed list */
        assertRedoSuccess("2", redoList, filteredPastEventList);
    }

    @Test
    public void redoFinishedTask_indexOutOfRange_invalidIndexMessageShown() {
        TestTask[] currentTaskList = tt.getTypicalTasks();
        TestTask[] filteredTaskList = TestUtil.getFilteredTestTasks(currentTaskList);
        TestTask[] redoList = TestUtil.getTasksFromListByIndex(filteredTaskList, 1, 4);

      //finishes 3 tasks
        setUpRedoTest();
        /** index out of range */
        assertRedoInvalidIndex(redoList.length + 1 ,
                "The Task index provided is invalid");
    }

    @Test
    public void redoFinishedTask_invalidFormat_invalidFormatMessageShown() {

      //finishes 3 tasks
        setUpRedoTest();

        /** empty index */
        assertInvalidFormat("redo",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        /** string as index */
        assertInvalidFormat("redo ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
    }

    /**
     * Assert Redo Command with invalid input fails
     * @param index
     * @param expected message
     */
    private void assertRedoInvalidIndex(int index, String expected) {
        commandBox.runCommand("redo " + index);
        assertResultMessage(expected);
    }

    /**
     * Assert Redo Command with invalid input fails
     * @param command
     * @param expected
     */
    private void assertInvalidFormat(String command, String expected) {
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
    private void assertRedoSuccess(String taskIndex, TestTask[] tasklist, TestEvent[] eventlist) {
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
