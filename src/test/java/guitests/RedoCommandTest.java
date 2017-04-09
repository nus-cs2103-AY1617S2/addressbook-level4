package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.util.Pair;
import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.RedoCommand;
import seedu.taskmanager.testutil.TaskBuilder;
import seedu.taskmanager.testutil.TestTask;
import seedu.taskmanager.testutil.TestUtil;

public class RedoCommandTest extends TaskManagerGuiTest {

    // @@author A0142418L
    @Test
    public void redoDeleteSuccess() {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndex);
        commandBox.runCommand("DELETE " + targetIndex);
        commandBox.runCommand("UNDO");
        commandBox.runCommand("REDO");
        assertRedoSuccess(expectedRemainder);
    }

    @Test
    public void redoAddSuccess() {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.sampleEvent;
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("UNDO");
        commandBox.runCommand("REDO");
        assertRedoSuccess(currentList);
    }

    @Test
    public void redoClearSuccess() {
        commandBox.runCommand("CLEAR");
        commandBox.runCommand("UNDO");
        commandBox.runCommand("REDO");
        assertListSize(0);
    }

    @Test
    public void redoUpdateSuccess() throws Exception  {
        TestTask[] currentList = td.getTypicalTasks();
        String detailsToUpdate = "take a snack break ON 03/03/19 1500 TO 1600 CATEGORY";
        int taskManagerIndex = 1;
        TestTask updatedTask = new TaskBuilder().withTaskName("take a snack break").withStartDate("03/03/19")
                .withStartTime("1500").withEndDate("03/03/19").withEndTime("1600").build();
        currentList = TestUtil.removeTaskFromList(currentList, taskManagerIndex);
        Pair<TestTask[], Integer> expectedList = TestUtil.addTasksToList(currentList, updatedTask);

        currentList = expectedList.getKey();

        commandBox.runCommand("UPDATE " + taskManagerIndex + " " + detailsToUpdate);
        commandBox.runCommand("UNDO");
        commandBox.runCommand("REDO");
        assertRedoSuccess(currentList);
    }

    @Test
    public void redoInvalidCommand() {
        commandBox.runCommand("REDOwrong");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void redoNothingToRedo() {
        commandBox.runCommand("REDO");
        assertResultMessage("Nothing to redo.");
    }

    @Test
    public void redoOverwritted() {
        commandBox.runCommand("CLEAR");
        commandBox.runCommand("UNDO");
        TestTask taskToAdd = td.sampleEvent;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("REDO");
        assertResultMessage("Nothing to redo.");
    }

    /**
     * Validate the redo command, confirming if the result is correct.
     *
     * @param currentList
     *            A copy of the current list of tasks (before undo).
     */
    private void assertRedoSuccess(TestTask[] expectedList) {

        // confirm the list now contains all tasks after redo
        assertTrue(eventTaskListPanel.isListMatching(expectedList));
        assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));

        // confirm the result message is correct
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }

}
