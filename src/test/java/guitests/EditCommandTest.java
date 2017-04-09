package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskit.commons.core.Messages;
import seedu.taskit.logic.commands.EditCommand;
import seedu.taskit.model.tag.Tag;
import seedu.taskit.model.task.Date;
import seedu.taskit.model.task.Priority;
import seedu.taskit.testutil.TaskBuilder;
import seedu.taskit.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends AddressBookGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    //@@author A0163996J

    public void sortTasksList() {
        Arrays.sort(expectedTasksList);
    }//@@author

    //@@author A0141872E

    @Test
    public void edit_title_success() throws Exception {
        sortTasksList();
        String detailsToEdit = "title Do Homework";
        int taskManagerIndex = 3;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTitle("Do Homework").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_tag_success() throws Exception {
        sortTasksList();
        String detailsToEdit = "tag school";
        int taskManagerIndex = 3;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("school").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_multipleFields_Success() throws Exception {
        String detailsToEdit = "title Do Homework from 4pm to 6pm priority low tag assignment";
        int taskManagerIndex = 3;

        TestTask editedTask = new TaskBuilder().withTitle("Do Homework").withStart("4pm").withEnd("6pm").withPriority("low").withTags("assignment").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_multiTags_success() throws Exception {
        sortTasksList();
        String detailsToEdit = "tag school tag work tag assignment";
        int taskManagerIndex = 3;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("school","work","assignment").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        sortTasksList();
        String detailsToEdit = "tag null";
        int taskManagerIndex = 5;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    //@@author A0163996J
    @Test
    public void edit_priority_success() throws Exception {
        sortTasksList();
        String detailsToEdit = "priority is high";
        int taskManagerIndex = 4;
        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withPriority("high").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_dateTime_success() throws Exception {
        sortTasksList();
        String detailsToEdit = "to 4 pm";
        int taskManagerIndex = 5;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withEnd("4pm").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_dateToday_success() throws Exception {
        sortTasksList();
        String detailsToEdit = "to today";
        int taskManagerIndex = 4;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withEnd("today").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }//@@author

    //@@author A0141872E
    @Test
    public void edit_dateToNull_success() throws Exception {
        sortTasksList();
        String detailsToEdit = "to null";
        int taskManagerIndex = 5;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_Keyword_success() throws Exception {
        sortTasksList();
        String detailsToEdit = "title \"today\" tag school";
        int taskManagerIndex = 5;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTitle("today").withTags("school").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        sortTasksList();
        commandBox.runCommand("find Do HW 1");

        String detailsToEdit = "tag Homework";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("Homework").build();

        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_failure() {
        sortTasksList();
        commandBox.runCommand("edit 1 ");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        sortTasksList();
        commandBox.runCommand("edit title Homework due");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        sortTasksList();
        commandBox.runCommand("edit 99 title Homework due");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        sortTasksList();
        commandBox.runCommand("edit ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidValues_failure() {
        sortTasksList();

        commandBox.runCommand("edit 1 tag *&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);

        commandBox.runCommand("edit 1 from day");
        assertResultMessage(Date.MESSAGE_DATE_FAIL);

        commandBox.runCommand("edit 1 to day");
        assertResultMessage(Date.MESSAGE_DATE_FAIL);

        commandBox.runCommand("edit 1 priority important");
        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        sortTasksList();
        commandBox.runCommand("edit 3 title Do HW 1");
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
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTitle().title);
        assertMatching(editedTask, editedCard);

        sortTasksList();

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        sortTasksList();
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
