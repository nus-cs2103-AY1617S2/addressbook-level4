package guitests;

import static org.junit.Assert.assertTrue;
import static werkbook.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import werkbook.task.commons.core.Messages;
import werkbook.task.logic.commands.EditCommand;
import werkbook.task.model.tag.Tag;
import werkbook.task.model.task.EndDateTime;
import werkbook.task.model.task.Name;
import werkbook.task.model.task.StartDateTime;
import werkbook.task.testutil.TaskBuilder;
import werkbook.task.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskListGuiTest {

    // The list of tasks in the task list panel is expected to match this
    // list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTaskList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Walk the dog d/Take Zelda on a walk around the park "
                + "s/01/01/2016 0900 e/01/01/2016 1000 t/Incomplete";
        int taskListIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Walk the dog")
                .withDescription("Take Zelda on a walk around the park")
                .withStartDateTime("01/01/2016 0900")
                .withEndDateTime("01/01/2016 1000").withTags("Incomplete").build();

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
        commandBox.runCommand("find fish");

        String detailsToEdit = "Walk the goldfish";
        int filteredTaskListIndex = 1;
        int taskListIndex = 5;

        TestTask taskToEdit = expectedTaskList[taskListIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withName("Walk the goldfish").build();

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
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        // commandBox.runCommand("edit 1 d/abcd");
        // assertResultMessage(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        commandBox.runCommand("edit 1 s/yahoo!!!");
        assertResultMessage(StartDateTime.MESSAGE_START_DATETIME_CONSTRAINTS);

        commandBox.runCommand("edit 1 e/");
        assertResultMessage(EndDateTime.MESSAGE_END_DATETIME_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 Walk the dog d/Take Zelda on a walk at the park"
                + "s/01/01/2016 0900 e/01/01/2016 1000 t/Important");
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskListIndex index of task to edit in the task list. Must refer
     *            to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit
     *            command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskListIndex, String detailsToEdit,
            TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().toString());
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task
        // with updated details
        expectedTaskList[taskListIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTaskList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
