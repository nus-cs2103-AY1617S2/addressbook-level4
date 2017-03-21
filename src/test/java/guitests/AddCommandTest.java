package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.doit.commons.core.Messages;
import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.AddCommand;
import seedu.doit.testutil.TestTask;
import seedu.doit.testutil.TestUtil;
import seedu.doit.testutil.TypicalTestTasks;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() throws IllegalValueException {
        //add one floating task
        TestTask[] currentList = this.td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.getFloatingTestTask();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = TypicalTestTasks.getDeadlineTestTask();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another event
        taskToAdd = TypicalTestTasks.getEventTestTask();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        this.commandBox.runCommand(TypicalTestTasks.getFloatingTestTask().getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertAllPanelsMatch(currentList);

        //add to empty list
        this.commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.getFloatingTestTask());

        //invalid command
        this.commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        this.commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data


        if (!taskToAdd.getIsDone() && taskToAdd.isFloatingTask()) {
            TaskCardHandle addedCard = this.floatingTaskListPanel.navigateToTask(taskToAdd.getName().fullName);
            assertMatching(taskToAdd, addedCard);
        } else if (!taskToAdd.getIsDone() && taskToAdd.isEvent()) {
            TaskCardHandle addedCard = this.eventListPanel.navigateToTask(taskToAdd.getName().fullName);
            assertMatching(taskToAdd, addedCard);
        } else if (!taskToAdd.getIsDone() && taskToAdd.isTask()) {
            TaskCardHandle addedCard = this.taskListPanel.navigateToTask(taskToAdd.getName().fullName);
            assertMatching(taskToAdd, addedCard);
        }


        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAllPanelsMatch(expectedList);
    }

}
