package seedu.taskmanager.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.taskmanager.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.taskmanager.commons.core.EventsCenter;
import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.commons.events.model.TaskManagerChangedEvent;
import seedu.taskmanager.commons.events.ui.JumpToListRequestEvent;
import seedu.taskmanager.commons.events.ui.ShowHelpRequestEvent;
import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.logic.commands.ClearCommand;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.CommandResult;
import seedu.taskmanager.logic.commands.DeleteCommand;
import seedu.taskmanager.logic.commands.DoneCommand;
import seedu.taskmanager.logic.commands.EditCommand;
import seedu.taskmanager.logic.commands.ExitCommand;
import seedu.taskmanager.logic.commands.FindCommand;
import seedu.taskmanager.logic.commands.HelpCommand;
import seedu.taskmanager.logic.commands.ListCommand;
import seedu.taskmanager.logic.commands.LoadCommand;
import seedu.taskmanager.logic.commands.SelectCommand;
import seedu.taskmanager.logic.commands.SortCommand;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.Model;
import seedu.taskmanager.model.ModelManager;
import seedu.taskmanager.model.ReadOnlyTaskManager;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.tag.Tag;
import seedu.taskmanager.model.tag.UniqueTagList;
import seedu.taskmanager.model.tag.UniqueTagList.DuplicateTagException;
import seedu.taskmanager.model.task.Description;
import seedu.taskmanager.model.task.EndDate;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.model.task.Repeat;
import seedu.taskmanager.model.task.StartDate;
import seedu.taskmanager.model.task.Status;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.Title;
import seedu.taskmanager.storage.StorageManager;
import seedu.taskmanager.ui.MainWindow;

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
    private int targetedJumpIndex;

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
        // @@author A0140032E
        logic = LogicManager.getInstance();
        logic.init(model, new StorageManager(tempTaskManagerFile, tempPreferencesFile));
        // @@author
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
    public void execute_add_invalidArgsFormat() {
        assertCommandFailure("add Valid Title s/maybe later d/valid, " + "description",
                StartDate.MESSAGE_STARTDATE_CONSTRAINTS);
        assertCommandFailure("add Valid Title s/01/03/2017 e/maybe later d/valid, " + "description",
                EndDate.MESSAGE_ENDDATE_CONSTRAINTS);
        // @@author A0140032E
        assertCommandFailure("add Valid Title s/02/01/2017 e/01/01/2017 d/valid, description",
                AddCommand.MESSAGE_DATE_ORDER_CONSTRAINTS);
        // @@author
    }

    @Test
    public void execute_add_invalidTaskData() {
        assertCommandFailure("add []\\[;] s/01/03/2017 e/05/03/2017 d/valid, description",
                Title.MESSAGE_TITLE_CONSTRAINTS);
        assertCommandFailure("add Valid Title s/not_numbers e/05/03/2017 d/valid, description",
                StartDate.MESSAGE_STARTDATE_CONSTRAINTS);
        assertCommandFailure("add Valid Title s/01/03/2017 e/not_numbers d/valid, description",
                EndDate.MESSAGE_ENDDATE_CONSTRAINTS);
        assertCommandFailure("add Valid Title s/today e/05/03/2017 d/valid, description t/invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);
        // @@author A0140032E
        assertCommandFailure("add Valid Title e/05/03/2017 d/valid, description r/year",
                AddCommand.MESSAGE_REPEAT_WITH_START_DATE_CONSTRAINTS);
        // @@author
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.t1();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded), String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTM, expectedTM.getTaskList());

    }

    // @@author A0140032E
    @Test
    public void execute_add_successful_with_inferred_end_time() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.t2();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded), String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTM, expectedTM.getTaskList());

    }

    @Test
    public void execute_add_successful_with_inferred_start_time() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.t3();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded), String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTM, expectedTM.getTaskList());
    }

    // @@author

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.t1();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task manager

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK);

    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedAB = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare task manager state
        helper.addToModel(model, 2);

        assertCommandSuccess("list", ListCommand.MESSAGE_SUCCESS_ALL, expectedAB, expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     *
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
     *
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 persons
        model.resetData(new TaskManager());
        for (Task p : taskList) {
            model.addTask(p);
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
    public void execute_select_jumpsToCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("select 2", String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2), expectedAB,
                expectedAB.getTaskList());
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

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB, expectedAB.getTaskList());
    }

    // @@author A0131278H
    @Test
    public void executeRemoveInvalidArgsFormatErrorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("remove", expectedMessage);
    }

    @Test
    public void executeRemoveIndexNotFoundErrorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("remove");
    }

    @Test
    public void executeRemoveRemovesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("remove 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void executeEditInvalidArgsFormatErrorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("edit", expectedMessage);
    }

    @Test
    public void executeEditIndexNotFoundErrorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("edit 100000");
    }

    @Test
    public void executeEditDuplicateTaskMessageShown() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithStartDate("01/03/2017");
        Task tTarget2 = helper.generateTaskWithStartDate("02/03/2017");
        Task tTarget3 = helper.generateTaskWithStartDate("03/03/2017");

        List<Task> uneditedTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
        helper.addToModel(model, uneditedTasks);

        assertCommandFailure("edit 1 s/03/03/2017", String.format(EditCommand.MESSAGE_DUPLICATE_TASK));
    }

    @Test
    public void executeEditSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithTitle("a");
        Task tTarget2 = helper.generateTaskWithTitle("b");
        Task tTarget3 = helper.generateTaskWithTitle("c");
        Task tTarget4 = helper.generateTaskWithTitle("d");

        List<Task> uneditedTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
        List<Task> editedTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget4);
        TaskManager expectedTM = helper.generateTaskManager(editedTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget1, tTarget2, tTarget4);
        helper.addToModel(model, uneditedTasks);

        assertCommandSuccess("edit 3 d", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, tTarget4), expectedTM,
                expectedList);
    }

    // @@author A0140032E
    @Test
    public void executeEditStartDateSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithStartDate("4 may 2016 3pm");
        Task tTarget2 = helper.generateTaskWithStartDate("6 may 2016 5pm");

        List<Task> uneditedTasks = helper.generateTaskList(tTarget1);
        List<Task> editedTasks = helper.generateTaskList(tTarget2);
        TaskManager expectedTM = helper.generateTaskManager(editedTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget2);
        helper.addToModel(model, uneditedTasks);

        assertCommandSuccess("edit 1 s/6 may 2016 5pm", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, tTarget2),
                expectedTM, expectedList);
    }

    @Test
    public void executeEditStartDateAfterWorkingHoursSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = new Task(new Title("Task A"), Optional.of(new StartDate("today")),
                Optional.of(new EndDate("today")), Optional.of(new Description("Some text")), Optional.ofNullable(null),
                new UniqueTagList(new Tag("tag1")));

        Task tTarget2 = new Task(new Title("Task A"), Optional.of(new StartDate("today 0am")),
                Optional.of(new EndDate("today 7am")), Optional.of(new Description("Some text")),
                Optional.ofNullable(null), new UniqueTagList(new Tag("tag1")));

        List<Task> uneditedTasks = helper.generateTaskList(tTarget1);
        List<Task> editedTasks = helper.generateTaskList(tTarget2);
        TaskManager expectedTM = helper.generateTaskManager(editedTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget2);
        helper.addToModel(model, uneditedTasks);

        assertCommandSuccess("edit 1 s/today e/today 7am",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, tTarget2), expectedTM, expectedList);
    }

    @Test
    public void executeEditEndDateSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithEndDate("1 june 2017 3am");
        Task tTarget2 = helper.generateTaskWithEndDate("3 june 2019 5am");

        List<Task> uneditedTasks = helper.generateTaskList(tTarget1);
        List<Task> editedTasks = helper.generateTaskList(tTarget2);
        TaskManager expectedTM = helper.generateTaskManager(editedTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget2);
        helper.addToModel(model, uneditedTasks);

        assertCommandSuccess("edit 1 e/3 june 2019 5am", String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, tTarget2),
                expectedTM, expectedList);
    }

    @Test
    public void executeEditMultipleFieldsSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = new Task(new Title("Task A"), Optional.of(new StartDate("01/01/2017")),
                Optional.of(new EndDate("02/01/2017")), Optional.of(new Description("Some text")),
                Optional.ofNullable(null), new UniqueTagList(new Tag("tag1")));

        Task tTarget2 = new Task(new Title("Task B"), Optional.of(new StartDate("03/01/2017")),
                Optional.of(new EndDate("04/01/2017")), Optional.of(new Description("More text")),
                Optional.of(new Repeat("DAY")), new UniqueTagList(new Tag("tag1")));

        List<Task> uneditedTasks = helper.generateTaskList(tTarget1);
        List<Task> editedTasks = helper.generateTaskList(tTarget2);
        TaskManager expectedTM = helper.generateTaskManager(editedTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget2);
        helper.addToModel(model, uneditedTasks);

        assertCommandSuccess("edit 1 Task B s/03/01/2017 e/04/01/2017 d/More text r/day",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, tTarget2), expectedTM, expectedList);

        Task tTarget3 = new Task(new Title("Task B"), Optional.of(new StartDate("yesterday")),
                Optional.of(new EndDate("next year")), Optional.of(new Description("More text")),
                Optional.of(new Repeat("WEEK")), new UniqueTagList(new Tag("tag1")));

        editedTasks = helper.generateTaskList(tTarget3);
        expectedTM = helper.generateTaskManager(editedTasks);
        expectedList = helper.generateTaskList(tTarget3);

        assertCommandSuccess("edit 1 s/yesterday e/next year r/week",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, tTarget3), expectedTM, expectedList);

        Task tTarget4 = new Task(new Title("Task C"), Optional.of(new StartDate("last year")),
                Optional.of(new EndDate("next year")), Optional.ofNullable(null),
                Optional.of(new Repeat("YEAR")), new UniqueTagList(new Tag("tag1")));

        editedTasks = helper.generateTaskList(tTarget4);
        expectedTM = helper.generateTaskManager(editedTasks);
        expectedList = helper.generateTaskList(tTarget4);

        assertCommandSuccess("edit 1 Task C s/last year d/ r/year",
                String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, tTarget4), expectedTM, expectedList);
    }

    @Test
    public void executeEditIllegalValues() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.t1();
        model.addTask(tTarget1);
        assertCommandFailure("edit 1 s/no date", StartDate.MESSAGE_STARTDATE_CONSTRAINTS);
        assertCommandFailure("edit 1 s/ r/month", EditCommand.MESSAGE_REPEAT_WITH_START_DATE_CONSTRAINTS);
        assertCommandFailure("edit 1 e/no date", EndDate.MESSAGE_ENDDATE_CONSTRAINTS);
        assertCommandFailure("edit 1 s/today e/yesterday", EditCommand.MESSAGE_DATE_ORDER_CONSTRAINTS);
        assertCommandFailure("edit 1 t/~invalid", Tag.MESSAGE_TAG_CONSTRAINTS);
        assertCommandFailure("edit 1 r/asd", Repeat.MESSAGE_REPEAT_CONSTRAINTS);
    }

    @Test
    public void executeEditDoneTaskIllegalValues() throws Exception {
        Task tTarget1 = new Task(new Title("Task A"), Optional.of(new StartDate("01/01/2017")),
                Optional.of(new EndDate("02/01/2017")), Optional.of(new Description("Some text")),
                Optional.ofNullable(null), new Status(true), new UniqueTagList(new Tag("tag1")));
        model.addTask(tTarget1);
        model.setSelectedTab(MainWindow.TAB_DONE);
        assertCommandFailure("edit 1 r/month", EditCommand.MESSAGE_REPEAT_WITH_DONE_CONSTRAINTS);
    }
    // @@author

    @Test
    public void executeChangeInvalidArgsFormatErrorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("change", expectedMessage);
    }

    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    // @@author A0140032E
    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Task tValid1 = helper.generateTaskWithTitle("bla bla KEY bla");
        Task tValid2 = helper.generateTaskWithTitle("bla KEY bla bceofeia");
        Task tValid3 = helper.generateTaskWithTitle("KEYKEYKEY sduauo");
        Task tInvalid1 = helper.generateTaskWithTitle("KE Y");

        List<Task> fourTasks = helper.generateTaskList(tValid1, tValid2, tValid3, tInvalid1);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(tValid1, tValid2, tValid3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedTM,
                expectedList);
    }
    // @@author

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateTaskWithTitle("bla bla KEY bla");
        Task t2 = helper.generateTaskWithTitle("bla KEY bla bceofeia");
        Task t3 = helper.generateTaskWithTitle("key key");
        Task t4 = helper.generateTaskWithTitle("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(t3, t1, t4, t2);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedTM,
                expectedList);
    }

    // @@author A0114269E
    @Test
    public void execute_load_invalidFilePath() throws Exception {
        assertCommandFailure("load !asdwie34$2.xml",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        assertCommandFailure("load data/taskmanager",
                String.format(Messages.MESSAGE_INVALID_XML_FORMAT, LoadCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_load_invalidXmlFile() throws Exception {
        assertCommandFailure("load src/test/data/cd_test/empty.xml", LoadCommand.MESSAGE_INVALID_DATA);
        assertCommandFailure("load src/test/data/cd_test/invalid.xml", LoadCommand.MESSAGE_INVALID_DATA);
    }

    @Test
    public void execute_done_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();

        Task tTarget1 = helper.generateTaskWithStatus(1, false);
        Task tTarget2 = helper.generateTaskWithStatus(2, false);
        Task tTarget3 = helper.generateTaskWithStatus(3, false);
        Task tTarget4 = helper.generateTaskWithStatus(3, true);

        List<Task> uneditedTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
        List<Task> editedTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget4);
        TaskManager expectedTM = helper.generateTaskManager(editedTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget1, tTarget2, tTarget4);
        helper.addToModel(model, uneditedTasks);

        // execute command and verify result
        assertCommandSuccess("done 3", String.format(DoneCommand.MESSAGE_MARK_DONE_TASK_SUCCESS, tTarget4), expectedTM,
                expectedList);
    }
    // @@author

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithTitle("bla bla KEY bla");
        Task tTarget2 = helper.generateTaskWithTitle("bla rAnDoM bla bceofeia");
        Task tTarget3 = helper.generateTaskWithTitle("key key");
        Task t1 = helper.generateTaskWithTitle("sduauo");

        List<Task> fourTasks = helper.generateTaskList(tTarget1, t1, tTarget2, tTarget3);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM, expectedList);
    }

    // @@author A0131278H
    @Test
    public void executeSortByStartDateCorrectOrderofTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithStartDate("03/03/2017");
        Task tTarget2 = helper.generateTaskWithStartDate("02/03/2017");
        Task tTarget3 = helper.generateTaskWithStartDate("01/03/2017");

        List<Task> sortedTasks = helper.generateTaskList(tTarget3, tTarget2, tTarget1);
        List<Task> unsortedTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
        TaskManager expectedTM = helper.generateTaskManager(sortedTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget3, tTarget2, tTarget1);
        helper.addToModel(model, unsortedTasks);

        assertCommandSuccess("sort s/", String.format(SortCommand.MESSAGE_SUCCESS_START), expectedTM, expectedList);
    }

    @Test
    public void executeSortByEndDateCorrectOrderofTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task tTarget1 = helper.generateTaskWithEndDate("04/04/2017");
        Task tTarget2 = helper.generateTaskWithEndDate("03/04/2017");
        Task tTarget3 = helper.generateTaskWithEndDate("02/04/2017");

        List<Task> sortedTasks = helper.generateTaskList(tTarget3, tTarget2, tTarget1);
        List<Task> unsortedTasks = helper.generateTaskList(tTarget1, tTarget2, tTarget3);
        TaskManager expectedTM = helper.generateTaskManager(sortedTasks);
        List<Task> expectedList = helper.generateTaskList(tTarget3, tTarget2, tTarget1);
        helper.addToModel(model, unsortedTasks);

        assertCommandSuccess("sort e/", String.format(SortCommand.MESSAGE_SUCCESS_END), expectedTM, expectedList);
    }
    // @@author

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task t1() throws Exception {
            Title title = new Title("Buy Handhone");
            StartDate startDate = new StartDate("12/03/2017");
            EndDate endDate = new EndDate("15/03/2017");
            Description description = new Description("Must not be iPhone");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(title, Optional.ofNullable(startDate), Optional.ofNullable(endDate),
                    Optional.ofNullable(description), Optional.ofNullable(null), tags);
        }

        // @@author A0140032E
        // task with start time after end of working hours
        Task t2() throws Exception {
            String title = "Do Homework";
            String startDate = "today 7pm";
            String endDate = "today";
            String description = "Do it fast";
            return (new AddCommand(title, Optional.ofNullable(startDate), Optional.ofNullable(endDate),
                    Optional.ofNullable(description), Optional.ofNullable(null), new HashSet<String>())).getTask();
        }

        // task with end time before start of working hours
        Task t3() throws Exception {
            String title = "Do Homework";
            String startDate = "today";
            String endDate = "today 8am";
            String description = "Do it fast";
            return (new AddCommand(title, Optional.ofNullable(startDate), Optional.ofNullable(endDate),
                    Optional.ofNullable(description), Optional.ofNullable(null), new HashSet<String>())).getTask();
        }
        // @@author

        /**
         * Generates a valid task using the given seed. Running this function
         * with the same parameter values guarantees the returned task will have
         * the same state. Each unique seed will generate a unique Task object.
         *
         * @param seed
         *            used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(new Title("Task " + seed), Optional.of(new StartDate("01/01/2017")),
                    Optional.of(new EndDate("01/01/2017")), Optional.of(new Description("House of " + seed)),
                    Optional.ofNullable(null),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }

        /**
         * Generates a valid task using the given seed and set status. Running
         * this function with the same parameter values guarantees the returned
         * task will have the same state. Each unique seed will generate a
         * unique Task object.
         *
         * @param seed
         *            used to generate the task data field values
         * @param status
         *            used to generate the status of the task
         */
        Task generateTaskWithStatus(int seed, boolean status) throws Exception {
            return new Task(new Title("Task " + seed), Optional.of(new StartDate("01/01/2017")),
                    Optional.of(new EndDate("01/01/2017")), Optional.of(new Description("House of " + seed)),
                    Optional.empty(), new Status(status),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();
            SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy h:mm a");
            cmd.append("add ");

            cmd.append(p.getTitle().toString());
            // @@author A0140032E
            cmd.append(" s/").append(p.getStartDate().isPresent() ? sdf.format(p.getStartDate().get()) : "");
            cmd.append(" e/").append(p.getEndDate().isPresent() ? sdf.format(p.getEndDate().get()) : "");
            cmd.append(" d/").append(p.getDescription().isPresent() ? p.getDescription().get() : "");
            cmd.append(" r/").append(p.getRepeat().isPresent() ? p.getRepeat().get().pattern.toString() : "");
            // @@author

            UniqueTagList tags = p.getTags();
            for (Tag t : tags) {
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
         *
         * @param taskManager
         *            The TaskManager to which the Tasks will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                taskManager.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         *
         * @param model
         *            The model to which the Persons will be added
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

        List<Task> generateTaskList(Task... persons) {
            return Arrays.asList(persons);
        }

        /**
         * Generates a Task object with given title. Other fields will have some
         * dummy values.
         */
        Task generateTaskWithTitle(String title) throws Exception {
            return new Task(new Title(title), Optional.of(new StartDate("12/03/2017")),
                    Optional.of(new EndDate("15/03/2017")), Optional.of(new Description("Buy house for 1")),
                    Optional.ofNullable(null), new UniqueTagList(new Tag("tag")));
        }

        // @@author A0131278H
        /**
         * Generates a Task object with given start date. Other fields will have
         * some dummy values.
         *
         * @throws IllegalValueException
         * @throws DuplicateTagException
         */
        public Task generateTaskWithStartDate(String startDate) throws DuplicateTagException, IllegalValueException {
            return new Task(new Title("Watch Clockwork Orange"), Optional.of(new StartDate(startDate)),
                    Optional.ofNullable(null), Optional.of(new Description("Just do it")), Optional.ofNullable(null),
                    new UniqueTagList(new Tag("tag")));
        }

        /**
         * Generates a Task object with given end date. Other fields will have
         * some dummy values.
         *
         * @throws IllegalValueException
         * @throws DuplicateTagException
         */
        public Task generateTaskWithEndDate(String endDate) throws DuplicateTagException, IllegalValueException {
            return new Task(new Title("Watch Halestorm concert"), Optional.ofNullable(null),
                    Optional.of(new EndDate(endDate)), Optional.of(new Description("Just do it")),
                    Optional.ofNullable(null), new UniqueTagList(new Tag("tag")));
        }
        // @@author A0131278H
    }
}
