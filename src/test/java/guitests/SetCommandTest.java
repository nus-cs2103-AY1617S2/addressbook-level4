//@@author A0138909R
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.AddCommand;
import seedu.doit.logic.commands.ClearCommand;
import seedu.doit.logic.commands.DeleteCommand;
import seedu.doit.logic.commands.DoneCommand;
import seedu.doit.logic.commands.EditCommand;
import seedu.doit.logic.commands.ExitCommand;
import seedu.doit.logic.commands.FindCommand;
import seedu.doit.logic.commands.HelpCommand;
import seedu.doit.logic.commands.ListCommand;
import seedu.doit.logic.commands.LoadCommand;
import seedu.doit.logic.commands.MarkCommand;
import seedu.doit.logic.commands.RedoCommand;
import seedu.doit.logic.commands.SaveCommand;
import seedu.doit.logic.commands.SelectCommand;
import seedu.doit.logic.commands.SetCommand;
import seedu.doit.logic.commands.SortCommand;
import seedu.doit.logic.commands.UndoCommand;
import seedu.doit.logic.commands.UnmarkCommand;
import seedu.doit.model.comparators.TaskNameComparator;
import seedu.doit.testutil.TaskBuilder;
import seedu.doit.testutil.TestTask;
import seedu.doit.testutil.TestUtil;
import seedu.doit.testutil.TypicalTestTasks;

public class SetCommandTest extends TaskManagerGuiTest {
    private static final String INVALID_COMMAND_CHANGE = " invalid invalid";
    private static final String NEW_UNMARK = "newUnmark";
    private static final String NEW_UNDO = "newUndo";
    private static final String NEW_SORT = "newSort";
    private static final String NEW_SET = "newSet";
    private static final String NEW_SELECT = "newSelect";
    private static final String NEW_SAVE = "newSave";
    private static final String NEW_REDO = "newRedo";
    private static final String NEW_MARK = "newMark";
    private static final String NEW_LOAD = "newLoad";
    private static final String NEW_LIST = "newList";
    private static final String NEW_HELP = "newHelp";
    private static final String NEW_FIND = "newFind";
    private static final String NEW_EXIT = "newExit";
    private static final String NEW_EDIT = "newEdit";
    private static final String NEW_DONE = "newDone";
    private static final String NEW_DELETE = "newDelete";
    private static final String NEW_CLEAR = "newClear";
    private static final String NEW_ADD = "newAdd";
    TestTask[] currentList = this.td.getTypicalTasks();

