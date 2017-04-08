package guitests;

import static org.junit.Assert.assertTrue;
import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import typetask.commons.core.Messages;
import typetask.logic.commands.EditCommand;
import typetask.testutil.TaskBuilder;
import typetask.testutil.TestTask;
//@@author A0139926R
// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TypeTaskGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    private TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void editAllFieldsSpecifiedSuccess() throws Exception {
        String detailsToEdit = "Bobby ";
        int typeTaskIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Bobby").withDate("").withEndDate("")
                .withCompleted(false).withPriority("Low").build();

        assertEditSuccess(typeTaskIndex, typeTaskIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editNotAllFieldsSpecifiedSuccess() throws Exception {
        String detailsToEdit = "by:10/10/1993 p/High";
        int typeTaskIndex = 2;

        TestTask taskToEdit = expectedTasksList[typeTaskIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withDate("").withEndDate("Sun Oct 10 1993 23:59:59")
                .withCompleted(false).withPriority("High").build();

        assertEditSuccess(typeTaskIndex, typeTaskIndex, detailsToEdit, editedTask);
    }
    @Test
    public void editFindThenEditStartDateToInvalidSchedule_fail() {
        commandBox.runCommand("find George");
        String detailsToEdit = "edit 1 from: 12 Oct 1993";
        commandBox.runCommand(detailsToEdit);
        assertResultMessage(EditCommand.MESSAGE_INVALID_DATE);
    }
    @Test
    public void editFindThenEditToInvalidDeadline_fail() {
        commandBox.runCommand("find George");
        String detailsToEdit = "edit 1 by: LOL";
        commandBox.runCommand(detailsToEdit);
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE);
    }
    @Test
    public void editFindThenEditToInvalidDeadlineWithTimePrefix_fail() {
        commandBox.runCommand("find George");
        String detailsToEdit = "edit 1 @ LOL";
        commandBox.runCommand(detailsToEdit);
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE);
    }
    @Test
    public void editFindThenEditToInvalidStartDate_fail() {
        commandBox.runCommand("find George");
        String detailsToEdit = "edit 1 from: LOL";
        commandBox.runCommand(detailsToEdit);
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_START_DATE);
    }
    @Test
    public void editFindThenEditToInvalidEndDate_fail() {
        commandBox.runCommand("find George");
        String detailsToEdit = "edit 1 to: LOL";
        commandBox.runCommand(detailsToEdit);
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_END_DATE);
    }

    @Test
    public void editFindThenEditSuccess() throws Exception {
        commandBox.runCommand("find Elle");

        String detailsToEdit = "Belle @Oct 10 1993";
        int filteredTaskListIndex = 1;
        int typeTaskIndex = 5;

        TestTask taskToEdit = expectedTasksList[typeTaskIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withName("Belle")
                .withDate("").withEndDate("Sun Oct 10 1993 23:59:59")
                .withCompleted(false).withPriority("Low").build();

        assertEditSuccess(filteredTaskListIndex, typeTaskIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editMissingTaskIndexFailure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void editInvalidTaskIndexFailure() {
        commandBox.runCommand("edit 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void editNoFieldsSpecifiedFailure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param typeTaskIndex index of task to edit in the typetask.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int typeTaskIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[typeTaskIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
