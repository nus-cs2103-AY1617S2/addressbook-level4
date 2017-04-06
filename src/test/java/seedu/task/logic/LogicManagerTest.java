package seedu.task.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.task.logic.LogicManagerTest.TestDataHelper;
import seedu.task.commons.core.Config;
import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.model.TaskManagerChangedEvent;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.events.ui.ShowHelpRequestEvent;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.logic.Logic;
import seedu.task.logic.LogicManager;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.ClearCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.DoneCommand;
import seedu.task.logic.commands.EditCommand;
import seedu.task.logic.commands.ExitCommand;
import seedu.task.logic.commands.FilterCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.HelpCommand;
import seedu.task.logic.commands.ListCommand;
import seedu.task.logic.commands.LoadCommand;
import seedu.task.logic.commands.PathCommand;
import seedu.task.logic.commands.SelectCommand;
import seedu.task.logic.commands.SortCommand;
import seedu.task.logic.commands.UndoCommand;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.TaskManager;
import seedu.task.model.Model;
import seedu.task.model.ModelManager;
import seedu.task.model.ReadOnlyTaskManager;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.TaskName;
import seedu.task.model.task.TaskStatus;
import seedu.task.model.task.TaskTime;
import seedu.task.model.task.Task;
import seedu.task.model.task.TaskDate;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.storage.StorageManager;

