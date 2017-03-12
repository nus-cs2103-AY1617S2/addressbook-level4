package seedu.taskboss.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.taskboss.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

import seedu.taskboss.commons.core.EventsCenter;
import seedu.taskboss.commons.events.model.TaskBossChangedEvent;
import seedu.taskboss.commons.events.ui.JumpToListRequestEvent;
import seedu.taskboss.commons.events.ui.ShowHelpRequestEvent;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.AddCommand;
import seedu.taskboss.logic.commands.ClearCommand;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.CommandResult;
import seedu.taskboss.logic.commands.DeleteCommand;
import seedu.taskboss.logic.commands.ExitCommand;
import seedu.taskboss.logic.commands.FindCommand;
import seedu.taskboss.logic.commands.HelpCommand;
import seedu.taskboss.logic.commands.ListCommand;
import seedu.taskboss.logic.commands.SelectCommand;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.parser.DateParser;
import seedu.taskboss.logic.parser.ParserUtil;
import seedu.taskboss.model.Model;
import seedu.taskboss.model.ModelManager;
import seedu.taskboss.model.ReadOnlyTaskBoss;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.category.Category;
import seedu.taskboss.model.category.UniqueCategoryList;
import seedu.taskboss.model.task.DateTime;
import seedu.taskboss.model.task.Information;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.PriorityLevel;
import seedu.taskboss.model.task.ReadOnlyTask;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.storage.StorageManager;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    // These are for checking the correctness of the events raised
    private ReadOnlyTaskBoss latestSavedTaskBoss;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskBossChangedEvent abce) {
        latestSavedTaskBoss = new TaskBoss(abce.data);
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
        String tempTaskBossFile = saveFolder.getRoot().getPath() + "TempTaskBoss.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskBossFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskBoss = new TaskBoss(model.getTaskBoss()); // last saved
        // assumed
        // to be up
        // to date
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
        assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
               HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and
     * that the result message is correct. Also confirms that both the
     * 'taskboss' and the 'last shown list' are as specified.
     *
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskBoss,
     *      List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            ReadOnlyTaskBoss expectedTaskBoss,
            List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedTaskBoss, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that
     * the result message is correct. Both the 'taskboss' and the 'last shown
     * list' are verified to be unchanged.
     *
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskBoss,
     *      List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        TaskBoss expectedTaskBoss = new TaskBoss(model.getTaskBoss());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedTaskBoss, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct and
     * that a CommandException is thrown if expected and also confirms that the
     * following three parts of the LogicManager object's state are as
     * expected:<br>
     * - the internal TaskBoss data are same as those in the
     * {@code expectedTaskBoss} <br>
     * - the backing list shown by UI matches the {@code shownList} <br>
     * - {@code expectedTaskBoss} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand,
            String expectedMessage, ReadOnlyTaskBoss expectedTaskBoss,
            List<? extends ReadOnlyTask> expectedShownList) {

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
        assertEquals(expectedTaskBoss, model.getTaskBoss());
        assertEquals(expectedTaskBoss, latestSavedTaskBoss);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE,
                new TaskBoss(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new TaskBoss(), Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new TaskBoss(), Collections.emptyList());
    }

    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add Valid Name p/1 sd/today ed/tomorrow", expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() {
        assertCommandFailure("add n/[]\\[;] p/1 sd/today ed/tomorrow i/valid, information",
                Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandFailure("add n/Valid Name p/not_numbers sd/today ed/tomorrow i/valid, information",
                PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
        assertCommandFailure("add n/Valid Name p/1 sd/today ed/tomorrow "
                + "i/valid, information c/invalid_-[.category",
                Category.MESSAGE_CATEGORY_CONSTRAINTS);
        assertCommandFailure("add n/Valid Name p/1 sd/today to next week ed/tomorrow i/valid, information",
                DateParser.getStartDateMultipleDatesError());
        assertCommandFailure("add n/Valid Name p/1 sd/invalid date ed/monday to friday i/valid, information",
                DateParser.getStartDateInvalidDateError());
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskBoss expectedAB = new TaskBoss();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB, expectedAB.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal TaskBoss

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK);

    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskBoss expectedAB = helper.generateTaskBoss(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare TaskBoss state
        helper.addToModel(model, 2);

        assertCommandSuccess("list", ListCommand.MESSAGE_SUCCESS, expectedAB, expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     *
     * @param commandWord to test assuming it targets a single task in the last shown
     * list based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertCommandFailure(commandWord, expectedMessage); // index missing
        assertCommandFailure(commandWord + " +1", expectedMessage); // index should be unsigned
        assertCommandFailure(commandWord + " -1", expectedMessage); // index should be unsigned
        assertCommandFailure(commandWord + " 0", expectedMessage); // index åcannot be 0
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

        // set AB state to 2 tasks
        model.resetData(new TaskBoss());
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
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBoss expectedAB = helper.generateTaskBoss(threeTasks);
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

        TaskBoss expectedAB = helper.generateTaskBoss(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 2", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS,
                threeTasks.get(1)),
                expectedAB, expectedAB.getTaskList());
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
        TaskBoss expectedAB = helper.generateTaskBoss(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find n/KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB, expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskBoss expectedAB = helper.generateTaskBoss(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find n/KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB, expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskBoss expectedAB = helper.generateTaskBoss(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find n/key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            PriorityLevel privatePriorityLevel = new PriorityLevel("1");
            DateTime startDateTime = new DateTime(ParserUtil.parseStartDate("today 5pm"));
            DateTime endDateTime = new DateTime(ParserUtil.parseEndDate("tomorrow 8pm"));
            Information privateInformation = new Information("111, alpha street");
            Category category1 = new Category("category1");
            Category category2 = new Category("longercategory2");
            UniqueCategoryList categories = new UniqueCategoryList(category1, category2);
            return new Task(name, privatePriorityLevel, startDateTime,
                    endDateTime, privateInformation, categories);
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
            return new Task(
                    new Name("Task " + seed),
                    new PriorityLevel("" + Math.abs(seed)),
                    new DateTime("Feb 19 10am, 2017"),
                    new DateTime("Feb 20 10am, 2017"),
                    new Information("House of " + seed),
                    new UniqueCategoryList(new Category("category" + Math.abs(seed)),
                           new Category("category" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given
         * @throws IllegalValueException */
        private String generateAddCommand(Task p) throws IllegalValueException {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(" n/").append(p.getName().toString());
            cmd.append(" p/").append(p.getPriorityLevel());
            cmd.append(" sd/").append(ParserUtil.parseStartDate(p.getStartDateTime().toString()));
            cmd.append(" ed/").append(ParserUtil.parseEndDate(p.getEndDateTime().toString()));
            cmd.append(" i/").append(p.getInformation());

            UniqueCategoryList categories = p.getCategories();
            for (Category t : categories) {
                cmd.append(" c/").append(t.categoryName);
            }

            return cmd.toString();
        }

        /**
         * Generates an TaskBoss with auto-generated tasks.
         */
        TaskBoss generateTaskBoss(int numGenerated) throws Exception {
            TaskBoss taskBoss = new TaskBoss();
            addToTaskBoss(taskBoss, numGenerated);
            return taskBoss;
        }

        /**
         * Generates an TaskBoss based on the list of Tasks given.
         */
        TaskBoss generateTaskBoss(List<Task> tasks) throws Exception {
            TaskBoss taskBoss = new TaskBoss();
            addToTaskBoss(taskBoss, tasks);
            return taskBoss;
        }

        /**
         * Adds auto-generated Task objects to the given TaskBoss
         *
         * @param taskBoss
         *            The TaskBoss to which the Tasks will be added
         */
        void addToTaskBoss(TaskBoss taskBoss, int numGenerated) throws Exception {
            addToTaskBoss(taskBoss, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskBoss
         */
        void addToTaskBoss(TaskBoss taskBoss, List<Task> tasksToAdd) throws Exception {
            for (Task t : tasksToAdd) {
                taskBoss.addTask(t);
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
            for (Task t : tasksToAdd) {
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
         * Generates a Task object with given name. Other fields will have some
         * dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new PriorityLevel("1"),
                    new DateTime("Feb 19 10am, 2017"),
                    new DateTime("Feb 20 10am, 2017"),
                    new Information("House of 1"),
                    new UniqueCategoryList(new Category("category"))
            );
        }
    }
}
