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
    private void getExpectedSelect(){
        ReadOnlyTask task =  this.model.getFilteredTaskList().get(this.index - 1);
        String taskDetails = "Name: \t\t\t".concat(task.getName().toString()).concat("\n").
                concat("Description: \t\t").concat(task.getDescription().toString()).concat("\n").
                concat("Priority: \t\t\t").concat(task.getPriority().toString()).concat("\n");
        if (task.hasStartTime()) {
            taskDetails = taskDetails.concat("Start Time: \t\t").concat(task.getStartTime().toString()).concat("\n");
        }
        if (task.hasEndTime()) {
            taskDetails = taskDetails.concat("End Time: \t\t").concat(task.getDeadline().toString()).concat("\n");
        }
        //tag not yet implemented
        return firstLine.concat("\n\n").concat(taskDetails);
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
