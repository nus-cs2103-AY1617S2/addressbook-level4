package seedu.doit.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.doit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.doit.logic.commands.CommandResult.tasksToString;
import static seedu.doit.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.doit.model.item.EndTime.MESSAGE_ENDTIME_CONSTRAINTS;
import static seedu.doit.model.item.Priority.MESSAGE_PRIORITY_CONSTRAINTS;
import static seedu.doit.model.item.StartTime.MESSAGE_STARTTIME_CONSTRAINTS;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.doit.commons.core.EventsCenter;
import seedu.doit.commons.events.model.TaskManagerChangedEvent;
import seedu.doit.commons.events.ui.JumpToListRequestEvent;
import seedu.doit.commons.events.ui.ShowHelpRequestEvent;
import seedu.doit.commons.util.FileUtil;
import seedu.doit.logic.commands.AddCommand;
import seedu.doit.logic.commands.ClearCommand;
import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.CommandResult;
import seedu.doit.logic.commands.DeleteCommand;
import seedu.doit.logic.commands.ExitCommand;
import seedu.doit.logic.commands.FindCommand;
import seedu.doit.logic.commands.HelpCommand;
import seedu.doit.logic.commands.ListCommand;
import seedu.doit.logic.commands.SaveCommand;
import seedu.doit.logic.commands.SelectCommand;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.Model;
import seedu.doit.model.ModelManager;
import seedu.doit.model.ReadOnlyTaskManager;
import seedu.doit.model.TaskManager;
import seedu.doit.model.comparators.TaskNameComparator;
import seedu.doit.model.item.Description;
import seedu.doit.model.item.EndTime;
import seedu.doit.model.item.Name;
import seedu.doit.model.item.Priority;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.Task;
import seedu.doit.model.tag.Tag;
import seedu.doit.model.tag.UniqueTagList;
import seedu.doit.storage.Storage;
import seedu.doit.storage.StorageManager;

public class LogicManagerTest {

    public static final String ADD_INVALID_TASK_NAME = "add []\\[;] p/12 e/15-3-2020 d/valid t/description";
    public static final String UNKNOWN_COMMAND = "uicfhmowqewca";
    public static final String TEMP_PREFERENCES_JSON = "TempPreferences.json";
    public static final String TEMP_TASK_MANAGER_XML = "TempTaskManager.xml";

    private static final String SAVE = SaveCommand.COMMAND_WORD + " ";

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Storage storage;
    private Logic logic;

    // These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedTaskManager;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
        this.latestSavedTaskManager = new TaskManager(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        this.helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        this.targetedJumpIndex = je.targetIndex;
    }

    @Before
    public void setUp() {
        this.model = new ModelManager();
        String tempTaskManagerFile = this.saveFolder.getRoot().getPath() + TEMP_TASK_MANAGER_XML;
        String tempPreferencesFile = this.saveFolder.getRoot().getPath() + TEMP_PREFERENCES_JSON;
        this.storage = new StorageManager(tempTaskManagerFile, tempPreferencesFile);
        this.logic = new LogicManager(this.model, this.storage);
        EventsCenter.getInstance().registerHandler(this);

        this.latestSavedTaskManager = new TaskManager(this.model.getTaskManager());
        // last saved assumed to be up to date
        this.helpShown = false;
        this.targetedJumpIndex = -1; // non yet
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
        TaskManager expectedTaskManager = new TaskManager(this.model.getTaskManager());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(this.model.getFilteredTaskList());
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
            CommandResult result = this.logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        }

        // Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, this.model.getFilteredTaskList());

        // Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTaskManager, this.model.getTaskManager());
        assertEquals(expectedTaskManager, this.latestSavedTaskManager);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = UNKNOWN_COMMAND;
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess(HelpCommand.COMMAND_WORD, HelpCommand.SHOWING_HELP_MESSAGE, new TaskManager(),
                Collections.emptyList());
        assertTrue(this.helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, new TaskManager(),
                Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        this.model.addTask(helper.generateTask(1));
        this.model.addTask(helper.generateTask(2));
        this.model.addTask(helper.generateTask(3));

        assertCommandSuccess(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_SUCCESS,
                new TaskManager(), Collections.emptyList());
    }

