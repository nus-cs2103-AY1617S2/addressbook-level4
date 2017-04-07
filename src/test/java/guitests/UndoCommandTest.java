package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.logic.commands.UndoCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo_emptySession_failure() {
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_BACKWARDS_COMMAND);
    }

    @Test
    public void undo_noPreviousCommand_failure() {
        //End of state stack
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_BACKWARDS_COMMAND);

        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_BACKWARDS_COMMAND);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_BACKWARDS_COMMAND);
    }

    @Test
    public void undo_addThenUndo_success() throws Exception {
        TestTask taskToAdd = td.hoon;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] expectedList = td.getTypicalTasks();

        assertUndoSuccess(expectedList);
    }

    @Test
    public void undo_clearThenUndo_success() throws Exception {
        commandBox.runCommand("reset");
        TestTask[] expectedList = td.getTypicalTasks();

        assertUndoSuccess(expectedList);
    }

    @Test
    public void undo_deleteThenUndo_success() throws Exception {
        commandBox.runCommand("delete 1");
        TestTask[] expectedList = td.getTypicalTasks();

        assertUndoSuccess(expectedList);
    }

    @Test
    public void undo_editThenUndo_success() throws Exception {
        String detailsToEdit = "Bobby for:91234567 priority:1 note:Block 123, Bobby Street 3 #husband";
        int taskListIndex = 1;
        commandBox.runCommand("edit " + taskListIndex + " " + detailsToEdit);
        TestTask[] expectedList = td.getTypicalTasks();

        assertUndoSuccess(expectedList);
    }

    @Test
    public void undo_markCompleteThenUndo_success() throws Exception {
        commandBox.runCommand("complete 1");
        TestTask[] expectedList = td.getTypicalTasks();
        assertUndoSuccess(expectedList);

        commandBox.runCommand("edit 1 for:floating");
        commandBox.runCommand("complete floating 1");
        expectedList = TestUtil.removeTasksFromList(expectedList, td.alice);

        assertUndoSuccess(expectedList);
    }

    @Test
    public void undo_markIncompleteThenUndo_success() throws Exception {
        commandBox.runCommand("complete 1");
        commandBox.runCommand("incomplete 1");
        TestTask[] expectedList = td.getTypicalTasks();
        expectedList = TestUtil.removeTasksFromList(expectedList, td.alice);

        assertUndoSuccess(expectedList);
    }

    /**
     * Runs the undo command to undo the previously executed command and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertUndoSuccess(TestTask[] expectedList) {
        commandBox.runCommand("undo");

        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}

