package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.geekeep.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.geekeep.commons.core.Messages;
import seedu.geekeep.logic.commands.UpdateCommand;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Title;
import seedu.geekeep.testutil.TaskBuilder;
import seedu.geekeep.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class UpdateCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskManagerIndex index of task to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("update " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTitle().fullTitle);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UpdateCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));

    }

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit
            = "Bobby s/01-04-17 1630 e/01-05-17 1630 l/Block 123, Bobby Street 3 t/husband";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withTitle("Bobby")
                .withEndDateTime("01-05-17 1630")
                .withStartDateTime("01-04-17 1630")
                .withLocation("Block 123, Bobby Street 3")
                .withTags("husband").build();

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
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("update 3 Alice Pauline s/01-04-17 1630 e/01-05-17 1630 "

                                + "l/123, Jurong West Ave 6, #08-111 t/friends");
        assertResultMessage(UpdateCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Elle");

        String detailsToEdit = "Belle";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 5;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTitle("Belle").build();

        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("update 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("update 1 *&");
        assertResultMessage(Title.MESSAGE_TITLE_CONSTRAINTS);

        commandBox.runCommand("update 1 e/abcd");
        assertResultMessage(DateTime.MESSAGE_DATETIME_CONSTRAINTS);

        commandBox.runCommand("update 1 s/yahoo!!!");
        assertResultMessage(DateTime.MESSAGE_DATETIME_CONSTRAINTS);

        commandBox.runCommand("update 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("update Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("update 1");
        assertResultMessage(UpdateCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }
}
