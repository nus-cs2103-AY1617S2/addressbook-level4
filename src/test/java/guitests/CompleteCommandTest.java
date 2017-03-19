package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CompleteCommand.MESSAGE_COMPLETE_TODO_SUCCESS;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.CompleteCommand;
import seedu.address.model.TodoList;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;
import seedu.address.testutil.TestTodo;
import seedu.address.testutil.TestUtil;

public class CompleteCommandTest extends TodoListGuiTest {

    /**
     * The list of todos in the todo list panel is expected to match this list
     */
    private TestTodo[] originalList;
    private TestTodo[] currentList;
    private Date timeOfCompletion;

    @Before
    public void setUp() {
        originalList = td.getTypicalTodos();
        currentList = TestUtil.addTodosToList(originalList, td.job, td.lunch);
        try {
            timeOfCompletion = StringUtil.parseDate("7:11PM 19/03/2017", CompleteCommand.COMPLETE_TIME_FORMAT);
        } catch (IllegalValueException e) {
        }
    }

    @Override
    protected TodoList getInitialData() {
        TodoList ab = super.getInitialData();
        try {
            ab.addTodo(new Todo(td.job));
            ab.addTodo(new Todo(td.lunch));
        } catch (UniqueTodoList.DuplicateTodoException e) {
            e.printStackTrace();
        }
        return ab;
    }

    @Test
    public void complete_validFloatingTodoNoTimeSpecified_success() {
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
    public void complete_validFloatingTodoTimeSpecified_success() {
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
    public void complete_alreadyCompleted_failure() {
        commandBox.runCommand("complete 1");
        commandBox.runCommand("complete 1");
        assertResultMessage("This todo is already complete");
    }

    @Test
    public void complete_invalidTime_failure() {
        commandBox.runCommand("complete 1 invalidDateString");
        assertResultMessage("Date must be entered as: " + CompleteCommand.COMPLETE_TIME_FORMAT);
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
        assertResultMessage("The todo index provided is invalid");
    }

    /**
     * Runs the complete command to complete the todo at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to complete the first todo in the list,
     * @param currentList A copy of the current list of todos (before completion).
     */
    private void assertCompleteSuccess(int targetIndexOneIndexed, final TestTodo[] currentList) {
        TestTodo[] completedTodoList = TestUtil.completeTodoInList(currentList, targetIndexOneIndexed, new Date());

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
            Date completeTime, final TestTodo[] currentList) {
        TestTodo todoToComplete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTodo[] completedTodoList = TestUtil.completeTodoInList(currentList, targetIndexOneIndexed, completeTime);

        commandBox.runCommand("complete " + targetIndexOneIndexed + " "
                + new SimpleDateFormat(CompleteCommand.COMPLETE_TIME_FORMAT).format(completeTime));

        //confirm the list now shows the todo as completed
        assertTrue(todoListPanel.isListMatching(true, completedTodoList));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETE_TODO_SUCCESS, todoToComplete));
    }
}
