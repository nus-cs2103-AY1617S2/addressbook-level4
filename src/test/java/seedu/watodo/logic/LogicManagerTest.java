package seedu.watodo.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.watodo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.watodo.commons.core.Config;
import seedu.watodo.commons.core.EventsCenter;
import seedu.watodo.commons.events.model.TaskListChangedEvent;
import seedu.watodo.commons.events.storage.StorageFilePathChangedEvent;
import seedu.watodo.commons.events.ui.JumpToListRequestEvent;
import seedu.watodo.commons.events.ui.ShowHelpRequestEvent;
import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.commons.util.ConfigUtil;
import seedu.watodo.logic.commands.AddCommand;
import seedu.watodo.logic.commands.ClearCommand;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.CommandResult;
import seedu.watodo.logic.commands.DeleteCommand;
import seedu.watodo.logic.commands.EditCommand;
import seedu.watodo.logic.commands.ExitCommand;
import seedu.watodo.logic.commands.FindCommand;
import seedu.watodo.logic.commands.HelpCommand;
import seedu.watodo.logic.commands.ListCommand;
import seedu.watodo.logic.commands.MarkCommand;
import seedu.watodo.logic.commands.SaveAsCommand;
import seedu.watodo.logic.commands.SelectCommand;
import seedu.watodo.logic.commands.UnmarkCommand;
import seedu.watodo.logic.commands.ViewFileCommand;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.logic.parser.DateTimeParser;
import seedu.watodo.model.Model;
import seedu.watodo.model.ModelManager;
import seedu.watodo.model.ReadOnlyTaskManager;
import seedu.watodo.model.TaskManager;
import seedu.watodo.model.tag.Tag;
import seedu.watodo.model.tag.UniqueTagList;
import seedu.watodo.model.task.DateTime;
import seedu.watodo.model.task.Description;
import seedu.watodo.model.task.ReadOnlyTask;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.TaskStatus;
import seedu.watodo.storage.StorageManager;
import seedu.watodo.testutil.EventsCollector;

