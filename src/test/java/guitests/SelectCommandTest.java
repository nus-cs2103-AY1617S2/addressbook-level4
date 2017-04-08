package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.taskmanager.model.task.ReadOnlyTask;

public class SelectCommandTest extends TaskManagerGuiTest {

    // @@author A0141102H
    @Test
    public void selectTask_nonEmptyList() {

        assertSelectionInvalid(20); // invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); // first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); // a task in the middle of the list

        assertSelectionInvalid(taskCount + 1); // invalid index
        assertTaskSelected(middleIndex); // assert previous selection remains

        /*
         * Testing other invalid indexes such as -1 should be done when testing
         * the SelectCommand
         */
    }

    @Test
    public void selectTask_emptyList() {
        commandBox.runCommand("CLEAR");
        assertListSize(0);
        assertSelectionInvalid(1); // invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("SELECT " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("SELECT " + index);
        assertResultMessage("Selected Task: " + index);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(eventTaskListPanel.getSelectedTasks().size(), 1);
        assertEquals(deadlineTaskListPanel.getSelectedTasks().size(), 1);
        assertEquals(floatingTaskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = eventTaskListPanel.getSelectedTasks().get(0);
        assertEquals(eventTaskListPanel.getEventTask(index - 1), selectedTask);
        assertEquals(deadlineTaskListPanel.getDeadlineTask(index - 1), selectedTask);
        assertEquals(floatingTaskListPanel.getFloatingTask(index - 1), selectedTask);
        // TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
