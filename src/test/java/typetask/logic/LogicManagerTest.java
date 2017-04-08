package typetask.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static typetask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static typetask.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static typetask.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
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

import typetask.commons.core.Config;
import typetask.commons.core.EventsCenter;
import typetask.commons.events.model.TaskManagerChangedEvent;
import typetask.commons.events.ui.JumpToListRequestEvent;
import typetask.commons.events.ui.ShowHelpRequestEvent;
import typetask.commons.exceptions.DataConversionException;
import typetask.logic.commands.AddCommand;
import typetask.logic.commands.ClearCommand;
import typetask.logic.commands.Command;
import typetask.logic.commands.CommandResult;
import typetask.logic.commands.DeleteCommand;
import typetask.logic.commands.ExitCommand;
import typetask.logic.commands.FindCommand;
import typetask.logic.commands.HelpCommand;
import typetask.logic.commands.ListCommand;
import typetask.logic.commands.SelectCommand;
import typetask.logic.commands.exceptions.CommandException;
import typetask.model.Model;
import typetask.model.ModelManager;
import typetask.model.ReadOnlyTaskManager;
import typetask.model.TaskManager;
import typetask.model.task.DueDate;
import typetask.model.task.Name;
import typetask.model.task.Priority;
import typetask.model.task.ReadOnlyTask;
import typetask.model.task.Task;
import typetask.storage.StorageManager;


public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;
    private Config config;

    //These are for checking the correctness of the events raised
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
        logic = new LogicManager(model, new StorageManager(tempTaskManagerFile, tempPreferencesFile), config);
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
    public void executeInvalid() throws IOException, DataConversionException {
        String invalidCommand = "       ";
        assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and that the result message is correct.
     * Also confirms that both the 'task manager' and the 'last shown list' are as specified.
     * @throws IOException
     * @throws DataConversionException
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyTaskManager expectedTaskManager,
                                      List<? extends ReadOnlyTask> expectedShownList) throws IOException,
                                                                                             DataConversionException {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedTaskManager, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'task manager' and the 'last shown list' are verified to be unchanged.
     * @throws IOException
     * @throws DataConversionException
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskManager, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) throws IOException,
                                                                                          DataConversionException {
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
     * @throws IOException
     * @throws DataConversionException
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
                                       ReadOnlyTaskManager expectedTaskManager,
                                       List<? extends ReadOnlyTask> expectedShownList) throws IOException,
                                                                                              DataConversionException {

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
        assertEquals(expectedTaskManager, latestSavedTaskManager);
    }

    @Test
    public void executeUnknownCommandWord() throws IOException, DataConversionException {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void executeHelp() throws IOException, DataConversionException {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new TaskManager(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void executeExit() throws IOException, DataConversionException {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new TaskManager(), Collections.emptyList());
    }

    @Test
    public void executeClear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new TaskManager(), Collections.emptyList());
    }


    //@Test
    //public void execute_add_invalidArgsFormat() {
        //String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        //assertCommandFailure("add wrong args wrong args", expectedMessage);
        //assertCommandFailure("add Valid Name 12345 e/valid@email", expectedMessage);
    //}

    //@Test
    //public void execute_add_invalidTaskData() {
        //assertCommandFailure("add []\\[;] p/12345 e/valid@e.mail a/valid, address",
                //Name.MESSAGE_NAME_CONSTRAINTS);
        //assertCommandFailure("add Valid Name p/not_numbers e/valid@e.mail a/valid, address",
                //Date.MESSAGE_PHONE_CONSTRAINTS);
        //assertCommandFailure("add Valid Name p/12345 e/notAnEmail a/valid, address",
                //Email.MESSAGE_EMAIL_CONSTRAINTS);
        //assertCommandFailure("add Valid Name p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag",
                //Tag.MESSAGE_TAG_CONSTRAINTS);

    //}

    @Test
    public void executeAddSuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedAB = new TaskManager();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }



    @Test
    public void executeListShowsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedAB = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare task manager state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


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

        // set AB state to 2 tasks
        model.resetData(new TaskManager());
        for (Task t : taskList) {
            model.addTask(t);
        }

        assertCommandFailure(commandWord + " 3", expectedMessage);
    }

    @Test
    public void executeSelectInvalidArgsFormatErrorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void executeSelectIndexNotFoundErrorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void executeSelectJumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }


    @Test
    public void executeDeleteInvalidArgsFormatErrorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void executeDeleteIndexNotFoundErrorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void executeDeleteRemovesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedAB = helper.generateTaskManager(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }


    @Test
    public void executeFindInvalidArgsFormat() throws IOException, DataConversionException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void executeFindOnlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void executeFindIsNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void executeFindMatchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskManager expectedAB = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        public Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            DueDate date = new DueDate("");
            DueDate endDate = new DueDate("");
            Priority priority = new Priority("Low");
            return new Task(name, date, endDate, false, priority);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * @param seed used to generate the Task data field values
         */
        public Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed), new DueDate(""),
                    new DueDate("") , false, new Priority("Low"));
        }

        /** Generates the correct add command based on the task given */
        public String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());

            return cmd.toString();
        }

        /**
         * Generates a TaskManager with auto-generated tasks.
         */
        public TaskManager generateTaskManager(int numGenerated) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates a TaskManager based on the list of Tasks given.
         */
        public TaskManager generateTaskManager(List<Task> tasks) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, tasks);
            return taskManager;
        }

        /**
         * Adds auto-generated Task objects to the given TaskManager
         * @param taskManager The TaskManager to which the Tasks will be added
         */
        public void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskManager
         */
        public void addToTaskManager(TaskManager taskManager, List<Task> tasksToAdd) throws Exception {
            for (Task t: tasksToAdd) {
                taskManager.addTask(t);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        public void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        public void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task t: tasksToAdd) {
                model.addTask(t);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        public List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        public List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        public Task generateTaskWithName(String name) throws Exception {
            return new Task(new Name(name), new DueDate(""), new DueDate(""), false, new Priority("Low"));
        }
    }
}
