package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;
import static seedu.ezdo.logic.commands.DoneCommand.MESSAGE_UNDONE_TASK_SUCCESS;

import java.util.ArrayList;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.logic.commands.DoneCommand;
import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TestUtil;
//@@author A0141010L
public class DoneCommandTest extends EzDoGuiTest {
    @Test
    public void done_success() {
        //marks first task in the list as done
        TestTask[] currentList = td.getTypicalTasks();
        TestTask[] doneList = td.getTypicalDoneTasks();
        int targetIndex = 1;
        TestTask toDone = currentList[targetIndex - 1];
        assertDoneSuccess(false, targetIndex, currentList, doneList);

        //marks the middle task in the list as done
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        doneList = TestUtil.addTasksToList(doneList, toDone);
        targetIndex = currentList.length / 2;
        toDone = currentList[targetIndex - 1];
        assertDoneSuccess(true, targetIndex, currentList, doneList);

        //marks last task in the list as done
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        doneList = TestUtil.addTasksToList(doneList, toDone);
        targetIndex = currentList.length;
        toDone = currentList[targetIndex - 1];
        assertDoneSuccess(false, targetIndex, currentList, doneList);

        //invalid index
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        doneList = TestUtil.addTasksToList(doneList, toDone);
        commandBox.runCommand("done " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid.");

        //invalid command
        commandBox.runCommand("done a");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));

        //invalid command
        commandBox.runCommand("dones 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        //view done tasks
        commandBox.runCommand("done");
        assertTrue(taskListPanel.isListMatching(doneList));
    }

    private void assertDoneSuccess(boolean usesShortCommand, int targetIndexOneIndexed, final TestTask[] currentList,
            final TestTask[] doneList) {

        TestTask taskToDone = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        ArrayList<TestTask> tasksToDone = new ArrayList<TestTask>();
        tasksToDone.add(taskToDone);
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
        TestTask[] expectedDone = TestUtil.addTasksToList(doneList, taskToDone);

        if (usesShortCommand) {
            commandBox.runCommand("d " + targetIndexOneIndexed);
        } else {
            commandBox.runCommand("done " + targetIndexOneIndexed);
        }

        //confirm the list now contains all tasks excluding the one just marked as done
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, tasksToDone));

        //confirm the new done list contains the right data
        commandBox.runCommand("done");
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToDone.getName().fullName);
        assertMatching(taskToDone, addedCard);
        assertTrue(taskListPanel.isListMatching(expectedDone));

        //confirm the undone list does not contain the task just marked as done
        commandBox.runCommand("list");
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }

    //@@author A0139248X
    @Test
    public void undone_success() {
        TestTask[] currentList = td.getTypicalDoneTasks();
        TestTask[] doneList = td.getTypicalTasks();
        //undone a task
        int targetIndex = 1;
        assertUndoneSuccess(false, targetIndex, currentList, doneList);
    }

    @Test
    public void undone_empty() {
        //no tasks in done list to undone
        commandBox.runCommand("done");
        commandBox.runCommand("done 1");
        assertResultMessage("The task index provided is invalid.");
    }

    private void assertUndoneSuccess(boolean usesShortCommand, int targetIndexOneIndexed, final TestTask[] currentList,
            final TestTask[] doneList) {

        StringBuilder sb = new StringBuilder(); // workaround to done all tasks so we can test
        sb.append("done ");
        for (int i = 0; i < doneList.length; i++) {
            sb.append((i + 1) + " ");
        }
        commandBox.runCommand(sb.toString());

        TestTask taskToUndone = doneList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        ArrayList<TestTask> tasksToUndone = new ArrayList<TestTask>();
        tasksToUndone.add(taskToUndone);
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(doneList, targetIndexOneIndexed);
        TestTask[] expectedUndone = TestUtil.addTasksToList(currentList, taskToUndone);

        commandBox.runCommand("done"); // to get to done list view

        if (usesShortCommand) {
            commandBox.runCommand("d " + targetIndexOneIndexed);
        } else {
            commandBox.runCommand("done " + targetIndexOneIndexed);
        }

        //confirm the done list now contains all done tasks excluding the one just marked as undone
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_UNDONE_TASK_SUCCESS, tasksToUndone));

        //confirm the new current list contains the right data
        commandBox.runCommand("list");
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToUndone.getName().fullName);
        assertMatching(taskToUndone, addedCard);
        assertTrue(taskListPanel.isListMatching(expectedUndone));

        //confirm the done list does not contain the task just marked as undone
        commandBox.runCommand("done");
        assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }
}
