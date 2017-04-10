package onlythree.imanager.logic;

import static onlythree.imanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static onlythree.imanager.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static onlythree.imanager.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import onlythree.imanager.commons.core.DateTimeFormats;
import onlythree.imanager.commons.core.EventsCenter;
import onlythree.imanager.commons.events.model.TaskListChangedEvent;
import onlythree.imanager.commons.events.ui.JumpToListRequestEvent;
import onlythree.imanager.commons.events.ui.ShowHelpRequestEvent;
import onlythree.imanager.logic.commands.AddCommand;
import onlythree.imanager.logic.commands.ClearCommand;
import onlythree.imanager.logic.commands.Command;
import onlythree.imanager.logic.commands.CommandResult;
import onlythree.imanager.logic.commands.DeleteCommand;
import onlythree.imanager.logic.commands.ExitCommand;
import onlythree.imanager.logic.commands.FindCommand;
import onlythree.imanager.logic.commands.HelpCommand;
import onlythree.imanager.logic.commands.SaveCommand;
import onlythree.imanager.logic.commands.SelectCommand;
import onlythree.imanager.logic.commands.ViewCommand;
import onlythree.imanager.logic.commands.exceptions.CommandException;
import onlythree.imanager.model.Model;
import onlythree.imanager.model.ModelManager;
import onlythree.imanager.model.ReadOnlyTaskList;
import onlythree.imanager.model.TaskList;
import onlythree.imanager.model.tag.Tag;
import onlythree.imanager.model.tag.UniqueTagList;
import onlythree.imanager.model.task.Deadline;
import onlythree.imanager.model.task.Name;
import onlythree.imanager.model.task.ReadOnlyTask;
import onlythree.imanager.model.task.StartEndDateTime;
import onlythree.imanager.model.task.Task;
import onlythree.imanager.storage.StorageManager;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
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

        latestSavedTaskList = new TaskList(model.getTaskList()); // last saved assumed to be up to date
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
     * Also confirms that both the 'task list' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskList, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyTaskList expectedTaskList,
                                      List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedTaskList, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'task list' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskList, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        TaskList expectedTaskList = new TaskList(model.getTaskList());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedTaskList, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal task list data are same as those in the {@code expectedTaskList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTaskList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
                                       ReadOnlyTaskList expectedTaskList,
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
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new TaskList(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new TaskList(), Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTaskWithStartEndDateTime(1));
        model.addTask(helper.generateTaskWithStartEndDateTime(2));
        model.addTask(helper.generateTaskWithStartEndDateTime(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new TaskList(), Collections.emptyList());
    }
    //@@author A0148052L
    public void execute_save_successful() throws Exception {
        assertCommandSuccess("save " + saveFolder.getRoot().getPath() + File.separator + "taskList.xml",
                  SaveCommand.MESSAGE_SUCCESS, new TaskList(), Collections.emptyList());
    }

    @Test
    public void execute_save_invalidFileName() throws Exception {
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(false, "save " + "data" + "\\" + ".xml",
                             SaveCommand.MESSAGE_INVALID_FILE_NAME, new TaskList(), expectedShownList);
    }
    //@@author

    //@@author A0140023E
    @Test
    public void execute_add_invalidTaskData() {
        assertCommandFailure("add invalid name with slash/ from tmr to 2 days after",
                Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandFailure("add valid name by yesterday t/validTag",
                Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        assertCommandFailure("add valid name from yesterday to tmr t/validTag",
                StartEndDateTime.MESSAGE_PAST_START_DATETIME_CONSTRAINTS);
        assertCommandFailure("add valid name from tmr to yesterday t/validTag",
                StartEndDateTime.MESSAGE_PAST_END_DATETIME_CONSTRAINTS);
        assertCommandFailure("add valid name from 3 days later to 2 days later t/validTag",
                StartEndDateTime.MESSAGE_INVALID_DURATION_CONSTRAINTS);
        assertCommandFailure("add Valid Name from 2 days later to 3 days later t/invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    //@@author
    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.accept();
        TaskList expectedTaskList = new TaskList();
        expectedTaskList.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTaskList,
                expectedTaskList.getTaskList());
    }

    @Test
    public void execute_view_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskList expectedTaskList = helper.generateTaskList(2);
        List<? extends ReadOnlyTask> expectedList = expectedTaskList.getTaskList();

        // prepare task list state
        helper.addToModel(model, 2);

        assertCommandSuccess("view",
                ViewCommand.MESSAGE_SUCCESS_VIEW_ALL_TASKS,
                expectedTaskList,
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
        List<Task> tasks = helper.generateTasks(2);

        // set AB state to 2 tasks
        model.resetData(new TaskList());
        for (Task task : tasks) {
            model.addTask(task);
        }

        assertCommandFailure(commandWord + " 3", expectedMessage);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("select", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_selectLastIndex() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> twoTasks = helper.generateTasks(2);

        TaskList expectedTaskList = helper.generateTaskList(twoTasks);
        helper.addToModel(model, twoTasks);

        // set task list state to 2 tasks
        model.resetData(new TaskList());
        for (Task task : twoTasks) {
            model.addTask(task);
        }

        int selectedTaskNum = 2;

        assertCommandSuccess("select 3",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, selectedTaskNum,
                        getCompactFormattedTask(twoTasks.get(selectedTaskNum - 1))),
                expectedTaskList,
                expectedTaskList.getTaskList());
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTasks(3);

        TaskList expectedAB = helper.generateTaskList(threeTasks);
        helper.addToModel(model, threeTasks);

        int selectedTaskNum = 2;

        assertCommandSuccess("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, selectedTaskNum,
                        getCompactFormattedTask(threeTasks.get(selectedTaskNum - 1))),
                expectedAB,
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
        List<Task> threeTasks = helper.generateTasks(3);

        TaskList expectedAB = helper.generateTaskList(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }


    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task taskTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task taskTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task task1 = helper.generateTaskWithName("KE Y");
        Task task2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTasks(task1, taskTarget1, task2, taskTarget2);
        TaskList expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTasks(taskTarget1, taskTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task task1 = helper.generateTaskWithName("bla bla KEY bla");
        Task task2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task task3 = helper.generateTaskWithName("key key");
        Task task4 = helper.generateTaskWithName("KEy sduauo");

        List<Task> fourTasks = helper.generateTasks(task3, task1, task4, task2);
        TaskList expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task taskTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task taskTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task taskTarget3 = helper.generateTaskWithName("key key");
        Task task1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTasks(taskTarget1, task1, taskTarget2, taskTarget3);
        TaskList expectedAB = helper.generateTaskList(fourTasks);
        List<Task> expectedList = helper.generateTasks(taskTarget1, taskTarget2, taskTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    /**
     * Returns the task with only the task name and tags.
     */
    private String getCompactFormattedTask(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();

        sb.append(task.getName());
        sb.append(System.lineSeparator());

        sb.append("Tags: ");
        task.getTags().forEach(sb::append);

        return sb.toString();
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {
        //@@author A0140023E
        // Starting Test Date Time is set to one day after today so that dates in the past is not
        // generated to prevent a PastDateTimeException from occuring. Furthermore the precision
        // is truncated to seconds as Natty does not parse milliseconds
        private ZonedDateTime startTestDateTime = ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(1);

        private Task accept() throws Exception {
            Name name = new Name("Accept Changes");
            StartEndDateTime startEndDateTime =
                    new StartEndDateTime(startTestDateTime.plusDays(2), startTestDateTime.plusDays(4));
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            // Note that a task is generated with a StartEndDateTime as that would be more complex
            // than a task with Deadline or a Task with no Deadline and StartEndDateTime, thus
            // making test cases more likely to fail
            return new Task(name, Optional.empty(), Optional.of(startEndDateTime), tags);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state
         * if it is using the same instance of {@link TestDataHelper}.
         * Each unique seed will generate a unique task object.
         *
         * @param seed used to generate the task data field values
         */
        private Task generateTaskWithStartEndDateTime(int seed) throws Exception {
            return new Task(
                    new Name("Task" + seed),
                    Optional.empty(),
                    Optional.of(new StartEndDateTime(startTestDateTime.plusDays(seed + 1),
                            startTestDateTime.plusDays(seed + 2))),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given */
        private String generateAddCommand(Task task) {
            // The date-times are transformed into a format that Natty can parse
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(task.getName().toString());

            if (task.getDeadline().isPresent()) {
                cmd.append(" by ");
                cmd.append(task.getDeadline().get().getDateTime().format(DateTimeFormats.TEST_FORMAT));
            }

            if (task.getStartEndDateTime().isPresent()) {
                StartEndDateTime startEndDateTime = task.getStartEndDateTime().get();
                cmd.append(" from ");
                cmd.append(startEndDateTime.getStartDateTime().format(DateTimeFormats.TEST_FORMAT));
                cmd.append(" to ");
                cmd.append(startEndDateTime.getEndDateTime().format(DateTimeFormats.TEST_FORMAT));
            }

            UniqueTagList tags = task.getTags();
            for (Tag t: tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        //@@author
        /**
         * Generates a TaskList with auto-generated tasks.
         */
        private TaskList generateTaskList(int numGenerated) throws Exception {
            TaskList taskList = new TaskList();
            addToTaskList(taskList, numGenerated);
            return taskList;
        }

        /**
         * Generates a TaskList based on the list of Tasks given.
         */
        TaskList generateTaskList(List<Task> tasks) throws Exception {
            TaskList taskList = new TaskList();
            addToTaskList(taskList, tasks);
            return taskList;
        }

        /**
         * Adds auto-generated Task objects to the given TaskList
         * @param taskList The TaskList to which the tasks will be added
         */
        private void addToTaskList(TaskList taskList, int numGenerated) throws Exception {
            addToTaskList(taskList, generateTasks(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TaskList
         */
        private void addToTaskList(TaskList taskList, List<Task> tasksToAdd) throws Exception {
            for (Task task: tasksToAdd) {
                taskList.addTask(task);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        private void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTasks(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        private void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task task: tasksToAdd) {
                model.addTask(task);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        private List<Task> generateTasks(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTaskWithStartEndDateTime(i));
            }
            return tasks;
        }

        List<Task> generateTasks(Task... taskss) {
            return Arrays.asList(taskss);
        }

        //@@author A0140023E
        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        private Task generateTaskWithName(String name) throws Exception {
            // Note that a task is generated with a StartEndDateTime as that would be more complex
            // than a task with Deadline or a Task with no Deadline and StartEndDateTime, thus
            // making test cases more likely to fail
            return new Task(
                    new Name(name),
                    Optional.empty(),
                    Optional.of(new StartEndDateTime(startTestDateTime.plusDays(3), startTestDateTime.plusDays(6))),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
