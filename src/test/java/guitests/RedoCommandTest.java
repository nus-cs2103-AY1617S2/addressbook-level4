//@@author A0139539R
package guitests;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class RedoCommandTest extends AddressBookGuiTest {

    @Test
    public void redo_emptySession_failure() {
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_NO_FORWARDS_COMMAND);
    }

    @Test
    public void redo_noForwardCommand_failure() {
        //End of state stack
        commandBox.runCommand("delete 1");
        commandBox.runCommand("undo");
        commandBox.runCommand("redo");
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_NO_FORWARDS_COMMAND);
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_NO_FORWARDS_COMMAND);
    }

    @Test
    public void redo_addThenRedo_success() throws Exception {
        TestTask taskToAdd = td.hoon;
        commandBox.runCommand(taskToAdd.getAddCommand());
        TestTask[] expectedList = td.getTypicalTasks();
        expectedList = TestUtil.addTasksToList(expectedList, td.hoon);

        assertRedoSuccess(expectedList);
    }

    @Test
    public void redo_clearThenRedo_success() throws Exception {
        commandBox.runCommand("reset");
        TestTask[] expectedList = new TestTask[0];

        assertRedoSuccess(expectedList);
    }

    @Test
    public void redo_deleteThenRedo_success() throws Exception {
        commandBox.runCommand("delete 1");
        TestTask[] expectedList = td.getTypicalTasks();
        expectedList = TestUtil.removeTasksFromList(expectedList, td.alice);

        assertRedoSuccess(expectedList);
    }

    @Test
    public void redo_editThenRedo_success() throws Exception {
        String detailsToEdit = "Bobby for: floating priority:1 note:Block 123, Bobby Street 3 #husband";
        int taskListIndex = 1;

        commandBox.runCommand("edit " + taskListIndex + " " + detailsToEdit);
        TestTask[] expectedList = td.getTypicalTasks();
        expectedList = TestUtil.removeTasksFromList(expectedList, td.alice);

        assertRedoSuccess(expectedList);
    }

    @Test
    public void undo_markCompleteThenRedo_success() throws Exception {
        commandBox.runCommand("complete 1");
        TestTask[] expectedList = td.getTypicalTasks();
        expectedList = TestUtil.removeTasksFromList(expectedList, td.alice);

        assertRedoSuccess(expectedList);

        commandBox.runCommand("edit 1 for: floating");
        commandBox.runCommand("complete floating 1");

        assertRedoSuccess(expectedList);
    }

    @Test
    public void undo_markIncompleteThenRedo_success() throws Exception {
        commandBox.runCommand("complete 1");
        commandBox.runCommand("incomplete 1");
        TestTask[] expectedList = td.getTypicalTasks();

        assertRedoSuccess(expectedList);
    }

    /**
     * Runs the redo command to redo the previously undone command and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first person in the list,
     * @param currentList A copy of the current list of persons (before deletion).
     */
    private void assertRedoSuccess(TestTask[] expectedList) {
        commandBox.runCommand("undo");
        commandBox.runCommand("redo");

        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);

    }

}
