//@@author A0139399J
package guitests;

import java.util.Arrays;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.RedoCommand;
import seedu.doit.model.comparators.TaskNameComparator;
import seedu.doit.testutil.TaskBuilder;
import seedu.doit.testutil.TestTask;
import seedu.doit.testutil.TestUtil;
import seedu.doit.testutil.TypicalTestTasks;

public class RedoCommandTest extends TaskManagerGuiTest {

    public static final String MESSAGE_REDO_COMMAND = "redo";
    public static final String MESSAGE_TEST_CLEAR_COMMAND = "clear";
    public static final String MESSAGE_TEST_MARK_COMMAND = "mark 5";
    public static final String MESSAGE_TEST_DELETE_COMMAND = "delete 7";
    public static final String MESSAGE_TEST_EDIT_COMMAND = "edit 2 t/hi";
    public static final String MESSAGE_TEST_UNDO_COMMAND = "undo";

    // The list of tasks in the task list panel is expected to match this list.
    private TestTask[] expectedTasksList = this.td.getTypicalTasks();

    @Test
    public void redo_add_success() throws Exception {
        TestTask[] currentList = this.td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.getFloatingTestTask();
        this.commandBox.runCommand(taskToAdd.getAddCommand());
        executeUndoThenRedo();
        assertAddSuccess(taskToAdd, currentList);
    }

    @Test
    public void redo_delete_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_DELETE_COMMAND);
        executeUndoThenRedo();
        assertDeleteSuccess();
    }

    @Test
    public void redo_edit_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_EDIT_COMMAND);
        executeUndoThenRedo();
        assertEditSuccess();

    }

    @Test
    public void redo_mark_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_MARK_COMMAND);
        executeUndoThenRedo();
        assertDoneSuccess();
    }

    @Test
    public void redo_clear_success() throws Exception {
        this.commandBox.runCommand(MESSAGE_TEST_CLEAR_COMMAND);
        executeUndoThenRedo();
        assertClearCommandSuccess();
    }

    @Test
    public void redo_emptyStack_failure() {
        this.commandBox.runCommand(MESSAGE_REDO_COMMAND);
        assertResultMessage(RedoCommand.MESSAGE_FAILURE);

    }

    private void executeUndoThenRedo() {
        this.commandBox.runCommand(MESSAGE_TEST_UNDO_COMMAND);
        this.commandBox.runCommand(MESSAGE_REDO_COMMAND);
    }

    private void assertClearCommandSuccess() {
        assertListSize(0);
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }

    private void assertDoneSuccess() throws IllegalValueException {

        // confirm the list now contains all previous tasks plus the task with
        // updated isDone variable

        TestTask taskToMark = this.expectedTasksList[5 - 1];
        TestTask markedTask = new TaskBuilder(taskToMark).withIsDone(true).build();

        this.expectedTasksList[5 - 1] = markedTask;
        Arrays.sort(this.expectedTasksList, new TaskNameComparator());

        assertAllPanelsMatch(this.expectedTasksList);
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        // confirm the new card contains the right data
        if (!taskToAdd.getIsDone() && taskToAdd.isFloatingTask()) {
            TaskCardHandle addedCard = this.floatingTaskListPanel.navigateToTask(taskToAdd.getName().fullName);
            assertMatching(taskToAdd, addedCard);
        } else if (!taskToAdd.getIsDone() && taskToAdd.isEvent()) {
            TaskCardHandle addedCard = this.eventListPanel.navigateToTask(taskToAdd.getName().fullName);
            assertMatching(taskToAdd, addedCard);
        } else if (!taskToAdd.getIsDone() && taskToAdd.isTask()) {
            TaskCardHandle addedCard = this.taskListPanel.navigateToTask(taskToAdd.getName().fullName);
            assertMatching(taskToAdd, addedCard);
        }
        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAllPanelsMatch(expectedList);
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }

    private void assertDeleteSuccess() {
        TestTask[] currentList = this.td.getTypicalTasks();
        int targetIndexOneIndexed = 7;

        TestUtil.sortTasks(currentList);
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        // confirm the list now contains all previous tasks except the deleted
        // task
        assertAllPanelsMatch(expectedRemainder);

        // confirm the result message is correct
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }

    private void assertEditSuccess() throws IllegalValueException {
        int taskManagerIndex = 2;
        TestTask taskToEdit = this.expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("hi").build();

        // confirm the new card contains the right data
        if (editedTask.isTask()) {
            TaskCardHandle editedCard = this.taskListPanel.navigateToTask(editedTask.getName().fullName);
            assertMatching(editedTask, editedCard);
        } else if (editedTask.isEvent()) {
            TaskCardHandle editedCard = this.eventListPanel.navigateToTask(editedTask.getName().fullName);
            assertMatching(editedTask, editedCard);
        } else {
            TaskCardHandle editedCard = this.floatingTaskListPanel.navigateToTask(editedTask.getName().fullName);
            assertMatching(editedTask, editedCard);
        }
        // confirm the list now contains all previous tasks plus the task with
        // updated details
        this.expectedTasksList[taskManagerIndex - 1] = editedTask;
        Arrays.sort(this.expectedTasksList, new TaskNameComparator());
        assertAllPanelsMatch(this.expectedTasksList);
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }

}
