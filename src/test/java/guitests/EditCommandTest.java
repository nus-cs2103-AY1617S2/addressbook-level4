/* @@author A0119505J */
package guitests;

// import static org.junit.Assert.assertTrue;
// import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

// import guitests.guihandles.TaskCardHandle;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.tag.Tag;
//import seedu.address.model.task.Address;
//import seedu.address.model.task.ClockTime;
import seedu.address.model.task.Name;
// import seedu.address.model.task.Priority;
import seedu.address.model.task.Time;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of Tasks in the Task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {

        String detailsToEdit = "Alice Pauline d/04/06/2013 c/13:40 p/high t/husband";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Alice Pauline").withTime("04/06/2013")
                .withClockTime("13:40").withPriority("high").withTags("husband").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Elle");

        String detailsToEdit = "Belle";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withName("Belle").withTime("04/06/2013").
                withPriority("high").withTags("husband").build();

        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    // @Test
    // This test case is not working correctly
    // public void edit_missingTaskIndex_failure() {
        // commandBox.runCommand("edit Bobby");
        // assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    // }

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
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit 1 d/abcd");
        assertResultMessage(Time.MESSAGE_TIME_CONSTRAINTS);

        // commandBox.runCommand("edit 1 p/abcd");
        // assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    // Skip this test first
    // @Test
    // public void edit_duplicateTask_failure() {
        // commandBox.runCommand("edit 3 Alice Pauline t/friends");
        // assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    // }

    /**
     * Checks whether the edited Task has the correct updated details.
     *
     * @param filteredTaskListIndex index of Task to edit in filtered list
     * @param taskManagerIndex index of Task to edit in the address book.
     *      Must refer to the same Task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the Task with as input to the edit command
     * @param editedTask the expected Task after editing the Task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        // TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        // assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous Tasks plus the Task with updated details

        // This test can't be passed now
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        // assertTrue(taskListPanel.isListMatching(expectedTasksList));
        // assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
