package guitests;

//import static org.junit.Assert.assertTrue;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

//import guitests.guihandles.TaskCardHandle;

import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.logic.commands.EditCommand;
//import seedu.onetwodo.logic.commands.FindCommand;
import seedu.onetwodo.model.tag.Tag;
import seedu.onetwodo.model.task.EndDate;
//import seedu.onetwodo.model.task.Description;
import seedu.onetwodo.model.task.Name;
//import seedu.onetwodo.model.task.StartDate;
//import seedu.onetwodo.testutil.TaskBuilder;
import seedu.onetwodo.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends ToDoListGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

/*
    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "GUARD duties s/15-12-2018 7:30am e/16-12-2018 11:30pm d/bring helmet t/army";
        String filteredTaskListIndex = "e1";
        int testTaskIndex = 0;

        TestTask editedTask = new TaskBuilder().withName("GUARD duties")
                .withStartDate("15-12-2018 7:30am").withEndDate("16-12-2018 11:30pm")
                .withDescription("bring helmet")
                .withTags("army")
                .build();

        assertEditSuccess(filteredTaskListIndex, testTaskIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/interest t/hobby";
        String filteredTaskListIndex = "t1";
        int testTaskIndex = 6;

        TestTask testTask = expectedTasksList[testTaskIndex];
        TestTask editedTask = new TaskBuilder(testTask)
                .withTags("interest", "hobby")
                .build();

        assertEditSuccess(filteredTaskListIndex, testTaskIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        String filteredTaskListIndex = "e2";
        int testTaskIndex = 1;

        TestTask taskToEdit = expectedTasksList[1];
        TestTask editedTask = new TaskBuilder(taskToEdit)
                .withTags()
                .build();

        assertEditSuccess(filteredTaskListIndex, testTaskIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand(FindCommand.COMMAND_WORD + " boss");

        String detailsToEdit = "meet bossy boss";
        String filteredTaskListIndex = "e1";
        int testTaskIndex = 2;

        TestTask taskToEdit = expectedTasksList[testTaskIndex];
        TestTask editedTask = new TaskBuilder(taskToEdit)
                .withName("meet bossy boss")
                .build();

        assertEditSuccess(filteredTaskListIndex, testTaskIndex, detailsToEdit, editedTask);
    }
*/
    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndexName_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " 1 Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " e99999 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " d1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " e1 *^;&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand(EditCommand.COMMAND_WORD + " e1 e/yahoo!!!");
        assertResultMessage(EndDate.MESSAGE_DATE_INPUT_CONSTRAINTS);

        commandBox.runCommand(EditCommand.COMMAND_WORD + " e1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " e1 study at home s/tmr 9pm e/tmr 10pm");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex prefix index of task to edit in filtered list
     * @param testTaskIndex index of task to edit in the test List.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     */
/*    private void assertEditSuccess(String filteredTaskListIndex, int testTaskIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand(EditCommand.COMMAND_WORD + " " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[testTaskIndex] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
*/
}
