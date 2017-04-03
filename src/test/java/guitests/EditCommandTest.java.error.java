package guitests;

import static org.junit.Assert.assertTrue;
import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;
import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.EditCommand;
import org.teamstbf.yats.model.item.Date;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.tag.Tag;
import org.teamstbf.yats.testutil.EventBuilder;
import org.teamstbf.yats.testutil.TestEvent;

import guitests.guihandles.EventCardHandle;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

	// The list of persons in the person list panel is expected to match this
	// list.
	// This list is updated with every successful call to assertEditSuccess().
	TestEvent[] expectedTaskList = td.getTypicalTasks();

	/**
	 * Checks whether the edited person has the correct updated details.
	 *
	 * @param filteredTaskListIndex
	 *            index of person to edit in filtered list
	 * @param taskManagerIndex
	 *            index of person to edit in the address book. Must refer to the
	 *            same person as {@code filteredPersonListIndex}
	 * @param detailsToEdit
	 *            details to edit the person with as input to the edit command
	 * @param editedTask
	 *            the expected person after editing the person's details
	 */
	private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex, String detailsToEdit,
			TestEvent editedTask) {
		commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

		// confirm the new card contains the right data
		EventCardHandle editedCard = taskListPanel.navigateToEvent(editedTask.getTitle().fullName);
		assertMatching(editedTask, editedCard);

		// confirm the list now contains all previous persons plus the person
		// with updated details
		expectedTaskList[taskManagerIndex - 1] = editedTask;
		assertTrue(taskListPanel.isListMatching(expectedTaskList));
		assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
	}

	@Test
	public void edit_allFieldsSpecified_success() throws Exception {
		String detailsToEdit = "Usorp the throne s/5:45am e/11:59pm l/Ruvenheigen City p/none "
				+ "d/Down to all traitors! Down to all non-believers! t/Betrayal";
		int taskManagerIndex = 1;

		TestEvent editedPerson = new EventBuilder().withTitle("Usorp the throne").withLocation("Ruvenheigen City")
				.withStartTime("5:45am").withEndTime("11:59pm")
				.withDescription("Down to all traitors! Down to all non-believers!").withTags("Betrayal").withIsDone("Yes").build();

		assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedPerson);
	}

	@Test
	public void edit_clearTags_success() throws Exception {
		String detailsToEdit = "t/";
		int taskManagerIndex = 2;

		TestEvent taskToEdit = expectedTaskList[taskManagerIndex - 1];
		TestEvent editedTask = new EventBuilder(taskToEdit).withTags().build();

		assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
	}

	@Test
	public void edit_duplicateTask_failure() {
		commandBox.runCommand(
				"edit 3 Alice Pauline p/85355255 e/alice@gmail.com " + "a/123, Jurong West Ave 6, #08-111 t/friends");
		assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
	}

	@Test
	public void edit_findThenEdit_success() throws Exception {
		commandBox.runCommand("find Elle");

		String detailsToEdit = "Belle";
		int filteredPersonListIndex = 1;
		int addressBookIndex = 5;

		TestEvent personToEdit = expectedTaskList[addressBookIndex - 1];
		TestEvent editedPerson = new EventBuilder(personToEdit).withTitle("Belle").build();

		assertEditSuccess(filteredPersonListIndex, addressBookIndex, detailsToEdit, editedPerson);
	}

	@Test
	public void edit_invalidPersonIndex_failure() {
		commandBox.runCommand("edit 8 Bobby");
		assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	}

	@Test
	public void edit_invalidValues_failure() {
		commandBox.runCommand("edit 1 *&");
		assertResultMessage(Title.MESSAGE_NAME_CONSTRAINTS);

		commandBox.runCommand("edit 1 p/abcd");
		assertResultMessage(Date.MESSAGE_DEADLINE_CONSTRAINTS);

		commandBox.runCommand("edit 1 e/yahoo!!!");
		assertResultMessage(Schedule.MESSAGE_TIME_CONSTRAINTS);

		commandBox.runCommand("edit 1 a/");
		// assertResultMessage(Description.MESSAGE_ADDRESS_CONSTRAINTS);

		commandBox.runCommand("edit 1 t/*&");
		assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
	}

	@Test
	public void edit_missingPersonIndex_failure() {
		commandBox.runCommand("edit Bobby");
		assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
	}

	@Test
	public void edit_noFieldsSpecified_failure() {
		commandBox.runCommand("edit 1");
		assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
	}

	@Test
	public void edit_notAllFieldsSpecified_success() throws Exception {
		String detailsToEdit = "t/sweetie t/bestie";
		int addressBookIndex = 2;

		TestEvent personToEdit = expectedTaskList[addressBookIndex - 1];
		TestEvent editedPerson = new EventBuilder(personToEdit).withTags("sweetie", "bestie").build();

		assertEditSuccess(addressBookIndex, addressBookIndex, detailsToEdit, editedPerson);
	}
}
