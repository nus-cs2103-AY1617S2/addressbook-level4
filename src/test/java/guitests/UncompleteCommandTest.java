package guitests;

//import static org.junit.Assert.assertTrue;
//import static seedu.address.logic.commands.UncompleteCommand.MESSAGE_UNCOMPLETE_TODO_SUCCESS;
//
//import java.util.Date;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.commons.util.StringUtil;
//import seedu.address.logic.commands.CompleteCommand;
//import seedu.address.model.TodoList;
//import seedu.address.model.todo.Todo;
//import seedu.address.model.todo.UniqueTodoList;
//import seedu.address.testutil.TestTodo;
//import seedu.address.testutil.TestUtil;

public class UncompleteCommandTest extends TodoListGuiTest {
//    //@@author A0163786N
//    /**
//     * The list of todos in the todo list panel is expected to match this list
//     */
//    private TestTodo[] currentList;
//
//    @Before
//    public void setUp() {
//        currentList = TestUtil.addTodosToList(td.getTypicalTodos(), td.job, td.lunch);
//    }
//
//    @Override
//    protected TodoList getInitialData() {
//        TodoList ab = super.getInitialData();
//        try {
//            ab.addTodo(new Todo(td.job));
//            ab.addTodo(new Todo(td.lunch));
//        } catch (UniqueTodoList.DuplicateTodoException e) {
//            e.printStackTrace();
//        }
//        return ab;
//    }
//
//    @Test
//    public void uncomplete_validFloatingTodoNoTimeSpecified_success() {
//        assertCompleteSuccess(1, currentList);
//    }
//
//    @Test
//    public void complete_validDeadlineNoTimeSpecified_success() {
//
//        assertCompleteSuccess(8, currentList);
//    }
//
//    @Test
//    public void complete_validEventNoTimeSpecified_success() {
//        assertCompleteSuccess(9, currentList);
//    }
//
//    @Test
//    public void complete_validFloatingTodoTimeSpecified_success() {
//        assertCompleteSuccess(1, timeOfCompletion, currentList);
//    }
//
//    @Test
//    public void complete_validDeadlineTimeSpecified_success() {
//        assertCompleteSuccess(8, timeOfCompletion, currentList);
//    }
//
//    @Test
//    public void complete_validEventTimeSpecified_success() {
//        assertCompleteSuccess(9, timeOfCompletion, currentList);
//    }
//
//    @Test
//    public void complete_alreadyCompleted_failure() {
//        commandBox.runCommand("complete 1");
//        commandBox.runCommand("complete 1");
//        assertResultMessage("This todo is already complete");
//    }
//
//    @Test
//    public void complete_invalidTime_failure() {
//        commandBox.runCommand("complete 1 invalidDateString");
//        assertResultMessage("Date must be entered as: " + CompleteCommand.COMPLETE_TIME_FORMAT);
//    }
//
//    @Test
//    public void complete_invalidCommand_failure() {
//        commandBox.runCommand("completes 1");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    @Test
//    public void complete_invalidIndex_failure() {
//        int invalidIndex = currentList.length + 1;
//        commandBox.runCommand("complete " + invalidIndex);
//        assertResultMessage("The todo index provided is invalid");
//    }
//
//    /**
//     * Runs the uncomplete command to uncomplete the todo at specified index and confirms the result is correct.
//     * @param targetIndexOneIndexed e.g. index 1 to uncomplete the first todo in the list,
//     * @param currentList A copy of the current list of todos (before completion).
//     */
//    private void assertUncompleteSuccess(int targetIndexOneIndexed, final TestTodo[] currentList) {
//        TestTodo[] uncompletedTodoList = TestUtil.uncompleteTodoInList(currentList, targetIndexOneIndexed);
//
//        commandBox.runCommand("uncomplete " + targetIndexOneIndexed);
//
//        //confirm the list now shows the todo as completed
//        assertTrue(todoListPanel.isListMatching(false, uncompletedTodoList));
//    }
}
