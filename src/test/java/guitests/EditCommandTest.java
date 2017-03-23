package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends ToDoListGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby v/91234567 s/bobby@gmail.com e/Block 123, Bobby Street 3 t/husband";
        int todoListIndex = 1;

        TestTask editedTask = new TaskBuilder().withTitle("Bobby").withVenue("91234567")
                .withStartTime("bobby@gmail.com").withEndTime("Block 123, Bobby Street 3")
                .withUrgencyLevel("1").withDescription("i love you").withTags("husband").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int todoListIndex = 2;

        TestTask taskToEdit = expectedTasksList[todoListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int todoListIndex = 2;

        TestTask taskToEdit = expectedTasksList[todoListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(todoListIndex, todoListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Tuition part-time job");

        String detailsToEdit = "Belle";
        int filteredTaskListIndex = 1;
        int todoListIndex = 5;

        TestTask taskToEdit = expectedTasksList[todoListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTitle("Belle").build();

        assertEditSuccess(filteredTaskListIndex, todoListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
//        commandBox.runCommand("edit 1 *&");
//        assertResultMessage(Title.MESSAGE_TITLE_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 v/abcd");
//        assertResultMessage(Venue.MESSAGE_VENUE_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 s/yahoo!!!");
//        assertResultMessage(StartTime.MESSAGE_STARTTIME_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 e/");
//        assertResultMessage(EndTime.MESSAGE_ENDTIME_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 t/*&");
//        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 Alice Pauline v/85355255 s/alice@gmail.com "
                                + "e/123, Jurong West Ave 6, #08-111 t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param ToDoListIndex index of task to edit in the address book.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int todoListIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTitle().title);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[todoListIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
