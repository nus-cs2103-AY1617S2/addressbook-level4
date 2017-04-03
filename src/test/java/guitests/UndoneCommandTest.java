package guitests;

import static org.junit.Assert.*;
import static seedu.ezdo.logic.commands.DoneCommand.MESSAGE_UNDONE_TASK_SUCCESS;

import java.util.ArrayList;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.testutil.TestTask;
import seedu.ezdo.testutil.TestUtil;
import seedu.ezdo.testutil.TypicalTestTasks;
//@@author A0139248X
public class UndoneCommandTest extends EzDoGuiTest {

    @Override
    protected EzDo getInitialData() {
        EzDo ez = new EzDo();
        TypicalTestTasks.loadEzDoWithSampleDataNonRecurring(ez);
        return ez;
    }

    @Test
    public void undone_success() {
        TestTask[] currentList = td.getTypicalDoneTasks();
        TestTask[] doneList = td.getTypicalNonRecurringTasks();
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
