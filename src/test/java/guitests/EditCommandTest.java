package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.label.Label;
import seedu.address.model.task.Title;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {

        String detailsToEdit = "Meet Bob from 20-04-2017 0900 to 20-04-2017 2359 #husband";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withTitle("Meet Bob").withStartTime("20-04-2017 0900")
                .withDeadline("20-04-2017 2359").withLabels("husband").withStatus(false).build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "#sweetie #bestie";
        int addressBookIndex = 2;

        TestTask taskToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withLabels("sweetie", "bestie").build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearLabels_success() throws Exception {
        String detailsToEdit = "#";
        int addressBookIndex = 2;

        TestTask taskToEdit = expectedTasksList[addressBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit)
                .withLabels().build();

        assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find 5");
        String detailsToEdit = "Complete task 25";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 5;
        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTitle("Complete task 25").build();
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTitle().title);
        assertTrue(editedCard.getTitle().equals(detailsToEdit));
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit 8 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Title.MESSAGE_TITLE_CONSTRAINTS);

        commandBox.runCommand("edit 1 #*&");
        assertResultMessage(Label.MESSAGE_LABEL_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 Complete task 4"
                                + " by 11-11-2017 2300 #friends");
        commandBox.runCommand("add Complete task 5 from 10-10-2017 0100 to 11-11-2017 "

                + "2300 #friends");
        commandBox.runCommand("edit 3 Complete task 5"

                                + " from 10-10-2017 0100 to 11-11-2017 2300 #friends");

        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    //@@author A0105287E
    @Test
    public void edit_editStartAndEndDate_success() throws Exception {
        String detailsToEdit = "from today to next week";
        int taskManagerIndex = 2;

        TestTask editedTask = new TaskBuilder().withTitle("Complete task 2").withStartTime("today")
                .withDeadline("next week").withLabels("owesMoney", "friends")
                .withStatus(false).build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    //@@author A0105287E
    @Test
    public void edit_clearDates_success() throws Exception {
        String detailsToEdit = "clear dates";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withTitle("Complete task 1")
                .withLabels("friends")
                .withStatus(false).build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    //@@author A0105287E
    @Test
    public void edit_changeFromTwoToOneDate_success() throws Exception {
        String detailsToEdit = "by next week 12pm";
        int taskManagerIndex = 3;

        TestTask editedTask = new TaskBuilder().withTitle("Complete task 3")
                .withDeadline("next week 12pm").withStatus(false).build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    //@@author A0105287E
    @Test
    public void edit_changeFromOneToTwoDates_success() throws Exception {
        String detailsToEdit = "from today to next week";
        int taskManagerIndex = 7;

        TestTask editedTask = new TaskBuilder().withTitle("Complete task 7").withStartTime("today")
                .withDeadline("next week").withStatus(false).build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }


    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param (int filteredTaskListIndex index of task to edit in filtered list
     * @param taskManagerIndex index of task to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);
        System.out.println("details to edit: " + detailsToEdit);
        System.out.println("edited task: " + editedTask);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTitle().title);
        System.out.println("Edited card: " + editedCard);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        Arrays.sort(expectedTasksList);
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
