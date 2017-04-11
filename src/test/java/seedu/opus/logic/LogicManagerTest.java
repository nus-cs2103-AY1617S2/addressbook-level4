package seedu.opus.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.opus.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.File;
import java.time.LocalDateTime;
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

import seedu.opus.commons.core.Config;
import seedu.opus.commons.core.EventsCenter;
import seedu.opus.commons.events.model.ChangeSaveLocationEvent;
import seedu.opus.commons.events.model.TaskManagerChangedEvent;
import seedu.opus.commons.events.ui.JumpToListRequestEvent;
import seedu.opus.commons.events.ui.ShowHelpRequestEvent;
import seedu.opus.commons.util.FileUtil;
import seedu.opus.logic.commands.AddCommand;
import seedu.opus.logic.commands.ClearCommand;
import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.CommandResult;
import seedu.opus.logic.commands.DeleteCommand;
import seedu.opus.logic.commands.EditCommand;
import seedu.opus.logic.commands.ExitCommand;
import seedu.opus.logic.commands.FindCommand;
import seedu.opus.logic.commands.HelpCommand;
import seedu.opus.logic.commands.ListCommand;
import seedu.opus.logic.commands.MarkCommand;
import seedu.opus.logic.commands.RedoCommand;
import seedu.opus.logic.commands.SaveCommand;
import seedu.opus.logic.commands.SelectCommand;
import seedu.opus.logic.commands.SortCommand;
import seedu.opus.logic.commands.UndoCommand;
import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.model.Model;
import seedu.opus.model.ModelManager;
import seedu.opus.model.ReadOnlyTaskManager;
import seedu.opus.model.TaskManager;
import seedu.opus.model.TaskManagerStateHistory;
import seedu.opus.model.tag.Tag;
import seedu.opus.model.tag.UniqueTagList;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Name;
import seedu.opus.model.task.Note;
import seedu.opus.model.task.Priority;
import seedu.opus.model.task.ReadOnlyTask;
import seedu.opus.model.task.Status;
import seedu.opus.model.task.Task;
import seedu.opus.storage.StorageManager;
import seedu.opus.testutil.EventsCollector;


