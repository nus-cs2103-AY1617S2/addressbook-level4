package guitests;

//import static org.junit.Assert.assertTrue;
import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;

//import guitests.guihandles.TaskCardHandle;

import seedu.watodo.commons.core.Messages;
import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.EditCommand;
import seedu.watodo.logic.parser.DateTimeParser;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.testutil.TaskBuilder;
import seedu.watodo.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of tasks in the task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    //@@author A0143076J
    @Test
    public void edit_InvalidFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        commandBox.runCommand("edit   ");
        assertResultMessage(expectedMessage);
        //missing task index
        commandBox.runCommand("edit p");
        assertResultMessage(expectedMessage);
        commandBox.runCommand("edit updated description but missing index");
        assertResultMessage(expectedMessage);
        //invalid task index
        commandBox.runCommand("edit 0 updatedDescription");
        assertResultMessage(expectedMessage);
        commandBox.runCommand("edit -1 updatedDescription");
        assertResultMessage(expectedMessage);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_editInvalidValues_failure() {
        commandBox.runCommand("edit 1 by/invalid date");
        assertResultMessage(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        commandBox.runCommand("edit 1 from/thurs");
        assertResultMessage(DateTimeParser.MESSAGE_INVALID_DATETIME_PREFIX_COMBI);
        commandBox.runCommand("edit 1 #$%^^");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit 8 meet friend for lunch");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    // test to edit the first and last indexes in the task manager in accordance to boundary value analysis
    @Test
    public void edit_DescriptionOnly_success() throws Exception {
        String detailsToEdit = "debug bugs add test cases";
        int taskManagerIndex = 1;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withDescription(detailsToEdit).build();
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);

        detailsToEdit = "don't play play";
        taskManagerIndex = 6;
        taskToEdit = expectedTasksList[taskManagerIndex - 1];
        editedTask = new TaskBuilder(taskToEdit).withDescription(detailsToEdit).build();
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    //equivalence partitions for editing dates includes promoting a floating task to a deadline or event,
    // changing the existing dates of a task (also include changing from event to deadline and vice versa)
    // and lastly removing the current start and/or end dates of a task (deadline or event to floating)
    @Test
    public void execute_editDatesOnly_success() throws IllegalValueException {
        //floating to deadline
        String detailsToEdit = "by/ thurs 6pm";
        int taskManagerIndex = 2;
        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withEndDate(detailsToEdit).build();
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);

        //floating to event
        detailsToEdit = "from/5/12 to/6/12";
        taskManagerIndex = 5;
        taskToEdit = expectedTasksList[taskManagerIndex - 1];
        editedTask = new TaskBuilder(taskToEdit).withStartDate("5/12").withEndDate("6/12").build();
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);

        //deadline to deadline
        detailsToEdit = "by/now";
        taskManagerIndex = 4;
        taskToEdit = expectedTasksList[taskManagerIndex - 1];
        editedTask = new TaskBuilder(taskToEdit).withEndDate("now").build();
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
        //deadline to event
        detailsToEdit = "from/3am to/7am";
        taskManagerIndex = 4;
        taskToEdit = expectedTasksList[taskManagerIndex - 1];
        editedTask = new TaskBuilder(taskToEdit).withStartDate("3am").withEndDate("7am").build();
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);

        //event to float
        detailsToEdit = "REMOVEDATES";
        taskManagerIndex = 4;
        taskToEdit = expectedTasksList[taskManagerIndex - 1];
        editedTask = new TaskBuilder(taskToEdit).build();
        editedTask.setStartDate(null);
        editedTask.setEndDate(null);
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
        //deadline to float
        taskManagerIndex = 3;
        taskToEdit = expectedTasksList[taskManagerIndex - 1];
        editedTask = new TaskBuilder(taskToEdit).build();
        editedTask.setEndDate(null);
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void execute_editAddNewTag_success() throws Exception {
        String detailsToEdit = "#impt";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        UniqueTagList currTags = taskToEdit.getTags();
        Tag tagToAdd = new Tag("impt");
        if (currTags.contains(tagToAdd)) {
            currTags.remove(tagToAdd);
        } else {
            currTags.add(tagToAdd);
        }
        TestTask editedTask = new TaskBuilder(taskToEdit).build();
        editedTask.setTags(currTags);

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

 /*   @Test
    public void execute_editDeleteExistingTag_success() throws Exception {
        String detailsToEdit = "#yumm";
        int taskManagerIndex = 3;
        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        UniqueTagList currTags = taskToEdit.getTags();
        Tag tagToDel = new Tag("yumm");
        if (currTags.contains(tagToDel)) {
            currTags.remove(tagToDel);
        } else {
            currTags.add(tagToDel);
        }
        TestTask editedTask = new TaskBuilder(taskToEdit).build();
        editedTask.setTags(currTags);

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_multipleFieldsSpecified_success() throws Exception {
        String detailsToEdit = "edited task description by/next week #hello #CS";
        int taskManagerIndex = 1;
        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder().withDescription("edited task description")
                .withEndDate("next week").withTags("hello").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);

        //flexible ordering
        detailsToEdit = "REMOVEDATES HEYO #lolol NO FOMO JUST YOLO #chill #yumm";
        taskManagerIndex = 3;
        taskToEdit = expectedTasksList[taskManagerIndex - 1];
        editedTask = new TaskBuilder().withDescription("HEYO NO FOMO JUST YOLO")
                .withTags("lolol", "chill").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_multipleFieldsSpecified_failure() throws Exception {
        String detailsToEdit = "REMOVEDATES edited tasks description by/next week #hello";
        int taskManagerIndex = 1;

        commandBox.runCommand("edit " + taskManagerIndex + " " + detailsToEdit);
        assertResultMessage(DateTimeParser.MESSAGE_INVALID_DATETIME_PREFIX_COMBI);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find mug");

        String detailsToEdit = "mugger muggings";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withDescription(detailsToEdit).build();

        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 6 sleep? by/fri #tag");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    //@@author A0143076J-reused
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
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getDescription().fullDescription);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        //assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
