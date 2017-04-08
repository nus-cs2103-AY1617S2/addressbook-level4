package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.DeadlineTaskCardHandle;
import guitests.guihandles.EventTaskCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.testutil.TestTask;
import seedu.taskmanager.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    // @@author A0141102H
    @Test
    public void add() {
        // add one floating task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.sampleEvent;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        
        // add another task (deadline)
        taskToAdd = td.sampleDeadline;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();

        // add duplicate task
        commandBox.runCommand(td.sampleDeadline.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(eventTaskListPanel.isListMatching(currentList));
        assertTrue(deadlineTaskListPanel.isListMatching(currentList));
        assertTrue(floatingTaskListPanel.isListMatching(currentList));

        // add to empty list
        commandBox.runCommand("CLEAR");
        assertAddSuccess(td.eatBreakfast);

        // invalid command
        commandBox.runCommand("ADDS Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        if (taskToAdd.isEventTask()) {
            EventTaskCardHandle addedCard = eventTaskListPanel.navigateToEventTask(taskToAdd.getTaskName().toString());
            assertMatching(taskToAdd, addedCard);
        } else {
            if (taskToAdd.isDeadlineTask()) {
                DeadlineTaskCardHandle addedCard = deadlineTaskListPanel
                        .navigateToDeadlineTask(taskToAdd.getTaskName().toString());
                assertMatching(taskToAdd, addedCard);
            } else {
                if (taskToAdd.isFloatingTask()) {
                    FloatingTaskCardHandle addedCard = floatingTaskListPanel
                            .navigateToFloatingTask(taskToAdd.getTaskName().toString());
                    assertMatching(taskToAdd, addedCard);
                }
            }
        }

        // confirm the list now contains all previous tasks plus the new
        // task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd).getKey();
        assertTrue(eventTaskListPanel.isListMatching(expectedList));
        assertTrue(deadlineTaskListPanel.isListMatching(expectedList));
        assertTrue(floatingTaskListPanel.isListMatching(expectedList));
    }

}
