package guitests;

import org.junit.Test;
//import static org.junit.Assert.assertTrue;

import guitests.guihandles.TaskCardHandle;
import seedu.task.logic.commands.AddCommand;
import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        commandBox.runCommand("clear");

        // add one person
        TestTask[] currentList = td.getTypicalTasks();
        TestTask TaskToAdd = td.zoo;
        assertAddSuccess(TaskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, TaskToAdd);

        // add another person
        TaskToAdd = td.jog;
        assertAddSuccess(TaskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, TaskToAdd);

        // add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.lab);

        // invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        // invalid add command
        commandBox.runCommand("add");
        assertResultMessage(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {

        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd);
        assertMatching(taskToAdd, addedCard);

        // confirm the list now contains all previous persons plus the new person
        // TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        // assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