    // @@author A0146809W
    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add d/valid", expectedMessage);
        assertCommandFailure("add e/valid", expectedMessage);
        assertCommandFailure("add Valid Task p/1", MESSAGE_PRIORITY_CONSTRAINTS);
        assertCommandFailure("add Valid Task e/invalid time", MESSAGE_ENDTIME_CONSTRAINTS);
        assertCommandFailure("add Valid Task s/invalid e/tomorrow ", MESSAGE_STARTTIME_CONSTRAINTS);
        assertCommandFailure("add Valid Name e/gogo ", MESSAGE_ENDTIME_CONSTRAINTS);
    }
    // @@author

    @Test
    public void execute_add_invalidTaskData() {
        assertCommandFailure(ADD_INVALID_TASK_NAME, Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskManager expectedTM = new TaskManager();
        expectedTM.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded), String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTM, expectedTM.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();

        // setup starting state
        this.model.addTask(toBeAdded); // task already in internal task manager

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK);

    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskManager expectedTM = helper.generateTaskManager(2);
        List<? extends ReadOnlyTask> expectedList = expectedTM.getTaskList();

        // prepare task manager state
        helper.addToModel(this.model, 2);

        assertCommandSuccess(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_SUCCESS, expectedTM, expectedList);
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

        // set AB state to 2 tasks
        this.model.resetData(new TaskManager());
        for (Task p : taskList) {
            this.model.addTask(p);
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

        TaskManager expectedTM = helper.generateTaskManager(threeTasks);
        helper.addToModel(this.model, threeTasks);

        assertEquals(this.model.getFilteredTaskList().get(1), threeTasks.get(1));
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

    // @@author A0146809W
    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskManager expectedTM = helper.generateTaskManager(threeTasks);
        expectedTM.removeTask(threeTasks.get(1));
        helper.addToModel(this.model, threeTasks);

        HashSet<ReadOnlyTask> tasksToDelete = new HashSet<>();
        tasksToDelete.add(threeTasks.get(1));

        String resultMessage = String.format(MESSAGE_DELETE_TASK_SUCCESS, tasksToString(tasksToDelete));

        assertCommandSuccess("delete 2", resultMessage, expectedTM, expectedTM.getTaskList());
    }

    @Test
    public void execute_delete_removesValidRangeOfTasks() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> fourTasks = helper.generateTaskList(4);

        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        expectedTM.removeTask(fourTasks.get(0));
        expectedTM.removeTask(fourTasks.get(1));
        expectedTM.removeTask(fourTasks.get(2));
        expectedTM.removeTask(fourTasks.get(3));

        helper.addToModel(this.model, fourTasks);

        HashSet<ReadOnlyTask> deletedTasks = helper.generateTaskSet(fourTasks.get(0), fourTasks.get(1),
            fourTasks.get(2), fourTasks.get(3));
        String tasksAsString = CommandResult.tasksToString(deletedTasks);

        // Delete all tasks ranging from 1 to 4
        // Then checks if the task manager have no tasks left
        assertCommandBehavior(false, "delete 1-4",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, tasksAsString), expectedTM,
                expectedTM.getTaskList());
    }

    @Test
    public void execute_delete_removesMultipleTasksNotInOrder() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> fourTasks = helper.generateTaskList(5);

        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        expectedTM.removeTask(fourTasks.get(0));
        expectedTM.removeTask(fourTasks.get(3));

        helper.addToModel(this.model, fourTasks);

        HashSet<ReadOnlyTask> deletedTasks = helper.generateTaskSet(fourTasks.get(0), fourTasks.get(3));
        String tasksAsString = CommandResult.tasksToString(deletedTasks);

        // Delete tasks 1 and 4
        // Then checks if the task manager have no tasks left
        assertCommandBehavior(false, "delete 1 4",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, tasksAsString), expectedTM,
                expectedTM.getTaskList());
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
        Collections.sort(fourTasks, new TaskNameComparator());
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        Collections.sort(expectedList, new TaskNameComparator());
        helper.addToModel(this.model, fourTasks);

        assertCommandSuccess("find n/KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedTM,
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
        Collections.sort(fourTasks, new TaskNameComparator());
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = fourTasks;
        helper.addToModel(this.model, fourTasks);

        assertCommandSuccess("find n/KEY", Command.getMessageForTaskListShownSummary(expectedList.size()), expectedTM,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("key rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskManager expectedTM = helper.generateTaskManager(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget2);
        helper.addToModel(this.model, fourTasks);

        assertCommandSuccess("find n/key rAnDoM", Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedTM, expectedList);
    }
    // @@author

    // @@author A0138909R
    @Test
    public void execute_save_successful() throws Exception {
        String filePath = "data/testfile1.xml";
        File file = new File(filePath);
        file.delete();
        assertCommandSuccess(SAVE + filePath, String.format(SaveCommand.MESSAGE_SUCCESS, filePath),
                this.model.getTaskManager(), this.model.getFilteredTaskList());
        file.delete();
    }

    @Test
    public void execute_save_not_xml() throws Exception {
        String filePath = "";
        assertCommandFailure(SAVE + filePath, SaveCommand.MESSAGE_NOT_XML_FILE);
    }

    @Test
    public void execute_save_invalidFileName() throws Exception {
        String filePath = "data/??.xml";
        assertCommandFailure(SAVE + filePath, SaveCommand.MESSAGE_INVALID_FILE_NAME);
    }

    @Test
    public void execute_save_duplicateFile() throws Exception {
        String filePath = "data/testfile3.xml";
        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        assertCommandFailure(SAVE + filePath, SaveCommand.MESSAGE_DUPLICATE_FILE);
        file.delete();
    }

    @Test
    public void execute_save_inSameFile() throws Exception {
        String filePath = this.storage.getTaskManagerFilePath();
        assertCommandFailure(SAVE + filePath, filePath + SaveCommand.MESSAGE_USING_SAME_FILE);
    }

    // @@author
    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        private static final String PRIORITY_LOW = "low";

        Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            Priority privatePriority = new Priority(PRIORITY_LOW);
            EndTime endTime = new EndTime("tomorrow");
            Description description = new Description("111, alpha street");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, privatePriority, endTime, description, tags);
        }

        /**
         * Generates a valid task using the given seed. Running this function
         * with the same parameter values guarantees the returned task will have
         * the same state. Each unique seed will generate a unique Task object.
         *
         * @param seed
         *            used to generate the task data field values
         */
        private Task generateTask(int seed) throws Exception {
            return new Task(new Name("Task " + seed), new Priority("high"), new EndTime("today " + "2359"),
                    new Description("House of " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }

        /**
         * Generates the correct add command based on the task given
         */
        private String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" e/").append(p.getDeadline());
            cmd.append(" p/").append(p.getPriority());
            cmd.append(" d/").append(p.getDescription());

            UniqueTagList tags = p.getTags();
            for (Tag t : tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an TaskManager with auto-generated tasks.
         */
        private TaskManager generateTaskManager(int numGenerated) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates an TaskManager based on the list of Tasks given.
         */
        private TaskManager generateTaskManager(List<Task> tasks) throws Exception {
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
        private void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        private void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        private List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        private List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given name. Other fields will have some
         * dummy values.
         */
        private Task generateTaskWithName(String name) throws Exception {
            return new Task(new Name(name), new Priority(PRIORITY_LOW), new EndTime("today"),
                    new Description("House of 1"), new UniqueTagList(new Tag("tag")));
        }

        private HashSet<ReadOnlyTask> generateTaskSet(ReadOnlyTask... tasks) {
            return new HashSet<>(Arrays.asList(tasks));
        }
    }
}
