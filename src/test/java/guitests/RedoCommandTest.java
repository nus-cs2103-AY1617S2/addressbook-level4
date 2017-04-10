package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.jobs.logic.commands.RedoCommand.MESSAGE_FAILURE;
import static seedu.jobs.logic.commands.RedoCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;
import seedu.jobs.testutil.TestTask;
import seedu.jobs.testutil.TestUtil;

public class RedoCommandTest extends TaskBookGuiTest {

    //@@author A0164440M
    @Test
    public void redoTest() throws IllegalArgumentException, IllegalTimeException {

        commandBox.runCommand("redo");
        assertResultMessage(MESSAGE_FAILURE);

        // to add a random task to be undone
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToDelete = currentList[currentList.length - 1];
        commandBox.runCommand("delete " + td.getTypicalTasks().length);
        commandBox.runCommand("undo");
        assertRedoSuccess(currentList, taskToDelete);
    }
    //@@author


  //@@author A0140055W
    private void assertRedoSuccess(final TestTask[] currentList, TestTask taskToBeAdd)
            throws IllegalArgumentException, IllegalTimeException {

        commandBox.runCommand("redo");
        TestTask[] expectedResult = TestUtil.removeTasksFromList(currentList, taskToBeAdd);

        assertTrue(taskListPanel.isListMatching(expectedResult));

        assertResultMessage(String.format(MESSAGE_SUCCESS, taskToBeAdd));

    }
}
