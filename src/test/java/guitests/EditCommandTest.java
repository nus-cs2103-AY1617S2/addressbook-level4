package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.tache.commons.core.Messages;
import seedu.tache.logic.commands.EditCommand;
import seedu.tache.logic.parser.EditCommandParser;
import seedu.tache.model.tag.Tag;
import seedu.tache.model.task.DateTime;
import seedu.tache.testutil.TaskBuilder;
import seedu.tache.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    //@@author A0142255M
    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "name Buy Eggs and Bread; end_date 01-04-17 19:55:12; tag HighPriority;";
        int taskManagerIndex = 3;
        TestTask editedTask = new TaskBuilder().withName("Buy Eggs and Bread")
                              .withEndDateTime("01-04-17 19:55:12")
                              .withTags("HighPriority").build();
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }
    //@@author

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "tag HighPriority;";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("HighPriority").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "tag ;";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    //@@author A0139925U
    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find friend");

        String detailsToEdit = "name Visit friends";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 4;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withName("Visit friends").build();

        assertEditWithSameFilterSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);

        commandBox.runCommand("find friends");

        detailsToEdit = "start_date 04-04-17; start_time 3pm";
        taskToEdit = expectedTasksList[taskManagerIndex - 1];
        editedTask = new TaskBuilder(taskToEdit).withStartDateTime("04-04-17 3pm").build();

        assertEditWithSameFilterSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
    }
    //@@author

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Project");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    //@@author A0139925U
    @Test
    public void edit_parameter_failure() {
        commandBox.runCommand("edit 1; chicken ccc;");
        assertResultMessage(EditCommandParser.MESSAGE_INVALID_PARAMETER);

        commandBox.runCommand("edit 1; chicken 02-02-17;");
        assertResultMessage(EditCommandParser.MESSAGE_INVALID_PARAMETER);
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit -1; name Project");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        commandBox.runCommand("edit 0; name Project");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        commandBox.runCommand("edit 8; name Project");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        commandBox.runCommand("edit 8a; name Project");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        commandBox.runCommand("edit A; name Project");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    //@@author

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage("Invalid command format! \n" + EditCommand.MESSAGE_USAGE);
    }

    //@@author A0139925U
    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1; start_date *&");
        assertResultMessage(DateTime.MESSAGE_DATE_CONSTRAINTS);

        commandBox.runCommand("edit 1; startdate *&");
        assertResultMessage(DateTime.MESSAGE_DATE_CONSTRAINTS);

        commandBox.runCommand("edit 1; sd *&");
        assertResultMessage(DateTime.MESSAGE_DATE_CONSTRAINTS);

        commandBox.runCommand("edit 1; end_date *&");
        assertResultMessage(DateTime.MESSAGE_DATE_CONSTRAINTS);

        commandBox.runCommand("edit 1; enddate *&");
        assertResultMessage(DateTime.MESSAGE_DATE_CONSTRAINTS);

        commandBox.runCommand("edit 1; ed *&");
        assertResultMessage(DateTime.MESSAGE_DATE_CONSTRAINTS);

        commandBox.runCommand("edit 1; tag *&;");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    //@@author A0142255M
    @Test
    public void edit_duplicateTask_failure() {

        commandBox.runCommand("edit 5; name Buy Eggs and Bread; end_date 04-01-17; end_time 19:55:12; "
                + "tag HighPriority;");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }
    //@@author

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

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskManagerIndex index of task to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */

    private void assertEditWithSameFilterSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                    String detailsToEdit, TestTask editedTask) {

        commandBox.runCommand("edit " + filteredTaskListIndex + "; " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        expectedTasksList[taskManagerIndex - 1] = editedTask;
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
