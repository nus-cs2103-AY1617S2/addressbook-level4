package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.doit.commons.core.Messages;
import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.AddCommand;
import seedu.doit.logic.commands.ClearCommand;
import seedu.doit.testutil.TestTask;
import seedu.doit.testutil.TestUtil;
import seedu.doit.testutil.TypicalTestTasks;

public class AddCommandTest extends TaskManagerGuiTest {
    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority should only be low med high";
    public static final String MESSAGE_STARTTIME_CONSTRAINTS = "Item Start Time should be in "
            + "MM-DD-YY HH:MM Format or relative date today, tomorrow, next wednesday";
    public static final String MESSAGE_ENDTIME_CONSTRAINTS = "Item End Time should be in "
            + "MM-DD-YY HH:MM Format or relative date today, tomorrow, next wednesday";

    // @@author A0160076L
    @Test
    public void add() throws IllegalValueException {
        // add one floating task
        TestTask[] currentList = this.td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.getFloatingTestTask();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // add another task
        taskToAdd = TypicalTestTasks.getDeadlineTestTask();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // add another event
        taskToAdd = TypicalTestTasks.getEventTestTask();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // missing description
        taskToAdd = TypicalTestTasks.getDeadlineTestTaskWithNoDescription();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAllPanelsMatch(currentList);

        // add duplicate floating task
        this.commandBox.runCommand(TypicalTestTasks.getFloatingTestTask().getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertAllPanelsMatch(currentList);

        // add duplicate event
        this.commandBox.runCommand(TypicalTestTasks.getEventTestTask().getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertAllPanelsMatch(currentList);

        // add duplicate task
        this.commandBox.runCommand(TypicalTestTasks.getDeadlineTestTask().getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertAllPanelsMatch(currentList);

        // add to empty list
        this.commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertAddSuccess(TypicalTestTasks.getFloatingTestTask());

        // invalid command
        this.commandBox.runCommand("adds invalid1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        // invalid start time
        this.commandBox.runCommand(AddCommand.COMMAND_WORD + " invalid2 s/kjsdf e/today p/high d/sss");
        assertResultMessage(MESSAGE_STARTTIME_CONSTRAINTS);
        // start time later than end time
        this.commandBox.runCommand(AddCommand.COMMAND_WORD + " invalid2 s/tomorrow e/today p/high d/sss");
        assertResultMessage(AddCommand.MESSAGE_INVALID_START_TIME);
        // invalid end time
        this.commandBox.runCommand(AddCommand.COMMAND_WORD + " invalid3 e/kjdgf p/high d/sss");
        assertResultMessage(MESSAGE_ENDTIME_CONSTRAINTS);
        // invalid priority
        this.commandBox.runCommand(AddCommand.COMMAND_WORD + " invalid4 p/dfjkhd d/sss");
        assertResultMessage(MESSAGE_PRIORITY_CONSTRAINTS);
    }
    // @@author

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        this.commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
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
        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAllPanelsMatch(expectedList);
    }

}
