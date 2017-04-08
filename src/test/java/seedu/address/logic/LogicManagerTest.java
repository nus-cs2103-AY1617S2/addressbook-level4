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
import java.util.Optional;

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
import seedu.address.model.task.DateTime;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTasks;

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
    private TypicalTasks td;

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

    // --------------- Initialize ---------------

    @Before
    public void setUp() throws DuplicateTaskException {
        model = new ModelManager();
        String tempTaskManagerFile = saveFolder.getRoot().getPath() + "TempTaskManager.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        String tempConfigFile = saveFolder.getRoot().getPath() + "TempConfig.json";
        config = new Config(tempConfigFile);
        config.setTaskManagerFilePath(tempTaskManagerFile);
        config.setUserPrefsFilePath(tempPreferencesFile);
        storage = new StorageManager(config);
        logic = new LogicManager(model);
        td = new TypicalTasks();
        EventsCenter.getInstance().registerHandler(this);
        populateModelWithTypicalTasks();

        // last saved assumed to be up to date
        latestSavedTaskManager = new TaskManager(model.getTaskManager());

        helpShown = false;
        targetedJumpIndex = ""; // non yet
    }

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }

    // @author A0093999Y
    // --------------- Helper ---------------

    /**
     * Populates the model with tasks from TypicalTask class in the testutil
     * package
     * 
     * @throws DuplicateTaskException
     */
    private void populateModelWithTypicalTasks() throws DuplicateTaskException {
        Task[] tasks = td.getTypicalTasks();
        for (Task task : tasks) {
            model.addTask(task);
        }
    }

    // @author
    // --------------- Assert ---------------

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
        assertCommandFailure(commandWord + " F100", expectedMessage);
    }

    // --------------- Execute ---------------

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

    // ------------- Help Command -----------------

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, td.getTypicalTaskManager(),
                Arrays.asList(td.getTypicalTasks()));
        assertTrue(helpShown);
    }

    // --------------- Exit Command ---------------

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, td.getTypicalTaskManager(),
                Arrays.asList(td.getTypicalTasks()));
    }

    // --------------- Clear ---------------

    @Test
    public void execute_clear() throws Exception {
        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }

    // --------------- Add ---------------

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TaskManager expectedAB = td.getTypicalTaskManager();
        expectedAB.addTask(td.extraFloat);

        // execute command and verify result
        assertCommandSuccess(TestUtil.makeAddCommandString(td.extraFloat),
                String.format(AddCommand.MESSAGE_SUCCESS, td.extraFloat), expectedAB, expectedAB.getTaskList());

    }

    // @@author A0144422R
    @Test
    public void execute_add_event_successful() throws Exception {
        // setup expectations
        Task toBeAdded = TestUtil.generateEventTaskWithNameTags("name", 0, 1, "tag1");
        TaskManager expectedAB = td.getTypicalTaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess("add name from today 0000 to tomorrow 0000 #tag1",
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedAB, expectedAB.getTaskList());

    }

    @Test
    public void execute_add_deadline_task_successful() throws Exception {
        // setup expectations
        Task toBeAdded = TestUtil.generateDeadlineTaskWithNameTags("name", 0, "tag1");
        TaskManager expectedAB = td.getTypicalTaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess("add name due today 0000 #tag1", String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        Task toBeAdded = td.futureListEvent;

        // execute command and verify result
        assertCommandFailure(TestUtil.makeAddCommandString(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK);

    }

    // ------------- List Command -----------------

    // author A0144422R
    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TaskManager expectedAB = td.getTypicalTaskManager();
        expectedAB.addTask(td.extraDeadline);
        model.addTask(td.extraDeadline);

        assertCommandSuccess("list", ListCommand.MESSAGE_SUCCESS, expectedAB, expectedAB.getTaskList());
    }

    // ---------- Delete Command --------------
    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TaskManager expectedAB = td.getTypicalTaskManager();
        expectedAB.removeTask(td.futureListDeadline);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("delete F2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, td.futureListDeadline), expectedAB,
                expectedAB.getTaskList());
    }

    // ---------- Done Command ---------------

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
        TaskManager expectedAB = td.getTypicalTaskManager();
        Task doneTask = Task.createTask(td.futureListFloat);
        doneTask.setDone(true);
        expectedAB.updateTask(5, doneTask);

        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("done F1", String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, doneTask), expectedAB,
                expectedAB.getTaskList());
    }

    // ------------ Not Done Command ---------------

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
        TaskManager expectedAB = td.getTypicalTaskManager();
        Task notDoneTask = Task.createTask(td.completedListFloat);
        notDoneTask.setDone(false);
        expectedAB.updateTask(8, notDoneTask);

        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("notdone C1", String.format(NotDoneCommand.MESSAGE_NOTDONE_TASK_SUCCESS, notDoneTask),
                expectedAB, expectedAB.getTaskList());
    }

    // -------------- Edit Command ------------------

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
        TaskManager expectedAB = td.getTypicalTaskManager();
        Task taskToEdit = Task.createTask(td.completedListFloat);
        taskToEdit.setName(new Name("new name"));
        expectedAB.updateTask(8, taskToEdit);
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("edit C1 new name",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, td.completedListFloat), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_editNameAndTag_successful() throws Exception {
        TaskManager expectedAB = td.getTypicalTaskManager();
        Task taskToEdit = Task.createTask(td.completedListFloat);
        taskToEdit.setName(new Name("new name"));
        UniqueTagList tags = new UniqueTagList("tag1", "tag2");
        taskToEdit.setTags(tags);
        expectedAB.updateTask(8, taskToEdit);

        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("edit C1 new name #tag1 #tag2",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, td.completedListFloat), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_editEventNameAndTag_successful() throws Exception {
        TaskManager expectedAB = td.getTypicalTaskManager();
        DateTime start = new DateTime(DateUtils.truncate(DateUtils.addDays(new Date(), 0), Calendar.DAY_OF_MONTH));
        DateTime end = new DateTime(DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DAY_OF_MONTH));
        Task taskToEdit = Task.createTask(new Name("new name"), new UniqueTagList("tag1", "tag2"), Optional.of(end),
                Optional.of(start), td.completedListFloat.isDone(), td.completedListFloat.isManualToday());
        expectedAB.updateTask(8, taskToEdit);

        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("edit C1 new name from today at 0000 to tomorrow at 0000 #tag1 #tag2",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, td.completedListFloat), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_editDeadlineTaskTag_successful() throws Exception {
        TaskManager expectedAB = td.getTypicalTaskManager();
        DateTime end = new DateTime(DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DAY_OF_MONTH));
        Task taskToEdit = Task.createTask(td.completedListFloat.getName(), new UniqueTagList("tag1", "tag2"),
                Optional.of(end), Optional.empty(), td.completedListFloat.isDone(),
                td.completedListFloat.isManualToday());
        expectedAB.updateTask(8, taskToEdit);

        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("edit C1 due tomorrow at 0000 #tag1 #tag2",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, td.completedListFloat), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_editConfusingName_successful() throws Exception {
        TaskManager expectedAB = td.getTypicalTaskManager();
        Task taskToEdit = Task.createTask(td.completedListFloat);
        taskToEdit.setName(new Name("from today uihasduhas to tomorrow uhaius"));
        taskToEdit.setTags(new UniqueTagList("tag1", "tag2"));
        expectedAB.updateTask(8, taskToEdit);

        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("edit C1 from today uihasduhas to tomorrow uhaius #tag1 #tag2",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, td.completedListFloat), expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_editDuplicateTask_notAllowed() throws Exception {
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandFailure("edit C1 " + td.futureListFloat.getName().toString() + " #tag3 #tag4",
                EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_editIndexNotFound_notAllowed() throws Exception {
        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandFailure("edit C100 Task 3 #tag3 #tag4", Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    // @@author A0093999Y
    // ----------------- Today Command ------------------

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
        TaskManager expectedAB = td.getTypicalTaskManager();
        Task taskToToday = Task.createTask(td.futureListDeadline);
        taskToToday.setToday(true);
        expectedAB.updateTask(6, taskToToday);

        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("today F2", String.format(TodayCommand.MESSAGE_TODAY_TASK_SUCCESS, td.futureListDeadline),
                expectedAB, expectedAB.getTaskList());
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
        TaskManager expectedAB = td.getTypicalTaskManager();
        Task taskToNotToday = Task.createTask(td.todayListFloat);
        taskToNotToday.setToday(false);
        expectedAB.updateTask(1, taskToNotToday);

        model.prepareTaskList(FXCollections.observableArrayList(), FXCollections.observableArrayList(),
                FXCollections.observableArrayList());
        assertCommandSuccess("nottoday T2",
                String.format(NotTodayCommand.MESSAGE_NOTTODAY_TASK_SUCCESS, taskToNotToday), expectedAB,
                expectedAB.getTaskList());
    }

    // --------------- Find Command -------------------

    // @@author
    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNamesAndNotCaseSensitive() throws Exception {
        TaskManager expectedAB = td.getTypicalTaskManager();
        List<Task> expectedList = Arrays.asList(td.completedListEvent);

        assertCommandSuccess("find go", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInTagsAndNotCaseSensitive() throws Exception {
        TaskManager expectedAB = td.getTypicalTaskManager();
        List<Task> expectedList = Arrays.asList(td.todayListFloat, td.futureListDeadline, td.futureListEvent,
                td.completedListFloat);

        assertCommandSuccess("find project", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    // @@author: A0144422R
    @Test
    public void execute_find_date() throws Exception {
        TaskManager expectedAB = td.getTypicalTaskManager();
        List<Task> expectedList = Arrays.asList(td.todayListDeadline, td.completedListDeadline, td.completedListEvent);

        assertCommandSuccess("find due today", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    @Test
    public void execute_find_multipleArgs() throws Exception {
        TaskManager expectedAB = td.getTypicalTaskManager();
        List<Task> expectedList = Arrays.asList(td.completedListDeadline, td.completedListEvent);

        assertCommandSuccess("find go goes", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB,
                expectedList);
    }

    // ------------------------ Rename Tag Command -----------------------

    // @@author A0093999Y
    @Test
    public void execute_renametag_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTagCommand.MESSAGE_USAGE);
        assertCommandFailure("renametag ", expectedMessage);
    }

    @Test
    public void execute_renametag_onlyMatchesFullWordsCorrectCaseInTags() throws Exception {
        TaskManager expectedAB = td.getTypicalTaskManager();
        Task taskToRenameTag1 = Task.createTask(td.todayListFloat);
        Task taskToRenameTag2 = Task.createTask(td.completedListFloat);
        UniqueTagList replacementTags = new UniqueTagList();
        replacementTags.add(new Tag("lol"));
        taskToRenameTag1.setTags(replacementTags);
        taskToRenameTag2.setTags(replacementTags);
        expectedAB.updateTask(1, taskToRenameTag1);
        expectedAB.updateTask(8, taskToRenameTag2);

        assertCommandSuccess("renametag project lol",
                String.format(RenameTagCommand.MESSAGE_RENAME_TAG_SUCCESS, "project", "lol"), expectedAB,
                expectedAB.getTaskList());
    }

    // ---------------- Delete Tag Command ----------------------

    @Test
    public void execute_deletetag_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);
        assertCommandFailure("deletetag ", expectedMessage);
    }

    @Test
    public void execute_deletetag_onlyMatchesFullWordsAndCorrectCaseInTags() throws Exception {
        TaskManager expectedAB = td.getTypicalTaskManager();
        Task taskToRenameTag1 = Task.createTask(td.todayListFloat);
        Task taskToRenameTag2 = Task.createTask(td.completedListFloat);
        UniqueTagList replacementTags = new UniqueTagList();
        taskToRenameTag1.setTags(replacementTags);
        taskToRenameTag2.setTags(replacementTags);
        expectedAB.updateTask(1, taskToRenameTag1);
        expectedAB.updateTask(8, taskToRenameTag2);

        assertCommandSuccess("deletetag project", String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, "KEY"),
                expectedAB, expectedAB.getTaskList());
    }

    // --------------------- SaveTo Command ----------------------
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

    // ----------------- Export Command ----------------------

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

    // --------------------- UseThisCommand -----------------------

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

    // ------------------------- ImportCommand ------------------------

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

    // ------------------- Undo Command ---------------------

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

    // ----------------- Redo Command -------------------------

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

    }
}
