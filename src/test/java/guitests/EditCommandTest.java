package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.EditCommand;
import seedu.tache.model.tag.Tag;
import seedu.tache.testutil.TaskBuilder;
import seedu.tache.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void editAllFieldsSpecifiedSuccess() throws Exception {
        String detailsToEdit = "name \"Buy Eggs and Bread\"; tag HighPriority;";
        int taskManagerIndex = 1;
        TestTask editedTask = new TaskBuilder().withName("Buy Eggs and Bread").withTags("HighPriority").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editNotAllFieldsSpecifiedSuccess() throws Exception {
        String detailsToEdit = "tag HighPriority;";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("HighPriority").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editClearTagsSuccess() throws Exception {
        String detailsToEdit = "tag ;";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editFindThenEditSuccess() throws Exception {
        commandBox.runCommand("find Grandma");

        String detailsToEdit = "name \"Visit Grandpa\"";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 3;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withName("Visit Grandpa").build();

        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editMissingTaskIndexFailure() {
        commandBox.runCommand("edit Project");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void editInvalidTaskIndexFailure() {
        commandBox.runCommand("edit 8; name \"Project\"");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void editNoFieldsSpecifiedFailure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void editInvalidValuesFailure() {
        commandBox.runCommand("edit 1; *&");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);

        commandBox.runCommand("edit 1; tag *&;");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void editDuplicateTaskFailure() {
        commandBox.runCommand("edit 3; name \"Buy Eggs and Bread\"; tag HighPriority;");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

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

        commandBox.runCommand("edit " + filteredTaskListIndex + "; " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
