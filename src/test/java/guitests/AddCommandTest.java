package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TodoCardHandle;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.AddCommand;
import seedu.todolist.testutil.TestTodo;
import seedu.todolist.testutil.TestUtil;

public class AddCommandTest extends TodoListGuiTest {

    @Test
    public void add() {
        //add one todo
        TestTodo[] currentList = td.getTypicalTodos();
        TestTodo todoToAdd = td.laundry;

        assertAddSuccess(todoToAdd, currentList);
        currentList = TestUtil.addTodosToList(currentList, todoToAdd);

        //add another todo
        todoToAdd = td.shopping;
        assertAddSuccess(todoToAdd, currentList);
        currentList = TestUtil.addTodosToList(currentList, todoToAdd);

        //add duplicate todo
        commandBox.runCommand(td.laundry.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TODO);
        assertTrue(todoListPanel.isListMatching(true, currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.dog);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

    }

    @Test
    public void addEventTest() {
        TestTodo[] currentList = td.getTypicalTodos();
        TestTodo todoToAdd = td.lunch;
        assertAddSuccess(todoToAdd, currentList);
    }

    @Test
    public void addDeadLineTest() {
        TestTodo[] currentList = td.getTypicalTodos();
        TestTodo todoToAdd = td.job;
        assertAddSuccess(todoToAdd, currentList);
    }

    private void assertAddSuccess(TestTodo todoToAdd, TestTodo... currentList) {

        commandBox.runCommand(todoToAdd.getAddCommand());

        //confirm the new card contains the right data
        TodoCardHandle addedCard = todoListPanel.navigateToTodo(todoToAdd.getName().fullName);
        assertMatching(todoToAdd, addedCard);

        //confirm the list now contains all previous todos plus the new todo
        TestTodo[] expectedList = TestUtil.addTodosToList(currentList, todoToAdd);
        assertTrue(todoListPanel.isListMatching(true, expectedList));
    }

}
