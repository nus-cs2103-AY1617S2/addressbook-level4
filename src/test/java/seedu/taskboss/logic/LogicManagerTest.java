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
import seedu.taskboss.commons.exceptions.BuiltInCategoryException;
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
import seedu.taskboss.logic.commands.ViewCommand;
import seedu.taskboss.logic.commands.exceptions.CommandException;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
import seedu.taskboss.logic.parser.DateTimeParser;
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
import seedu.taskboss.model.task.Recurrence;
import seedu.taskboss.model.task.Recurrence.Frequency;
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
    private void handleLocalModelChangedEvent(TaskBossChangedEvent abce) throws IllegalValueException {
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
    public void setUp() throws IllegalValueException {
        model = new ModelManager();
        String tempTaskBossFile = saveFolder.getRoot().getPath() + "TempTaskBoss.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskBossFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskBoss = new TaskBoss(model.getTaskBoss()); // last saved
                                                                 // assumed to be up to date
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() throws IllegalValueException, InvalidDatesException, BuiltInCategoryException {
        String invalidCommand = "       ";
        assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
               HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and
     * that the result message is correct. Also confirms that both the
     * 'taskboss' and the 'last shown list' are as specified.
     * @throws IllegalValueException
     * @throws InvalidDatesException
     * @throws BuiltInCategoryException
     *
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskBoss,
     *      List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            ReadOnlyTaskBoss expectedTaskBoss,
            List<? extends ReadOnlyTask> expectedShownList) throws IllegalValueException,
                                                                InvalidDatesException, BuiltInCategoryException {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedTaskBoss, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that
     * the result message is correct. Both the 'taskboss' and the 'last shown
     * list' are verified to be unchanged.
     * @throws IllegalValueException
     * @throws InvalidDatesException
     * @throws BuiltInCategoryException
     *
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskBoss,
     *      List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) throws IllegalValueException,
        InvalidDatesException, BuiltInCategoryException {
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
     * @throws IllegalValueException
     * @throws InvalidDatesException
     * @throws BuiltInCategoryException
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand,
            String expectedMessage, ReadOnlyTaskBoss expectedTaskBoss,
            List<? extends ReadOnlyTask> expectedShownList) throws IllegalValueException,
                                                                InvalidDatesException, BuiltInCategoryException {

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
    public void execute_unknownCommandWord() throws IllegalValueException,
                                                 InvalidDatesException, BuiltInCategoryException {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() throws IllegalValueException, InvalidDatesException,
                                    BuiltInCategoryException {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE,
                new TaskBoss(), Collections.emptyList());
        assertTrue(helpShown);
    }


    @Test
    public void execute_helpShortCommand() throws IllegalValueException, InvalidDatesException,
                                                BuiltInCategoryException {
        assertCommandSuccess("h", HelpCommand.SHOWING_HELP_MESSAGE,
                new TaskBoss(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() throws IllegalValueException, InvalidDatesException,
                                    BuiltInCategoryException {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new TaskBoss(), Collections.emptyList());
    }

    @Test
    public void execute_exitShortCommand() throws IllegalValueException, InvalidDatesException,
                                                BuiltInCategoryException {
        assertCommandSuccess("x", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
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
    public void execute_add_invalidTaskData() throws IllegalValueException, InvalidDatesException,
        BuiltInCategoryException {

        //invalid empty task name
        assertCommandFailure("add sd/today ed/tomorrow "
                + "i/valid, information c/valid",
                Name.MESSAGE_NAME_CONSTRAINTS);

        //invalid name which longer than 45 characters
        assertCommandFailure("add longggggggggggggggggggggggggggggggggggTaskName sd/today ed/tomorrow "
                + "i/valid, information c/valid",
                Name.MESSAGE_NAME_CONSTRAINTS);

        assertCommandFailure("add Valid Name! sd/today ed/tomorrow "
                + "i/valid, information c/invalid_-[.category",
                Category.MESSAGE_CATEGORY_CONSTRAINTS);
        assertCommandFailure("add Valid Name sd/today to next week ed/tomorrow i/valid, information",
                DateTimeParser.getMultipleDatesError());
        assertCommandFailure("add Valid Name sd/invalid date ed/tomorrow i/valid, information",
                DateTime.MESSAGE_DATE_CONSTRAINTS);
        assertCommandFailure("add n/Valid Name sd/today ed/invalid i/valid, information",
                DateTime.MESSAGE_DATE_CONSTRAINTS);
        assertCommandFailure("add n/Valid Name sd/tomorrow ed/next friday i/valid info r/invalid recurrence",
                Recurrence.MESSAGE_RECURRENCE_CONSTRAINTS);
        assertCommandFailure("add n/Valid Name r/weekly monthly",
                Recurrence.MESSAGE_RECURRENCE_CONSTRAINTS);
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
        TaskBoss expectedTB = helper.generateTaskBoss(2);
        List<? extends ReadOnlyTask> expectedList = expectedTB.getTaskList();

        // prepare TaskBoss state
        helper.addToModel(model, 2);

        assertCommandSuccess("list", ListCommand.MESSAGE_SUCCESS, expectedTB, expectedList);
    }

    @Test
    public void execute_listShortCommand_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskBoss expectedTB = helper.generateTaskBoss(2);
        List<? extends ReadOnlyTask> expectedList = expectedTB.getTaskList();

        // prepare TaskBoss state
        helper.addToModel(model, 2);

        assertCommandSuccess("l", ListCommand.MESSAGE_SUCCESS, expectedTB, expectedList);
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
    public void execute_viewInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("view", expectedMessage);
    }

    @Test
    public void execute_viewIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("view");
    }

    @Test
    public void execute_view_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBoss expectedAB = helper.generateTaskBoss(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("view 2", String.format(ViewCommand.MESSAGE_VIEW_TASK_SUCCESS, 2), expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }
    //@@author A0138961W
    @Test
    public void execute_delete_removesSingleTask() throws Exception {
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
    public void execute_delete_removesMultipleTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBoss expectedAB = helper.generateTaskBoss(threeTasks);
        expectedAB.removeTask(threeTasks.get(2));
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 2 3", String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS,
                threeTasks.get(2), threeTasks.get(1)),
                expectedAB, expectedAB.getTaskList());
    }

    @Test
    public void execute_delete_removesInvalidTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBoss expectedAB = helper.generateTaskBoss(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandFailure("delete 0", MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    //@@author

    @Test
    public void execute_find_invalidArgsFormat() throws IllegalValueException,
                                                     InvalidDatesException, BuiltInCategoryException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    //---------------- Tests for find by keywords --------------------------------------

    /*
     * Valid equivalence partitions:
     * - Find the tasks only when full words in name or information match the keywords
     * - Is not case sensitive
     * - Find the tasks when any of the keywords match the name
     * The three test cases below test valid input as a name.
     */

    @Test
    public void execute_findKeyword_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskBoss expectedAB = helper.generateTaskBoss(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB, expectedList);
    }

    @Test
    public void execute_findKeyword_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskBoss expectedAB = helper.generateTaskBoss(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()), expectedAB, expectedList);
    }

    @Test
    public void execute_findKeyword_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskBoss expectedAB = helper.generateTaskBoss(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    //---------------- Tests for find by datetime --------------------------------------

    /*
     * Valid equivalence partitions:
     * - Find the tasks only when the keywords present in order
     * - Do not need match full words
     * The two test cases below test valid input as datetime.
     */

    //@@author A0147990R
    @Test
    public void execute_findStartDatetime_matchesOnlyIfKeywordPresentInOrder() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithStartDateTime("Monday, 13 March, 2017");
        Task p1 = helper.generateTaskWithStartDateTime("16 March, 2017");
        Task p2 = helper.generateTaskWithStartDateTime("Monday, 1 May, 2017");
        Task p3 = helper.generateTaskWithStartDateTime("2 July, 2017");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, p2, p3);
        TaskBoss expectedTB = helper.generateTaskBoss(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, p1);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find sd/Mar", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTB, expectedList);
    }

    //@@author A0147990R
    @Test
    public void execute_findEndDatetime_matchesOnlyIfKeywordPresentInOrder() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithEndDateTime("Monday, 13 March, 2017");
        Task p1 = helper.generateTaskWithEndDateTime("16 March, 2017");
        Task p2 = helper.generateTaskWithEndDateTime("Monday, 1 May, 2017");
        Task p3 = helper.generateTaskWithEndDateTime("2 July, 2017");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, p2, p3);
        TaskBoss expectedAB = helper.generateTaskBoss(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, p1);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find ed/Mar", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB, expectedList);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            PriorityLevel priorityLevel = new PriorityLevel("Yes");
            DateTime startDateTime = new DateTime("today 5pm");
            DateTime endDateTime = new DateTime("tomorrow 8pm");
            Information information = new Information("111, alpha street");
            Recurrence recurrence = new Recurrence(Frequency.NONE);
            Category category1 = new Category("Category1");
            Category category2 = new Category("Longercategory2");
            UniqueCategoryList categories = new UniqueCategoryList(category2, new Category("Alltasks"), category1);
            return new Task(name, priorityLevel, startDateTime,
                    endDateTime, information, recurrence, categories);
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
                    new PriorityLevel("Yes"),
                    new DateTime("Feb 19 10am 2017"),
                    new DateTime("Feb 20 10am 2017"),
                    new Information("House of " + seed),
                    new Recurrence(Frequency.NONE),
                    new UniqueCategoryList(new Category("category" + Math.abs(seed)),
                           new Category("category" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given
         * @throws IllegalValueException */
        private String generateAddCommand(Task p) throws IllegalValueException {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            //@@author A0144904H
            cmd.append(p.getName().toString());
            cmd.append(" p/").append(p.getPriorityLevel().input);
            cmd.append(" sd/").append(p.getStartDateTime().toString());
            cmd.append(" ed/").append(p.getEndDateTime().toString());
            cmd.append(" i/").append(p.getInformation());
            cmd.append(" r/").append(p.getRecurrence().toString());

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
         * Generates TaskBoss based on the list of Tasks given.
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
                    new PriorityLevel("Yes"),
                    new DateTime("Feb 19 10am 2017"),
                    new DateTime("Feb 20 10am 2017"),
                    new Information("House of 1"),
                    new Recurrence(Frequency.NONE),
                    new UniqueCategoryList(new Category("category"))
            );
        }

        //@@author A0147990R
        /**
         * Generates a Task object with given startDatetime. Other fields will have some
         * dummy values.
         */
        Task generateTaskWithStartDateTime(String startDatetime) throws Exception {
            return new Task(
                    new Name("testTask"),
                    new PriorityLevel("Yes"),
                    new DateTime(startDatetime),
                    new DateTime("Feb 20 10am 2018"),
                    new Information("House of 1"),
                    new Recurrence(Frequency.NONE),
                    new UniqueCategoryList(new Category("category"))
            );
        }

        /**
         * Generates a Task object with given endDatetime. Other fields will have some
         * dummy values.
         */
        Task generateTaskWithEndDateTime(String endDatetime) throws Exception {
            return new Task(
                    new Name("testTask"),
                    new PriorityLevel("Yes"),
                    new DateTime("Feb 20 10am 2017"),
                    new DateTime(endDatetime),
                    new Information("House of 1"),
                    new Recurrence(Frequency.NONE),
                    new UniqueCategoryList(new Category("category"))
            );
        }
    }
}