public class LogicManagerTest {

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
	String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempTaskManager.xml";
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
	// targetedJumpIndex = -1; // non yet
    }

    @After
    public void tearDown() {
	EventsCenter.clearSubscribers();
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and
     * that the result message is correct. Also confirms that both the 'task
     * manager' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager,
     *      List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
	    ReadOnlyTaskManager expectedTaskManager, List<? extends ReadOnlyTask> expectedShownList) {
	assertCommandBehavior(false, inputCommand, expectedMessage, expectedTaskManager, expectedShownList);

    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct. Both the 'task manager' and the 'last 
     * shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager,
     *      List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {

	TaskManager expectedTaskManager = new TaskManager(model.getTaskManager());
	List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
	assertCommandBehavior(true, inputCommand, expectedMessage, expectedTaskManager, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct and
     * that a CommandException is thrown if expected and also confirms that the
     * following three parts of the LogicManager object's state are as
     * expected:<br>
     * - the internal task manager data are same as those in the
     * {@code expectedTaskManager} <br>
     * - the backing list shown by UI matches the {@code shownList} <br>
     * - {@code expectedTaskManager} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
	    ReadOnlyTaskManager expectedTaskManager, List<? extends ReadOnlyTask> expectedShownList) {

	try {
	    CommandResult result = logic.execute(inputCommand);
	    assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
	    assertEquals(expectedMessage, result.feedbackToUser);
	} catch (CommandException e) {
	    assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
	    assertEquals(expectedMessage, e.getMessage());
	}

	// List<? extends ReadOnlyTask> actualList =
	// model.getTaskManager().getTaskList();

	// Confirm the ui display elements should contain the right data
	assertEquals(expectedShownList, model.getTaskManager().getTaskList());

	// Confirm the state of data (saved and in-memory) is as expected
	assertEquals(expectedTaskManager, model.getTaskManager());
	assertEquals(expectedTaskManager, latestSavedTaskManager);

    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
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
     * command targeting a single task in the shown list, using visible index.
     * @param commandWord
     *            to test assuming it targets a single person in the last shown
     *            list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
	String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
	TestDataHelper helper = new TestDataHelper();

	List<Task> taskList = helper.generateTaskList(2);

	// set TM state to 2 task
	model.resetData(new TaskManager());
	for (Task p : taskList) {

	    model.addTask(p);
	}

	assertCommandFailure(commandWord + " 3", expectedMessage);
    }

    // @@author A0146757R
    @Test
    public void execute_invalid() {
	String invalidCommand = "       ";
	assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_unknownCommandWord() {
	String unknownCommand = "uicfhmowqewca";
	assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
	assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new TaskManager(), Collections.emptyList());
	assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
	assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, new TaskManager(),
		Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
	TestDataHelper helper = new TestDataHelper();
	model.addTask(helper.generateTask(1));
	model.addTask(helper.generateTask(2));
	model.addTask(helper.generateTask(3));

	assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }

    @Test
    public void execute_add_noTaskName() throws Exception {
	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
	assertCommandFailure("add ", expectedMessage);
    }

    @Test
    public void execute_edit_successful() throws Exception {
	TestDataHelper helper = new TestDataHelper();
	List<Task> threeTasks = helper.generateTaskList(3);

	TaskManager expectedTM = helper.generateTaskManager(threeTasks);
	expectedTM.updateTask(1, threeTasks.get(1));
	helper.addToModel(model, threeTasks);

	assertCommandSuccess("edit 2 d/090919", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, threeTasks.get(1)),
		expectedTM, expectedTM.getTaskList());
    }

    @Test
    public void execute_edit_invalidDate() throws Exception {
	TestDataHelper helper = new TestDataHelper();
	List<Task> threeTasks = helper.generateTaskList(3);

	TaskManager expectedTM = helper.generateTaskManager(threeTasks);
	expectedTM.updateTask(1, threeTasks.get(1));
	helper.addToModel(model, threeTasks);

	assertCommandSuccess("edit 2 d/090919", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, threeTasks.get(1)),
		expectedTM, expectedTM.getTaskList());
    }

    @Test
    public void execute_delete_InvalidArgsFormatErrorMessageShown() throws Exception {
	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
	assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_delete_IndexNotFoundErrorMessageShown() throws Exception {
	assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_done_InvalidArgsFormatErrorMessageShown() throws Exception {
	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE);
	assertIncorrectIndexFormatBehaviorForCommand("done", expectedMessage);
    }

    @Test
    public void execute_done_OnCompletedTask() throws Exception {
	TestDataHelper helper = new TestDataHelper();
	List<Task> oneTask = helper.generateTaskList(1);
	helper.addToModel(model, oneTask);
	String expectedMessage = String.format(DoneCommand.MESSAGE_ALREADY_COMPLETED, 1);
	CommandResult result = logic.execute("done 1");

	TaskManager expectedTM = helper.generateTaskManager(oneTask);
	assertCommandSuccess("done 1", expectedMessage, expectedTM, expectedTM.getTaskList());

    }

    @Test
    public void execute_done_success() throws Exception {
	TestDataHelper helper = new TestDataHelper();
	List<Task> oneTask = helper.generateTaskList(1);
	helper.addToModel(model, oneTask);
	String expectedTask = oneTask.get(0).getTaskStatus().toString().replaceAll("Ongoing", "Completed");
	String expectedMessage = String.format(DoneCommand.MESSAGE_COMPLETED_TASK_SUCCESS, 1).concat("\n");
	TaskManager expectedTM = helper.generateTaskManager(oneTask);
	assertCommandSuccess("done 1", expectedMessage, expectedTM, expectedTM.getTaskList());
    }

    @Test
    public void execute_find_invalidArgsFormat() {
	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
	assertCommandFailure("find ", expectedMessage);
    }

    // @@author A0164061N
    @Test
    public void execute_path_invalidArgsFormat() {
	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PathCommand.MESSAGE_USAGE);
	assertCommandFailure("path ", expectedMessage);
    }

    @Test
    public void execute_load_invalidArgsFormat() {
	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE);
	assertCommandFailure("load ", expectedMessage);
    }

    @Test
    public void execute_filter_invalidArgsFormat() {
	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);
	assertCommandFailure("filter ", expectedMessage);
    }

    @Test
    public void execute_sort_invalidArgsFormat() {
	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
	assertCommandFailure("sort ", expectedMessage);
    }

    @Test
    public void execute_select_invalidArgsFormat() {
	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
	assertCommandFailure("google ", expectedMessage);
    }

    // @Test
    // public void execute_done_IndexNotFoundErrorMessageShown() throws
    // Exception {
    // assertIndexNotFoundBehaviorForCommand("done");
    // }

    @Test
    public void execute_select_IndexNotFoundErrorMessageShown() throws Exception {
	assertIndexNotFoundBehaviorForCommand("google");
    }

    // @@author

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
	TestDataHelper helper = new TestDataHelper();
	Task p1 = helper.generateTaskWithName("bla bla KEY bla");
	Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
	Task p3 = helper.generateTaskWithName("key key");
	Task p4 = helper.generateTaskWithName("KEy sduauo");

	List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
	TaskManager expectedAB = helper.generateTaskManager(fourTasks);
	List<Task> expectedList = fourTasks;
	helper.addToModel(model, fourTasks);

	assertCommandSuccess("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
		expectedList);
    }

    @Test
    public void execute_save_invalidArgsFormat() throws Exception {
	String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, PathCommand.MESSAGE_USAGE);
	assertCommandFailure("path", expectedMessage);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

	Task adam() throws Exception {
	    TaskName taskName = new TaskName("Pick Adam");
	    TaskDate taskDate = new TaskDate("101010");
	    TaskTime taskStartTime = new TaskTime("1100");
	    TaskTime taskEndTime = new TaskTime("1130");
	    String taskDescription = new String("Buy popcorn");
	    TaskStatus taskStatus = new TaskStatus("Ongoing");
	    UniqueTagList tags = new UniqueTagList();
	    return new Task(taskName, taskDate, taskStartTime, taskEndTime, taskDescription, taskStatus, tags);

	}

	/**
	 * Generates a valid task using the given seed. Running this function
	 * with the same parameter values guarantees the returned person will
	 * have the same state. Each unique seed will generate a unique Person
	 * object.
	 *
	 * @param seed
	 *            used to generate the person data field values
	 */
	Task generateTask(int seed) throws Exception {
	    return new Task(new TaskName("Task" + seed), new TaskDate("090919"), new TaskTime("0900"),
		    new TaskTime("0930"), new String("Description" + seed), new TaskStatus("Ongoing"),
		    new UniqueTagList());

	}

	/** Generates the correct add command based on the task given */
	String generateAddCommand(Task p) {
	    StringBuffer cmd = new StringBuffer();

	    cmd.append("add ");

	    cmd.append(p.getTaskName().toString());
	    cmd.append(" d/").append(p.getTaskDate());
	    cmd.append(" s/").append(p.getTaskStartTime());
	    cmd.append(" e/").append(p.getTaskEndTime());
	    cmd.append(" m/").append(p.getTaskDescription());

	    UniqueTagList tags = p.getTags();
	    for (Tag t : tags) {
		cmd.append(" t/").append(t.tagName);
	    }

	    return cmd.toString();
	}

	// @@author
	/**
	 * Generates an TaskManager with auto-generated tasks.
	 */
	TaskManager generateTaskManager(int numGenerated) throws Exception {
	    TaskManager taskManager = new TaskManager();
	    addToTaskManager(taskManager, numGenerated);
	    return taskManager;
	}

	/**
	 * Generates an TaskManager based on the list of Tasks given.
	 */

	TaskManager generateTaskManager(List<Task> tasks) throws Exception {
	    TaskManager taskManager = new TaskManager();
	    addToTaskManager(taskManager, tasks);
	    return taskManager;
	}

	/**
	 * Adds auto-generated Task objects to the given AddressBook
	 * @param taskManager
	 *            The TaskManager to which the Tasks will be added
	 */
	void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
	    addToTaskManager(taskManager, generateTaskList(numGenerated));
	}

	/**
	 * Adds the given list of Tasks to the given TaskManager
	 */
	void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception {
	    for (Task p : tasksToAdd) {
		taskManager.addJobTask(p);
	    }
	}

	/**Adds auto-generated Task objects to the given model 
	 * @param model
	 *            The model to which the Tasks will be added
	 */
	void addToModel(Model model, int numGenerated) throws Exception {
	    addToModel(model, generateTaskList(numGenerated));
	}

	/**
	 * Adds the given list of Tasks to the given model
	 */
	void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
	    for (Task p : tasksToAdd) {
		model.addTask(p);
	    }
	}

	/**
	 * Generates a list of Tasks based on the flags.
	 */
	List<Task> generateTaskList(int numGenerated) throws Exception {
	    List<Task> tasks = new ArrayList<>();
	    for (int i = 1; i <= numGenerated; i++) {
		tasks.add(generateTask(i));
	    }
	    return tasks;
	}

	List<Task> generateTaskList(Task... tasks) {
	    return Arrays.asList(tasks);
	}

	// @@author A0146757R
	/**
	 * Generates a Task object with given name. Other fields will have some
	 * dummy values.
	 */
	Task generateTaskWithName(String name) throws Exception {
	    return new Task(new TaskName(name), new TaskDate("111111"), new TaskTime("1405"), new TaskTime("1408"),
		    new String("This is some description."), new TaskStatus("Ongoing"), new UniqueTagList());
	}
	// @@author
    }
}
