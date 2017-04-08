package guitests;

import static org.junit.Assert.assertEquals;
import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.SelectCommand;
import seedu.tache.model.task.ReadOnlyTask;

public class SelectCommandTest extends TaskManagerGuiTest {

    @Test
    public void select_nonEmptyList_success() {

        assertSelectionInvalid(10); // invalid index
        assertNoTaskSelected();

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
    public void select_emptyList_failure() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    //@@author A0142255M
    @Test
    public void select_invalidCommand_failure() {
        commandBox.runCommand("selectgysudcv");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void select_shortCommand_success() {
        int index = 1;
        commandBox.runCommand(SelectCommand.SHORT_COMMAND_WORD + " " + index);
        assertResultMessage("Selected Task: " + index);
        assertTaskSelected(index);
    }

    @Test
    public void select_invalidIndex_failure() {
        commandBox.runCommand(SelectCommand.COMMAND_WORD + " -1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        commandBox.runCommand(SelectCommand.COMMAND_WORD + " Book");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        commandBox.runCommand(SelectCommand.COMMAND_WORD + " ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
    //@@author

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
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
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
