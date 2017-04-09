package guitests;

import static org.junit.Assert.assertTrue;
import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;
import org.teamstbf.yats.commons.core.Messages;
import org.teamstbf.yats.logic.commands.EditCommand;
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
		String detailsToEdit = "Usorp the throne -s 5 june 2017 5:45am -e 5 june 2017 11:59pm -l Ruvenheigen City"
				+ "-d Down to all traitors! Down to all non-believers! -t Betrayal -t KingGeorgeVI";
		int taskManagerIndex = 1;

		TestEvent editedPerson = new EventBuilder().withTitle("Usorp the throne").withLocation("Ruvenheigen City")
				.withStartTime("5:45AM 05/06/2017").withEndTime("11:59PM 05/06/2017").withDeadline("")
				.withDescription("Down to all traitors! Down to all non-believers!").withTags("Betrayal")
				.withIsDone("No").build();

		assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedPerson);
	}

	@Test
	public void edit_clearTags_success() throws Exception {
		String detailsToEdit = "-t ";
		int taskManagerIndex = 2;

		TestEvent taskToEdit = expectedTaskList[taskManagerIndex - 1];
		TestEvent editedTask = new EventBuilder(taskToEdit).withTags().build();

		assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
	}

	/*
	 * @Test public void edit_duplicateTask_failure() { commandBox.runCommand(
	 * "edit 3 Alice Pauline");
	 * assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK); }
	 */

	@Test
	public void edit_findThenEdit_success() throws Exception {
		commandBox.runCommand("find Oxygen");

		String detailsToEdit = "Carbon Dioxide";
		int filteredEventListIndex = 1;
		int taskManagerIndex = 3;

		TestEvent eventToEdit = expectedTaskList[taskManagerIndex - 1];
		TestEvent editedEvent = new EventBuilder(eventToEdit).withTitle("Carbon Dioxide").build();

		assertEditSuccess(filteredEventListIndex, taskManagerIndex, detailsToEdit, editedEvent);
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
		// assertResultMessage(SimpleDate.MESSAGE_DEADLINE_CONSTRAINTS);

		commandBox.runCommand("edit 1 something -s 5 may 2017 10:22PM -e 5 may 2017 11:23PM -by 9:00PM 05/05/2017");
		assertResultMessage(EditCommand.MESSAGE_ILLEGAL_DEADLINE_AND_EVENT_OBJECT);

		// commandBox.runCommand("edit 1 a/");
		// assertResultMessage(Description.MESSAGE_ADDRESS_CONSTRAINTS);

		commandBox.runCommand("edit 1 something -t *&");
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
		String detailsToEdit = "-t sweetie -t bestie -t reaper";
		int taskManagerIndex = 2;

		TestEvent eventToEdit = expectedTaskList[taskManagerIndex - 1];
		TestEvent editedEvent = new EventBuilder(eventToEdit).withTags("sweetie", "bestie").build();

		assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedEvent);
	}
}
