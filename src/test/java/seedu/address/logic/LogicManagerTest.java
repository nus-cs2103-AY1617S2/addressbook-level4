package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.time.DateUtils;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NotDoneCommand;
import seedu.address.logic.commands.NotTodayCommand;
import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.logic.commands.SaveToCommand;
import seedu.address.logic.commands.TodayCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UseThisCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;
    private Config config;
    private Storage storage;

    String tempTaskManagerFile;
    String tempPreferencesFile;
    String tempConfigFile;

    // These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedTaskManager;
    private boolean helpShown;
    private String targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
        latestSavedTaskManager = new TaskManager(abce.data);
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
        String tempConfigFile = saveFolder.getRoot().getPath() + "TempConfig.json";
        config = new Config(tempConfigFile);
        config.setTaskManagerFilePath(tempTaskManagerFile);
        config.setUserPrefsFilePath(tempPreferencesFile);
        storage = new StorageManager(config);
        logic = new LogicManager(model);
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
        targetedJumpIndex = ""; // non yet
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
     * Executes the command, confirms that a CommandException is not thrown and
     * that the result message is correct. Also confirms that both the 'task
     * manager' and the 'last shown list' are as specified.
     *
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager,
     *      List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            ReadOnlyTaskManager expectedTaskManager, List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedTaskManager, expectedShownList);
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
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedTaskManager, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct and
     * that a CommandException is thrown if expected and also confirms that the
     * following three parts of the LogicManager object's state are as expected:
     * <br>
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
        // Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, model.getFilteredTaskList());

        // Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, model.getTaskManager());
        assertEquals(expectedTaskManager, latestSavedTaskManager);
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
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded), String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB, expectedAB.getTaskList());

    }

    // @@author A0144422R
    @Test
    public void execute_add_event_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateEventTaskWithNameTags("name", 0, 1, "tag1");
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess("add name from today 0000 to tomorrow 0000 #tag1",
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());

    }

    @Test
    public void execute_add_deadline_task_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.generateDeadlineTaskWithNameTags("name", 0, "tag1");
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess("add name due today 0000 #tag1", String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task manager

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_PERSON);

    }

    // author A0144422R
    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedAB = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare task manager state
        helper.addToModel(model, 2);

        assertCommandSuccess("list", ListCommand.MESSAGE_SUCCESS, expectedAB, expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     *
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.setData(new TaskManager(), true);
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandFailure(commandWord + " F100", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("delete C1", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB, expectedAB.getTaskList());
    }

    // @@author A0093999Y
    @Test
    public void execute_done_invalidArgsFormat() {
        String expectedMessage = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        assertCommandFailure("done ", expectedMessage);
    }

    @Test
    public void execute_doneIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("done");
    }

    @Test
    public void execute_done_valid() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        // TestUtil.assignUiIndex(threeTasks);
        Task taskToDone = threeTasks.get(0);
        Task doneTask = new FloatingTask(taskToDone.getName(), taskToDone.getTags(), true, false);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.updateTask(0, doneTask);

        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("done F1", String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, doneTask), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_notdone_invalidArgsFormat() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        assertCommandFailure("notdone ", expectedMessage);
    }

    @Test
    public void execute_notdoneIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("notdone");
    }

    @Test
    public void execute_notdone_valid() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        Task taskToNotDone = threeTasks.get(1);
        taskToNotDone.setDone(true);
        Task notDoneTask = new FloatingTask(taskToNotDone.getName(), taskToNotDone.getTags(), false, false);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.updateTask(1, notDoneTask);
        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("notdone C1", String.format(NotDoneCommand.MESSAGE_NOTDONE_TASK_SUCCESS, notDoneTask),
                expectedAB, expectedAB.getTaskList());

    }

    // @@author A0093999Y
    @Test
    public void execute_editOnlyIndex_invalid() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertCommandFailure("edit F1", expectedMessage);
    }

    @Test
    public void execute_editNoIndex_invalid() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertCommandFailure("edit task2 #well", expectedMessage);
    }

    // @@author A0144422R
    @Test
    public void execute_editInvalidTag_errorMessageShown() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertCommandFailure("edit F1 #@!89", expectedMessage);
    }

    @Test
    public void execute_editInvalidName_errorMessageShown() {
        String expectedMessage = String.format(Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandFailure("edit F1 abc$%%^% from 2pm to 3pm", expectedMessage);
    }

    @Test
    public void execute_editNameOnly_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        Task taskToEdit = threeTasks.get(1);
        Task backup = new FloatingTask(taskToEdit);
        taskToEdit = new FloatingTask(new Name("new name"), new UniqueTagList(), false, false);
        expectedAB.updateTask(1, taskToEdit);
        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("edit C1 new name", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, backup),
                expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_editNameAndTag_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        Task taskToEdit = threeTasks.get(1);
        Task backup = new FloatingTask(taskToEdit);
        taskToEdit = new FloatingTask(new Name("new name"), new UniqueTagList("tag1", "tag2"), false, false);
        expectedAB.updateTask(1, taskToEdit);
        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("edit C1 new name #tag1 #tag2",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, backup), expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_editEventNameAndTag_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        Task taskToEdit = threeTasks.get(1);
        Date start = DateUtils.truncate(DateUtils.addDays(new Date(), 0), Calendar.DAY_OF_MONTH);
        Date end = DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DAY_OF_MONTH);
        Task backup = new FloatingTask(taskToEdit);
        taskToEdit = new EventTask(new Name("new name"), new UniqueTagList("tag1", "tag2"), end, start, false, false);
        expectedAB.updateTask(1, taskToEdit);
        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("edit C1 new name from today at 0000 to tomorrow at 0000 #tag1 #tag2",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, backup), expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_editDeadlineTaskTag_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        Task taskToEdit = threeTasks.get(1);
        Task backup = new FloatingTask(taskToEdit);
        Date end = DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DAY_OF_MONTH);
        taskToEdit = new DeadlineTask(taskToEdit.getName(), new UniqueTagList("tag1", "tag2"), end, false, false);
        expectedAB.updateTask(1, taskToEdit);
        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("edit C1 due tomorrow at 0000 #tag1 #tag2",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, backup), expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_editConfusingName_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        Task taskToEdit = threeTasks.get(1);
        Task backup = new FloatingTask(taskToEdit);
        taskToEdit = new FloatingTask(new Name("from today uihasduhas to tomorrow uhaius"),
                new UniqueTagList("tag1", "tag2"), false, false);
        expectedAB.updateTask(1, taskToEdit);
        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("edit C1 from today uihasduhas to tomorrow uhaius #tag1 #tag2",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, backup), expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_editDuplicateTask_notAllowed() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandFailure("edit C1 Task 3 #tag3 #tag4", EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_editIndexNotFound_notAllowed() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandFailure("edit C100 Task 3 #tag3 #tag4", Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    // @@author A0093999Y
    // ----------------------Today----------------------

    @Test
    public void execute_today_invalidArgsFormat() {
        String expectedMessage = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        assertCommandFailure("today ", expectedMessage);
    }

    @Test
    public void execute_todayIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("today");
    }

    @Test
    public void execute_today_valid() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        // TestUtil.assignUiIndex(threeTasks);
        Task taskToToday = threeTasks.get(0);
        Task todayTask = new FloatingTask(taskToToday.getName(), taskToToday.getTags(), taskToToday.isDone(), true);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.updateTask(0, todayTask);

        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("today F1", String.format(TodayCommand.MESSAGE_TODAY_TASK_SUCCESS, todayTask), expectedAB,
                expectedAB.getTaskList());
    }

    // ------------------- Not Today -----------------------

    @Test
    public void execute_notToday_invalidArgsFormat() {
        String expectedMessage = Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        assertCommandFailure("nottoday ", expectedMessage);
    }

    @Test
    public void execute_notTodayIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("nottoday");
    }

    @Test
    public void execute_notToday_valid() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);
        // TestUtil.assignUiIndex(threeTasks);
        Task firstTask = threeTasks.get(0);
        firstTask.setToday(true);
        Task notTodayTask = new FloatingTask(firstTask.getName(), firstTask.getTags(), firstTask.isDone(), false);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.updateTask(0, notTodayTask);

        helper.addToModel(model, threeTasks);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("nottoday T1", String.format(NotTodayCommand.MESSAGE_NOTTODAY_TASK_SUCCESS, notTodayTask),
                expectedAB, expectedAB.getTaskList());
    }

    // @@author
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
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInTags() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task targetTagAndName1 = helper.generateTaskWithNameAndTags("bla bla KEY bla", "KEY");
        Task targetTagAndName2 = helper.generateTaskWithNameAndTags("bla KEY bla bceofeia", "blahbla", "KEY");
        Task targetTag1 = helper.generateTaskWithNameAndTags("bla bleepa", "KEY");
        Task targetTag2 = helper.generateTaskWithNameAndTags("bloopy beep", "blahbla", "KEY");
        Task p1 = helper.generateTaskWithNameAndTags("KE Y", "nope");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        Task p3 = helper.generateTaskWithNameAndTags("KE YY", "KEYY");

        List<Task> sevenTasks = helper.generateTaskList(p1, targetTagAndName1, p2, targetTagAndName2, targetTag1,
                targetTag2, p3);
        TaskManager expectedAB = helper.generateTaskManager(sevenTasks);
        List<Task> expectedList = helper.generateTaskList(targetTagAndName1, targetTagAndName2, targetTag1, targetTag2);
        helper.addToModel(model, sevenTasks);

        assertCommandSuccess("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    // @@author: A0144422R
    @Test
    public void execute_find_date() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task targetDeadline1 = helper.generateDeadlineTaskWithNameTags("bla bla KEY bla", 0, "KEY");
        Task targetDeadline2 = helper.generateEventTaskWithNameTags("bla KEY bla bceofeia", -1, 0, "blahbla", "KEY");
        Task p1 = helper.generateTaskWithNameAndTags("KE Y", "nope");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        Task p3 = helper.generateTaskWithNameAndTags("KE YY", "KEYY");
        Task p4 = helper.generateDeadlineTaskWithNameTags("KEYKEYKEY sduauo", 2);
        Task p5 = helper.generateEventTaskWithNameTags("KEEEEY", 2, 3);

        List<Task> sevenTasks = helper.generateTaskList(p1, targetDeadline1, p2, targetDeadline2, p4, p5, p3);
        TaskManager expectedAB = helper.generateTaskManager(sevenTasks);
        List<Task> expectedList = helper.generateTaskList(targetDeadline1, targetDeadline2);
        helper.addToModel(model, sevenTasks);

        assertCommandSuccess("find due today", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    @Test
    public void execute_find_multipleKeywords() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task targetTagAndName1 = helper.generateTaskWithNameAndTags("bla bla KEY bla", "KEY");
        Task targetTagAndName2 = helper.generateTaskWithNameAndTags("bla KEY bla bceofeia", "blahbla", "KEY");
        Task targetTag1 = helper.generateTaskWithNameAndTags("bla bleepa", "KEY");
        Task targetTag2 = helper.generateTaskWithNameAndTags("bloopy beep", "blahbla", "KEY");
        Task targetTag3 = helper.generateTaskWithNameAndTags("KE Y", "nope");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        Task p3 = helper.generateTaskWithNameAndTags("KE YY", "KEYY");

        List<Task> sevenTasks = helper.generateTaskList(targetTag3, targetTagAndName1,
                p2, targetTagAndName2, targetTag1,
                targetTag2, p3);
        TaskManager expectedAB = helper.generateTaskManager(sevenTasks);
        List<Task> expectedList = helper.generateTaskList(targetTag3);
        helper.addToModel(model, sevenTasks);

        assertCommandSuccess("find nope",
                Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
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
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_invalidArgs() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    // @@author A0093999Y
    @Test
    public void execute_renametag_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTagCommand.MESSAGE_USAGE);
        assertCommandFailure("renametag ", expectedMessage);
    }

    @Test
    public void execute_renametag_onlyMatchesFullWordsInTags() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateTaskWithNameAndTags("bla bla bla", "KEY");
        Task t2 = helper.generateTaskWithNameAndTags("bla bla bceofeia", "blahbla", "KEY");
        Task r1 = helper.generateTaskWithNameAndTags("bla bla bla", "newkey");
        Task r2 = helper.generateTaskWithNameAndTags("bla bla bceofeia", "blahbla", "newkey");
        Task p1 = helper.generateTaskWithNameAndTags("KE Y", "nope");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        Task p3 = helper.generateTaskWithNameAndTags("KE YY", "KEYY");

        List<Task> fiveTasks = helper.generateTaskList(p1, t1, p2, t2, p3);
        List<Task> expectedList = helper.generateTaskList(p1, r1, p2, r2, p3);
        TaskManager expectedAB = helper.generateTaskManager(expectedList);
        helper.addToModel(model, fiveTasks);

        assertCommandSuccess("renametag KEY newkey",
                String.format(RenameTagCommand.MESSAGE_RENAME_TAG_SUCCESS, "KEY", "newkey"), expectedAB, expectedList);
    }

    @Test
    public void execute_renametag_onlyMatchesCorrectCaseInTags() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateTaskWithNameAndTags("bla bla bla", "KEY");
        Task t2 = helper.generateTaskWithNameAndTags("bla bla bceofeia", "blahbla", "KEY");
        Task r1 = helper.generateTaskWithNameAndTags("bla bla bla", "newkey");
        Task r2 = helper.generateTaskWithNameAndTags("bla bla bceofeia", "blahbla", "newkey");
        Task p1 = helper.generateTaskWithNameAndTags("KE Y", "nope");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        Task p3 = helper.generateTaskWithNameAndTags("KE YY", "KEy");

        List<Task> fiveTasks = helper.generateTaskList(p1, t1, p2, t2, p3);
        List<Task> expectedList = helper.generateTaskList(p1, r1, p2, r2, p3);
        TaskManager expectedAB = helper.generateTaskManager(expectedList);
        helper.addToModel(model, fiveTasks);

        assertCommandSuccess("renametag KEY newkey",
                String.format(RenameTagCommand.MESSAGE_RENAME_TAG_SUCCESS, "KEY", "newkey"), expectedAB, expectedList);
    }

    // DeleteTagCommand Tests

    @Test
    public void execute_deletetag_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);
        assertCommandFailure("deletetag ", expectedMessage);
    }

    @Test
    public void execute_deletetag_onlyMatchesFullWordsInTags() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateTaskWithNameAndTags("bla bla bla", "KEY");
        Task t2 = helper.generateTaskWithNameAndTags("bla bla bceofeia", "blahbla", "KEY");
        Task r1 = helper.generateTaskWithNameAndTags("bla bla bla");
        Task r2 = helper.generateTaskWithNameAndTags("bla bla bceofeia", "blahbla");
        Task p1 = helper.generateTaskWithNameAndTags("KE Y", "nope");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        Task p3 = helper.generateTaskWithNameAndTags("KE YY", "KEYY");

        List<Task> fiveTasks = helper.generateTaskList(p1, t1, p2, t2, p3);
        List<Task> expectedList = helper.generateTaskList(p1, r1, p2, r2, p3);
        TaskManager expectedAB = helper.generateTaskManager(expectedList);
        helper.addToModel(model, fiveTasks);

        assertCommandSuccess("deletetag KEY", String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, "KEY"),
                expectedAB, expectedList);
    }

    @Test
    public void execute_deletetag_onlyMatchesCorrectCaseInTags() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateTaskWithNameAndTags("bla bla bla", "KEY");
        Task t2 = helper.generateTaskWithNameAndTags("bla bla bceofeia", "blahbla", "KEY");
        Task r1 = helper.generateTaskWithNameAndTags("bla bla bla");
        Task r2 = helper.generateTaskWithNameAndTags("bla bla bceofeia", "blahbla");
        Task p1 = helper.generateTaskWithNameAndTags("KE Y", "nope");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");
        Task p3 = helper.generateTaskWithNameAndTags("KE YY", "KEy");

        List<Task> fiveTasks = helper.generateTaskList(p1, t1, p2, t2, p3);
        List<Task> expectedList = helper.generateTaskList(p1, r1, p2, r2, p3);
        TaskManager expectedAB = helper.generateTaskManager(expectedList);
        helper.addToModel(model, fiveTasks);

        assertCommandSuccess("deletetag KEY", String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, "KEY"),
                expectedAB, expectedList);
    }

    // SaveToCommand Tests
    // @@author A0139388M
    @Test
    public void execute_saveTo_canonicalSameDirectory() throws Exception {
        File tmFile = new File(".", SaveToCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("saveto .", String.format(SaveToCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()),
                new TaskManager(), Collections.emptyList());
        tmFile.delete();
    }

    @Test
    public void execute_saveTo_canonicalParentDirectory() throws Exception {
        File tmFile = new File("..", SaveToCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("saveto ..", String.format(SaveToCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()),
                new TaskManager(), Collections.emptyList());
        tmFile.delete();
    }

    @Test
    public void execute_saveTo_canonicalSubDirectory() throws Exception {
        File tmFile = new File("testSubDir", SaveToCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("saveto testSubDir",
                String.format(SaveToCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()), new TaskManager(),
                Collections.emptyList());
        tmFile.delete();
        tmFile.getParentFile().delete();
    }

    @Test
    public void execute_saveTo_absoluteSameDirectory() throws Exception {
        File tmFile = new File(".", SaveToCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("saveto " + tmFile.getParentFile().getAbsolutePath(),
                String.format(SaveToCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()), new TaskManager(),
                Collections.emptyList());
        tmFile.delete();
    }

    @Test
    public void execute_saveTo_absoluteParentDirectory() throws Exception {
        File tmFile = new File("..", SaveToCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("saveto " + tmFile.getParentFile().getAbsolutePath(),
                String.format(SaveToCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()), new TaskManager(),
                Collections.emptyList());
        tmFile.delete();
    }

    @Test
    public void execute_saveTo_absoluteSubDirectory() throws Exception {
        File tmFile = new File("testSubDir", SaveToCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("saveto " + tmFile.getParentFile().getAbsolutePath(),
                String.format(SaveToCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()), new TaskManager(),
                Collections.emptyList());
        tmFile.delete();
        tmFile.getParentFile().delete();
    }

    @Test
    public void execute_saveTo_noWritePermissions() throws Exception {
        File noWritePermissionsFile = new File("noPermissions", SaveToCommand.TASK_MANAGER_FILE_NAME);
        if (noWritePermissionsFile.setReadOnly()) {
            assertCommandFailure("saveto " + noWritePermissionsFile.getParentFile().getAbsolutePath(),
                    String.format(SaveToCommand.MESSAGE_WRITE_FILE_ERROR, noWritePermissionsFile.getAbsolutePath()));
            noWritePermissionsFile.getParentFile().delete();
        }
    }

    @Test
    public void execute_saveTo_invalidFolderName() throws Exception {
        File invalidFolderNameFile = new File("////?!", SaveToCommand.TASK_MANAGER_FILE_NAME);
        assertCommandFailure("saveto " + invalidFolderNameFile.getParentFile().getAbsolutePath(),
                String.format(SaveToCommand.MESSAGE_WRITE_FILE_ERROR, invalidFolderNameFile.getAbsolutePath()));
    }

    // End SaveToCommand tests

    // ExportCommand Tests

    @Test
    public void execute_export_canonicalSameDirectory() throws Exception {
        File tmFile = new File(".", ExportCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("export .", String.format(ExportCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()),
                new TaskManager(), Collections.emptyList());
        tmFile.delete();
    }

    @Test
    public void execute_export_canonicalParentDirectory() throws Exception {
        File tmFile = new File("..", ExportCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("export ..", String.format(ExportCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()),
                new TaskManager(), Collections.emptyList());
        tmFile.delete();
    }

    @Test
    public void execute_export_canonicalSubDirectory() throws Exception {
        File tmFile = new File("testSubDir", ExportCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("export testSubDir",
                String.format(ExportCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()), new TaskManager(),
                Collections.emptyList());
        tmFile.delete();
        tmFile.getParentFile().delete();
    }

    @Test
    public void execute_export_absoluteSameDirectory() throws Exception {
        File tmFile = new File(".", ExportCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("export " + tmFile.getParentFile().getAbsolutePath(),
                String.format(ExportCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()), new TaskManager(),
                Collections.emptyList());
        tmFile.delete();
    }

    @Test
    public void execute_export_absoluteParentDirectory() throws Exception {
        File tmFile = new File("..", ExportCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("export " + tmFile.getParentFile().getAbsolutePath(),
                String.format(ExportCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()), new TaskManager(),
                Collections.emptyList());
        tmFile.delete();
    }

    @Test
    public void execute_export_absoluteSubDirectory() throws Exception {
        File tmFile = new File("testSubDir", ExportCommand.TASK_MANAGER_FILE_NAME);
        assertCommandSuccess("export " + tmFile.getParentFile().getAbsolutePath(),
                String.format(ExportCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()), new TaskManager(),
                Collections.emptyList());
        tmFile.delete();
        tmFile.getParentFile().delete();
    }

    @Test
    public void execute_export_noWritePermissions() throws Exception {
        File noWritePermissionsFile = new File("noPermissions", ExportCommand.TASK_MANAGER_FILE_NAME);
        if (noWritePermissionsFile.setReadOnly()) {
            assertCommandFailure("export " + noWritePermissionsFile.getParentFile().getAbsolutePath(),
                    String.format(ExportCommand.MESSAGE_WRITE_FILE_ERROR, noWritePermissionsFile.getAbsolutePath()));
            noWritePermissionsFile.getParentFile().delete();
        }
    }

    @Test
    public void execute_export_invalidFileName() throws Exception {
        File invalidFileNameFile = new File("////?!", ExportCommand.TASK_MANAGER_FILE_NAME);
        assertCommandFailure("export " + invalidFileNameFile.getParentFile().getAbsolutePath(),
                String.format(ExportCommand.MESSAGE_WRITE_FILE_ERROR, invalidFileNameFile.getAbsolutePath()));
    }

    // End ExportCommand Tests

    // UseThisCommand Tests

    @Test
    public void execute_useThis_absoluteSubDirectory() throws Exception {
        File tmFile = new File("testSubDir", UseThisCommand.TASK_MANAGER_FILE_NAME);
        FileUtil.createFile(tmFile);
        assertCommandSuccess("usethis " + tmFile.getParentFile().getAbsolutePath(),
                String.format(UseThisCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()), new TaskManager(),
                Collections.emptyList());
        tmFile.delete();
        tmFile.getParentFile().delete();
    }

    @Test
    public void execute_useThis_invalidFolderName() throws Exception {
        File invalidFolderNameFile = new File("////?!", UseThisCommand.TASK_MANAGER_FILE_NAME);
        assertCommandFailure("usethis " + invalidFolderNameFile.getParentFile().getAbsolutePath(),
                String.format(UseThisCommand.MESSAGE_FILE_MISSING_ERROR, invalidFolderNameFile.getAbsolutePath()));
    }

    // End UseThisCommand tests

    // ImportCommand Tests

    @Test
    public void execute_import_absoluteSubDirectory() throws Exception {
        File tmFile = new File("testSubDir", ImportCommand.TASK_MANAGER_FILE_NAME);

        // add adam to list
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // verify added
        String addCommand = helper.generateAddCommand(toBeAdded);
        assertCommandSuccess(addCommand, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());

        // no difference after importing empty file
        FileUtil.createFile(tmFile);
        assertCommandSuccess("import " + tmFile.getParentFile().getAbsolutePath(),
                String.format(ImportCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()), expectedAB,
                expectedAB.getTaskList());

        // cleanup
        tmFile.delete();
        tmFile.getParentFile().delete();
    }

    @Test
    public void execute_import_invalidFolderName() throws Exception {
        File invalidFolderNameFile = new File("////?!", ImportCommand.TASK_MANAGER_FILE_NAME);
        assertCommandFailure("import " + invalidFolderNameFile.getParentFile().getAbsolutePath(),
                String.format(UseThisCommand.MESSAGE_FILE_MISSING_ERROR, invalidFolderNameFile.getAbsolutePath()));
    }

    // End ImportCommand tests

    // UndoCommand tests

    @Test
    public void execute_undoAdd_successful() throws Exception {
        // add adam to list
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // verify added
        String addCommand = helper.generateAddCommand(toBeAdded);
        assertCommandSuccess(addCommand, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());

        // undo command
        expectedAB.removeTask(toBeAdded);
        assertCommandSuccess("undo", String.format(UndoCommand.MESSAGE_SUCCESS, addCommand), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_undoSave_successful() throws Exception {
        // save to same directory
        File tmFile = new File(".", SaveToCommand.TASK_MANAGER_FILE_NAME);
        String commandText = "saveto " + tmFile.getParentFile().getAbsolutePath();
        assertCommandSuccess(commandText, String.format(SaveToCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()),
                new TaskManager(), Collections.emptyList());
        assertTrue(FileUtil.isFileExists(tmFile));

        // undo command
        assertCommandSuccess("undo", String.format(UndoCommand.MESSAGE_SUCCESS, commandText), new TaskManager(),
                Collections.emptyList());
        assertFalse(FileUtil.isFileExists(tmFile));
    }

    @Test
    public void execute_undoExport_successful() throws Exception {
        // save to same directory
        File tmFile = new File(".", SaveToCommand.TASK_MANAGER_FILE_NAME);
        String commandText = "export " + tmFile.getParentFile().getAbsolutePath();
        assertCommandSuccess(commandText, String.format(ExportCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()),
                new TaskManager(), Collections.emptyList());
        assertTrue(FileUtil.isFileExists(tmFile));

        // undo command
        assertCommandSuccess("undo", String.format(UndoCommand.MESSAGE_SUCCESS, commandText), new TaskManager(),
                Collections.emptyList());
        assertFalse(FileUtil.isFileExists(tmFile));
    }

    @Test
    public void execute_undo_nothingToUndo() throws Exception {
        // undo command
        assertCommandFailure("undo", UndoCommand.MESSAGE_NO_PREV_COMMAND);
    }

    // End UndoCommand tests

    // RedoCommand tests

    @Test
    public void execute_undoAddRedo_successful() throws Exception {
        // add adam to list
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // verify added
        String addCommand = helper.generateAddCommand(toBeAdded);
        assertCommandSuccess(addCommand, String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());

        // undo command
        expectedAB.removeTask(toBeAdded);
        assertCommandSuccess("undo", String.format(UndoCommand.MESSAGE_SUCCESS, addCommand), expectedAB,
                expectedAB.getTaskList());

        // redo command
        expectedAB.addTask(toBeAdded);
        assertCommandSuccess("redo", String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void execute_undoSaveRedo_successful() throws Exception {
        // save to same directory
        File tmFile = new File(".", SaveToCommand.TASK_MANAGER_FILE_NAME);
        String commandText = "saveto " + tmFile.getParentFile().getAbsolutePath();
        assertCommandSuccess(commandText, String.format(SaveToCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()),
                new TaskManager(), Collections.emptyList());
        assertTrue(FileUtil.isFileExists(tmFile));

        // undo command
        assertCommandSuccess("undo", String.format(UndoCommand.MESSAGE_SUCCESS, commandText), new TaskManager(),
                Collections.emptyList());
        assertFalse(FileUtil.isFileExists(tmFile));

        // redo command
        assertCommandSuccess("redo", String.format(SaveToCommand.MESSAGE_SUCCESS, tmFile.getCanonicalPath()),
                new TaskManager(), Collections.emptyList());
        assertTrue(FileUtil.isFileExists(tmFile));
        tmFile.delete();
    }

    // End RedoCommand tests
    // @@author

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            boolean done = false;
            return new FloatingTask(name, tags, done, false);
        }

        Task bob() throws Exception {
            Name name = new Name("Bob Black");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            boolean done = false;
            return new DeadlineTask(name, tags, new Date(), done, false);
        }

        /**
         * Generates a valid task using the given seed. Running this function
         * with the same parameter values guarantees the returned task will have
         * the same state. Each unique seed will generate a unique Task object.
         *
         * @param seed
         *            used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new FloatingTask(new Name("Task " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))),
                    seed % 2 == 0, false);
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());

            UniqueTagList tags = p.getTags();
            for (Tag t : tags) {
                cmd.append(" #").append(t.tagName);
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
         *
         * @param taskManager
         *            The TaskManager to which the Tasks will be added
         */
        private void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskManager
         */
        private void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                taskManager.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         *
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

        /**
         * Generates a Task object with given name. Other fields will have some
         * dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new FloatingTask(new Name(name), new UniqueTagList(new Tag("tag")), false, false);
        }

        /**
         * Generates a Task object with given name and tag. Other fields will
         * have some dummy values.
         */
        Task generateTaskWithNameAndTags(String name, String... tagNames) throws Exception {
            ArrayList<Tag> tags = new ArrayList<Tag>();
            for (String tagName : tagNames) {
                tags.add(new Tag(tagName));
            }
            return new FloatingTask(new Name(name), new UniqueTagList(tags), false, false);
        }

        // @@author: A0144422R
        Task generateEventTaskWithNameTags(String name, int days1, int days2, String... tagNames) throws Exception {
            ArrayList<Tag> tags = new ArrayList<Tag>();
            for (String tagName : tagNames) {
                tags.add(new Tag(tagName));
            }
            Date start = DateUtils.truncate(DateUtils.addDays(new Date(), days1), Calendar.DAY_OF_MONTH);
            Date end = DateUtils.truncate(DateUtils.addDays(new Date(), days2), Calendar.DAY_OF_MONTH);
            return new EventTask(new Name(name), new UniqueTagList(tags), end, start, false, false);
        }

        // @@author: A0144422R
        Task generateDeadlineTaskWithNameTags(String name, int days, String... tagNames) throws Exception {
            ArrayList<Tag> tags = new ArrayList<Tag>();
            for (String tagName : tagNames) {
                tags.add(new Tag(tagName));
            }
            Date end = DateUtils.truncate(DateUtils.addDays(new Date(), days), Calendar.DAY_OF_MONTH);
            return new DeadlineTask(new Name(name), new UniqueTagList(tags), end, false, false);
        }
    }
}
