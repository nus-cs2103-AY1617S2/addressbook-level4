package guitests;

import static org.junit.Assert.assertTrue;

import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;

import static seedu.todolist.logic.commands.CompleteCommand.MESSAGE_COMPLETE_TODO_SUCCESS;

import org.junit.Before;
import org.junit.Test;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.testutil.TestTodo;
import seedu.todolist.testutil.TestUtil;

public class CompleteCommandTest extends TodoListGuiTest {
    //@@author A0163786N
    /**
     * The list of todos in the todo list panel is expected to match this list
     */
    private TestTodo[] currentList;
    private String timeOfCompletion = "7:11PM 19/03/17";

    @Before
    public void setUp() {
        currentList = td.getTypicalTodos();
    }

    @Test
    public void complete_validFloatingTaskNoTimeSpecified_success() {
        assertCompleteSuccess(1, currentList);
    }

    @Test
    public void complete_validDeadlineNoTimeSpecified_success() {

        assertCompleteSuccess(8, currentList);
    }

    @Test
    public void complete_validEventNoTimeSpecified_success() {
        assertCompleteSuccess(9, currentList);
    }

    @Test
    public void complete_validFloatingTaskTimeSpecified_success() {
        assertCompleteSuccess(1, timeOfCompletion, currentList);
    }

    @Test
    public void complete_validDeadlineTimeSpecified_success() {
        assertCompleteSuccess(8, timeOfCompletion, currentList);
    }

    @Test
    public void complete_validEventTimeSpecified_success() {
        assertCompleteSuccess(9, timeOfCompletion, currentList);
    }

    @Test
    public void complete_alreadyCompletedFloatingTask_failure() {
        commandBox.runCommand("complete 10");
        assertResultMessage("This todo is already complete");
    }

    @Test
    public void complete_alreadyCompletedDeadline_failure() {
        commandBox.runCommand("complete 11");
        assertResultMessage("This todo is already complete");
    }

    @Test
    public void complete_alreadyCompletedEvent_failure() {
        commandBox.runCommand("complete 12");
        assertResultMessage("This todo is already complete");
    }

    @Test
    public void complete_invalidTime_failure() {
        commandBox.runCommand("complete 1 invalidDateString");
        assertResultMessage("Date must be entered as: " + DATE_FORMAT);
    }

    @Test
    public void complete_invalidCommand_failure() {
        commandBox.runCommand("completes 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void complete_invalidIndex_failure() {
        int invalidIndex = currentList.length + 1;
        commandBox.runCommand("complete " + invalidIndex);
        assertResultMessage(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    /**
     * Runs the complete command to complete the todo at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to complete the first todo in the list,
     * @param currentList A copy of the current list of todos (before completion).
     */
    private void assertCompleteSuccess(int targetIndexOneIndexed, final TestTodo[] currentList) {
        TestTodo[] completedTodoList = TestUtil.completeTodoInList(currentList, targetIndexOneIndexed,
            timeOfCompletion);

        commandBox.runCommand("complete " + targetIndexOneIndexed);

        //confirm the list now shows the todo as completed
        assertTrue(todoListPanel.isListMatching(false, completedTodoList));
    }

    /**
     * Runs the complete command to complete the todo at specified index and time and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to complete the first todo in the list,
     * @param completeTime Time that todo is to be completed;
     * @param currentList A copy of the current list of todos (before completion).
     */
    private void assertCompleteSuccess(int targetIndexOneIndexed,
            String completeTime, final TestTodo[] currentList) {
        TestTodo todoToComplete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTodo[] completedTodoList = TestUtil.completeTodoInList(currentList, targetIndexOneIndexed, completeTime);

        commandBox.runCommand("complete " + targetIndexOneIndexed + " " + completeTime);

        //confirm the list now shows the todo as completed
        assertTrue(todoListPanel.isListMatching(true, completedTodoList));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TODO_SUCCESS, todoToComplete));
    }
}
