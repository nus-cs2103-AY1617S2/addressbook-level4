package guitests;

import static org.junit.Assert.assertEquals;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

//import seedu.onetwodo.logic.commands.EditCommand;

import seedu.onetwodo.logic.commands.SelectCommand;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestUtil;

public class SelectCommandTest extends ToDoListGuiTest {


    @Test
    public void selectTask_nonEmptyList() {
        assertNoTaskSelected();

        assertSelectionSuccess("t1"); // first to-do task in the list
        assertSelectionSuccess("e2"); // 2nd event task in the list
        assertSelectionSuccess("d3"); // last deadline task in the list

        // invalid index
        //assertSelectionInvalid("t100");
        assertSelectionInvalid("t4");

        commandBox.runCommand(SelectCommand.COMMAND_WORD + " t0");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        commandBox.runCommand(SelectCommand.COMMAND_WORD + " t-1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }

    @Test
    public void selectTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid("t1"); //invalid index
    }

    private void assertSelectionInvalid(String prefixIndex) {
        commandBox.runCommand("select " + prefixIndex);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(String prefixIndex) {
        commandBox.runCommand("select " + prefixIndex);
        String displayPrefixIndex = TestUtil.getTaskTypeFromIndex(prefixIndex).getPrefix() + prefixIndex.substring(1);
        assertResultMessage("Selected Task: " + displayPrefixIndex);
        assertTaskSelected(prefixIndex);
    }

    private void assertTaskSelected(String filteredIndex) {
        TaskType taskType = TestUtil.getTaskTypeFromIndex(filteredIndex);
        int index = TestUtil.getFilteredIndexInt(filteredIndex);

        assertEquals(taskListPanel.getSelectedTasks(taskType).size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks(taskType).get(0);
        assertEquals(taskListPanel.getTask(taskType, index), selectedTask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
