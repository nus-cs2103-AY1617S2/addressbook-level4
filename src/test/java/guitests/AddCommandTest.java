package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import savvytodo.commons.core.Messages;
import savvytodo.logic.commands.AddCommand;
import savvytodo.testutil.TestTask;
import savvytodo.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        System.err.println("1");
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.discussion;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        System.err.println("2");

        //add another task
        taskToAdd = td.interview;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        System.err.println("3");

        //add duplicate task
        commandBox.runCommand(td.discussion.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
        System.err.println("4");

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.presentation);
        System.err.println("5");

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        System.err.println("6");
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {

        System.err.println("a");
        commandBox.runCommand(taskToAdd.getAddCommand());


        System.err.println("b");
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().name);
        assertMatching(taskToAdd, addedCard);

        System.err.println("c");

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));

        System.err.println("d");
    }

}
