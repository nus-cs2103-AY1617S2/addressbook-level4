package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tache.commons.core.Messages;
import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.logic.commands.AddCommand;
import seedu.tache.model.recurstate.RecurState.RecurInterval;
import seedu.tache.model.task.Name;
import seedu.tache.testutil.TaskBuilder;
import seedu.tache.testutil.TestTask;
import seedu.tache.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add_floatingTask_success() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.getFit;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.findGirlfriend;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
    }
    //@@author A0150120H
    @Test
    public void add_floatingTask_failure() {
        TestTask[] currentList = td.getTypicalTasks();
        //add duplicate task
        commandBox.runCommand(td.eggsAndBread.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //invalid command
        commandBox.runCommand("adds Read Newspaper");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void add_timedTask_success() {
        //add task with end date only
        commandBox.runCommand("clear");
        TestTask[] currentList = new TestTask[0];
        assertAddSuccess(td.eggsAndBread, currentList);
        currentList = TestUtil.addTasksToList(currentList, td.eggsAndBread);

        //add task with both start and end date
        assertAddSuccess(td.visitFriend, currentList);
    }

    @Test
    public void add_timedTask_failure() {
        //Invalid format: Start date only
        commandBox.runCommand(td.startDateOnly.getAddCommand());
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void add_recurringTask_success() throws IllegalValueException {
        TestTask taskToAdd = new TaskBuilder().withName("Go to school").withStartDateTime("9am")
                .withEndDateTime("5pm").withRecurringInterval(RecurInterval.DAY).build();
        commandBox.runCommand(taskToAdd.getAddCommand());
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);
    }

    //@@author A0142255M
    @Test
    public void add_invalidTask_failure() {
        commandBox.runCommand(AddCommand.COMMAND_WORD + " ");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void add_shortCommand_success() {
        TestTask toAdd = td.getFit;
        commandBox.runCommand(AddCommand.SHORT_COMMAND_WORD + " " + toAdd.getName().fullName);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, toAdd.toString()));
    }
    //@@author
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
