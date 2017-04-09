package org.teamstbf.yats.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static org.teamstbf.yats.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.teamstbf.yats.commons.core.EventsCenter;
import org.teamstbf.yats.commons.events.model.TaskManagerChangedEvent;
import org.teamstbf.yats.commons.events.ui.JumpToListRequestEvent;
import org.teamstbf.yats.commons.events.ui.ShowHelpRequestEvent;
import org.teamstbf.yats.logic.commands.AddCommand;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.CommandResult;
import org.teamstbf.yats.logic.commands.DeleteCommand;
import org.teamstbf.yats.logic.commands.ExitCommand;
import org.teamstbf.yats.logic.commands.FindCommand;
import org.teamstbf.yats.logic.commands.HelpCommand;
import org.teamstbf.yats.logic.commands.ListCommand;
import org.teamstbf.yats.logic.commands.ResetCommand;
import org.teamstbf.yats.logic.commands.SelectCommand;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.Model;
import org.teamstbf.yats.model.ModelManager;
import org.teamstbf.yats.model.ReadOnlyTaskManager;
import org.teamstbf.yats.model.TaskManager;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Recurrence;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.tag.Tag;
import org.teamstbf.yats.model.tag.UniqueTagList;
import org.teamstbf.yats.storage.StorageManager;

import com.google.common.eventbus.Subscribe;

public class LogicManagerTest {

	/**
	 * A utility class to generate test data.
	 */
	class TestDataHelper {

		Event testEvent() throws Exception {
			Title name = new Title("sleep");
			Location location = new Location("bed");
			Schedule startTime = new Schedule("");
			Schedule endTime = new Schedule("");
			Schedule deadline = new Schedule("");
			Description description = new Description("oh no can't sleep i'm tired");
			Tag tag1 = new Tag("tag1");
			Tag tag2 = new Tag("longertag2");
			UniqueTagList tags = new UniqueTagList(tag1, tag2);
			IsDone isDone = new IsDone("Yes");
			boolean isRecurring = false;
			Recurrence recurrence = new Recurrence();
			return new Event(name, location, startTime, endTime, deadline, description, tags, isDone, isRecurring,
					recurrence);
		}

		/**
		 * Adds auto-generated Event objects to the given TaskManager
		 *
		 * @param taskManager
		 *            The TaskManager to which the Events will be added
		 */
		void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
			addToTaskManager(taskManager, generateEventList(numGenerated));
		}

		/**
		 * Adds the given list of Events to the given TaskManager
		 */
		void addToTaskManager(TaskManager taskManager, List<Event> eventsToAdd) throws Exception {
			for (Event p : eventsToAdd) {
				taskManager.addEvent(p);
			}
		}

		/**
		 * Adds auto-generated Event objects to the given model
		 *
		 * @param model
		 *            The model to which the Events will be added
		 */
		void addToModel(Model model, int numGenerated) throws Exception {
			addToModel(model, generateEventList(numGenerated));
		}

		/**
		 * Adds the given list of Events to the given model
		 */
		void addToModel(Model model, List<Event> eventsToAdd) throws Exception {
			for (Event p : eventsToAdd) {
				model.addEvent(p);
			}
		}

		/** Generates the correct add command based on the Event given */
		String generateAddCommand(Event p) {
			StringBuffer cmd = new StringBuffer();

			cmd.append("add ");

			cmd.append(p.getTitle().toString());
			cmd.append(" l/").append(p.getLocation());
			cmd.append(" p/").append(p.getEndTime());
			cmd.append(" s/").append(p.getStartTime());
			cmd.append(" e/").append(p.getEndTime());
			cmd.append(" d/").append(p.getDescription());
			UniqueTagList tags = p.getTags();
			for (Tag t : tags) {
				cmd.append(" t/").append(t.tagName);
			}

			return cmd.toString();
		}

		/**
		 * Generates an TaskManager with auto-generated Events.
		 */
		TaskManager generateTaskManager(int numGenerated) throws Exception {
			TaskManager taskManager = new TaskManager();
			addToTaskManager(taskManager, numGenerated);
			return taskManager;
		}

		/**
		 * Generates an TaskManager based on the list of Events given.
		 */
		TaskManager generateTaskManager(List<Event> events) throws Exception {
			TaskManager taskManager = new TaskManager();
			addToTaskManager(taskManager, events);
			return taskManager;
		}

