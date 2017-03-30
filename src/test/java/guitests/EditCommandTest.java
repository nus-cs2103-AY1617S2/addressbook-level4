package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.watodo.commons.core.Messages;
import seedu.watodo.logic.commands.EditCommand;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.Description;
import seedu.watodo.testutil.TaskBuilder;
import seedu.watodo.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby p/91234567 e/bobby@gmail.com a/Block 123, Bobby Street 3 t/husband";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withDescription("Bobby")
                .withTags("husband").build();

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
        int taskManagerIndex = 5;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withDescription("Belle").build();

        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
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
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        commandBox.runCommand("edit 1 p/abcd");
        assertResultMessage(DateTime.MESSAGE_DATETIME_CONSTRAINTS);


        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 Alice Pauline p/85355255 e/alice@gmail.com "
                                + "a/123, Jurong West Ave 6, #08-111 t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskManagerIndex index of person to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getDescription().fullDescription);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
