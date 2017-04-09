package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.UndoCommand;
import seedu.taskmanager.testutil.TestTask;

public class UndoCommandTest extends TaskManagerGuiTest {

    // @@author A0142418L
    @Test
    public void undoDeleteSuccess() {
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        commandBox.runCommand("DELETE " + targetIndex);
        commandBox.runCommand("UNDO");
        assertUndoSuccess(currentList);
    }

    @Test
    public void undoAddSuccess() {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.sampleEvent;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand("UNDO");
        assertUndoSuccess(currentList);
    }

    @Test
    public void undoClearSuccess() {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("CLEAR");
        commandBox.runCommand("UNDO");
        assertUndoSuccess(currentList);
    }

    @Test
    public void undoUpdateSuccess() {
        TestTask[] currentList = td.getTypicalTasks();
        String detailsToUpdate = "take a snack break ON 03/03/19 1500 TO 1600 CATEGORY";
        int taskManagerIndex = 1;

        commandBox.runCommand("UPDATE " + taskManagerIndex + " " + detailsToUpdate);
        commandBox.runCommand("UNDO");
        assertUndoSuccess(currentList);
    }

    @Test
    public void undoInvalidCommand() {
        commandBox.runCommand("UNDOwrong");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void undoNothingToUndo() {
        commandBox.runCommand("UNDO");
        assertResultMessage("Nothing to undo.");
    }



    /**
     * Validate the undo command, confirming if the result is correct.
     *
     * @param currentList
     *            A copy of the current list of tasks (before undo).
     */
    private void assertUndoSuccess(TestTask[] expectedList) {

        // confirm the list now contains all tasks after undo
        assertTrue(eventTaskListPanel.isListMatching(expectedList));
        assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));

        // confirm the result message is correct
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }

}
