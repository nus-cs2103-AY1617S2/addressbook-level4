package seedu.jobs.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jobs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jobs.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.jobs.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

import seedu.jobs.commons.core.EventsCenter;
import seedu.jobs.commons.events.model.TaskBookChangedEvent;
import seedu.jobs.commons.events.ui.JumpToListRequestEvent;
import seedu.jobs.commons.events.ui.ShowHelpRequestEvent;
import seedu.jobs.logic.commands.AddCommand;
import seedu.jobs.logic.commands.ClearCommand;
import seedu.jobs.logic.commands.Command;
import seedu.jobs.logic.commands.CommandResult;
import seedu.jobs.logic.commands.DeleteCommand;
import seedu.jobs.logic.commands.ExitCommand;
import seedu.jobs.logic.commands.FindCommand;
import seedu.jobs.logic.commands.HelpCommand;
import seedu.jobs.logic.commands.ListCommand;
import seedu.jobs.logic.commands.SelectCommand;
import seedu.jobs.logic.commands.exceptions.CommandException;
import seedu.jobs.model.Model;
import seedu.jobs.model.ModelManager;
import seedu.jobs.model.ReadOnlyTaskBook;
import seedu.jobs.model.TaskBook;
import seedu.jobs.model.tag.Tag;
import seedu.jobs.model.tag.UniqueTagList;
import seedu.jobs.model.task.Description;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Period;
import seedu.jobs.model.task.ReadOnlyTask;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.Time;
import seedu.jobs.storage.StorageManager;


public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskBook latestTaskAddressBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskBookChangedEvent abce) {
        latestTaskAddressBook = new TaskBook(abce.data);
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
        String tempTaskBookFile = saveFolder.getRoot().getPath() + "TempAddressBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        String tempLoginInfoFile = saveFolder.getRoot().getPath() + "TempLoginInfo.json";
        logic = new LogicManager(model, new StorageManager(tempTaskBookFile, tempPreferencesFile, tempLoginInfoFile));
        EventsCenter.getInstance().registerHandler(this);

        latestTaskAddressBook = new TaskBook(model.getTaskBook()); // last saved assumed to be up to date
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
     * Also confirms that both the 'address book' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskBook, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyTaskBook expectedAddressBook,
                                      List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedAddressBook, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'address book' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTaskBook, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        TaskBook expectedTaskBook = new TaskBook(model.getTaskBook());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedTaskBook, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedAddressBook} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
                                       ReadOnlyTaskBook expectedTaskBook,
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
        assertEquals(expectedTaskBook, model.getTaskBook());
        assertEquals(expectedTaskBook, latestTaskAddressBook);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new TaskBook(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new TaskBook(), Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new TaskBook(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        //TODO
//        assertCommandFailure("add wrong start/args wrong args", expectedMessage);
//        assertCommandFailure("add task start/01/01/01 00:00 01/01/01 00:00", expectedMessage);
//        assertCommandFailure("add task start/01/01/01 00:00 end/01/01/01 00:00
//          desciption/invalid prefix", expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() {
        //151 characters
        assertCommandFailure("add A123456789A123456789A123456789A123456789A123456789A123456789A123456789"
                + "A123456789A123456789A123456789A123456789A123456789A123456789A123456789A1234567891",
                Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandFailure("add Valid start/01-02-17 18:49",
                Time.MESSAGE_TIME_CONSTRAINT);
        assertCommandFailure("add Valid start/02:01:05 18:49",
                Time.MESSAGE_TIME_CONSTRAINT);
        //TODO
        //recur constraint

        assertCommandFailure("add valid desc/A123456789A123456789A123456789A123456789A123456789A123456789A123456789"
                + "A123456789A123456789A123456789A123456789A123456789A123456789A123456789A1234567891",
                Description.MESSAGE_DESCRIPTION_CONSTRAINT);
        assertCommandFailure("add Valid tag/invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        TaskBook expectedAB = new TaskBook();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal address book

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_DUPLICATE_TASK);

    }


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TaskBook expectedAB = helper.generateTaskBook(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare address book state
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
        model.resetData(new TaskBook());
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

        TaskBook expectedAB = helper.generateAddressBook(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2),
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
        List<Task> threeTasks = helper.generateTaskList(3);

        TaskBook expectedAB = helper.generateAddressBook(threeTasks);
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
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithName("KE Y");
        Task p2 = helper.generateTaskWithName("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        TaskBook expectedAB = helper.generateAddressBook(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
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
        TaskBook expectedAB = helper.generateAddressBook(fourTasks);
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
        Task pTarget1 = helper.generateTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithName("key key");
        Task p1 = helper.generateTaskWithName("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        TaskBook expectedAB = helper.generateAddressBook(fourTasks);
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

        Task adam() throws Exception {
            Name name = new Name(Optional.of("Test Task"));
            Time start = new Time(Optional.of("03/12/2017 15:00"));
            Time end = new Time(Optional.of("03/12/2017 16:00"));
            Description desc = new Description(Optional.of("Valid task"));
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            Period period = new Period(Optional.of("0"));
            return new Task(name, start, end, desc, tags, period);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Name(Optional.of("Task " + seed)),
                    new Time(Optional.of("03/11/2017 15:00")),
                    new Time(Optional.of("03/11/2017 16:00")),
                    new Description(Optional.of("House of " + seed)),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)),
                    new Tag("tag" + Math.abs(seed + 1))),
                    new Period(Optional.of("0"))
            );
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task t) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(t.getName().toString());
            cmd.append(" start/").append(t.getStartTime());
            cmd.append(" end/").append(t.getEndTime());
            cmd.append(" desc/").append(t.getDescription());

            UniqueTagList tags = t.getTags();
            for (Tag tag: tags) {
                cmd.append(" tag/").append(tag.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated tasks.
         */
        TaskBook generateTaskBook(int numGenerated) throws Exception {
            TaskBook taskBook = new TaskBook();
            addToTaskBook(taskBook, numGenerated);
            return taskBook;
        }

        /**
         * Generates an AddressBook based on the list of Tasks given.
         */
        TaskBook generateAddressBook(List<Task> tasks) throws Exception {
            TaskBook addressBook = new TaskBook();
            addToTaskBook(addressBook, tasks);
            return addressBook;
        }

        /**
         * Adds auto-generated Task objects to the given AddressBook
         * @param taskBook The AddressBook to which the Tasks will be added
         */
        void addToTaskBook(TaskBook taskBook, int numGenerated) throws Exception {
            addToTaskBook(taskBook, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given AddressBook
         */
        void addToTaskBook(TaskBook taskBook, List<Task> tasksToAdd) throws Exception {
            for (Task t: tasksToAdd) {
                taskBook.addTask(t);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task t: tasksToAdd) {
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
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generateTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(Optional.of(name)),
                    new Time(Optional.of("03/11/2017 15:00")),
                    new Time(Optional.of("03/11/2017 16:00")),
                    new Description(Optional.of("Valid task")),
                    new UniqueTagList(new Tag("tag")),
                    new Period(Optional.of("0"))
            );
        }
    }
}
