package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import onlythree.imanager.logic.commands.SelectCommand;
import onlythree.imanager.model.task.ReadOnlyTask;

public class SelectCommandTest extends TaskListGuiTest {

    @Test
    public void selectTask_nonEmptyList() {
        int taskCount = td.getTypicalTasks().length;

        assertSelectionSuccess(10, taskCount); // invalid index
        assertTaskSelected(taskCount); // assert last task selected

        assertSelectionSuccess(1); // first task in the list
        assertSelectionSuccess(taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); // a task in the middle of the list

        assertSelectionSuccess(taskCount + 1, taskCount); // index bigger than the taskCount
        assertTaskSelected(taskCount); // assert last task selected

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
        assertNoTaskSelected();
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        assertSelectionSuccess(index, index);
    }

    private void assertSelectionSuccess(int index, int lastIndex) {
        commandBox.runCommand("select " + index);
        int selectedIndex = index;

        if (selectedIndex > lastIndex) {
            selectedIndex = lastIndex;
        }
        ReadOnlyTask task = taskListPanel.getSelectedTasks().get(0);

        assertResultMessage(String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, selectedIndex,
                getCompactFormattedTask(task)));
        assertTaskSelected(selectedIndex);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index - 1), selectedTask);
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

    /**
     * Returns the task with only the task name and tags.
     */
    private String getCompactFormattedTask(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();

        sb.append(task.getName());
        sb.append(System.lineSeparator());

        sb.append("Tags: ");
        task.getTags().forEach(sb::append);

        return sb.toString();
    }

}
