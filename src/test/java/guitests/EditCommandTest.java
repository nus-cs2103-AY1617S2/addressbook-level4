package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TodoCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.Name;
import seedu.address.testutil.TestTodo;
import seedu.address.testutil.TodoBuilder;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TodoListGuiTest {

    // The list of todos in the todo list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTodo[] expectedTodosList = td.getTypicalTodos();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby t/husband";
        int addressBookIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int addressBookIndex = 2;

        TestTodo todoToEdit = expectedTodosList[addressBookIndex - 1];
        TestTodo editedTodo = new TodoBuilder(todoToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int addressBookIndex = 2;
        //commandBox.runCommand("edit " + "1" + " t/aaa" );
        TestTodo todoToEdit = expectedTodosList[addressBookIndex - 1];
        TestTodo editedTodo = new TodoBuilder(todoToEdit).withTags().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find cat");

        String detailsToEdit = "Feed the cat";
        int filteredTodoListIndex = 1;
        int addressBookIndex = 5;

        TestTodo todoToEdit = expectedTodosList[addressBookIndex - 1];
        TestTodo editedTodo = new TodoBuilder(todoToEdit).withName("Feed the cat").build();

        assertEditSuccess(filteredTodoListIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void edit_missingTodoIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTodoIndex_failure() {
        commandBox.runCommand("edit 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTodo_failure() {
        commandBox.runCommand("edit 1 Walk the dog t/petcare");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TODO);
    }

    /**
     * Checks whether the edited todo has the correct updated details.
     *
     * @param filteredTodoListIndex index of todo to edit in filtered list
     * @param addressBookIndex index of todo to edit in the address book.
     *      Must refer to the same todo as {@code filteredTodoListIndex}
     * @param detailsToEdit details to edit the todo with as input to the edit command
     * @param editedTodo the expected todo after editing the todo's details
     */
    private void assertEditSuccess(int filteredTodoListIndex, int addressBookIndex,
                                    String detailsToEdit, TestTodo editedTodo) {

        commandBox.runCommand("edit " + filteredTodoListIndex + " " + detailsToEdit);
        //new java.util.Scanner(System.in).nextLine();
        // confirm the new card contains the right data
        TodoCardHandle editedCard = todoListPanel.navigateToTodo(editedTodo.getName().fullName);
        assertMatching(editedTodo, editedCard);

        // confirm the list now contains all previous todos plus the todo with updated details
        expectedTodosList[addressBookIndex - 1] = editedTodo;
        assertTrue(todoListPanel.isListMatching(expectedTodosList));

        //edit result didn't show in display
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TODO_SUCCESS, editedTodo));
    }
}
