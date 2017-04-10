package guitests;
// @@author A0160076L
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.doit.model.item.ReadOnlyTask;

public class SelectCommandTest extends TaskManagerGuiTest {


    @Test
    public void selectTask_nonEmptyList() {
        assertSelectionInvalid(30); // invalid index
        assertNoTaskSelected();
        //assertSelectionSuccess(1); // first task in the list
        int taskCount = this.td.getTypicalTasks().length;
        //assertSelectionSuccess(taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        //assertSelectionSuccess(middleIndex); // a task in the middle of the list
        //assertSelectionInvalid(taskCount + 1); // invalid index
        //assertTaskSelected(middleIndex); // assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectTask_emptyList() {
        this.commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        this.commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        this.commandBox.runCommand("select " + index);
        assertResultMessage("Selected Task: " + index);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(this.taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = this.taskListPanel.getSelectedTasks().get(0);
        assertEquals(this.taskListPanel.getTask(index - 1), selectedTask);
    }


        //TODO: confirm the correct page is loaded in the Browser Panel



    private void assertNoTaskSelected() {
        assertEquals(this.taskListPanel.getSelectedTasks().size(), 0);
    }
    //@@author

}
