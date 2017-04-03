package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.jobs.logic.commands.UndoCommand.MESSAGE_FAILURE;
import static seedu.jobs.logic.commands.UndoCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;
import seedu.jobs.testutil.TestTask;
import seedu.jobs.testutil.TestUtil;

//@@author A0140055W

public class UndoCommandTest extends TaskBookGuiTest {

    @Test
    public void undo() throws IllegalArgumentException, IllegalTimeException {

        commandBox.runCommand("undo");
        assertListSize(0);

        commandBox.runCommand("redo");
        // to add a random task to be undone
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.CS4101;
        commandBox.runCommand(td.CS4101.getAddCommand());
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertUndoSuccess(currentList, currentList.length - 1);
        // to revert back to original

        commandBox.runCommand("redo");

        // add another task for 2 undoes
        taskToAdd = td.CS4102;
        commandBox.runCommand(taskToAdd.getAddCommand());
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        // first undo
        assertUndoSuccess(currentList, currentList.length - 1);
        // second undo
        currentList = TestUtil.removeTasksFromList(currentList, currentList[currentList.length - 1]);
        assertUndoSuccess(currentList, currentList.length - 1);
        // third undo
        currentList = TestUtil.removeTasksFromList(currentList, currentList[currentList.length - 1]);
        commandBox.runCommand("undo");
        // last undo
        commandBox.runCommand("undo");

        assertResultMessage(MESSAGE_FAILURE);


    }

    private void assertUndoSuccess(final TestTask[] currentList, int lastIndex)
            throws IllegalArgumentException, IllegalTimeException {
        TestTask taskToBeUndone = currentList[lastIndex];
        TestTask[] expectedResult = TestUtil.removeTasksFromList(currentList, taskToBeUndone);

        commandBox.runCommand("undo");

        // confirm the resultant list after undoing matches the original
        assertTrue(taskListPanel.isListMatching(expectedResult));

        // confirm that the result message is correct
        assertResultMessage(String.format(MESSAGE_SUCCESS, taskToBeUndone));

    }
}
