package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.testutil.TestTodo;
import seedu.todolist.testutil.TestUtil;

public class UncompleteCommandTest extends TodoListGuiTest {
    //@@author A0163786N
    /**
     * The list of todos in the todo list panel is expected to match this list
     */
    private TestTodo[] currentList;

    @Before
    public void setUp() {
        currentList = td.getTypicalTodos();
    }

    @Test
    public void ununcomplete_validFloatingTodo_success() {
        assertUncompleteSuccess(10, currentList);
    }

    @Test
    public void uncomplete_validDeadline_success() {
        assertUncompleteSuccess(11, currentList);
    }

    @Test
    public void uncomplete_validEvent_success() {
        assertUncompleteSuccess(12, currentList);
    }

    @Test
    public void uncomplete_notCompletedFloatingTask_failure() {
        commandBox.runCommand("uncomplete 7");
        assertResultMessage("This todo is not complete");
    }

    @Test
    public void uncomplete_notCompletedDeadline_failure() {
        commandBox.runCommand("uncomplete 8");
        assertResultMessage("This todo is not complete");
    }

    @Test
    public void uncomplete_notCompletedEvent_failure() {
        commandBox.runCommand("uncomplete 9");
        assertResultMessage("This todo is not complete");
    }

    @Test
    public void uncomplete_invalidCommand_failure() {
        commandBox.runCommand("uncompletes 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void uncomplete_invalidIndex_failure() {
        int invalidIndex = currentList.length + 1;
        commandBox.runCommand("uncomplete " + invalidIndex);
        assertResultMessage(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    /**
     * Runs the uncomplete command to uncomplete the todo at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to uncomplete the first todo in the list,
     * @param currentList A copy of the current list of todos (before uncompletion).
     */
    private void assertUncompleteSuccess(int targetIndexOneIndexed, final TestTodo[] currentList) {
        TestTodo[] uncompletedTodoList = TestUtil.uncompleteTodoInList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("uncomplete " + targetIndexOneIndexed);

        //confirm the list now shows the todo as uncompleted
        assertTrue(todoListPanel.isListMatching(false, uncompletedTodoList));
    }
}
