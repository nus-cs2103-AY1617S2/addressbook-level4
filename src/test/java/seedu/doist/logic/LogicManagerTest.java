package seedu.doist.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.doist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

import seedu.doist.commons.core.EventsCenter;
import seedu.doist.commons.events.model.TodoListChangedEvent;
import seedu.doist.commons.events.ui.JumpToListRequestEvent;
import seedu.doist.commons.events.ui.ShowHelpRequestEvent;
import seedu.doist.logic.commands.AddCommand;
import seedu.doist.logic.commands.ClearCommand;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.CommandResult;
import seedu.doist.logic.commands.DeleteCommand;
import seedu.doist.logic.commands.ExitCommand;
import seedu.doist.logic.commands.FindCommand;
import seedu.doist.logic.commands.HelpCommand;
import seedu.doist.logic.commands.ListCommand;
import seedu.doist.logic.commands.SelectCommand;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.AliasListMapManager;
import seedu.doist.model.AliasListMapModel;
import seedu.doist.model.ConfigManager;
import seedu.doist.model.ConfigModel;
import seedu.doist.model.Model;
import seedu.doist.model.ModelManager;
import seedu.doist.model.ReadOnlyTodoList;
import seedu.doist.model.TodoList;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Description;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskMatchingComparator;
import seedu.doist.model.task.Task;
import seedu.doist.storage.StorageManager;


