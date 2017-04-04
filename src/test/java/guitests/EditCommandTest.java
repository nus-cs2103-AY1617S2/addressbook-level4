package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import guitests.guihandles.TodoCardHandle;
import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.AddCommand;
import seedu.todolist.logic.commands.EditCommand;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.todo.Name;
import seedu.todolist.testutil.TestTodo;
import seedu.todolist.testutil.TodoBuilder;

public class EditCommandTest extends TodoListGuiTest {

    // The list of todos in the todo list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTodo[] expectedTodosList = td.getTypicalTodos();

    @Test
    public void editAllFieldsSpecifiedSuccess() throws Exception {
        String detailsToEdit = "Bobby t/husband";
        int todoListIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").withTags("husband").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }
    //@@author A0165043M
    @Test
    public void editTaskToEventSuccess() throws Exception {
        String detailsToEdit = "Bobby s/3:00AM 17/10/11 e/6:00AM 17/11/11 t/husband";
        int todoListIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").withStartTime("3:00AM 17/10/11").
                withEndTime("6:00AM 17/11/11").withTags("husband").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editTaskToDeadLineWithoutOtherChangeSuccess() throws Exception {
        String detailsToEdit = "e/6:00AM 17/11/2011";
        int todoListIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Walk the dog").
                withEndTime("6:00AM 17/11/2011").withTags("petcare").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editDeadLineWithDefaultSuccess() throws Exception {
        String detailsToEdit = "e/";
        int todoListIndex = 8;

        TestTodo editedTodo = new TodoBuilder().withName("Write essay").
                withEndTime(getTodayPlusDaysMidnight(1)).build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editEventWithDefaultSuccess() throws Exception {
        String detailsToEdit = "s/ e/";
        int todoListIndex = 9;

        TestTodo editedTodo = new TodoBuilder().withName("Go to the bathroom").
                withStartTime(getTodayPlusDaysMidnight(0)).
                withEndTime(getTodayPlusDaysMidnight(1)).withTags("personal").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editTaskToEventWithoutOtherChangeSuccess() throws Exception {
        String detailsToEdit = "s/6:00AM 18/11/2011 e/6:00AM 17/11/2011";
        int todoListIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Walk the dog").withStartTime("6:00AM 18/11/2011").
                withEndTime("6:00AM 17/11/2011").withTags("petcare").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editDeadLineToEventWithoutOtherChangeSuccess() throws Exception {
        String detailsToEdit = "s/6:00AM 18/11/2011 e/6:00AM 17/11/2011";
        int todoListIndex = 8;

        TestTodo editedTodo = new TodoBuilder().withName("Write essay").withStartTime("6:00AM 18/11/2011").
                withEndTime("6:00AM 17/11/2011").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editTaskToDeadLineSuccess() throws Exception {
        String detailsToEdit = "Bobby e/6:00AM 17/11/11 t/husband";
        int todoListIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").
                withEndTime("6:00AM 17/11/11").withTags("husband").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editDeadLineToEventSuccess() throws Exception {
        String detailsToEdit = "Bobby s/3:00AM 17/10/11 e/6:00AM 17/11/11 t/husband";
        int todoListIndex = 8;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").withStartTime("3:00AM 17/10/11").
                withEndTime("6:00AM 17/11/11").withTags("husband").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editDeadLineToTaskSuccess() throws Exception {
        String detailsToEdit = " Bobby t/husband";
        int todoListIndex = 8;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").withTags("husband").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editEventToTaskSuccess() throws Exception {
        String detailsToEdit = "Bobby t/husband";
        int todoListIndex = 9;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").withTags("husband").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editEventToDeadLineSuccess() throws Exception {
        String detailsToEdit = "Bobby e/6:00AM 17/11/2011 t/husband";
        int todoListIndex = 9;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").
                withEndTime("6:00AM 17/11/11").withTags("husband").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editWithAddTagsSuccess() throws Exception {
        String detailsToEdit = " ta/husband";
        int todoListIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Walk the dog").withTags("petcare", "husband").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editWithTwoAddTagsSuccess() throws Exception {
        String detailsToEdit = "ta/husband ta/twoAddTags";
        int todoListIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Walk the dog").
                withTags("petcare", "husband", "twoAddTags").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }
    //@@author
    @Test
    public void editAllFieldsSpecifiedEventSuccess() throws Exception {
        String detailsToEdit = "Bobby t/husband";
        int todoListIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Bobby").withTags("husband").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editNotAllFieldsSpecifiedSuccess() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int todoListIndex = 2;

        TestTodo todoToEdit = expectedTodosList[todoListIndex - 1];
        TestTodo editedTodo = new TodoBuilder(todoToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editClearTagsSuccess() throws Exception {
        String detailsToEdit = "t/";
        int todoListIndex = 2;
        TestTodo todoToEdit = expectedTodosList[todoListIndex - 1];
        TestTodo editedTodo = new TodoBuilder(todoToEdit).withTags().build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }

    @Test
    public void editFindThenEditSuccess() throws Exception {
        commandBox.runCommand("find cat");

        String detailsToEdit = "Feed the cat";
        int filteredTodoListIndex = 1;
        int todoListIndex = 2;

        TestTodo todoToEdit = expectedTodosList[todoListIndex - 1];
        TestTodo editedTodo = new TodoBuilder(todoToEdit).withName("Feed the cat").build();

        assertEditSuccess(filteredTodoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }
    //@@author A0165043M
    @Test
    public void editWithOnlyDateSuccess() throws Exception {
        String detailsToEdit = "e/11/11/11";
        int todoListIndex = 1;

        TestTodo editedTodo = new TodoBuilder().withName("Walk the dog").withEndTime("12:00AM 11/11/11").
                withTags("petcare").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);

        commandBox.runCommand("undo");
        detailsToEdit = "s/11/11/11 e/11/11/11";
        todoListIndex = 1;

        editedTodo = new TodoBuilder().withName("Walk the dog").withStartTime("12:00AM 11/11/11").
                withEndTime("12:00AM 11/11/11").
                withTags("petcare").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTodo);
    }
    @Test
    public void editWithInvalidValuefailure() {
        commandBox.runCommand("edit 1 Walk the dog s/12 e/12");
        assertResultMessage(AddCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void editTimeOnlyFailure() {
        commandBox.runCommand("edit 1 s/12:00PM");
        assertResultMessage(AddCommand.MESSAGE_INVALID_TIME);
    }
    //@@author
    @Test
    public void editMissingTodoIndexFailure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }


    @Test
    public void editInvalidTodoIndexFailure() {
        int invalidTodoIndex = expectedTodosList.length + 1;
        commandBox.runCommand("edit " + invalidTodoIndex + " Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void editNoFieldsSpecifiedFailure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void editInvalidValuesFailure() {
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void editDuplicateTodoFailure() {
        commandBox.runCommand("edit 2 Walk the dog t/petcare");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TODO);
    }

    /**
     * Checks whether the edited todo has the correct updated details.
     *
     * @param filteredTodoListIndex index of todo to edit in filtered list
     * @param todoListIndex index of todo to edit in the todoList.
     *      Must refer to the same todo as {@code filteredTodoListIndex}
     * @param detailsToEdit details to edit the todo with as input to the edit command
     * @param editedTodo the expected todo after editing the todo's details
     */
    private void assertEditSuccess(int filteredTodoListIndex, int todoListIndex,
                                    String detailsToEdit, TestTodo editedTodo) {

        commandBox.runCommand("edit " + filteredTodoListIndex + " " + detailsToEdit);
        //new java.util.Scanner(System.in).nextLine();
        // confirm the new card contains the right data
        TodoCardHandle editedCard = todoListPanel.navigateToTodo(editedTodo.getName().fullName);
        assertMatching(editedTodo, editedCard);

        // confirm the list now contains all previous todos plus the todo with updated details
        expectedTodosList[todoListIndex - 1] = editedTodo;
        assertTrue(todoListPanel.isListMatching(true, expectedTodosList));

        //assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TODO_SUCCESS, editedTodo));
    }

    private String getTodayPlusDaysMidnight(int addDays) {
        Date dtAssign = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dtAssign);
        c.add(Calendar.DATE, addDays);
        dtAssign = c.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "12:00AM" + " " + dateFormat.format(dtAssign);
    }
}
