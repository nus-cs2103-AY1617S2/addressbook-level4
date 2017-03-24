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
    public void editTaskToEventSuccess() throws Exception {
        String detailsToEdit = "Bobby s/3:00AM 17/10/2011 e/6:00AM 17/11/2011 t/husband";
        int addressBookIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").withStartTime("3:00AM 17/10/2011").
                withEndTime("6:00AM 17/11/2011").withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }
    //dog = new TodoBuilder().withName("Walk the dog").withTags("petcare").build();
    //essay = new TodoBuilder().withName("Write essay").withEndTime("6:00PM 11/11/2017").build();
    //toilet = new TodoBuilder().withName("Go to the bathroom").withStartTime("12:00PM 11/11/2017")
    //.withEndTime("1:00PM 11/11/2017").build();
    @Test
    public void editTaskToDeadLineWithoutOtherChangeSuccess() throws Exception {
        String detailsToEdit = "e/6:00AM 17/11/2011";
        int addressBookIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Walk the dog").
                withEndTime("6:00AM 17/11/2011").withTags("petcare").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editDeadLineToTaskWithoutOtherChangeSuccess() throws Exception {
        String detailsToEdit = "e/";
        int addressBookIndex = 8;

        TestTodo editedTodo = new TodoBuilder().withName("Write essay").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editEventToTaskWithoutOtherChangeSuccess() throws Exception {
        String detailsToEdit = "s/ e/";
        int addressBookIndex = 9;

        TestTodo editedTodo = new TodoBuilder().withName("Go to the bathroom").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editTaskToEventWithoutOtherChangeSuccess() throws Exception {
        String detailsToEdit = "s/6:00AM 18/11/2011 e/6:00AM 17/11/2011";
        int addressBookIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Walk the dog").withStartTime("6:00AM 18/11/2011").
                withEndTime("6:00AM 17/11/2011").withTags("petcare").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editDeadLineToEventWithoutOtherChangeSuccess() throws Exception {
        String detailsToEdit = "s/6:00AM 18/11/2011 e/6:00AM 17/11/2011";
        int addressBookIndex = 8;

        TestTodo editedTodo = new TodoBuilder().withName("Write essay").withStartTime("6:00AM 18/11/2011").
                withEndTime("6:00AM 17/11/2011").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editEventToDeadLineWithoutOtherChangeSuccess() throws Exception {
        String detailsToEdit = "s/ e/1:00PM 11/11/2017";
        int addressBookIndex = 9;

        TestTodo editedTodo = new TodoBuilder().withName("Go to the bathroom").withEndTime("1:00PM 11/11/2017").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editTaskToDeadLineSuccess() throws Exception {
        String detailsToEdit = "Bobby e/6:00AM 17/11/2011 t/husband";
        int addressBookIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").
                withEndTime("6:00AM 17/11/2011").withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editDeadLineToEventSuccess() throws Exception {
        String detailsToEdit = "Bobby s/3:00AM 17/10/2011 e/6:00AM 17/11/2011 t/husband";
        int addressBookIndex = 8;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").withStartTime("3:00AM 17/10/2011").
                withEndTime("6:00AM 17/11/2011").withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editDeadLineToTaskSuccess() throws Exception {
        String detailsToEdit = "Bobby  t/husband";
        int addressBookIndex = 8;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editEventToTaskSuccess() throws Exception {
        String detailsToEdit = "Bobby t/husband";
        int addressBookIndex = 9;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editEventToDeadLineSuccess() throws Exception {
        String detailsToEdit = "Bobby e/6:00AM 17/11/2011 t/husband";
        int addressBookIndex = 9;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").
                withEndTime("6:00AM 17/11/2011").withTags("husband").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTodo);
    }
    @Test
    public void editAllFieldsSpecifiedEventSuccess() throws Exception {
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
        int todoListIndex = 2;

        TestTodo todoToEdit = expectedTodosList[todoListIndex - 1];
        TestTodo editedTodo = new TodoBuilder(todoToEdit).withName("Feed the cat").build();

        assertEditSuccess(filteredTodoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void edit_missingTodoIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTodoIndex_failure() {
        int invalidTodoIndex = expectedTodosList.length + 1;
        commandBox.runCommand("edit " + invalidTodoIndex + " Bobby");
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
        commandBox.runCommand("edit 2 Walk the dog t/petcare");
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
        assertTrue(todoListPanel.isListMatching(true, expectedTodosList));

        //edit result didn't show in display
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TODO_SUCCESS, editedTodo));
    }
}