public class LogicManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    private void handleLocalModelChangedEvent(TaskListChangedEvent abce) {
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
        logic = new LogicManager(model, new StorageManager(tempTaskManagerFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskManager = new TaskManager(model.getTaskManager()); // last saved assumed to be
        // up to date
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }

    // ================ For all commands ==============================

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
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand,
            String expectedMessage, ReadOnlyTaskManager expectedTaskManager,
            List<? extends ReadOnlyTask> expectedShownList) {

        try {
            CommandResult result;
            result = logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        } catch (IllegalValueException e) {
            e.printStackTrace();
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

    // ================ For Add Command ==============================

    //@@author A0143076J
    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add", expectedMessage); //args is empty string
        assertCommandFailure("add    ", expectedMessage); //args is whitespaces
        assertCommandFailure("add by/tmr #tag", expectedMessage); //no task description
        assertCommandFailure("add Valid Description by/notValidDate #validTag", DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        assertCommandFailure("add Valid Description by/today by/tmr #validTag",
                DateTimeParser.MESSAGE_INVALID_DATETIME_PREFIX_COMBI);
        assertCommandFailure("add task with start but no end date from/tmr #validTag",
                DateTimeParser.MESSAGE_INVALID_DATETIME_PREFIX_COMBI);
        assertCommandFailure("add Valid Description from/1 may to/3 may #><-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    // equivalence partitions are a floating task w/o dates, and 2 valid formats each for deadline and event tasks
    // 1. adding a task without any dates
    // 2. by/...
    // 3. on/...
    // 4. from/... to/...
    // 5. on/... to/...
    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();

        List<Task> tasksToBeAdded = helper.generateTaskList(//covers the partition 1, 2, and 5
                helper.floating(), helper.deadline(), helper.event());
        TaskManager expectedTM = new TaskManager();
        for (Task toBeAdded : tasksToBeAdded) {
            expectedTM.addTask(toBeAdded);
            // execute command and verify result
            assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                    String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedTM, expectedTM.getTaskList());
        }
        //equivalence partition 3
        Task deadline3 = helper.generateTaskWithDescriptionAndEndDate("CS2103 v0.5 final", "10 apr 11.59 pm");
        StringBuilder builder = new StringBuilder();
        builder.append("add ").append(deadline3.getDescription()).append(" on/").append(deadline3.getEndDate());
        expectedTM.addTask(deadline3);
        assertCommandSuccess(builder.toString(), String.format(AddCommand.MESSAGE_SUCCESS, deadline3),
                expectedTM, expectedTM.getTaskList());
        //equivalence partition 5
        Task event5 = helper.generateTaskWithDescriptionAndStartEndDates(
                "no sleep code everyday", "today", "next sun", "life");
        builder = new StringBuilder();
        builder.append("add ").append(event5.getDescription()).append(" on/").append(event5.getStartDate())
        .append(" to/").append(event5.getEndDate());
        UniqueTagList tags = event5.getTags();
        for (Tag t : tags) {
            builder.append(" #").append(t.tagName);
        }
        expectedTM.addTask(event5);
        assertCommandSuccess(builder.toString(), String.format(AddCommand.MESSAGE_SUCCESS, event5),
                expectedTM, expectedTM.getTaskList());
    }

    @Test
    public void execute_addflexibleArgsFormat_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task testTask1 = helper.generateTaskWithDescriptionAndStartEndDates(
                "watch webcast", "next tues", "next fri", "BackLog");
        Task testTask2 = helper.generateTaskWithDescriptionAndStartEndDates(
                "study for finals", "today", "may 5", "letgo");
        Task testTask3 = new Task(new Description("ie2150 project"), new DateTime("11 apr 12pm"),
                new UniqueTagList(new Tag("DED"), new Tag("10000words")));
        TaskManager expectedTM = new TaskManager();

        expectedTM.addTask(testTask1);
        String command = "add from/next tues to/next fri watch webcast #BackLog";
        assertCommandSuccess(command, String.format(AddCommand.MESSAGE_SUCCESS, testTask1),
                expectedTM, expectedTM.getTaskList());

        expectedTM.addTask(testTask2);
        command = "add to/ may 5 study for finals from/today #letgo";
        assertCommandSuccess(command, String.format(AddCommand.MESSAGE_SUCCESS, testTask2),
                expectedTM, expectedTM.getTaskList());

        expectedTM.addTask(testTask3);
        command = "add #DED by/ 11 apr 12pm ie2150 project #10000words";
        assertCommandSuccess(command, String.format(AddCommand.MESSAGE_SUCCESS, testTask3),
                expectedTM, expectedTM.getTaskList());
    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        List<Task> tasksToBeAdded = helper.generateTaskList(
                helper.floating(), helper.deadline(), helper.event());

        // setup starting state
        for (Task toBeAdded : tasksToBeAdded) {
            model.addTask(toBeAdded); // task already in internal task manager
            // execute command and verify result
            assertCommandFailure(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK);
        }
    }

    @Test
    public void execute_addEventTaskEndDateEarlierThanStartDate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task invalidEvent = helper.generateTaskWithDescriptionAndStartEndDates("meeting", "next week", "today", "work");

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(invalidEvent), DateTime.MESSAGE_DATETIME_START_LATER_THAN_END);
    }

    // ================ For Edit Command ==============================

    //@@author A0143076J-reused
    //similar to EditCommandTest cases under guitests package
    @Test
    public void execute_editInvalidFormat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
        assertCommandFailure("edit   ", expectedMessage);
        //missing task index
        assertCommandFailure("edit p", expectedMessage);
        assertCommandFailure("edit updated description but missing index", expectedMessage);
        //invalid task index
        assertCommandFailure("edit 0 updatedDescription", expectedMessage);
        assertCommandFailure("edit -1 updatedDescription", expectedMessage);
    }

    @Test
    public void execute_editNoFieldsSpecified_failure() {
        assertCommandFailure("edit 1", EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_editInvalidValues_failure() {
        assertCommandFailure("edit 1 by/invalid date", DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        assertCommandFailure("edit 1 from/thurs", DateTimeParser.MESSAGE_INVALID_DATETIME_PREFIX_COMBI);
        assertCommandFailure("edit 1 #$%^^", Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    // ================ For List Command ==============================

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
     * @param commandWord to test assuming it targets a single task in the last
     *            shown list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertCommandFailure(commandWord, expectedMessage); // index missing
        assertCommandFailure(commandWord + " +1", expectedMessage); // index should be unsigned
        assertCommandFailure(commandWord + " -1", expectedMessage); // index should be unsigned
        assertCommandFailure(commandWord + " 0", expectedMessage); // index cannot be 0
        assertCommandFailure(commandWord + " not_a_number", expectedMessage);
    }

    //@@author
    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     *
     * @param commandWord to test assuming it targets a single task in the last
     *            shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set TM state to 2 tasks
        model.resetData(new TaskManager());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandFailure(commandWord + " 3", expectedMessage);
    }

    // ================ For Select Command ==============================

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

        TaskManager expectedTM = helper.generateTaskManager(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("select 2", String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedTM, expectedTM.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }

    // ================ For Delete Command ==============================

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedTM = helper.generateTaskManager(threeTasks);
        expectedTM.removeTask(threeTasks.get(0));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 1", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESSFUL,
                1, threeTasks.get(0) + "\n"), expectedTM, expectedTM.getTaskList());
    }

    //@@author A0141077L-reused
    @Test
    public void execute_deleteIndexMissing_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE);
        assertCommandFailure("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotUnsignedPositiveInteger_errorMessageShown() throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX + '\n' +
                DeleteCommand.MESSAGE_USAGE;
        assertCommandFailure("delete +1", expectedMessage); // index should be unsigned
        assertCommandFailure("delete -1", expectedMessage);
        assertCommandFailure("delete hello", expectedMessage);
    }

    @Test
    public void execute_deleteIndexOutOfBounds_errorMessageShown() throws Exception {
        final StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(String.format(DeleteCommand.MESSAGE_DELETE_TASK_UNSUCCESSFUL, 4) + '\n'
                + DeleteCommand.MESSAGE_INDEX_OUT_OF_BOUNDS + '\n');
        assertCommandFailure("delete 4", expectedMessage.toString());
    }

    @Test
    public void execute_deleteDuplicateIndices_errorMessageShown() {
        String expectedMessage = DeleteCommand.MESSAGE_DUPLICATE_INDICES;
        assertCommandFailure("delete 1 2 1", expectedMessage);
    }

    @Test
    public void execute_deleteMultipleIndicesInDescendingOrder_success() throws IllegalValueException {
        String messageForNonDescendingIndices = "";
        String messageForDescendingIndices = "";

        try {
            logic.execute("delete 1 4 2");
        } catch (CommandException e1) {
            //expect all indices to be unsuccessful, and error message to be printed for each index in descending order
            messageForNonDescendingIndices = new String (e1.getMessage());
        }

        try {
            logic.execute("delete 4 2 1");
        } catch (CommandException e2) {
            //expect all indices to be unsuccessful, and error message to be printed for each index
            messageForDescendingIndices = new String (e2.getMessage());
        }

        assertEquals(messageForDescendingIndices, messageForNonDescendingIndices);
    }

    @Test
    public void execute_deleteValidIndicesAmongstInvalidOnes_success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithDescription("Task1");
        List<Task> twoTasks = helper.generateTaskList(p1);
        helper.addToModel(model, twoTasks);

        final StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(DeleteCommand.MESSAGE_INCOMPLETE_EXECUTION + '\n');
        expectedMessage.append(String.format(DeleteCommand.MESSAGE_DELETE_TASK_UNSUCCESSFUL, 2) + '\n'
                + DeleteCommand.MESSAGE_INDEX_OUT_OF_BOUNDS + '\n');
        expectedMessage.append(String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESSFUL, 1, p1) + '\n');

        String actualMessage = "";
        try {
            //indices 2 is invalid (OOB) and will be executed before index 1.
            logic.execute("delete 1 2");
        } catch (CommandException e1) {
            actualMessage = new String (e1.getMessage());
        }

        assertEquals(expectedMessage.toString(), actualMessage);
    }

    //@@author


    // ================ For Mark Command ==============================

    //@@author A0141077L
    @Test
    public void execute_markIndexMissing_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkCommand.MESSAGE_USAGE);
        assertCommandFailure("mark", expectedMessage);
    }

    @Test
    public void execute_markIndexNotUnsignedPositiveInteger_errorMessageShown() throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX + '\n' +
                MarkCommand.MESSAGE_USAGE;
        assertCommandFailure("mark +1", expectedMessage); // index should be unsigned
        assertCommandFailure("mark -1", expectedMessage);
        assertCommandFailure("mark hello", expectedMessage);
    }

    @Test
    public void execute_markIndexOutOfBounds_errorMessageShown() throws Exception {
        final StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(String.format(MarkCommand.MESSAGE_MARK_TASK_UNSUCCESSFUL, 4) + '\n'
                + MarkCommand.MESSAGE_INDEX_OUT_OF_BOUNDS + '\n');
        assertCommandFailure("mark 4", expectedMessage.toString());
    }

    @Test
    public void execute_markDuplicateIndices_errorMessageShown() {
        String expectedMessage = MarkCommand.MESSAGE_DUPLICATE_INDICES;
        assertCommandFailure("mark 1 2 1", expectedMessage);
    }

    @Test
    public void execute_markMultipleIndicesInDescendingOrder_success() throws IllegalValueException {
        String messageForNonDescendingIndices = "";
        String messageForDescendingIndices = "";

        try {
            logic.execute("mark 1 4 2");
        } catch (CommandException e1) {
            //expect all indices to be unsuccessful, and error message to be printed for each index in descending order
            messageForNonDescendingIndices = new String (e1.getMessage());
        }

        try {
            logic.execute("mark 4 2 1");
        } catch (CommandException e2) {
            //expect all indices to be unsuccessful, and error message to be printed for each index
            messageForDescendingIndices = new String (e2.getMessage());
        }

        assertEquals(messageForDescendingIndices, messageForNonDescendingIndices);
    }

    @Test
    public void execute_markValidIndicesAmongstInvalidOnes_success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithDescription("undoneTask1");
        Task p2 = helper.generateTaskWithDescription("doneTask2");
        List<Task> twoTasks = helper.generateTaskList(p1, p2);
        helper.addToModel(model, twoTasks);
        p2.setStatus(TaskStatus.DONE);

        final StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(MarkCommand.MESSAGE_INCOMPLETE_EXECUTION + '\n');
        expectedMessage.append(String.format(MarkCommand.MESSAGE_MARK_TASK_UNSUCCESSFUL, 3) + '\n'
                + MarkCommand.MESSAGE_INDEX_OUT_OF_BOUNDS + '\n');
        expectedMessage.append(String.format(MarkCommand.MESSAGE_MARK_TASK_UNSUCCESSFUL, 2) + '\n'
                + MarkCommand.MESSAGE_STATUS_ALREADY_DONE + '\n');
        expectedMessage.append(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESSFUL, 1, p1) + '\n');

        String actualMessage = "";
        try {
            //indices 2 and 3 are invalid (one already marked, one OOB) and will be executed before index 1.
            logic.execute("mark 1 3 2");
        } catch (CommandException e1) {
            actualMessage = new String (e1.getMessage());
        }

        assertEquals(expectedMessage.toString(), actualMessage);
    }

    //@@author



    // ================ For Unmark Command ==============================

    //@@author A0141077L-reused
    @Test
    public void execute_unmarkIndexMissing_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkCommand.MESSAGE_USAGE);
        assertCommandFailure("unmark", expectedMessage);
    }

    @Test
    public void execute_unmarkIndexNotUnsignedPositiveInteger_errorMessageShown() throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX + '\n' +
                UnmarkCommand.MESSAGE_USAGE;
        assertCommandFailure("unmark +1", expectedMessage); // index should be unsigned
        assertCommandFailure("unmark -1", expectedMessage);
        assertCommandFailure("unmark hello", expectedMessage);
    }

    @Test
    public void execute_unmarkIndexOutOfBounds_errorMessageShown() throws Exception {
        final StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_UNSUCCESSFUL, 4) + '\n'
                + UnmarkCommand.MESSAGE_INDEX_OUT_OF_BOUNDS + '\n');
        assertCommandFailure("unmark 4", expectedMessage.toString());
    }

    @Test
    public void execute_unmarkDuplicateIndices_errorMessageShown() {
        String expectedMessage = UnmarkCommand.MESSAGE_DUPLICATE_INDICES;
        assertCommandFailure("unmark 1 2 1", expectedMessage);
    }

    @Test
    public void execute_unmarkMultipleIndicesInDescendingOrder_success() throws IllegalValueException {
        String messageForNonDescendingIndices = "";
        String messageForDescendingIndices = "";

        try {
            logic.execute("unmark 1 4 2");
        } catch (CommandException e1) {
            //expect all indices to be unsuccessful, and error message to be printed for each index in descending order
            messageForNonDescendingIndices = new String (e1.getMessage());
        }

        try {
            logic.execute("unmark 4 2 1");
        } catch (CommandException e2) {
            //expect all indices to be unsuccessful, and error message to be printed for each index
            messageForDescendingIndices = new String (e2.getMessage());
        }

        assertEquals(messageForDescendingIndices, messageForNonDescendingIndices);
    }

    @Test
    public void execute_unmarkValidIndicesAmongstInvalidOnes_success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithDescription("doneTask1");
        Task p2 = helper.generateTaskWithDescription("undoneTask2");
        List<Task> twoTasks = helper.generateTaskList(p1, p2);
        helper.addToModel(model, twoTasks);
        p1.setStatus(TaskStatus.DONE);

        final StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(UnmarkCommand.MESSAGE_INCOMPLETE_EXECUTION + '\n');
        expectedMessage.append(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_UNSUCCESSFUL, 3) + '\n'
                + UnmarkCommand.MESSAGE_INDEX_OUT_OF_BOUNDS + '\n');
        expectedMessage.append(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_UNSUCCESSFUL, 2) + '\n'
                + UnmarkCommand.MESSAGE_STATUS_ALREADY_UNDONE + '\n');
        expectedMessage.append(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESSFUL, 1, p1) + '\n');

        String actualMessage = "";
        try {
            //indices 2 and 3 are invalid (one already unmarked, one OOB) and will be executed before index 1.
            logic.execute("unmark 1 3 2");
        } catch (CommandException e1) {
            actualMessage = new String (e1.getMessage());
        }

        assertEquals(expectedMessage.toString(), actualMessage);
    }

    //@@author

    // ================ For Find Command ==============================

    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_matchesFullAndPartialWordsInDescriptions() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithDescription("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithDescription("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithDescription("KE Y");
        Task p2 = helper.generateTaskWithDescription("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, p2, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM, expectedList);
    }

    //@@author A0143076J
    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithDescription("bla bla KEY bla");
        Task p2 = helper.generateTaskWithDescription("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithDescription("keyop keyul"); //partial word match
        Task p4 = helper.generateTaskWithDescription("KEykitgua sduauo"); //partial word match

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithDescription("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithDescription("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithDescription("key key");
        Task p1 = helper.generateTaskWithDescription("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key random", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    //@@ author

    // ================ For SaveAs Command ==============================
    //@@author A0141077L

    @Test
    public void execute_saveasFilePathNotFound_errorMessageShown() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveAsCommand.MESSAGE_USAGE);
        assertCommandFailure("saveas", expectedMessage);
    }

    @Test
    public void execute_saveas_nullFilePath_assertionFailure() {
        thrown.expect(AssertionError.class);
        new SaveAsCommand(null);
    }

    @Test
    public void execute_saveas_notXmlFormat_invalidCommand() {
        assertCommandFailure("saveas filePathWithoutXmlExtension", SaveAsCommand.MESSAGE_INVALID_FILE_PATH_EXTENSION);
        assertCommandFailure("saveas filePath.WithOtherExtension", SaveAsCommand.MESSAGE_INVALID_FILE_PATH_EXTENSION);
    }

    @Test
    public void execute_saveToSameFilePath_exceptionThrown() throws CommandException {
        thrown.expect(CommandException.class);
        Config config = new Config();
        String oldFilePath = config.getWatodoFilePath();
        new SaveAsCommand(oldFilePath).execute();
    }

    @Test
    public void execute_saveToSameFilePath_errorMessageShown() throws CommandException {
        Config config = new Config();
        String oldFilePath = config.getWatodoFilePath();
        assertCommandFailure("saveas " + oldFilePath, SaveAsCommand.MESSAGE_DUPLICATE_FILE_PATH);
    }

    @Test
    public void execute_saveas_handleStorageFilePathChangedEvent_eventRaised()
            throws CommandException, IllegalValueException, IOException {
        Config config = new Config();
        String originalFilePath = config.getWatodoFilePath();

        EventsCollector eventCollector = new EventsCollector();
        logic.execute("saveas newFilePath.xml");
        assertTrue(eventCollector.get(0) instanceof StorageFilePathChangedEvent);

        config.setWatodoFilePath(originalFilePath); //reset config to original file path
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
    }

    @Test
    public void execute_saveas_success() throws IOException {
        Config config = new Config();
        String originalFilePath = config.getWatodoFilePath();

        String newFilePath = "newFilePath.xml";
        String expectedMessage = String.format(SaveAsCommand.MESSAGE_SUCCESS, newFilePath);

        assertCommandSuccess("saveas " + newFilePath, expectedMessage, new TaskManager(), Collections.emptyList());

        config.setWatodoFilePath(originalFilePath); //reset config to original file path
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
    }

    //@@author

    // ================ For ViewFile Command ==============================

    //@@author A0141077L
    @Test
    public void execute_viewfile_succes() {
        Config config = new Config();
        String oldFilePath = config.getWatodoFilePath();
        String expectedMessage = String.format(ViewFileCommand.VIEW_FILE_MESSAGE, oldFilePath);

        String actualMessage = new ViewFileCommand().execute().feedbackToUser;

        assertEquals(expectedMessage, actualMessage);
    }

    //@@author

    // ================ To generate test data ==============================

    //@@author A0143076J
    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task floating() throws Exception {
            Description description = new Description("read Lord of the Rings");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(description, tags);
        }

        Task deadline() throws Exception {
            Description description = new Description("project report");
            DateTime deadline = new DateTime("thurs 4pm");
            Tag tag1 = new Tag("urgent");
            Tag tag2 = new Tag("longertag2");
            Tag tag3 = new Tag("impt");
            UniqueTagList tags = new UniqueTagList(tag1, tag2, tag3);
            return new Task(description, deadline, tags);
        }

        Task event() throws Exception {
            Description description = new Description("career fair exhibition");
            DateTime startDate = new DateTime("mar 4 at 9am");
            DateTime endDate = new DateTime("2.30pm mar 6");
            Tag tag1 = new Tag("work");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(description, startDate, endDate, tags);
        }

        /**
         * Generates a valid task using the given seed. Running this function
         * with the same parameter values guarantees the returned task will have
         * the same state. Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(new Description("Task "
                    + seed), new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getDescription().toString());

            UniqueTagList tags = p.getTags();
            for (Tag t : tags) {
                cmd.append(" #").append(t.tagName);
            }

            if (p.getStartDate() != null) { // event
                cmd.append(" from/").append(p.getStartDate().toString());
                cmd.append(" to/").append(p.getEndDate().toString());
            }

            if (p.getEndDate() != null && p.getStartDate() == null) { // deadline
                cmd.append(" by/").append(p.getEndDate().toString());
            }

            return cmd.toString();
        }

        /**
         * Generates a TaskManager with auto-generated tasks.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates a TaskManager based on the list of tasks given.
         */
        TaskManager generateTaskManager(List<Task> tasks) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, tasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Task objects to the given TaskManager
         *
         * @param taskManager The TaskManager to which the Tasks will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                taskManager.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         *
         * @param model The model to which the Tasks will be added
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
        Task generateTaskWithDescription(String name) throws Exception {
            return new Task(new Description(name), new UniqueTagList(new Tag("tag")));
        }

        /**
         * Generates a Task object with given name and endDate. Other fields will have some
         * dummy values.
         */
        Task generateTaskWithDescriptionAndEndDate(String name, String endDate) throws Exception {
            return new Task(new Description(name), new DateTime(endDate), new UniqueTagList());
        }

        /**
         * Generates a Task object with given name, startDate and endDate, and a tag.
         * Other fields will have some dummy values.
         */
        Task generateTaskWithDescriptionAndStartEndDates(String name, String startDate, String endDate, String tag)
                throws Exception {
            return new Task(new Description(name), new DateTime(startDate),
                    new DateTime(endDate), new UniqueTagList(tag));
        }
    }
}
