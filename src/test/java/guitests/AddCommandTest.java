package guitests;

import static org.junit.Assert.assertTrue;

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
    public void add() throws IllegalValueException{
        //add one floating task
        TestTask[] currentList = this.td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.getFloatingTestTask();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task with deadline
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
        assertAllColmnsMatch(currentList);

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
        TaskCardHandle addedCard = this.taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(this.taskListPanel.isListMatching(expectedList));
    }

}
