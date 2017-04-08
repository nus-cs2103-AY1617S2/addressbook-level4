package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.jobs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.jobs.commons.core.Messages;
import seedu.jobs.logic.commands.EditCommand;
import seedu.jobs.model.tag.Tag;
import seedu.jobs.model.task.ModelConstant;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Period;
import seedu.jobs.model.task.Time;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;
import seedu.jobs.testutil.TaskBuilder;
import seedu.jobs.testutil.TestTask;


public class EditCommandTest extends TaskBookGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

//    @Test
//    public void edit_allFieldsSpecified_success() throws Exception {
//        String detailsToEdit = "CS3101S start/10/03/17 15:00 end/10/03/17 16:00 desc/revision for final tag/tutorial";
//        int taskBookIndex = 1;
//
//        TestTask editedTask = new TaskBuilder().withName("CS3101").withStartTime("10/03/2017 15:00")
//                .withEndTime("10/03/2017 16:00").withDescription("revision for final").withTags("tutorial").build();
//
//        assertEditSuccess(taskBookIndex, taskBookIndex, detailsToEdit, editedTask);
//    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "tag/lecture tag/test";
        int taskBookIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags("lecture", "test").build();

        assertEditSuccess(taskBookIndex, taskBookIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "tag/";
        int taskBookIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskBookIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

        assertEditSuccess(taskBookIndex, taskBookIndex, detailsToEdit, editedTask);
    }

//    @Test
//    public void edit_findThenEdit_success() throws Exception {
//        commandBox.runCommand("find CS3101");
//
//        String detailsToEdit = "CS3101S";
//        int filteredPersonListIndex = 1;
//        int addressBookIndex = 5;
//
//        TestTask personToEdit = expectedTasksList[addressBookIndex - 1];
//        TestTask editedTask = new TaskBuilder(personToEdit).withName("CS3101S").build();
//
//        assertEditSuccess(filteredPersonListIndex, addressBookIndex, detailsToEdit, editedTask);
//    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidPersonIndex_failure() {
        commandBox.runCommand("edit 100 Bobby");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 " + ModelConstant.getLongString(151));
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit 1 start/abcd");
        assertResultMessage(Time.MESSAGE_TIME_CONSTRAINT);

        commandBox.runCommand("edit 1 end/yahoo!!!");
        assertResultMessage(Time.MESSAGE_TIME_CONSTRAINT);

        commandBox.runCommand("edit 1 recur/abc");
        assertResultMessage(Period.MESSAGE_PERIOD_CONSTRAINT);

        commandBox.runCommand("edit 1 tag/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 CS3101 start/10/03/2017 15:00 end/10/03/2017 17:00 "
                                + "desc/chapter 1 to chapter 6 tag/test");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param taskBookIndex index of task to edit in the task book.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the task with as input to the edit command
     * @param editedTask the expected task after editing the task's details
     * @throws IllegalTimeException
     * @throws IllegalArgumentException
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskBookIndex,
                                    String detailsToEdit, TestTask editedTask)
                                            throws IllegalArgumentException, IllegalTimeException {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskBookIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
