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
public class UpdateCommandTest extends GeeKeepGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    private TestTask[] expectedTasksList = td.getTypicalTasks();

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param geeKeepIndex index of task to edit in GeeKeep.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertUpdateSuccess(int filteredTaskListIndex, int geeKeepIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("update " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTitle().title);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[geeKeepIndex - 1] = editedTask;

        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, editedTask));

    }

    @Test
    public void update_allFieldsSpecified_success() throws Exception {
        String detailsToEdit
            = "Smile s/01-04-17 1630 e/01-05-17 1630 d/Block 123, Bobby Street 3 t/husband";
        int geeKeepIndex = 1;

        TestTask editedTask = new TaskBuilder().withTitle("Smile")
                .withEndDateTime("01-05-17 1630")
                .withStartDateTime("01-04-17 1630")
                .withDescription("Block 123, Bobby Street 3")
                .withTags("husband").build();

        assertUpdateSuccess(geeKeepIndex, geeKeepIndex, detailsToEdit, editedTask);
    }

    @Test
    public void update_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int geeKeepIndex = 2;

        TestTask taskToEdit = expectedTasksList[geeKeepIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertUpdateSuccess(geeKeepIndex, geeKeepIndex, detailsToEdit, editedTask);
    }

    @Test
    public void update_duplicateTask_failure() {
        commandBox.runCommand("update 3 Dance Camp s/01-04-17 1630 e/01-05-17 1630 "

                                + "d/123, Jurong West Ave 6, #08-111 t/friends");
        assertResultMessage(UpdateCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void update_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Hackathon");

        String detailsToEdit = "New Event";
        int filteredTaskListIndex = 1;
        int geeKeepIndex = 5;

        TestTask taskToEdit = expectedTasksList[geeKeepIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTitle("New Event").build();

        assertUpdateSuccess(filteredTaskListIndex, geeKeepIndex, detailsToEdit, editedTask);
    }

    @Test
    public void update_invalidTaskIndex_failure() {
        commandBox.runCommand("update 8 Random Event");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void update_invalidValues_failure() {
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
    public void update_missingTaskIndex_failure() {
        commandBox.runCommand("update Random Event");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }

    @Test
    public void update_noFieldsSpecified_failure() {
        commandBox.runCommand("update 1");
        assertResultMessage(UpdateCommand.MESSAGE_NOT_UPDATED);
    }

    @Test
    public void update_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int geeKeepIndex = 2;

        TestTask taskToEdit = expectedTasksList[geeKeepIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("sweetie", "bestie").build();

        assertUpdateSuccess(geeKeepIndex, geeKeepIndex, detailsToEdit, editedTask);
    }
}
