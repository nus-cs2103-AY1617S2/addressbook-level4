package guitests;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.EditCommand;
import seedu.task.model.tag.Tag;
import seedu.task.model.task.TaskName;
import seedu.task.testutil.TaskBuilder;
import seedu.task.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of persons in the person list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Go Hiking d/04012017 s/0600 e/2300 m/Bring lots of water.";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withTaskName("Go Hiking").withTaskStartTime("0600")
                .withTaskEndTime("2300").withTaskDate("04012017")
                .withTaskDescription("Bring lots of water.").withTags().build();
        commandBox.runCommand("clear");
        commandBox.runCommand(expectedTasksList[0].getAddCommand());

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie";
        int taskManagerIndex = 1;
        commandBox.runCommand("clear");
        commandBox.runCommand(expectedTasksList[taskManagerIndex - 1].getAddCommand());

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("sweetie").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int taskManagerIndex = 1;
        commandBox.runCommand("clear");
        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask beforeEdit = new TaskBuilder(taskToEdit).withTags("sweetie").build();
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        commandBox.runCommand(beforeEdit.getAddCommand());
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("clear");
        TestTask taskToEdit = expectedTasksList[01];
        commandBox.runCommand(taskToEdit.getAddCommand());
        commandBox.runCommand("find Buy");

        String detailsToEdit = "buy Orange";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 5;

        TestTask editedTask = new TaskBuilder(taskToEdit).withTaskName("buy Orange").build();

        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
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
        assertResultMessage(TaskName.MESSAGE_NAME_CONSTRAINTS);
        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex
     *            index of task to edit in filtered list
     * @param taskManagerIndex
     *            index of task to edit in the task manager. Must refer to the same task as
     *            {@code filteredTaskListIndex}
     * @param detailsToEdit
     *            details to edit the task with as input to the edit command
     * @param editedTask
     *            the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
            String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel
                .navigateToTask(editedTask.getTaskName().fullTaskName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous persons plus the person
        // with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        // assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
