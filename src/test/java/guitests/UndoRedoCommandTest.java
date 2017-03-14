package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.RedoCommand;
import seedu.tasklist.logic.commands.UndoCommand;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TestUtil;



public class UndoRedoCommandTest extends TaskListGuiTest {

    @Test
    public void assertUndoRedoAddSuccess() {
        TestTask taskToBeAdded = td.hoon;
        commandBox.runCommand(taskToBeAdded.getAddCommand());
        commandBox.runCommand("undo");
        TestTask[] expectedTaskList = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(expectedTaskList));
        assertResultMessage(String.format(UndoCommand.MESSAGE_UNDO_SUCCESS));

        //redo
        commandBox.runCommand("redo");
        TestTask[] currentList = TestUtil.addTasksToList(td.getTypicalTasks(), td.hoon);
        assertTrue(taskListPanel.isListMatching(currentList));
        assertResultMessage(String.format(RedoCommand.MESSAGE_REDO_SUCCESS));
    }

    /**
     * Test to edit the name. Beyond MVP can edit all other parameters for more coverage.
     * @throws IllegalValueException
     */

    @Test
    public void assertUndoRedoEditSuccess() throws IllegalValueException {
        commandBox.runCommand("edit 1 Wang PC");
        commandBox.runCommand("undo");
        TestTask[] expectedTaskList = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(expectedTaskList));
        assertResultMessage(String.format(UndoCommand.MESSAGE_UNDO_SUCCESS));

        //redo
        commandBox.runCommand("redo");
        expectedTaskList[0].setName(new Name("Wang PC"));
        assertTrue(taskListPanel.isListMatching(expectedTaskList));
        assertResultMessage(String.format(RedoCommand.MESSAGE_REDO_SUCCESS));
    }

    @Test
    public void assertUndoRedoDeleteSuccess() {
        TestTask[] currentList = TestUtil.removeTaskFromList(td.getTypicalTasks(), 6);
        commandBox.runCommand("delete 6");
        commandBox.runCommand("undo");
        TestTask[] expectedTaskList = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(expectedTaskList));
        assertResultMessage(String.format(UndoCommand.MESSAGE_UNDO_SUCCESS));

        //redo
        commandBox.runCommand("redo");
        currentList = TestUtil.removeTaskFromList(td.getTypicalTasks(), 6);
        assertTrue(taskListPanel.isListMatching(currentList));
        assertResultMessage(String.format(RedoCommand.MESSAGE_REDO_SUCCESS));

    }

    /**
     * Test for events where multiple undo/redo commands are entered, and go over the stack limit.
     */
    @Test
    public void assertUndoRedoFail() {
        TestTask taskToBeAdded = td.hoon;
        commandBox.runCommand(taskToBeAdded.getAddCommand());
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_UNDO_SUCCESS);
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_UNDO_FAILED);

        commandBox.runCommand("redo");
        assertResultMessage(String.format(RedoCommand.MESSAGE_REDO_SUCCESS));
        commandBox.runCommand("redo");
        assertResultMessage(String.format(RedoCommand.MESSAGE_REDO_FAILED));

    }

    @Test
    public void assertUndoRedoClearSuccess() {
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_UNDO_SUCCESS);
        TestTask[] expectedTaskList = td.getTypicalTasks();
        assertTrue(taskListPanel.isListMatching(expectedTaskList));

        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_REDO_SUCCESS);
        assertTrue(taskListPanel.isListMatching());
    }
}


