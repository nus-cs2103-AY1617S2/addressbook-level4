package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.taskboss.model.task.ReadOnlyTask;

public class ViewCommandTest extends TaskBossGuiTest {

    @Test
    public void viewTask_nonEmptyList() {

        assertViewInvalid(false, 10); // invalid index
        assertNoTaskViewed();

        assertViewSuccess(false, 1); // first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertViewSuccess(false, taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertViewSuccess(false, middleIndex); // a task in the middle of the list

        assertViewInvalid(false, taskCount + 1); // invalid index
        assertTaskViewed(middleIndex); // assert previous viewing remains

        /* Testing other invalid indexes such as -1 should be done when testing the ViewCommand */
    }

    @Test
    public void viewTaskShortCommand_nonEmptyList() {

        assertViewInvalid(true, 20); // invalid index
        assertNoTaskViewed();

        assertViewSuccess(true, 1); // first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertViewSuccess(true, taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertViewSuccess(true, middleIndex); // a task in the middle of the list

        assertViewInvalid(true, taskCount + 1); // invalid index
        assertTaskViewed(middleIndex); // assert previous viewing remains
    }

    @Test
    public void viewTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertViewInvalid(false, 1); //invalid index
    }

    private void assertViewInvalid(boolean isShortCommand, int index) {
        if (isShortCommand) {
            commandBox.runCommand("v " + index);
        } else {
            commandBox.runCommand("view " + index);
        }
        assertResultMessage("The task index provided is invalid");
    }

    private void assertViewSuccess(boolean isShortCommand, int index) {
        if (isShortCommand) {
            commandBox.runCommand("v " + index);
        } else {
            commandBox.runCommand("view " + index);
        }
        assertResultMessage("Viewed Task: " + index);
        assertTaskViewed(index);
    }

    private void assertTaskViewed(int index) {
        assertEquals(taskListPanel.getViewingTasks().size(), 1);
        ReadOnlyTask viewedTask = taskListPanel.getViewingTasks().get(0);
        assertEquals(taskListPanel.getTask(index - 1), viewedTask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskViewed() {
        assertEquals(taskListPanel.getViewingTasks().size(), 0);
    }

}
