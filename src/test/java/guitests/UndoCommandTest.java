package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

//@@author A0162877N
public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo_EditLabel_invalidCommand() {
        LogicManager.undoCommandHistory.clear();
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("undo");
        //No change should occur and show error message
        assertResultMessage(UndoCommand.MESSAGE_UNSUCCESSFUL_UNDO);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void undo_EditLabelValid_ReturnTrue() throws IllegalValueException {
        LogicManager.undoCommandHistory.clear();
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("editlabel friends allies");
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");

        assertTrue(td.getTypicalTasks().length == taskListPanel.getNumberOfTasks());
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void undo_addTask_ReturnTrue() {
        LogicManager.undoCommandHistory.clear();
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.task8;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(currentList));

        //undo command
        commandBox.runCommand("undo");
        taskToAdd = td.task8;
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));

        //undo command
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));

        //empty list and undo
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTitle().title);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new tasks
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
