package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

	// The list of tasks in the task list panel is expected to match this list.
	// This list is updated with every successful call to assertEditSuccess().
	TestTask[] expectedTasksList = this.td.getTypicalTasks();

	@Test
	public void edit_allFieldsSpecified_success() throws Exception {
		String detailsToEdit = "Bobby p/91234567 e/bobby@gmail.com d/Block 123, Bobby Street 3 t/husband";
		int addressBookIndex = 1;

		TestTask editedTask = new TaskBuilder().withName("Bobby").withPriority("91234567")
				.withDeadline("bobby@gmail.com").withDescription("Block 123, Bobby Street 3").withTags("husband")
				.build();

		assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
	}

	@Test
	public void edit_notAllFieldsSpecified_success() throws Exception {
		String detailsToEdit = "t/sweetie t/bestie";
		int addressBookIndex = 2;

		TestTask taskToEdit = this.expectedTasksList[addressBookIndex - 1];
		TestTask editedTask = new TaskBuilder(taskToEdit).withTags("sweetie", "bestie").build();

		assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
	}

	@Test
	public void edit_clearTags_success() throws Exception {
		String detailsToEdit = "t/";
		int addressBookIndex = 2;

		TestTask taskToEdit = this.expectedTasksList[addressBookIndex - 1];
		TestTask editedTask = new TaskBuilder(taskToEdit).withTags().build();

		assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedTask);
	}

	@Test
	public void edit_findThenEdit_success() throws Exception {
		this.commandBox.runCommand("find Elle");

		String detailsToEdit = "Belle";
		int filteredTaskListIndex = 1;
		int addressBookIndex = 5;

		TestTask taskToEdit = this.expectedTasksList[addressBookIndex - 1];
		TestTask editedTask = new TaskBuilder(taskToEdit).withName("Belle").build();

		assertEditSuccess(filteredTaskListIndex, addressBookIndex, detailsToEdit, editedTask);
	}

	@Test
	public void edit_missingTaskIndex_failure() {
		this.commandBox.runCommand("edit Bobby");
		assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
	}

	@Test
	public void edit_invalidTaskFIndex_failure() {
		this.commandBox.runCommand("edit 8 Bobby");
		assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	}

	@Test
	public void edit_noFieldsSpecified_failure() {
		this.commandBox.runCommand("edit 1");
		assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
	}

	@Test
	public void edit_invalidValues_failure() {
		this.commandBox.runCommand("edit 1 *&");
		assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

		this.commandBox.runCommand("edit 1 p/abcd");
		assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);

		this.commandBox.runCommand("edit 1 t/*&");
		assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
	}

	@Test
	public void edit_duplicateTask_failure() {
		this.commandBox
				.runCommand("edit 3 Alice Pauline p/85355255 e/1 " + "d/123, Jurong West Ave 6, #08-111 t/friends");
		assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
	}

	/**
	 * Checks whether the edited task has the correct updated details.
	 *
	 * @param filteredTaskListIndex
	 *            index of task to edit in filtered list
	 * @param addressBookIndex
	 *            index of task to edit in the address book. Must refer to the
	 *            same task as {@code filteredTaskListIndex}
	 * @param detailsToEdit
	 *            details to edit the task with as input to the edit command
	 * @param editedTask
	 *            the expected task after editing the task's details
	 */
	private void assertEditSuccess(int filteredTaskListIndex, int addressBookIndex, String detailsToEdit,
			TestTask editedTask) {
		this.commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

		// confirm the new card contains the right data
		TaskCardHandle editedCard = this.taskListPanel.navigateToTask(editedTask.getName().fullName);
		assertMatching(editedTask, editedCard);

		// confirm the list now contains all previous tasks plus the task with
		// updated details
		this.expectedTasksList[addressBookIndex - 1] = editedTask;
		assertTrue(this.taskListPanel.isListMatching(this.expectedTasksList));
		assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
	}
}
