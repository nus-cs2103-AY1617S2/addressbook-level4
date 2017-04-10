package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.onetwodo.model.history.CommandHistoryEntry.COMMAND_FORMATTER;
import static seedu.onetwodo.model.history.ToDoListHistoryManager.MESSAGE_EMPTYREDOHISTORY;

import org.junit.Test;

import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.EditCommand;
import seedu.onetwodo.logic.commands.ListCommand;
import seedu.onetwodo.logic.commands.RedoCommand;
import seedu.onetwodo.logic.commands.UndoCommand;
import seedu.onetwodo.logic.commands.UndoneCommand;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TaskBuilder;
import seedu.onetwodo.testutil.TestTask;
import seedu.onetwodo.testutil.TestUtil;

//@@author A0135739W
public class RedoCommandTest extends ToDoListGuiTest {

    TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void redo_noMoreUndo_failure () {
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e2");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);
        assertResultMessage(MESSAGE_EMPTYREDOHISTORY);
    }

    @Test
    public void redo_failedUndoDueToFailedCommand_failure () {
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e999");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);
        assertResultMessage(MESSAGE_EMPTYREDOHISTORY);
    }

    @Test
    public void undo_change_redo_failure () {
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e2");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " d2");
        commandBox.runCommand(RedoCommand.COMMAND_WORD);
        assertResultMessage(MESSAGE_EMPTYREDOHISTORY);
    }

    @Test
    public void redo_undo_add_success () {
        TestTask taskToAdd = td.task1;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);

        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(taskToAdd.getTaskType(), expectedList));

        String feedbackMessage = String.format(AddCommand.COMMAND_WORD.concat(COMMAND_FORMATTER), taskToAdd);
        assertResultMessage(RedoCommand.COMMAND_WORD + " successfully.\n" + feedbackMessage);
    }

    @Test
    public void redo_undo_add_with_menu_success() {
        TestTask taskToAdd = td.task1;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        mainMenu.executeRedoWithMenu();

        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(taskToAdd.getTaskType(), expectedList));

        String feedbackMessage = String.format(AddCommand.COMMAND_WORD.concat(COMMAND_FORMATTER), taskToAdd);
        assertResultMessage(RedoCommand.COMMAND_WORD + " successfully.\n" + feedbackMessage);
    }

    @Test
    public void redo_undo_delete_success () {
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e2");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);

        TestTask[] filteredTasks = TestUtil.getTasksByTaskType(currentList, TaskType.EVENT);
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(filteredTasks, 2);
        assertTrue(taskListPanel.isListMatching(TaskType.EVENT, expectedRemainder));

        String feedbackMessage = String.format(DeleteCommand.COMMAND_WORD.concat(COMMAND_FORMATTER), filteredTasks[1]);
        assertResultMessage(RedoCommand.COMMAND_WORD + " successfully.\n" + feedbackMessage);
    }

    @Test
    public void redo_undo_edit_success () throws Exception {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " t1 t/interest t/hobby");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);

        //edit Task t1
        TestTask[] filteredTaskList = TestUtil.getTasksByTaskType(currentList, TaskType.TODO);
        TestTask editedTask = new TaskBuilder(filteredTaskList[0]).withTags("interest", "hobby").build();
        filteredTaskList[0] = editedTask;
        assertTrue(taskListPanel.isListMatching(TaskType.TODO, filteredTaskList));

        String feedbackMessage = String.format(EditCommand.COMMAND_WORD.concat(COMMAND_FORMATTER), editedTask);
        assertResultMessage(RedoCommand.COMMAND_WORD + " successfully.\n" + feedbackMessage);
    }

    @Test
    public void redo_undo_done_success() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " d2");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);

        TestTask[] filteredTaskList = TestUtil.getTasksByTaskType(currentList, TaskType.DEADLINE);
        int testTaskIndex = TestUtil.getFilteredIndexInt("d2");

        TestTask targetTask = filteredTaskList[testTaskIndex];
        if (!targetTask.hasRecur()) {
            targetTask.setIsDone(true);
        } else {
            TestTask newTestTask = new TaskBuilder(targetTask).build();
            newTestTask.updateTaskRecurDate(true);
            targetTask.setIsDone(true);
            filteredTaskList = TestUtil.addTasksToList(currentList, newTestTask);
        }
        TestTask[] filteredUndoneList = TestUtil.getTasksByDoneStatus(filteredTaskList, false);
        assertTrue(taskListPanel.isListMatching(TaskType.DEADLINE, filteredUndoneList));

        String feedbackMessage = String.format(DoneCommand.COMMAND_WORD.concat(COMMAND_FORMATTER),
                targetTask);
        assertResultMessage(RedoCommand.COMMAND_WORD + " successfully.\n" + feedbackMessage);
    }

    @Test
    public void redo_undo_undone_success() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t1");
        commandBox.runCommand(ListCommand.COMMAND_LIST_DONE);
        commandBox.runCommand(UndoneCommand.COMMAND_WORD + " t1");
        commandBox.runCommand(ListCommand.COMMAND_WORD);
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);
        assertTrue(taskListPanel.isListMatching(TaskType.TODO, currentList));

        String feedbackMessage = String.format(UndoneCommand.COMMAND_WORD.concat(COMMAND_FORMATTER),
                td.taskG);
        assertResultMessage(RedoCommand.COMMAND_WORD + " successfully.\n" + feedbackMessage);
    }

    @Test
    public void redo_undo_clear_done_success() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t1");
        commandBox.runCommand(ListCommand.COMMAND_LIST_DONE);
        commandBox.runCommand(ClearCommand.COMMAND_CLEAR_DONE);
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);
        assertListSize(0);
        assertResultMessage(RedoCommand.COMMAND_WORD + " successfully.\n" + ClearCommand.MESSAGE_CLEAR_DONE_SUCCESS);
    }

    @Test
    public void redo_undo_clear_undone_success() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " t1");
        commandBox.runCommand(ClearCommand.COMMAND_CLEAR_UNDONE);
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);
        assertListSize(0);
        assertResultMessage(RedoCommand.COMMAND_WORD + " successfully.\n" + ClearCommand.MESSAGE_CLEAR_UNDONE_SUCCESS);
    }

    @Test
    public void redo_undo_clear_all_success() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(RedoCommand.COMMAND_WORD);

        TestTask[] emptyList = new TestTask[0];
        assertTrue(taskListPanel.isListMatching(TaskType.EVENT, emptyList));
        assertTrue(taskListPanel.isListMatching(TaskType.DEADLINE, emptyList));
        assertTrue(taskListPanel.isListMatching(TaskType.TODO, emptyList));

        assertResultMessage(RedoCommand.COMMAND_WORD + " successfully.\n" + ClearCommand.MESSAGE_CLEAR_ALL_SUCCESS);
    }
}
