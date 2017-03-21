package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.taskboss.model.task.ReadOnlyTask;

public class ViewCommandTest extends TaskBossGuiTest {

    @Test
    public void selectTask_nonEmptyList() {

        assertSelectionInvalid(false, 10); // invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(false, 1); // first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(false, taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(false, middleIndex); // a task in the middle of the list

        assertSelectionInvalid(false, taskCount + 1); // invalid index
        assertTaskSelected(middleIndex); // assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectTaskShortCommand_nonEmptyList() {

        assertSelectionInvalid(true, 20); // invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(true, 1); // first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(true, taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(true, middleIndex); // a task in the middle of the list

        assertSelectionInvalid(true, taskCount + 1); // invalid index
        assertTaskSelected(middleIndex); // assert previous selection remains
    }

    @Test
    public void selectTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(false, 1); //invalid index
    }

    private void assertSelectionInvalid(boolean isShortCommand, int index) {
        if (isShortCommand) {
            commandBox.runCommand("v " + index);
        } else {
            commandBox.runCommand("view " + index);
        }
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(boolean isShortCommand, int index) {
        if (isShortCommand) {
            commandBox.runCommand("v " + index);
        } else {
            commandBox.runCommand("view " + index);
        }
        assertResultMessage("Selected Task: " + index);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index - 1), selectedTask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
