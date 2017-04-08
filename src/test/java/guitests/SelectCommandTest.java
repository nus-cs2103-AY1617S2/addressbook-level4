package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import org.junit.Test;

import seedu.ezdo.commons.core.Messages;
import seedu.ezdo.logic.commands.SelectCommand;
import seedu.ezdo.model.todo.ReadOnlyTask;
import seedu.ezdo.testutil.TestTask;

public class SelectCommandTest extends EzDoGuiTest {

    @Test
    public void select() {
        // invalid command
        commandBox.runCommand("selects");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        // invalid command
        commandBox.runCommand("select");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        // invalid command
        commandBox.runCommand("select  ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }

    @Test
    public void selectTask_nonEmptyList() {

        assertSelectionInvalid(10); // invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); // first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); // a task in the middle of the list

        assertSelectionInvalid(0); // invalid index
        assertSelectionInvalid(taskCount + 1);
        assertTaskSelected(middleIndex); // assert previous selection remains

        // select index smaller than the range in done list
        assertSelectionInvalidInDoneList(0);

        // select index larger than the range in done list
        commandBox.runCommand("list");
        commandBox.runCommand("done 1 3");
        commandBox.runCommand("done");
        commandBox.runCommand("select " + 987);
        assertResultMessage("The task index provided is invalid.");

        /*
         * Testing other invalid indexes such as -1 should be done when testing
         * the SelectCommand
         */
    }

    @Test
    public void selectMultipleTasks_nonEmptyList() {

        TestTask[] currentList = td.getTypicalTasks();
        assertFalse((currentList[1]).getStarted());

        assertMultipleSelectionSuccess(currentList);

        // select index smaller than the range in done list
        assertSelectionInvalidInDoneList(0);
        
        // select any task in done list
        assertDoneTaskSelectionInvalid(2);

        // select index larger than the range in done list
        commandBox.runCommand("list");
        commandBox.runCommand("done 1 3");
        commandBox.runCommand("done");
        commandBox.runCommand("select " + 987);
        assertResultMessage("The task index provided is invalid.");

        /*
         * Testing other invalid indexes such as -1 should be done when testing
         * the SelectCommand
         */
    }

    private void assertMultipleSelectionSuccess(TestTask[] currentList) {

        ArrayList<Integer> listOfTasks = new ArrayList<>();
        for (int i = 0; i < currentList.length; i++) {
            listOfTasks.add(i);
        }
        assertSelectionSuccess(listOfTasks);
    }

    @Test
    public void selectTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); // invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid.");
    }

    private void assertSelectionInvalidInDoneList(int index) {
        commandBox.runCommand("done 1 2 4");
        commandBox.runCommand("done");
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid.");
    }

    private void assertDoneTaskSelectionInvalid(int index) {
        commandBox.runCommand("done 1 2 4");
        commandBox.runCommand("done");
        commandBox.runCommand("select " + index);
        assertResultMessage("The task has a status marked as done.");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Task: " + "[" + index + "]");
        assertTaskSelected(index);
    }

    private void assertSelectionSuccess(ArrayList<Integer> indexes) {
        String listOfTasks = "";
        for (int i = 1; i <= indexes.size(); i++) {
            listOfTasks += " ";
            listOfTasks += i;
        }
        commandBox.runCommand("select " + listOfTasks);

        listOfTasks = listOfTasks.trim();
        listOfTasks = listOfTasks.replace(" ", ", ");
        assertResultMessage("Selected Task: " + "[" + listOfTasks + "]");
        assertTaskSelected(indexes.size());
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
