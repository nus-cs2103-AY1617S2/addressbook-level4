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
        if (eventTaskListPanel.getNumberOfTask() > index) {
            assertEquals(eventTaskListPanel.getSelectedTasks().size(), 1);
            assertNoDeadlineTaskSelected();
            assertNoFloatingTaskSelected();
            ReadOnlyTask selectedTask = eventTaskListPanel.getSelectedTasks().get(0);
            assertEquals(eventTaskListPanel.getEventTask(index - 1), selectedTask);
        } else {
            if ((eventTaskListPanel.getNumberOfTask() + deadlineTaskListPanel.getNumberOfTask()) > index) {
                assertEquals(deadlineTaskListPanel.getSelectedTasks().size(), 1);
                assertNoEventTaskSelected();
                assertNoFloatingTaskSelected();
                ReadOnlyTask selectedTask = deadlineTaskListPanel.getSelectedTasks().get(0).getKey();
                assertEquals(deadlineTaskListPanel.getDeadlineTask(index - eventTaskListPanel.getNumberOfTask()
                        - 1), selectedTask);

            } else {
                assertEquals(floatingTaskListPanel.getSelectedTasks().size(), 1);
                assertNoDeadlineTaskSelected();
                assertNoEventTaskSelected();
                ReadOnlyTask selectedTask = floatingTaskListPanel.getSelectedTasks().get(0).getKey();
                assertEquals(floatingTaskListPanel.getFloatingTask(
                        index - eventTaskListPanel.getNumberOfTask() - deadlineTaskListPanel.getNumberOfTask()
                        - 1), selectedTask);
            }
        }
    }

    private void assertNoTaskSelected() {
        assertNoEventTaskSelected();
        assertNoDeadlineTaskSelected();
        assertNoFloatingTaskSelected();
    }

    private void assertNoEventTaskSelected() {
        assertEquals(eventTaskListPanel.getSelectedTasks().size(), 0);
    }

    private void assertNoDeadlineTaskSelected() {
        assertEquals(deadlineTaskListPanel.getSelectedTasks().size(), 0);
    }

    private void assertNoFloatingTaskSelected() {
        assertEquals(floatingTaskListPanel.getSelectedTasks().size(), 0);
    }

}
