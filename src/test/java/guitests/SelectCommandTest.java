package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.task.ReadOnlyTask;
import seedu.address.ui.TaskListPanel;

public class SelectCommandTest extends ToDoListGuiTest {


    @Test
    public void selectTask_nonEmptyList() {

        assertSelectionInvalid(10); // invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); // first Task in the list
        int TaskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(TaskCount); // last Task in the list
        int middleIndex = TaskCount / 2;
        assertSelectionSuccess(middleIndex); // a Task in the middle of the list

        assertSelectionInvalid(TaskCount + 1); // invalid index
        assertTaskSelected(middleIndex); // assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The Task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
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