		/**
		 * Generates a valid Event using the given seed. Running this function
		 * with the same parameter values guarantees the returned Event will
		 * have the same state. Each unique seed will generate a unique Event
		 * object.
		 *
		 * @param seed
		 *            used to generate the Event data field values
		 */
		Event generateEvent(int seed) throws Exception {
			return new Event(new Title("person" + seed), new Location("bed" + seed), new Schedule("11:59PM 08/04/2017"),
					new Schedule("11:59PM 08/04/2017"), new Schedule(""),
					new Description("oh no can't sleep i'm tired" + seed),
					new UniqueTagList(new Tag("tag" + Math.abs(seed))), new IsDone("Yes"), false, new Recurrence());
		}

		List<Event> generateEventList(Event... events) {
			return Arrays.asList(events);
		}

		/**
		 * Generates a list of Events based on the flags.
		 */
		List<Event> generateEventList(int numGenerated) throws Exception {
			List<Event> events = new ArrayList<>();
			for (int i = 1; i <= numGenerated; i++) {
				events.add(generateEvent(i));
			}
			return events;
		}

		/**
		 * Generates a Event object with given name. Other fields will have some
		 * dummy values.
		 */
		Event generateEventWithName(String name) throws Exception {
			return new Event(new Title(name), new Location("home"), new Schedule(""), new Schedule(""),
					new Schedule("11:59PM 08/04/2017"), new Description("House of 1"),
					new UniqueTagList(new Tag("tag")), new IsDone("No"), false, new Recurrence());
		}
	}

	/**
	 * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
	 */
	@Rule
	public TemporaryFolder saveFolder = new TemporaryFolder();
	private Model model;

	private Logic logic;
	// These are for checking the correctness of the events raised
	private ReadOnlyTaskManager latestSavedTaskManager;
	private boolean helpShown;

	private int targetedJumpIndex;

	/**
	 * Executes the command, confirms that the result message is correct and
	 * that a CommandException is thrown if expected and also confirms that the
	 * following three parts of the LogicManager object's state are as
	 * expected:<br>
	 * - the internal task manager data are same as those in the
	 * {@code expectedAddressBook} <br>
	 * - the backing list shown by UI matches the {@code shownList} <br>
	 * - {@code expectedAddressBook} was saved to the storage file. <br>
	 */
	private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
			ReadOnlyTaskManager expectedTaskManager, List<? extends ReadOnlyEvent> expectedShownList) {

		try {
			CommandResult result = logic.execute(inputCommand);
			assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
			assertEquals(expectedMessage, result.feedbackToUser);
		} catch (CommandException e) {
			assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
			assertEquals(expectedMessage, e.getMessage());
		}

		// Confirm the ui display elements should contain the right data
		assertEquals(expectedShownList, model.getFilteredTaskList());

		// Confirm the state of data (saved and in-memory) is as expected
		assertEquals(expectedTaskManager, model.getTaskManager());
		assertEquals(expectedTaskManager, latestSavedTaskManager);
	}

	/**
	 * Executes the command, confirms that a CommandException is thrown and that
	 * the result message is correct. Both the 'task manager' and the 'last
	 * shown list' are verified to be unchanged.
	 *
	 * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager,
	 *      List)
	 */
	private void assertCommandFailure(String inputCommand, String expectedMessage) {
		TaskManager expectedTaskManager = new TaskManager(model.getTaskManager());
		List<ReadOnlyEvent> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
		assertCommandBehavior(true, inputCommand, expectedMessage, expectedTaskManager, expectedShownList);
	}

	/**
	 * Executes the command, confirms that a CommandException is not thrown and
	 * that the result message is correct. Also confirms that both the 'task
	 * manager' and the 'last shown list' are as specified.
	 *
	 * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager,
	 *      List)
	 */
	private void assertCommandSuccess(String inputCommand, String expectedMessage,
			ReadOnlyTaskManager expectedTaskManager, List<? extends ReadOnlyEvent> observableList) {
		assertCommandBehavior(false, inputCommand, expectedMessage, expectedTaskManager, observableList);
	}

	/**
	 * Confirms the 'invalid argument index number behaviour' for the given
	 * command targeting a single Event in the shown list, using visible index.
	 *
	 * @param commandWord
	 *            to test assuming it targets a single Event in the last shown
	 *            list based on visible index.
	 */
	private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
			throws Exception {
		assertCommandFailure(commandWord, expectedMessage); // index missing
		assertCommandFailure(commandWord + " +1", expectedMessage); // index
		// should be
		// unsigned
		assertCommandFailure(commandWord + " -1", expectedMessage); // index
		// should be
		// unsigned
		assertCommandFailure(commandWord + " 0", expectedMessage); // index
		// cannot be
		// 0
		assertCommandFailure(commandWord + " not_a_number", expectedMessage);
	}

	/**
	 * Confirms the 'invalid argument index number behaviour' for the given
	 * command targeting a single Event in the shown list, using visible index.
	 *
	 * @param commandWord
	 *            to test assuming it targets a single Event in the last shown
	 *            list based on visible index.
	 */
	private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
		String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
		TestDataHelper helper = new TestDataHelper();
		List<Event> eventList = helper.generateEventList(2);

		// set AB state to 2 persons
		model.resetData(new TaskManager());
		for (Event p : eventList) {
			model.addEvent(p);
		}

		assertCommandFailure(commandWord + " 3", expectedMessage);
	}

	@Test
	public void execute_add_invalidArgsFormat() {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
		assertCommandFailure("add", expectedMessage);
	}

	@Test // TO BE EDITED
	public void execute_add_invalidEventData() {
		assertCommandFailure("add []\\[;] p/12345 e/valid@e.mail a/valid, address", Title.MESSAGE_NAME_CONSTRAINTS);
		assertCommandFailure("add Valid Name p/12345 e/notAnEmail a/valid, address", Schedule.MESSAGE_TIME_CONSTRAINTS);
		assertCommandFailure("add Valid Name p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag",
				Tag.MESSAGE_TAG_CONSTRAINTS);

	}

	@Test
	public void execute_add_successful() throws Exception {
		// setup expectations
		TestDataHelper helper = new TestDataHelper();
		Event toBeAdded = helper.testEvent();
		TaskManager expectedAB = new TaskManager();
		expectedAB.addEvent(toBeAdded);

		// execute command and verify result
		assertCommandSuccess("add sleep @bed //oh no can't sleep i'm tired #tag1 #longertag2",
				String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());

	}

	/*
	 * @Test public void execute_addDuplicate_notAllowed() throws Exception { //
	 * setup expectations TestDataHelper helper = new TestDataHelper(); Event
	 * toBeAdded = helper.testEvent();
	 *
	 * // setup starting state model.addEvent(toBeAdded); // Event already in
	 * internal address book
	 *
	 * // execute command and verify result
	 * assertCommandFailure(helper.generateAddCommand(toBeAdded),
	 * AddCommand.MESSAGE_DUPLICATE_EVENT);
	 *
	 * }
	 */

	@Test
	public void execute_reset() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		model.addEvent(helper.generateEvent(1));
		model.addEvent(helper.generateEvent(2));
		model.addEvent(helper.generateEvent(3));

		assertCommandSuccess("reset", ResetCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
	}

	@Test
	public void execute_delete_removesCorrectEvent() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		List<Event> threeEvents = helper.generateEventList(3);

		TaskManager expectedAB = helper.generateTaskManager(threeEvents);
		expectedAB.removeEvent(threeEvents.get(1));
		helper.addToModel(model, threeEvents);

		assertCommandSuccess("delete 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeEvents.get(1)),
				expectedAB, expectedAB.getTaskList());
	}

	@Test
	public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
		assertIndexNotFoundBehaviorForCommand("delete");
	}

	@Test
	public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
		assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
	}

	@Test
	public void execute_exit() {
		assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, new TaskManager(),
				Collections.emptyList());
	}

	@Test
	public void execute_find_invalidArgsFormat() {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
		assertCommandFailure("find ", expectedMessage);
	}

	@Test
	public void execute_find_isNotCaseSensitive() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Event p1 = helper.generateEventWithName("bla bla KEY bla");
		Event p2 = helper.generateEventWithName("bla KEY bla bceofeia");
		Event p3 = helper.generateEventWithName("key key");
		Event p4 = helper.generateEventWithName("KEy sduauo");

		List<Event> fourEvents = helper.generateEventList(p3, p1, p4, p2);
		TaskManager expectedAB = helper.generateTaskManager(fourEvents);
		List<Event> expectedList = fourEvents;
		helper.addToModel(model, fourEvents);

		assertCommandSuccess("find KEY", Command.getMessageForPersonListShownSummary(expectedList.size()), expectedAB,
				expectedList);
	}

	@Test
	public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Event pTarget1 = helper.generateEventWithName("bla bla KEY bla");
		Event pTarget2 = helper.generateEventWithName("bla rAnDoM bla bceofeia");
		Event pTarget3 = helper.generateEventWithName("key key");
		Event p1 = helper.generateEventWithName("sduauo");

		List<Event> fourEvents = helper.generateEventList(pTarget1, p1, pTarget2, pTarget3);
		TaskManager expectedAB = helper.generateTaskManager(fourEvents);
		List<Event> expectedList = helper.generateEventList(pTarget1, pTarget2, pTarget3);
		helper.addToModel(model, fourEvents);

		assertCommandSuccess("find key rAnDoM", Command.getMessageForPersonListShownSummary(expectedList.size()),
				expectedAB, expectedList);
	}

	@Test
	public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		Event pTarget1 = helper.generateEventWithName("bla bla KEY bla");
		Event pTarget2 = helper.generateEventWithName("bla KEY bla bceofeia");
		Event p1 = helper.generateEventWithName("KE Y");
		Event p2 = helper.generateEventWithName("KEYKEYKEY sduauo");

		List<Event> fourEvents = helper.generateEventList(p1, pTarget1, p2, pTarget2);
		TaskManager expectedAB = helper.generateTaskManager(fourEvents);
		List<Event> expectedList = helper.generateEventList(pTarget1, pTarget2);
		helper.addToModel(model, fourEvents);

		assertCommandSuccess("find KEY", Command.getMessageForPersonListShownSummary(expectedList.size()), expectedAB,
				expectedList);
	}

	@Test
	public void execute_help() {
		assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new TaskManager(), Collections.emptyList());
		assertTrue(helpShown);
	}

	@Test
	public void execute_invalid() {
		String invalidCommand = "       ";
		assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
	}

	@Test
	public void execute_list_showsAllEvents() throws Exception {
		// prepare expectations
		TestDataHelper helper = new TestDataHelper();
		TaskManager expectedAB = helper.generateTaskManager(2);
		List<? extends ReadOnlyEvent> expectedList = expectedAB.getTaskList();

		// prepare TaskManager state
		helper.addToModel(model, 2);

		assertCommandSuccess("list", ListCommand.MESSAGE_SUCCESS, expectedAB, expectedList);
	}

	@Test
	public void execute_select_jumpsToCorrectEvent() throws Exception {
		TestDataHelper helper = new TestDataHelper();
		List<Event> threeEvents = helper.generateEventList(3);

		TaskManager expectedAB = helper.generateTaskManager(threeEvents);
		helper.addToModel(model, threeEvents);

		assertCommandSuccess("select 2", String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2), expectedAB,
				expectedAB.getTaskList());
		assertEquals(2, targetedJumpIndex + 1);
		assertEquals(model.getFilteredTaskList().get(0), threeEvents.get(0));
	}

	@Test
	public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
		assertIndexNotFoundBehaviorForCommand("select");
	}

	@Test
	public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
		String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
		assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
	}

	@Test
	public void execute_unknownCommandWord() {
		String unknownCommand = "uicfhmowqewca";
		assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
	}

	@Subscribe
	private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
		targetedJumpIndex = je.targetIndex;
	}

	@Subscribe
	private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
		latestSavedTaskManager = new TaskManager(abce.data);
	}

	@Subscribe
	private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
		helpShown = true;
	}

	@Before
	public void setUp() {
		model = new ModelManager();
		String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempAddressBook.xml";
		String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
		logic = new LogicManager(model, new StorageManager(tempTaskManagerFile, tempPreferencesFile));
		EventsCenter.getInstance().registerHandler(this);

		latestSavedTaskManager = new TaskManager(model.getTaskManager()); // last
		// saved
		// assumed
		// to
		// be
		// up
		// to
		// date
		helpShown = false;
		targetedJumpIndex = -1; // non yet
	}

	@After
	public void tearDown() {
		EventsCenter.clearSubscribers();
	}
}
