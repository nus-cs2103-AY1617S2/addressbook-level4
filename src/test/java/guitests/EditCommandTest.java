package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.EditCommand;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.Description;
import seedu.task.testutil.TaskBuilder;
import seedu.task.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskListGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTaskList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby t/husband";
        int taskListIndex = 1;

        TestTask editedTask = new TaskBuilder().withDescription("Bobby").withTags("husband").build();

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int taskListIndex = 2;

        TestTask taskToEdit = expectedTaskList[taskListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int taskListIndex = 2;

        TestTask taskToEdit = expectedTaskList[taskListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(taskListIndex, taskListIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find Fish");

        String detailsToEdit = "Walk the fishies";
        int filteredTaskListIndex = 1;
        int taskListIndex = 5;

        TestTask taskToEdit = expectedTaskList[taskListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withDescription("Walk the fishies").build();

        assertEditSuccess(filteredTaskListIndex, taskListIndex, detailsToEdit, editedTask);
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

        commandBox.runCommand("edit 1 t/*&");

        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 Walk the bear t/urgent");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited tasks has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskListIndex index of task to edit in the task list.
     *      Must refer to the same task as {@code filteredPersonListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskListIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        PersonCardHandle editedCard = taskListPanel.navigateToPerson(editedTask.getDescription().description);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        expectedTaskList[taskListIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTaskList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
