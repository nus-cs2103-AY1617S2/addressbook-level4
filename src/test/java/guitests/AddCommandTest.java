package guitests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import guitests.guihandles.TaskCardHandle;
import seedu.taskboss.commons.core.Messages;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.AddCommand;
import seedu.taskboss.model.category.UniqueCategoryList.DuplicateCategoryException;
import seedu.taskboss.testutil.TestCategory;
import seedu.taskboss.testutil.TestTask;
import seedu.taskboss.testutil.TestUtil;

public class AddCommandTest extends TaskBossGuiTest {

    @Test
    public void add() throws DuplicateCategoryException, IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        //add one task
        TestTask taskToAdd = td.taskH;
        assertAddSuccess(false, false, Lists.newArrayList(new TestCategory
        (AddCommand.BUILT_IN_ALL_TASKS, td.taskA)), taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.taskI;
        assertAddSuccess(false, false, Lists.newArrayList(new TestCategory
        (AddCommand.BUILT_IN_ALL_TASKS, td.taskA)), taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task using short command
        taskToAdd = td.taskK;
        assertAddSuccess(false, true, Lists.newArrayList(new TestCategory
        (AddCommand.BUILT_IN_ALL_TASKS, td.taskA)), taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task using short command '+'
        taskToAdd = td.taskL;
        assertAddSuccess(true, false, Lists.newArrayList(new TestCategory
        (AddCommand.BUILT_IN_ALL_TASKS, td.taskA)), taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.taskH.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add invalid dates task
        commandBox.runCommand(td.taskJ.getAddCommand());
        assertResultMessage(AddCommand.ERROR_INVALID_DATES);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(false, false, Lists.newArrayList(new TestCategory
        (AddCommand.BUILT_IN_ALL_TASKS, td.taskA), new TestCategory("Friends", td.taskA)), td.taskA);

        //invalid command
        commandBox.runCommand("adds new task");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(boolean isPlusSign, boolean isShortCommand,
            List<TestCategory> expectedCategoryList,
            TestTask taskToAdd, TestTask... currentList)
            throws DuplicateCategoryException, IllegalValueException {
        if (isShortCommand) {
            commandBox.runCommand(taskToAdd.getShortAddCommand());
        } else if (isPlusSign) {
            commandBox.runCommand(taskToAdd.getAddCommandPlus());
        } else {
            commandBox.runCommand(taskToAdd.getAddCommand());
        }

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));

        assertTrue(categoryListPanel.isListMatching(expectedCategoryList));
    }

}
