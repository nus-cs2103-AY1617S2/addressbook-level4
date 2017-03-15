package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.logic.commands.EditCommand;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Title;
import seedu.taskmanager.testutil.TaskBuilder;
import seedu.taskmanager.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    // to do
//    @Test
//    public void edit_allFieldsSpecified_success() throws Exception {
//        String detailsToEdit = "Celebrate Bobby's birthday s/01/01/2017 e/02/01/2017 d/party! #husband";
//        int taskManagerIndex = 1;
//
//        TestTask editedTask = new TaskBuilder().withTitle("Celebrate Bobby's birthday").withStartDate("01/01/2017")
//                .withEndDate("02/01/2017")
//                .withDescription("party!").withTags("husband").build();
//
//        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
//    }

    // to do
//   @Test
//    public void edit_notAllFieldsSpecified_success() throws Exception {
//        String detailsToEdit = "#sweetie #bestie";
//        int taskManagerIndex = 2;
//
//        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
//        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("sweetie", "bestie").build();
//
//        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
//    }

    // to do
//    @Test
//    public void edit_clearTags_success() throws Exception {
//        String detailsToEdit = "#";
//        int taskManagerIndex = 2;
//
//        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
//        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();
//
//        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
//    }

    // To do
//    @Test
//    public void edit_findThenEdit_success() throws Exception {
//        commandBox.runCommand("find Banana");
//
//        String detailsToEdit = "Apple";
//        int filteredTaskListIndex = 1;
//        int taskManagerIndex = 7;
//
//        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
//        TestTask editedTask = new TaskBuilder(taskToEdit).withTitle("Apple").build();
//
//        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
//    }

//    @Test
//    public void edit_missingTaskIndex_failure() {
//        commandBox.runCommand("edit Bobby");
//        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
//    }
//
//    @Test
//    public void edit_invalidTaskIndex_failure() {
//        commandBox.runCommand("edit 20 Meeting");
//        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void edit_noFieldsSpecified_failure() {
//        commandBox.runCommand("edit 1");
//        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
//    }
//
//    @Test
//    public void edit_invalidValues_failure() {
//        commandBox.runCommand("edit 1 *&");
//        assertResultMessage(Title.MESSAGE_TITLE_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 s/abcd");
//        assertResultMessage(StartDate.MESSAGE_STARTDATE_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 e/yahoo!!!");
//        assertResultMessage(EndDate.MESSAGE_ENDDATE_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 d/");
//        assertResultMessage(Description.MESSAGE_TASK_CONSTRAINTS);
//
//        commandBox.runCommand("edit 1 #*&");
//        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
//    }
//
//    @Test
//    public void edit_duplicateTask_failure() {
//        commandBox.runCommand("edit 3 Submit CS2013 s/03/04/2017 e/05/04/2017 d/TaskManager Version 1.0.2");
//        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
//    }
//
//    /**
//     *
//     * Checks whether the edited task has the correct updated details.
//     *
//     * @param filteredTaskListIndex index of task to edit in filtered list
//     * @param taskManagerIndex index of task to edit in the task manager.
//     *      Must refer to the same person as {@code filteredPersonListIndex}
//     * @param detailsToEdit details to edit the task with as input to the edit command
//     * @param editedTask the expected task after editing the task's details
//     */
//    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
//                                    String detailsToEdit, TestTask editedTask) {
//        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);
//
//        // confirm the new card contains the right data
//        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTitle().value);
//        assertMatching(editedTask, editedCard);
//
//        // confirm the list now contains all previous persons plus the person with updated details
//        expectedTasksList[taskManagerIndex - 1] = editedTask;
//        assertTrue(taskListPanel.isListMatching(expectedTasksList));
//        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
//    }
}
