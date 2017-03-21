package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.AddCommand;
import seedu.tasklist.testutil.FloatingTaskBuilder;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TestUtil;

public class AddCommandTest extends TaskListGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.internship;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.internship.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.tutorial);

        //invalid command
        commandBox.runCommand("adding newTask");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

    }

    @Test
    public void addWithFlexibleCommands() {
        try {
            //add one task with flexible command, with capital letters
            TestTask[] currentList = td.getTypicalTasks();
            commandBox.runCommand("InserT floating t/tag1 c/comments p/low");
            TestTask taskToAdd = new FloatingTaskBuilder().withName("floating").withTags("tag1").withComment("comments")
                    .withPriority("low").withStatus(false).build();

            assertFlexibleAddSuccess(taskToAdd, currentList);

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    @Test
    public void addWithFlexiblePrefixes() {
        try {
            //add one task with flexible command, with capital letters
            TestTask[] currentList = td.getTypicalTasks();
            commandBox.runCommand("add floating tAg/tag1 Comments/comments p/low");
            TestTask taskToAdd = new FloatingTaskBuilder().withName("floating").withTags("tag1").withComment("comments")
                    .withPriority("low").withStatus(false).build();

            assertFlexibleAddSuccess(taskToAdd, currentList);

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    /**
     * Checks if the addition is successful.
     * Very similar to the preceding method, except without running the command.
     */
    private void assertFlexibleAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
