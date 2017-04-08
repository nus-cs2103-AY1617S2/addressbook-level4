package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.AddCommand;
import seedu.taskboss.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.taskboss.testutil.TestTask;
import seedu.taskboss.testutil.TestUtil;

public class AddCommandTest extends TaskBossGuiTest {

    @Test
    public void add() throws DuplicateCategoryException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        //add one task
        TestTask taskToAdd = td.taskH;

        assertAddSuccess(false, false, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.taskI;
        assertAddSuccess(false, false, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task using short command
        taskToAdd = td.taskK;
        assertAddSuccess(false, true, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task using short command '+'
        taskToAdd = td.taskL;
        assertAddSuccess(true, false, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.taskH.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add invalid dates task
        commandBox.runCommand(td.taskJ.getAddCommand());
        assertResultMessage(AddCommand.ERROR_INVALID_ORDER_DATES);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(false, false, td.taskA);

        //add to built-in done category
        commandBox.runCommand("add floating task c/done");
        assertResultMessage(AddCommand.ERROR_CANNOT_ADD_DONE_CATEGORY);

        //invalid command
        commandBox.runCommand("adds new task");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(boolean isPlusSign, boolean isShortCommand, TestTask taskToAdd,
                    TestTask... currentList)
            throws DuplicateCategoryException, IllegalValueException {
        if (isShortCommand) {
            commandBox.runCommand(taskToAdd.getShortAddCommand());
        } else {
            if (isPlusSign) {
                commandBox.runCommand(taskToAdd.getAddCommandPlus());
            } else {
                commandBox.runCommand(taskToAdd.getAddCommand());
            }
        }

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
