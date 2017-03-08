package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.todo.ReadOnlyTodo;

public class SelectCommandTest extends TodoListGuiTest {

    /*
    @Test
    public void selectTodo_nonEmptyList() {

        assertSelectionInvalid(10); // invalid index
        assertNoTodoSelected();

        assertSelectionSuccess(1); // first todo in the list
        int todoCount = td.getTypicalTodos().length;
        assertSelectionSuccess(todoCount); // last todo in the list
        int middleIndex = todoCount / 2;
        assertSelectionSuccess(middleIndex); // a todo in the middle of the list

        assertSelectionInvalid(todoCount + 1); // invalid index
        assertTodoSelected(middleIndex); // assert previous selection remains


    }
	*/
    @Test
    public void selectTodo_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The todo index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Todo: " + index);
        assertTodoSelected(index);
    }

    private void assertTodoSelected(int index) {
        assertEquals(todoListPanel.getSelectedTodos().size(), 1);
        ReadOnlyTodo selectedTodo = todoListPanel.getSelectedTodos().get(0);
        assertEquals(todoListPanel.getTodo(index - 1), selectedTodo);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTodoSelected() {
        assertEquals(todoListPanel.getSelectedTodos().size(), 0);
    }

}
