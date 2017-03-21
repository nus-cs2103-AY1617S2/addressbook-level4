package guitests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby t/husband";
        String taskManagerIndex = "F1";

        TestTask editedTask = new TaskBuilder().withName("Bobby")
                .withTags("husband").build();

        assertEditSuccess(taskManagerIndex, detailsToEdit,
                editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        String taskManagerIndex = "F2";

        TestTask taskToEdit = expectedTasksList[1];
        TestTask editedTask = new TaskBuilder(taskToEdit)
                .withTags("sweetie", "bestie").build();

        assertEditSuccess(taskManagerIndex, detailsToEdit,
                editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        String taskManagerIndex = "F2";

        TestTask taskToEdit = expectedTasksList[1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(taskManagerIndex, detailsToEdit,
                editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find CS2106");

        String detailsToEdit = "Complete CS2103 Lab Assignment";
        String filteredTaskListIndex = "F5";

        TestTask taskToEdit = expectedTasksList[4];
        TestTask editedTask = new TaskBuilder(taskToEdit)
                .withName("Complete CS2103 Lab Assignment").build();

        assertEditSuccess(filteredTaskListIndex,
                detailsToEdit, editedTask);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit F8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit F1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit F1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit F1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit F3 Do math assignment t/math");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex
     *            index of task to edit in filtered list
     * @param taskManagerIndex
     *            index of task to edit in the task manager. Must refer to the
     *            same task as {@code filteredTaskListIndex}
     * @param detailsToEdit
     *            details to edit the task with as input to the edit command
     * @param editedTask
     *            the expected task after editing the task's details
     * @throws IllegalValueException
     * @throws IllegalArgumentException
     */
    private void assertEditSuccess(String filteredTaskListIndex,
            String detailsToEdit, TestTask editedTask)
            throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(
                "edit " + filteredTaskListIndex + " " + detailsToEdit);

        /* confirm the new card contains the right data
         * TODO if edited task has a differnt deadline (e.g changed from future to today),
         * the following part will fail
         * 
         */ 
        TaskCardHandle editedCard = futureTaskListPanel
                .navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        /* confirm the list now contains all previous tasks plus the task with
         * updated details
         *
         * TODO This test is disabled since edited task may have a deadline that is 
         * different from its previous one which results in itself being displayed on
         * a different panel
         *
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        assertTrue(futureTaskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String
                .format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTask));
        */
    }
}
