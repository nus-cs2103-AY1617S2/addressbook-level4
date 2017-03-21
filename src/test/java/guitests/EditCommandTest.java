package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.EditCommand;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.TaskTime;
import seedu.task.testutil.TaskBuilder;
import seedu.task.testutil.TestTask;

//needs working on
// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

	// The list of tasks in the task list panel is expected to match this list.
	// This list is updated with every successful call to assertEditSuccess().
	TestTask[] expectedTasksList = td.getTypicalTasks();

	@Test
	public void edit_allFieldsSpecified_success() throws Exception {
		String detailsToEdit = "Go Hiking d/04012017 s/0600 e/2300 m/Bring lots of water.";
		int taskManagerIndex = 1;

		TestTask editedTask = new TaskBuilder().withTaskName("Go Hiking").withTaskStartTime("0600")
				.withTaskEndTime("2300").withTaskDate("04012017").withTaskDescription("Bring lots of water.").build();

		assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
	}

	@Test
	public void edit_notAllFieldsSpecified_success() throws Exception {
		String detailsToEdit = "s/0100";
		int taskManagerIndex = 2;

		TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
		TestTask editedTask = new TaskBuilder(taskToEdit).withTaskStartTime("0100").build();

		assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
	}

	/*
	 * @Test public void edit_findThenEdit_success() throws Exception {
	 * commandBox.runCommand("find Elle");
	 * 
	 * String detailsToEdit = "Belle"; int filteredPersonListIndex = 1; int
	 * addressBookIndex = 5;
	 * 
	 * TestTask personToEdit = expectedTasksList[addressBookIndex - 1]; TestTask
	 * editedPerson = new
	 * TaskBuilder(personToEdit).withTaskName("Belle").build();
	 * 
	 * assertEditSuccess(filteredPersonListIndex, addressBookIndex,
	 * detailsToEdit, editedPerson); }
	 * 
	 * @Test public void edit_missingPersonIndex_failure() {
	 * commandBox.runCommand("edit Bobby");
	 * assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
	 * EditCommand.MESSAGE_USAGE)); }
	 * 
	 * @Test public void edit_invalidPersonIndex_failure() {
	 * commandBox.runCommand("edit 8 Bobby");
	 * assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX); }
	 * 
	 * @Test public void edit_noFieldsSpecified_failure() {
	 * commandBox.runCommand("edit 1");
	 * assertResultMessage(EditCommand.MESSAGE_NOT_EDITED); }
	 * 
	 * @Test public void edit_invalidValues_failure() {
	 * commandBox.runCommand("edit 1 *&");
	 * assertResultMessage(TaskName.MESSAGE_TASK_NAME_CONSTRAINTS);
	 * 
	 * commandBox.runCommand("edit 1 p/abcd");
	 * assertResultMessage(Phone.MESSAGE_PHONE_CONSTRAINTS);
	 * 
	 * commandBox.runCommand("edit 1 e/yahoo!!!");
	 * assertResultMessage(Email.MESSAGE_EMAIL_CONSTRAINTS);
	 * 
	 * commandBox.runCommand("edit 1 a/");
	 * assertResultMessage(Address.MESSAGE_ADDRESS_CONSTRAINTS);
	 * 
	 * commandBox.runCommand("edit 1 t/*&");
	 * assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS); }
	 * 
	 * @Test public void edit_duplicatePerson_failure() { commandBox.
	 * runCommand("edit 3 Alice Pauline p/85355255 e/alice@gmail.com " +
	 * "a/123, Jurong West Ave 6, #08-111 t/friends");
	 * assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK); }
	 */
	/**
	 * Checks whether the edited task has the correct updated details.
	 *
	 * @param filteredTaskListIndex
	 *            index of task to edit in filtered list
	 * @param taskManagerIndex
	 *            index of task to edit in the task manager. Must refer to the
	 *            same task as {@code filteredPersonListIndex}
	 * @param detailsToEdit
	 *            details to edit the person with as input to the edit command
	 * @param editedTask
	 *            the expected task after editing the task's details
	 */
	private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex, String detailsToEdit,
			TestTask editedTask) {
		commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

		// confirm the new card contains the right data
		TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getTaskName().fullTaskName);
		assertMatching(editedTask, editedCard);

		// confirm the list now contains all previous tasks plus the task with
		// updated details
		expectedTasksList[taskManagerIndex - 1] = editedTask;
		assertTrue(taskListPanel.isListMatching(expectedTasksList));
		assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
	}
}
