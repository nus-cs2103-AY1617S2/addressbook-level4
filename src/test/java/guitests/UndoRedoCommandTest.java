package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.logic.GlobalStack;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;


public class UndoRedoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo_redo_previous_add_action() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.task8;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //undo previous addition of task
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
        assertFindResult("find Task8");

        //redo the previous undo
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
        assertFindResult("find Task8", td.task8);
    }

    @Test
    public void noAction_to_undo() {
        commandBox.runCommand("undo");
        assertResultMessage(GlobalStack.MESSAGE_NOTHING_TO_UNDO);

    }

    @Test
    public void noAction_to_redo() {
        commandBox.runCommand("redo");
        assertResultMessage(GlobalStack.MESSAGE_NOTHING_TO_REDO);
    }

    // adapted from AddCommandTest
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTaskName().taskName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    // adapted from FindCommandTest
    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
