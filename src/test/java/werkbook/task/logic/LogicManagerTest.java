package werkbook.task.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static werkbook.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static werkbook.task.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static werkbook.task.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

import werkbook.task.commons.core.EventsCenter;
import werkbook.task.commons.events.model.TaskListChangedEvent;
import werkbook.task.commons.events.ui.JumpToListRequestEvent;
import werkbook.task.commons.events.ui.ShowHelpRequestEvent;
import werkbook.task.logic.commands.AddCommand;
import werkbook.task.logic.commands.ClearCommand;
import werkbook.task.logic.commands.Command;
import werkbook.task.logic.commands.CommandResult;
import werkbook.task.logic.commands.DeleteCommand;
import werkbook.task.logic.commands.ExitCommand;
import werkbook.task.logic.commands.FindCommand;
import werkbook.task.logic.commands.HelpCommand;
import werkbook.task.logic.commands.ListCommand;
import werkbook.task.logic.commands.SelectCommand;
import werkbook.task.logic.commands.UndoCommand;
import werkbook.task.logic.commands.exceptions.CommandException;
import werkbook.task.model.Model;
import werkbook.task.model.ModelManager;
import werkbook.task.model.ReadOnlyTaskList;
import werkbook.task.model.TaskList;
import werkbook.task.model.tag.Tag;
import werkbook.task.model.tag.UniqueTagList;
import werkbook.task.model.task.Description;
import werkbook.task.model.task.EndDateTime;
import werkbook.task.model.task.Name;
import werkbook.task.model.task.ReadOnlyTask;
import werkbook.task.model.task.StartDateTime;
import werkbook.task.model.task.Task;
import werkbook.task.storage.StorageManager;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    // These are for checking the correctness of the events raised
    private ReadOnlyTaskList latestSavedTaskList;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskListChangedEvent abce) {
        latestSavedTaskList = new TaskList(abce.data);
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
        String tempTaskListFile = saveFolder.getRoot().getPath() + "TempTaskList.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTaskListFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTaskList = new TaskList(model.getTaskList()); // last saved
                                                                 // assumed to
                                                                 // be up to
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
        assertCommandFailure(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and
     * that the result message is correct. Also confirms that both the 'task
     * list' and the 'last shown list' are as specified.
     *
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskList,
     *      List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            ReadOnlyTaskList expectedTaskList, List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedTaskList, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that
     * the result message is correct. Both the 'task list' and the 'last shown
     * list' are verified to be unchanged.
     *
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskList,
     *      List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        TaskList expectedTaskList = new TaskList(model.getTaskList());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedTaskList, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct and
     * that a CommandException is thrown if expected and also confirms that the
     * following three parts of the LogicManager object's state are as
     * expected:<br>
     * - the internal task list data are same as those in the
     * {@code expectedTaskList} <br>
     * - the backing list shown by UI matches the {@code shownList} <br>
     * - {@code expectedTaskList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand,
            String expectedMessage, ReadOnlyTaskList expectedTaskList,
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
        assertEquals(expectedTaskList, model.getTaskList());
        assertEquals(expectedTaskList, latestSavedTaskList);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new TaskList(),
                Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, new TaskList(),
                Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new TaskList(), Collections.emptyList());
    }

    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        // Add without any arguments
        assertCommandFailure("add", expectedMessage);
        // Add without task name
        assertCommandFailure("add s/01/01/1980 1111", expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() {
        assertCommandFailure("add []\\[;] d/12345 s/01/01/1980 0000 e/01/01/1980 0100",
                Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandFailure("add Valid Name d/12345 s/invaliddate e/01/01/1980 0100",
                StartDateTime.MESSAGE_START_DATETIME_CONSTRAINTS);
        assertCommandFailure(
                "add Valid Name d/12345 s/01/01/1980 0000 e/01/01/1980 0000 t/invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertCommandFailure(
                "add Valid Name d/12345 s/01/01/1980 0000",
                Task.MESSAGE_START_WITHOUT_END_CONSTRAINTS);
        assertCommandFailure(
                "add Valid Name d/12345 s/01/01/1980 0000 e/01/01/1979 0000",
                Task.MESSAGE_END_BEFORE_START_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedTaskList,
                expectedTaskList.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal task ist

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK);

    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskList expectedTaskList = helper.generateTaskListByNumber(2);
        List<? extends ReadOnlyTask> expectedList = expectedTaskList.getTaskList();

        // prepare task list state
        helper.addToModel(model, 2);

        assertCommandSuccess("list", ListCommand.MESSAGE_SUCCESS, expectedTaskList, expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command targeting a single task in the shown list, using visible index.
     *
     * @param commandWord to test assuming it targets a single task in the
     *            last shown list based on visible index.
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
     * @param commandWord to test assuming it targets a single task in the
     *            last shown list based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set taskList state to 2 tasks
        model.resetData(new TaskList());
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

        TaskList expectedTaskList = helper.generateTaskList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("select 2", String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedTaskList, expectedTaskList.getTaskList());
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

        TaskList expectedTaskList = helper.generateTaskList(threeTasks);
        expectedTaskList.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)), expectedTaskList,
                expectedTaskList.getTaskList());
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
        TaskList expectedTaskList = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTaskList, expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithName("key key");
        Task p4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        TaskList expectedTaskList = helper.generateTaskList(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTaskList, expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskList expectedTaskList = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()), expectedTaskList,
                expectedList);
    }

    @Test
    public void execute_undo_withPriorMutablePriorAction() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTaskList,
                expectedTaskList.getTaskList());

        //add one more task
        Task newTask = helper.generateTask(1);
        TaskList newExpectedTaskList = new TaskList();
        newExpectedTaskList.addTask(toBeAdded);
        newExpectedTaskList.addTask(newTask);
        assertCommandSuccess(helper.generateAddCommand(newTask),
                String.format(AddCommand.MESSAGE_SUCCESS, newTask),
                newExpectedTaskList,
                newExpectedTaskList.getTaskList());

        assertCommandSuccess("undo", UndoCommand.MESSAGE_SUCCESS, expectedTaskList, expectedTaskList.getTaskList());
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task adam() throws Exception {
            Name name = new Name("Email Adam Brown");
            Description description = new Description("By lunch time");
            StartDateTime startDateTime = new StartDateTime("01/01/1980 0000");
            EndDateTime endDateTime = new EndDateTime("01/01/1980 0500");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, description, startDateTime, endDateTime, tags);
        }

        /**
         * Generates a valid task using the given seed. Running this function
         * with the same parameter values guarantees the returned task will have
         * the same state. Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(new Name("Task " + seed), new Description("" + Math.abs(seed)),
                    new StartDateTime("01/01/2016 0900"), new EndDateTime("02/01/2016 1000"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" d/").append(p.getDescription().toString());
            cmd.append(" s/").append(p.getStartDateTime().toString());
            cmd.append(" e/").append(p.getEndDateTime().toString());

            UniqueTagList tags = p.getTags();
            for (Tag t : tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an TaskList with auto-generated tasks.
         */
        TaskList generateTaskListByNumber(int numGenerated) throws Exception {
            TaskList taskList = new TaskList();
            addToTaskList(taskList, numGenerated);
            return taskList;
        }

        /**
         * Generates an TaskList based on the list of Tasks given.
         */
        TaskList generateTaskList(List<Task> tasks) throws Exception {
            TaskList taskList = new TaskList();
            addtoTaskList(taskList, tasks);
            return taskList;
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
         * Adds auto-generated Task objects to the given TaskList
         *
         * @param taskList The TaskList to which the Tasks will be added
         */
        void addToTaskList(TaskList taskList, int numGenerated) throws Exception {
            addtoTaskList(taskList, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskList
         */
        void addtoTaskList(TaskList taskList, List<Task> tasksToAdd) throws Exception {
            for (Task t : tasksToAdd) {
                taskList.addTask(t);
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
            for (Task t : tasksToAdd) {
                model.addTask(t);
            }
        }

        /**
         * Generates a Task object with given name. Other fields will have some
         * dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(new Name(name), new Description("1"), new StartDateTime("01/01/1980 0000"),
                    new EndDateTime("01/01/1980 0100"), new UniqueTagList(new Tag("tag")));
        }
    }
}
