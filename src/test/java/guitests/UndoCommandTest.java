package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.onetwodo.model.history.CommandHistoryEntry.COMMAND_FORMATTER;
import static seedu.onetwodo.model.history.ToDoListHistoryManager.MESSAGE_EMPTYUNDOHISTORY;

import org.junit.Test;

import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.logic.commands.DoneCommand;
import seedu.onetwodo.logic.commands.EditCommand;
import seedu.onetwodo.logic.commands.UndoCommand;
import seedu.onetwodo.logic.commands.UndoneCommand;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.testutil.TestTask;

//@@author A0135739W
public class UndoCommandTest extends ToDoListGuiTest {

    TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void undo_noCommand_failure () {
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e2");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(MESSAGE_EMPTYUNDOHISTORY);
    }

    @Test
    public void undo_add_success() {
        TestTask taskToAdd = td.task1;
        commandBox.runCommand(taskToAdd.getAddCommand());
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertTrue(taskListPanel.isListMatching(taskToAdd.getTaskType(), currentList));

        String feedbackMessage = String.format(DeleteCommand.COMMAND_WORD.concat(COMMAND_FORMATTER), taskToAdd);
        assertResultMessage(UndoCommand.RESULT_SUCCESS + feedbackMessage);
    }

    @Test
    public void undo_failedAdd_failure() {
        commandBox.runCommand(AddCommand.COMMAND_WORD + " ");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(MESSAGE_EMPTYUNDOHISTORY);
    }

    @Test
    public void undo_delete_success() {
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e2");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertTrue(taskListPanel.isListMatching(TaskType.EVENT, currentList));

        String feedbackMessage = String.format(AddCommand.COMMAND_WORD.concat(COMMAND_FORMATTER), td.taskB);
        assertResultMessage(UndoCommand.RESULT_SUCCESS + feedbackMessage);
    }

    @Test
    public void undo_failedDelete_failure() {
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " e999");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(MESSAGE_EMPTYUNDOHISTORY);
    }

    @Test
    public void undo_edit_success() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " e1" +
                " GUARD duties s/ e/16-12-2018 11:30pm d/bring helmet t/");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertTrue(taskListPanel.isListMatching(TaskType.EVENT, currentList));

        String feedbackMessage = String.format("Restore Task".concat(COMMAND_FORMATTER), td.taskA);
        assertResultMessage(UndoCommand.RESULT_SUCCESS + feedbackMessage);
    }

    @Test
    public void undo_failedEdit_invalidIndex_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " e999 hello");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(MESSAGE_EMPTYUNDOHISTORY);
    }

    @Test
    public void undo_failedEdit_addRepeatedTask_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " e1");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(MESSAGE_EMPTYUNDOHISTORY);
    }

    @Test
    public void undo_done_success() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " d1");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertTrue(taskListPanel.isListMatching(TaskType.DEADLINE, currentList));

        String feedbackMessage = String.format(UndoneCommand.COMMAND_WORD_CAP.concat(COMMAND_FORMATTER), td.taskD);
        assertResultMessage(UndoCommand.RESULT_SUCCESS + feedbackMessage);
    }

    @Test
    public void undo_failedDone_failure() {
        commandBox.runCommand(DoneCommand.COMMAND_WORD + " e999");
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertResultMessage(MESSAGE_EMPTYUNDOHISTORY);
    }

    @Test
    public void undo_clear_success() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertTrue(taskListPanel.isListMatching(TaskType.EVENT, currentList));
        assertTrue(taskListPanel.isListMatching(TaskType.DEADLINE, currentList));
        assertTrue(taskListPanel.isListMatching(TaskType.TODO, currentList));
        assertResultMessage(UndoCommand.RESULT_SUCCESS + ClearCommand.MESSAGE_UNDO_CLEAR_ALL_SUCCESS);
    }
}
