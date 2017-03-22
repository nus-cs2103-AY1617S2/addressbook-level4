package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.model.task.Name;
import seedu.onetwodo.testutil.TestTask;
import seedu.onetwodo.testutil.TestUtil;

public class AddCommandTest extends ToDoListGuiTest {

    @Test
    public void add() {
        TestTask[] currentList = td.getTypicalTasks();

        //add one task
        TestTask taskToAdd = td.task1;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.task2;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(taskToAdd.getTaskType(), currentList));

        //add to empty list
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertAddSuccess(td.task1);

        //invalid command
        commandBox.runCommand(AddCommand.COMMAND_WORD);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        commandBox.runCommand(AddCommand.COMMAND_WORD + " =n0n-41phanumer1c");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(taskToAdd.getTaskType(), expectedList));
    }
}