public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private AliasListMapModel aliasModel;
    private ConfigModel configModel;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTodoList latestSavedTodoList;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TodoListChangedEvent tce) {
        latestSavedTodoList = new TodoList(tce.data);
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
        aliasModel = new AliasListMapManager();
        configModel = new ConfigManager();
        String tempTodoListFile = saveFolder.getRoot().getPath() + "TempTodoList.xml";
        String tempAliasListMapFile = saveFolder.getRoot().getPath() + "TempAliasListMap.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, aliasModel, configModel, new StorageManager(tempTodoListFile,
                tempAliasListMapFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedTodoList = new TodoList(model.getTodoList()); // last saved assumed to be up to date
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
     * Also confirms that both the 'to do list' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTodoList, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyTodoList expectedTodoList,
                                      List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedTodoList, expectedShownList);
    }

    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            ReadOnlyTodoList expectedAddressBook,
            List<? extends ReadOnlyTask> expectedShownList, boolean isTest) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedAddressBook, expectedShownList, isTest);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'to do list' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTodoList, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        TodoList expectedTodoList = new TodoList(model.getTodoList());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedTodoList, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal todo list data are same as those in the {@code expectedTodoList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTodoList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
                                       ReadOnlyTodoList expectedTodoList,
                                       List<? extends ReadOnlyTask> expectedShownList, boolean isFind) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        }

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, new ArrayList<ReadOnlyTask>(model.getFilteredTaskList()));

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTodoList, model.getTodoList());
        if (!isFind) {
            // If not find, then check saved todolist
            assertEquals(expectedTodoList, latestSavedTodoList);
        }
    }

    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
            ReadOnlyTodoList expectedAddressBook,
            List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(isCommandExceptionExpected, inputCommand, expectedMessage,
                expectedAddressBook, expectedShownList, false);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new TodoList(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new TodoList(), Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new TodoList(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add ", expectedMessage);
        assertCommandFailure("add Valid Name but empty argument \\under  ", expectedMessage);
        assertCommandFailure("add Valid Name \\from345 Invalid Prefix", expectedMessage);
        assertCommandFailure("add Valid Name \\from 1500 \\to 1600 \\ ", expectedMessage);
    }

    @Test
    public void execute_add_invalidPersonData() {
        //assertCommandFailure("add []\\[;] p/12345 e/valid@e.mail a/valid, address",
              //  Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        //assertCommandFailure("add Valid Name p/not_numbers e/valid@e.mail a/valid, address",
                //Phone.MESSAGE_PHONE_CONSTRAINTS);
        //assertCommandFailure("add Valid Name p/12345 e/notAnEmail a/valid, address",
                //Email.MESSAGE_EMAIL_CONSTRAINTS);
        //assertCommandFailure("add Valid Name p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag",
              //  Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.doLaundry();
        TodoList expectedTodoList = new TodoList();
        expectedTodoList.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedTodoList,
                expectedTodoList.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.doLaundry();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal todo list

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_DUPLICATE_TASK);
    }


    @Test
    public void execute_list_showsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TodoList expectedAB = helper.generateTodoList(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare todo list state
        helper.addToModel(model, 2);

        assertCommandSuccess("list all",
                ListCommand.MESSAGE_ALL,
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
        model.resetData(new TodoList());
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
        List<Task> threePersons = helper.generateTaskList(3);

        TodoList expectedAB = helper.generateTodoList(threePersons);
        helper.addToModel(model, threePersons);

        assertCommandSuccess("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threePersons.get(1));
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
    public void execute_delete_removesCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threePersons = helper.generateTaskList(3);

        TodoList expectedAB = helper.generateTodoList(threePersons);
        expectedAB.removeTask(threePersons.get(1));
        helper.addToModel(model, threePersons);

        ArrayList<Task> tasksDeleted = new ArrayList<Task>();
        tasksDeleted.add(threePersons.get(1));
        assertCommandSuccess("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, tasksDeleted),
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
        Task pTarget1 = helper.generateTaskWithDescription("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithDescription("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithDescription("should not Match");
        Task p2 = helper.generateTaskWithDescription("KEYKEYKEY sduauo");

        List<Task> fourPersons = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TodoList expectedAB = helper.generateTodoList(fourPersons);
        List<Task> expectedList = helper.generateTaskList(pTarget1, p2, pTarget2);
        helper.addToModel(model, fourPersons);

        // After the command, tasks will be auto sorted
        expectedList.sort(new ReadOnlyTaskMatchingComparator("KEY"));
        expectedAB.sortTasks(new ReadOnlyTaskMatchingComparator("KEY"));
        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList, true);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithDescription("bla bla KEY bla");
        Task p2 = helper.generateTaskWithDescription("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithDescription("key key");
        Task p4 = helper.generateTaskWithDescription("KEy sduauo");

        List<Task> fourPersons = helper.generateTaskList(p3, p1, p4, p2);
        TodoList expectedAB = helper.generateTodoList(fourPersons);
        List<Task> expectedList = fourPersons;
        helper.addToModel(model, fourPersons);

        // After the command, tasks will be auto sorted
        expectedList.sort(new ReadOnlyTaskMatchingComparator("KEY"));
        expectedAB.sortTasks(new ReadOnlyTaskMatchingComparator("KEY"));
        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList, true);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithDescription("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithDescription("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithDescription("key key");
        Task p1 = helper.generateTaskWithDescription("sduauo");

        List<Task> fourPersons = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TodoList expectedAB = helper.generateTodoList(fourPersons);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourPersons);

        // After the command, tasks will be auto sorted
        expectedList.sort(new ReadOnlyTaskMatchingComparator("key rAnDoM"));
        expectedAB.sortTasks(new ReadOnlyTaskMatchingComparator("key rAnDoM"));
        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList, true);
    }


    /**
     * A utility class to generate test data.
     */
    protected class TestDataHelper {

        protected Task doLaundry() throws Exception {
            Description name = new Description("Do Laundry");
            //Phone privatePhone = new Phone("111111");
            //Email email = new Email("adam@gmail.com");
            //Address privateAddress = new Address("111, alpha street");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, tags);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */

        // TODO: MAKE IT EASIER TO GENERATE RANDOM DATES
        protected Task generateTask(int seed) throws Exception {
            return new Task(
                    new Description("Task " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the task given */
        protected String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getDescription().toString());

            UniqueTagList tags = p.getTags();
            cmd.append(" \\under ");
            for (Tag t: tags) {
                cmd.append(t.tagName);
                cmd.append(" ");
            }
            String trimmedCmd = cmd.toString().trim();
            return trimmedCmd;
        }

        /**
         * Generates an TodoList with auto-generated tasks.
         */
        protected TodoList generateTodoList(int numGenerated) throws Exception {
            TodoList todoList = new TodoList();
            addToTodoList(todoList, numGenerated);
            return todoList;
        }

        /**
         * Generates an TodoList based on the list of Tasks given.
         */
        protected TodoList generateTodoList(List<Task> tasks) throws Exception {
            TodoList todoList = new TodoList();
            addToTodoList(todoList, tasks);
            return todoList;
        }

        /**
         * Adds auto-generated Task objects to the given TodoList
         * @param todoList The TodoList to which the Tasks will be added
         */
        protected void addToTodoList(TodoList todoList, int numGenerated) throws Exception {
            addToTodoList(todoList, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given TodoList
         */
        protected void addToTodoList(TodoList todoList, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                todoList.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        protected void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        protected void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         */
        protected List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        protected List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Task object with given description. Other fields will have some dummy values.
         */
        // TODO: REFACTOR THIS
        protected Task generateTaskWithDescription(String name) throws Exception {
            return new Task(
                    new Description(name),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