public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedTaskManager;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent tmce) {
        latestSavedTaskManager = new TaskManager(tmce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setUp() {
        model = new ModelManager();
        String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempTaskManager.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskManagerFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskManager = new TaskManager(model.getTaskManager()); // last saved assumed to be up to date
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() {
        String invalidCommand = "       ";
        assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and that the result message is correct.
     * Also confirms that both the 'task manager' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyTaskManager expectedTaskManager,
                                      List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedTaskManager, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'task manager' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        TaskManager expectedTaskManager = new TaskManager(model.getTaskManager());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedTaskManager, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task manager data are same as those in the {@code expectedTaskManager} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskManager} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
                                       ReadOnlyTaskManager expectedTaskManager,
                                       List<? extends ReadOnlyTask> expectedShownList) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        }

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getTaskManager());
        if (!inputCommand.contains("sort")) {
            assertEquals(expectedTaskManager, latestSavedTaskManager);
        }
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
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new TaskManager(), Collections.emptyList());
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
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedTaskManager = new TaskManager();
        expectedTaskManager.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTaskManager,
                expectedTaskManager.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal TaskManager

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_DUPLICATE_TASK);

    }

    //@@author A0126345J
    @Test
    public void executeAddInvalidEventNotAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateTask(1);
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        toBeAdded.setEndTime(new DateTime(yesterday));

        // setup starting state
        model.addTask(toBeAdded); // task already in internal TaskManager

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_INVALID_EVENT);

    }
    //@@author


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedTaskManager = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedTaskManager.getTaskList();

        // prepare TaskManager state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedTaskManager,
                expectedList);
    }

    //@@author A0148081H
    @Test
    public void executeSortTasksByStartTime() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithStartTime("p1", "02/01/2017 00:00");
        Task p2 = helper.generateTaskWithStartTime("p2", "02/01/2017 00:00");
        Task p3 = helper.generateTaskWithStartTime("p3", "02/01/2017 23:59");
        Task p4 = helper.generateTaskWithStartTime("p4", "03/01/2017 00:00");
        Task p5 = helper.generateTaskWithStartTime("p5", "03/02/2017 00:00");
        Task p6 = helper.generateTaskWithStartTime("p6", "03/02/2018 00:00");
        Task p7 = helper.generateFloatingTask("Floating Task 1");
        Task p8 = helper.generateFloatingTask("Floating Task 2");

        List<Task> eightTasks = helper.generateTaskList(p1, p2, p3, p4, p5, p6, p7, p8);
        TaskManager expectedTaskManager = helper.generateTaskManager(eightTasks);

        model.resetData(new TaskManager());
        model.addTask(p6);
        model.addTask(p3);
        model.addTask(p1);
        model.addTask(p7);
        model.addTask(p5);
        model.addTask(p2);
        model.addTask(p4);
        model.addTask(p8);

        assertCommandSuccess("sort start",
                SortCommand.MESSAGE_SUCCESS + "start",
                expectedTaskManager,
                eightTasks);
    }

    @Test
    public void executeSortTasksByEndTime() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithEndTime("p1", "02/01/2017 00:00");
        Task p2 = helper.generateTaskWithEndTime("p2", "02/01/2017 00:00");
        Task p3 = helper.generateTaskWithEndTime("p3", "02/01/2017 23:59");
        Task p4 = helper.generateTaskWithEndTime("p4", "03/01/2017 00:00");
        Task p5 = helper.generateTaskWithEndTime("p5", "03/02/2017 00:00");
        Task p6 = helper.generateTaskWithEndTime("p6", "03/02/2018 00:00");
        Task p7 = helper.generateFloatingTask("Floating Task 1");
        Task p8 = helper.generateFloatingTask("Floating Task 2");

        List<Task> eightTasks = helper.generateTaskList(p1, p2, p3, p4, p5, p6, p7, p8);
        TaskManager expectedTaskManager = helper.generateTaskManager(eightTasks);

        model.resetData(new TaskManager());
        model.addTask(p6);
        model.addTask(p3);
        model.addTask(p1);
        model.addTask(p7);
        model.addTask(p5);
        model.addTask(p2);
        model.addTask(p4);
        model.addTask(p8);

        assertCommandSuccess("sort end",
                SortCommand.MESSAGE_SUCCESS + "end",
                expectedTaskManager,
                eightTasks);
    }

    @Test
    public void executeSortTasksByPriority() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithPriority("p1", "hi");
        Task p2 = helper.generateTaskWithPriority("p2", "hi");
        Task p3 = helper.generateTaskWithPriority("p3", "mid");
        Task p4 = helper.generateTaskWithPriority("p4", "low");
        Task p5 = helper.generateFloatingTask("Floating Task 1");
        Task p6 = helper.generateFloatingTask("Floating Task 2");

        List<Task> sixTasks = helper.generateTaskList(p1, p2, p3, p4, p5, p6);
        TaskManager expectedTaskManager = helper.generateTaskManager(sixTasks);

        model.resetData(new TaskManager());
        model.addTask(p1);
        model.addTask(p3);
        model.addTask(p2);
        model.addTask(p5);
        model.addTask(p4);
        model.addTask(p6);

        assertCommandSuccess("sort priority",
                SortCommand.MESSAGE_SUCCESS + "priority",
                expectedTaskManager,
                sixTasks);
    }

    @Test
    public void executeSortInvalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
        assertCommandFailure("sort ", expectedMessage);
    }

    @Test
    public void executeSaveSuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedTaskManager = new TaskManager();
        Task testTask = helper.generateTaskWithName("test");
        expectedTaskManager.addTask(testTask);
        model.addTask(testTask);

        String location = "data/test_successful.xml";
        CommandResult result;
        String inputCommand;
        String feedback;
        EventsCollector eventCollector = new EventsCollector();

        inputCommand = "save " + location;
        result = logic.execute(inputCommand);
        feedback = String.format(SaveCommand.MESSAGE_SUCCESS, location);
        assertEquals(feedback, result.feedbackToUser);
        assertTrue(eventCollector.get(0) instanceof ChangeSaveLocationEvent);
        assertTrue(eventCollector.get(1) instanceof TaskManagerChangedEvent);

        inputCommand = "save default";
        result = logic.execute(inputCommand);
        feedback = String.format(SaveCommand.MESSAGE_LOCATION_DEFAULT, Config.DEFAULT_SAVE_LOCATION);
        assertEquals(feedback, result.feedbackToUser);
        assertTrue(eventCollector.get(2) instanceof ChangeSaveLocationEvent);
        assertTrue(eventCollector.get(3) instanceof TaskManagerChangedEvent);

        // delete file
        FileUtil.deleteFile(location);
    }

    @Test
    public void executeSaveFileExistsFail() throws Exception {
        // setup expectations
        TaskManager expectedTaskManager = new TaskManager();
        String location = "data/test_save_fail.xml";

        // create file
        FileUtil.createIfMissing(new File(location));

        // error that file already exists
        assertCommandSuccess("save " + location,
                String.format(SaveCommand.MESSAGE_FILE_EXISTS, location),
                expectedTaskManager,
                expectedTaskManager.getTaskList());

        // delete file
        FileUtil.deleteFile(location);
    }
    //@@author

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list
     *                    based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertCommandFailure(commandWord , expectedMessage); //index missing
        assertCommandFailure(commandWord + " +1", expectedMessage); //index should be unsigned
        assertCommandFailure(commandWord + " -1", expectedMessage); //index should be unsigned
        assertCommandFailure(commandWord + " 0", expectedMessage); //index cannot be 0
        assertCommandFailure(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list
     *                    based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set TM state to 2 tasks
        model.resetData(new TaskManager());
        for (Task t : taskList) {
            model.addTask(t);
        }

        assertCommandFailure(commandWord + " 3", expectedMessage);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedTaskManager = helper.generateTaskManager(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedTaskManager,
                expectedTaskManager.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }

    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedTaskManager = helper.generateTaskManager(threeTasks);
        expectedTaskManager.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedTaskManager,
                expectedTaskManager.getTaskList());
    }

    @Test
    public void executeMarkCompleteTest() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithStatus("incomplete");
        List<Task> oneTask = helper.generateTaskList(p1);
        helper.addToModel(model, oneTask);

        Task editedTask = helper.generateTaskWithStatus("complete");
        TaskManager expectedTaskManager = helper.generateTaskManager(helper.generateTaskList(editedTask));

        // first mark should toggle status to complete
        assertCommandSuccess("mark 1",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask),
                expectedTaskManager,
                expectedTaskManager.getTaskList());

        // second mark should toggle status to incomplete
        editedTask = helper.generateTaskWithStatus("incomplete");
        expectedTaskManager = helper.generateTaskManager(helper.generateTaskList(editedTask));
        assertCommandSuccess("mark 1",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask),
                expectedTaskManager,
                expectedTaskManager.getTaskList());
    }

    @Test
    public void executeMarkInvalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE);
        assertCommandFailure("mark ", expectedMessage);
    }

    @Test
    public void executeMarkInvalidIndex() throws Exception {
        assertIndexNotFoundBehaviorForCommand("mark");
    }

    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskManager expectedTaskManager = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTaskManager,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedTaskManager = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTaskManager,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskManager expectedTaskManager = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTaskManager,
                expectedList);
    }

    //@@author A0126345J
    @Test
    public void executeFindMatchesIfAllAttributesPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget1 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task p2 = helper.generateTaskWithName("key key");
        Task p3 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, p2, pTarget1, p3);
        TaskManager expectedTaskManager = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find rAnDoM b/12/20/2020 e/12/20/2020 p/hi s/incomplete",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTaskManager,
                expectedList);
    }

    @Test
    public void executeFindMatchesIfAnyAttributePresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget1 = helper.generateTaskWithStartTime("pTarget1", "12/12/2017");
        Task p2 = helper.generateTaskWithName("key key");
        Task p3 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, p3);
        TaskManager expectedTaskManager = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find b/12/20/2017",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTaskManager,
                expectedList);
    }
    //@@author

    //@@author A0148087W
    @Test
    public void executeUndoPreviousAddTaskCommandWithEmptyTaskListSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");

        //Adding task to empty task manager and undo it
        model.resetData(new TaskManager());
        model.addTask(testTask1);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }

    @Test
    public void executeUndoPreviousAddTaskCommandWithExistingTasksSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");
        Task testTask2 = helper.generateTaskWithName("Task2");
        Task testTask3 = helper.generateTaskWithName("Task3");
        List<Task> existingTasks = helper.generateTaskList(testTask1, testTask2);
        TaskManager existingTaskManager = helper.generateTaskManager(existingTasks);

        //Undo adding task when there are existing tasks
        model.resetData(existingTaskManager);
        model.addTask(testTask3);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, existingTaskManager, existingTasks);
    }

    @Test
    public void executeUndoPreviousDeleteCommandSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");
        List<Task> oneTasks = helper.generateTaskList(testTask1);
        TaskManager expectedTaskManager = helper.generateTaskManager(oneTasks);

        //Undo Deletion
        model.addTask(testTask1);
        model.deleteTask(testTask1);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, expectedTaskManager, oneTasks);
    }

    @Test
    public void executeUndoPreviousEditCommandSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");
        Task testTask2 = helper.generateTaskWithName("Task2");
        List<Task> oneTasks = helper.generateTaskList(testTask1);
        TaskManager expectedTaskManager = helper.generateTaskManager(oneTasks);
        Task testTask1Copy = helper.generateTaskWithName("Task1");

        //Undo Edit
        model.addTask(testTask1Copy);
        model.updateTask(0, testTask2);
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, expectedTaskManager, oneTasks);
    }

    @Test
    public void executeUndoMultipleCommandsSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");
        Task testTask2 = helper.generateTaskWithName("Task2");
        Task testTask3 = helper.generateTaskWithName("Task3");
        Task testTask1Copy = helper.generateTaskWithName("Task1");

        model.addTask(testTask1Copy);
        model.updateTask(0, testTask2);
        model.addTask(testTask3);
        model.deleteTask(testTask3);
        model.addTask(testTask1);

        //Undo all previous commands
        model.resetToPreviousState();
        model.resetToPreviousState();
        model.resetToPreviousState();
        model.resetToPreviousState();
        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }

    @Test
    public void assertUndoExceptionWithNoPreviousCommandExecuted() {
        assertCommandFailure("undo", TaskManagerStateHistory.MESSAGE_INVALID_UNDO);
    }

    @Test
    public void assertUndoExceptionAfterUndoingSomeCommandWithNoUndoAvailable() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");

        //Adding task to empty task manager and undo it
        model.addTask(testTask1);
        model.resetToPreviousState();
        assertCommandFailure("undo", TaskManagerStateHistory.MESSAGE_INVALID_UNDO);
    }

    @Test
    public void executeRedoPreviousUndoAddCommandSuccesful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");
        List<Task> oneTasks = helper.generateTaskList(testTask1);
        TaskManager expectedTaskManagerWithOneTask = helper.generateTaskManager(oneTasks);

        //Redo adding one task
        model.addTask(testTask1);
        model.resetToPreviousState();   //undo
        assertCommandSuccess("redo", RedoCommand.MESSAGE_SUCCESS, expectedTaskManagerWithOneTask, oneTasks);
    }

    @Test
    public void executeRedoMultipleUndoAddCommandSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");
        Task testTask2 = helper.generateTaskWithName("Task2");
        List<Task> oneTasks = helper.generateTaskList(testTask1);
        List<Task> twoTasks = helper.generateTaskList(testTask1, testTask2);
        TaskManager expectedTaskManagerWithOneTask = helper.generateTaskManager(oneTasks);
        TaskManager expectedTaskManagerWithTwoTask = helper.generateTaskManager(twoTasks);

        //Redo adding two task
        model.addTask(testTask1);
        model.addTask(testTask2);
        model.resetToPreviousState();   //undo
        model.resetToPreviousState();   //undo
        assertCommandSuccess("redo", RedoCommand.MESSAGE_SUCCESS, expectedTaskManagerWithOneTask, oneTasks);
        assertCommandSuccess("redo", RedoCommand.MESSAGE_SUCCESS, expectedTaskManagerWithTwoTask, twoTasks);
    }

    @Test
    public void executeRedoPreviousUndoDeleteCommandSuccessful() throws Exception {
        TaskManager tempTaskManager;
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");

        model.addTask(testTask1);
        model.deleteTask(testTask1);
        tempTaskManager = new TaskManager(model.getTaskManager());
        model.resetToPreviousState();   //undo
        assertCommandSuccess("redo", RedoCommand.MESSAGE_SUCCESS, tempTaskManager, Collections.emptyList());
    }

    @Test
    public void executeRedoPreviousUndoEditCommandSuccessful() throws Exception {
        TaskManager tempTaskManager;
        TestDataHelper helper = new TestDataHelper();
        Task testTask2 = helper.generateTaskWithName("Task2");
        List<Task> taskTwoOnly = helper.generateTaskList(testTask2);
        Task testTask1Copy = helper.generateTaskWithName("Task1");

        model.addTask(testTask1Copy);
        model.updateTask(0, testTask2);
        tempTaskManager = new TaskManager(model.getTaskManager());
        model.resetToPreviousState();   //undo
        assertCommandSuccess("redo", RedoCommand.MESSAGE_SUCCESS, tempTaskManager, taskTwoOnly);
    }

    @Test
    public void assertUndoExceptionWithNoPreviousUndoCommandExecuted() {
        assertCommandFailure("redo", TaskManagerStateHistory.MESSAGE_INVALID_REDO);
    }

    @Test
    public void assertRedoExceptionAfterRedoingSomeUndoCommandsWithNoRedoAvailable() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithName("Task1");

        model.addTask(testTask1);
        model.resetToPreviousState();
        model.resetToNextState();
        assertCommandFailure("redo", TaskManagerStateHistory.MESSAGE_INVALID_REDO);
    }
    //@@author

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task adam() throws Exception {
            Name name = new Name("Rehearse OP1");
            Priority priority = new Priority("hi");
            Status status = new Status("incomplete");
            Note note = new Note("edit slides");
            DateTime startTime = new DateTime("12/12/2020 12:00");
            DateTime endTime = new DateTime("12/12/2020 13:00");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, priority, status, note, startTime, endTime, tags);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    generatePriorityWithSeed(seed),
                    new Status("incomplete"),
                    new Note("House of " + seed),
                    new DateTime("12/12/" + (2017 + seed) + " 12:00"),
                    new DateTime("12/12/" + (2017 + seed) + " 13:00"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" s/").append(p.getStatus());

            if (p.getPriority().isPresent()) {
                cmd.append(" p/").append(p.getPriority().get().toString());
            }

            if (p.getNote().isPresent()) {
                cmd.append(" n/").append(p.getNote().get().toString());
            }

            if (p.getStartTime().isPresent()) {
                cmd.append(" b/").append(p.getStartTime().get().toString());
            }

            if (p.getEndTime().isPresent()) {
                cmd.append(" e/").append(p.getEndTime().get().toString());
            }

            UniqueTagList tags = p.getTags();
            for (Tag t: tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

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
         * Adds auto-generated Task objects to the given TaskManager
         * @param taskManager The TaskManager to which the Tasks will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                taskManager.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task t: tasksToAdd) {
                model.addTask(t);
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

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Priority("hi"),
                    new Status("incomplete"),
                    new Note("House of 1"),
                    new DateTime("12/12/2020 12:00"),
                    new DateTime("12/12/2020 13:00"),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        //@@author A0148081H-reused
        /**
         * Generates a Task object with given name and start time. Other fields will have some dummy values.
         */
        private Task generateTaskWithStartTime(String name, String startTime) throws Exception {
            return new Task(
                    new Name(name),
                    new Priority("hi"),
                    new Status("incomplete"),
                    new Note("House of 1"),
                    new DateTime(startTime),
                    new DateTime("02/02/2017 00:00"),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a Task object with given name and end time. Other fields will have some dummy values.
         */
        private Task generateTaskWithEndTime(String name, String endTime) throws Exception {
            return new Task(
                    new Name(name),
                    new Priority("hi"),
                    new Status("incomplete"),
                    new Note("House of 1"),
                    new DateTime("01/01/2017 00:00"),
                    new DateTime(endTime),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a Task object with given name and priority. Other fields will have some dummy values.
         */
        private Task generateTaskWithPriority(String name, String priority) throws Exception {
            return new Task(
                    new Name(name),
                    new Priority(priority),
                    new Status("incomplete"),
                    new Note("House of 1"),
                    new DateTime("01/01/2017 00:00"),
                    new DateTime("01/01/2017 23:59"),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a Task object with given status. Other fields will have some dummy values.
         */
        private Task generateTaskWithStatus(String status) throws Exception {
            return new Task(
                    new Name("Finish assignment"),
                    new Priority("hi"),
                    new Status(status),
                    new Note("House of 1"),
                    new DateTime("01/01/2017 00:00"),
                    new DateTime("01/01/2017 23:59"),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a Task object with given name. Other fields will be null except for status and tags
         */
        private Task generateFloatingTask(String name) throws Exception {
            return new Task(
                    new Name(name),
                    null,
                    new Status(),
                    null,
                    null,
                    null,
                    new UniqueTagList(new Tag("tag"))
            );
        }
        //@@author

        private Priority generatePriorityWithSeed(int seed) {
            switch(seed) {
            case 1:
                return new Priority(Priority.Level.LOW);
            case 2:
                return new Priority(Priority.Level.MEDIUM);
            case 3:
                return new Priority(Priority.Level.HIGH);
            default:
                return null;
            }
        }
    }
}