    @Test
    public void set_Add_Success() {

        // set add
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + AddCommand.COMMAND_WORD + " " + NEW_ADD);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, AddCommand.COMMAND_WORD + " into " + NEW_ADD));
        this.commandBox.runCommand(NEW_ADD);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void set_Clear_Success() {
        // set clear
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + ClearCommand.COMMAND_WORD + " " + NEW_CLEAR);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, ClearCommand.COMMAND_WORD + " into " + NEW_CLEAR));
        assertClearCommandSuccess();
        this.commandBox.runCommand(UndoCommand.COMMAND_WORD);
    }

    @Test
    public void set_Delete_Success() {
        // set delete
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + DeleteCommand.COMMAND_WORD + " " + NEW_DELETE);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, DeleteCommand.COMMAND_WORD + " into " + NEW_DELETE));
        this.commandBox.runCommand(NEW_DELETE);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void set_Done_Success() throws IllegalValueException {
        // set done
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + DoneCommand.COMMAND_WORD + " " + NEW_DONE);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, DoneCommand.COMMAND_WORD + " into " + NEW_DONE));
        assertDoneSuccess(NEW_DONE);
    }

    @Test
    public void set_Edit_Success() {
        // set edit
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + EditCommand.COMMAND_WORD + " " + NEW_EDIT);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, EditCommand.COMMAND_WORD + " into " + NEW_EDIT));
        this.commandBox.runCommand(NEW_EDIT);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test

    public void set_Find_Success() {
        // set find
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD + " " + NEW_FIND);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, FindCommand.COMMAND_WORD + " into " + NEW_FIND));
        this.commandBox.runCommand(NEW_FIND);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void set_Help_Success() {
        // set help
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + HelpCommand.COMMAND_WORD + " " + NEW_HELP);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, HelpCommand.COMMAND_WORD + " into " + NEW_HELP));
        assertHelpWindowOpen(this.commandBox.runNewHelpCommand(NEW_HELP));
    }

    @Test
    public void set_List_Success() throws IllegalValueException {
        // set list
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + ListCommand.COMMAND_WORD + " " + NEW_LIST);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, ListCommand.COMMAND_WORD + " into " + NEW_LIST));
        assertListSuccess(NEW_LIST);
    }

    @Test
    public void set_Load_Success() {
        // set load
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + LoadCommand.COMMAND_WORD + " " + NEW_LOAD);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, LoadCommand.COMMAND_WORD + " into " + NEW_LOAD));
        this.commandBox.runCommand(NEW_LOAD);
        assertResultMessage(LoadCommand.MESSAGE_INVALID_FILE_NAME);
    }

    @Test
    public void set_Mark_Success() {
        // set mark
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + MarkCommand.COMMAND_WORD + " " + NEW_MARK);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, MarkCommand.COMMAND_WORD + " into " + NEW_MARK));
        this.commandBox.runCommand(NEW_MARK);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void set_Redo_Success() throws Exception {
        // set redo
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + RedoCommand.COMMAND_WORD + " " + NEW_REDO);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, RedoCommand.COMMAND_WORD + " into " + NEW_REDO));
        redo_add_success(NEW_REDO);
    }

    @Test
    public void set_Save_Success() {
        // set save
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + SaveCommand.COMMAND_WORD + " " + NEW_SAVE);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, SaveCommand.COMMAND_WORD + " into " + NEW_SAVE));
        this.commandBox.runCommand(NEW_SAVE);
        assertResultMessage(SaveCommand.MESSAGE_NOT_XML_FILE);
    }

    @Test
    public void set_Select_Success() {
        // set select
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + SelectCommand.COMMAND_WORD + " " + NEW_SELECT);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, SelectCommand.COMMAND_WORD + " into " + NEW_SELECT));
        this.commandBox.runCommand(NEW_SELECT);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }

    @Test
    public void set_Set_Success() {
        // set set
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + SetCommand.COMMAND_WORD + " " + NEW_SET);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, SetCommand.COMMAND_WORD + " into " + NEW_SET));
        this.commandBox.runCommand(NEW_SET);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
    }

    @Test
    public void set_Sort_Success() {
        // set sort
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + SortCommand.COMMAND_WORD + " " + NEW_SORT);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, SortCommand.COMMAND_WORD + " into " + NEW_SORT));
        this.commandBox.runCommand(NEW_SORT);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void set_Undo_Success() throws Exception {
        // set undo
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + UndoCommand.COMMAND_WORD + " " + NEW_UNDO);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, UndoCommand.COMMAND_WORD + " into " + NEW_UNDO));
        undo_add_success(NEW_UNDO);
    }

    @Test
    public void set_Unmark_Success() {
        // set unmark
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + UnmarkCommand.COMMAND_WORD + " " + NEW_UNMARK);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, UnmarkCommand.COMMAND_WORD + " into " + NEW_UNMARK));
        this.commandBox.runCommand(NEW_UNMARK);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void set_Exit_Success() {
        // set exit
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + ExitCommand.COMMAND_WORD + " " + NEW_EXIT);
        assertResultMessage(
                String.format(SetCommand.MESSAGE_SET_TASK_SUCCESS, ExitCommand.COMMAND_WORD + " into " + NEW_EXIT));
    }

    @Test
    public void set_Fail_Invalid_Command() {
        this.commandBox.runCommand(SetCommand.COMMAND_WORD);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
    }

    @Test
    public void set_Fail_Command_Exist_In_Default() {
        this.commandBox
                .runCommand(SetCommand.COMMAND_WORD + " " + SetCommand.COMMAND_WORD + " " + SetCommand.COMMAND_WORD);
        assertResultMessage(SetCommand.COMMAND_ALREADY_EXISTS);
    }

    @Test
    public void set_Fail_Command_Exist_In_CommandSettings() {
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + SetCommand.COMMAND_WORD + " " + NEW_SET);
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + " " + SetCommand.COMMAND_WORD + " " + NEW_SET);
        assertResultMessage(SetCommand.COMMAND_ALREADY_EXISTS);
    }

    @Test
    public void set_Fail_No_Such_Command() {
        this.commandBox.runCommand(SetCommand.COMMAND_WORD + INVALID_COMMAND_CHANGE);
        assertResultMessage(SetCommand.NO_SUCH_COMMAND_TO_CHANGE);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        this.commandBox.runCommand(taskToAdd.getAddCommand());

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
    }

    public void assertClearCommandSuccess() {
        this.commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertResultMessage("All tasks has been cleared!");
    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }

    public void undo_add_success(String newUndo) throws Exception {
        TestTask[] currentList = this.td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.getFloatingTestTask();
        this.commandBox.runCommand(taskToAdd.getAddCommand());
        this.commandBox.runCommand(UndoCommandTest.MESSAGE_UNDO_COMMAND);
        assertAllPanelsMatch(currentList);
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }

    public void redo_add_success(String newRedo) throws Exception {
        TestTask[] currentList = this.td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.getFloatingTestTask();
        this.commandBox.runCommand(taskToAdd.getAddCommand());
        this.commandBox.runCommand(RedoCommandTest.MESSAGE_TEST_UNDO_COMMAND);
        this.commandBox.runCommand(RedoCommandTest.MESSAGE_REDO_COMMAND);
        assertAddSuccess(taskToAdd, currentList);
    }

    public void assertListSuccess(String newList) throws IllegalValueException {
        this.commandBox.runCommand(DoneCommand.COMMAND_WORD);
        this.commandBox.runCommand(newList);
        TestTask taskToMark = this.currentList[MarkCommandTest.INDEX_MARK_VALID - 1];
        TestTask markedTask = new TaskBuilder(taskToMark).withIsDone(true).build();
        assertMarkSuccess(MarkCommandTest.INDEX_MARK_VALID, MarkCommandTest.INDEX_MARK_VALID, markedTask);
    }

    public void assertDoneSuccess(String newDone) throws IllegalValueException {
        this.commandBox.runCommand(ListCommand.COMMAND_WORD);
        TestTask taskToUnmark = this.currentList[UnmarkCommandTest.INDEX_UNMARK_VALID - 1];
        this.commandBox.runCommand(UnmarkCommandTest.MESSAGE_MARK_COMMAND + UnmarkCommandTest.INDEX_UNMARK_VALID);
        this.commandBox.runCommand(newDone);
        TestTask unmarkedTask = new TaskBuilder(taskToUnmark).withIsDone(false).build();
        this.commandBox
                .runCommand(UnmarkCommandTest.MESSAGE_UNMARK_COMMAND + UnmarkCommandTest.INDEX_UNMARK_VALID_DONE);
        assertUnmarkSuccess(unmarkedTask);

    }

    private void assertMarkSuccess(int filteredTaskListIndex, int taskManagerIndex, TestTask markedTask) {

        this.commandBox.runCommand(MarkCommandTest.MESSAGE_MARK_COMMAND + filteredTaskListIndex);

        // confirm the list now contains all previous tasks plus the task with
        // updated isDone variable
        this.currentList[taskManagerIndex - 1] = markedTask;
        Arrays.sort(this.currentList, new TaskNameComparator());

        assertAllPanelsMatch(this.currentList);
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, markedTask));

    }

    private void assertUnmarkSuccess(TestTask unmarkedTask) {

        // confirm the list now contains all previous tasks plus the task with
        assertAllPanelsMatch(this.currentList);
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, unmarkedTask));
    }
}
