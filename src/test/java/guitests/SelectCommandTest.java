package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.taskit.logic.commands.SelectCommand;
import seedu.taskit.commons.core.Messages;
import seedu.taskit.model.task.ReadOnlyTask;

//@@author A0141011J
public class SelectCommandTest extends AddressBookGuiTest {


    @Test
    public void selectTask_invalidIndex() {
        assertSelectionInvalid(100); // invalid index
        assertNoTaskSelected();

        commandBox.runCommand("select -1"); // negative index
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        assertNoTaskSelected();
    }

    @Test
    public void selectTask_incorrectCommand() {
        //Invalid command input
        commandBox.runCommand("select hw");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,SelectCommand.MESSAGE_USAGE));
        assertNoTaskSelected();
    }

    @Test
    public void selectTask_nonEmptyList_success() {

        assertSelectionSuccess(1); // first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); // a task in the middle of the list

        assertSelectionInvalid(taskCount + 1); // invalid index
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
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        ReadOnlyTask task = taskListPanel.getTask(index - 1);
        String expectedMsg = String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, task.getTitle());
        assertResultMessage(expectedMsg);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index - 1), selectedTask);
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
