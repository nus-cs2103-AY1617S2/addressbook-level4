package todolist.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static todolist.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static todolist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

import todolist.commons.core.EventsCenter;
import todolist.commons.events.model.ToDoListChangedEvent;
import todolist.commons.events.ui.JumpToListRequestEvent;
import todolist.commons.events.ui.ShowHelpRequestEvent;
import todolist.logic.commands.AddCommand;
import todolist.logic.commands.ClearCommand;
import todolist.logic.commands.Command;
import todolist.logic.commands.CommandResult;
import todolist.logic.commands.DeleteCommand;
import todolist.logic.commands.ExitCommand;
import todolist.logic.commands.FindCommand;
import todolist.logic.commands.HelpCommand;
import todolist.logic.commands.ListCommand;
import todolist.logic.commands.SelectCommand;
import todolist.logic.commands.exceptions.CommandException;
import todolist.model.Model;
import todolist.model.ModelManager;
import todolist.model.ReadOnlyToDoList;
import todolist.model.ToDoList;
import todolist.model.tag.Tag;
import todolist.model.tag.UniqueTagList;
import todolist.model.task.Description;
import todolist.model.task.EndTime;
import todolist.model.task.ReadOnlyTask;
import todolist.model.task.StartTime;
import todolist.model.task.Task;
import todolist.model.task.Title;
import todolist.model.task.UrgencyLevel;
import todolist.model.task.Venue;
import todolist.storage.StorageManager;

public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    // These are for checking the correctness of the events raised
    private ReadOnlyToDoList latestSavedToDoList;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(ToDoListChangedEvent abce) {
        latestSavedToDoList = new ToDoList(abce.data);
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
        String tempToDoListFile = saveFolder.getRoot().getPath() + "TempToDoList.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempToDoListFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedToDoList = new ToDoList(model.getToDoList()); // last saved
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
        assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and
     * that the result message is correct.
     * Also confirms that both the 'address book' and the 'last shown list' are
     * as specified.
     *
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyToDoList,
     *      List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            ReadOnlyToDoList expectedToDoList,
            List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedToDoList, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that
     * the result message is correct.
     * Both the 'address book' and the 'last shown list' are verified to be
     * unchanged.
     *
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyToDoList,
     *      List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        ToDoList expectedToDoList = new ToDoList(model.getToDoList());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedToDoList, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager
     * object's state are as expected:<br>
     * - the internal address book data are same as those in the
     * {@code expectedToDoList} <br>
     * - the backing list shown by UI matches the {@code shownList} <br>
     * - {@code expectedToDoList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
            ReadOnlyToDoList expectedToDoList,
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
        assertEquals(expectedToDoList, model.getToDoList());
        assertEquals(expectedToDoList, latestSavedToDoList);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new ToDoList(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new ToDoList(), Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new ToDoList(), Collections.emptyList());
    }

    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        //assertCommandFailure("add wrong args wrong args", expectedMessage);
        //assertCommandFailure("add Valid Title 12345 e/valid@email.butNoVenuePrefix a/valid,address", expectedMessage);
        //assertCommandFailure("add Valid Title p/12345 valid@email.butNoPrefix a/valid, address", expectedMessage);
        //assertCommandFailure("add Valid Title p/12345 e/valid@email.butNoEndTimePrefix valid, address",
        //        expectedMessage);
    }

    @Test
    public void execute_add_invalidTaskData() {
//        assertCommandFailure("add []\\[;] v/12345 s/valid@e.mail e/valid, address",
//                Title.MESSAGE_TITLE_CONSTRAINTS);
//        assertCommandFailure("add Valid Title v/not_numbers s/valid@e.mail e/valid, address",
//                Venue.MESSAGE_VENUE_CONSTRAINTS);
//        assertCommandFailure("add Valid Title v/12345 s/notAnStartTime e/valid, address",
//                StartTime.MESSAGE_STARTTIME_CONSTRAINTS);
//        assertCommandFailure("add Valid Title v/12345 s/valid@e.mail e/valid, address t/invalid_-[.tag",
//                Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.cs2103();
        ToDoList expectedAB = new ToDoList();
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
        Task toBeAdded = helper.cs2103();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal address book

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK);

    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        ToDoList expectedAB = helper.generateToDoList(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given
     * command
     * targeting a single task in the shown list, using visible index.
     *
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list
     *            based on visible index.
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
     * command
     * targeting a single task in the shown list, using visible index.
     *
     * @param commandWord
     *            to test assuming it targets a single task in the last shown
     *            list
     *            based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 tasks
        model.resetData(new ToDoList());
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

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("select f2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2),
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

        ToDoList expectedAB = helper.generateToDoList(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete e2",
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
    public void execute_find_onlyMatchesFullWordsInTitles() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithTitle("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithTitle("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithTitle("KE Y");
        Task p2 = helper.generateTaskWithTitle("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
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
        Task p1 = helper.generateTaskWithTitle("bla bla KEY bla");
        Task p2 = helper.generateTaskWithTitle("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithTitle("key key");
        Task p4 = helper.generateTaskWithTitle("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
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
        Task pTarget1 = helper.generateTaskWithTitle("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithTitle("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateTaskWithTitle("key key");
        Task p1 = helper.generateTaskWithTitle("sduauo");

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        ToDoList expectedAB = helper.generateToDoList(fourTasks);
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

        Task cs2103() throws Exception {
            Title name = new Title("CS2103 Tutorial");
            Venue privateVenue = new Venue("COM1 B103");
            StartTime privateStartTime = new StartTime("Wed 10am");
            EndTime privateEndTime = new EndTime("Wednesday 11am");
            UrgencyLevel privateUrgencyLevel = new UrgencyLevel("3");
            Description privateDescription = new Description("I love 2103!!!");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, privateVenue, privateStartTime, privateEndTime,
                    privateUrgencyLevel, privateDescription, tags);
        }

        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values guarantees the
         * returned task will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed
         *            used to generate the task data field values
         */
        Task generateTask(int seed) throws Exception {
            return new Task(
                    new Title("Task " + seed),
                    new Venue("" + Math.abs(seed)),
                    new StartTime("0000"),
                    new EndTime("1200" + seed),
                    new UrgencyLevel("3"),
                    new Description("I love 2103!!"),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))));
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getTitle().toString());
            if(p.getVenue().isPresent()) {
                cmd.append(" /venue ").append(p.getVenue().get());
            }
            if(p.getStartTime().isPresent()) {
                cmd.append(" /from ").append(p.getStartTime().get());
            }
            if(p.getEndTime().isPresent()) {
                cmd.append(" /to ").append(p.getEndTime().get());
            }
            if(p.getUrgencyLevel().isPresent()) {
                cmd.append(" /level ").append(p.getUrgencyLevel().get());
            }
            if(p.getDescription().isPresent()) {
                cmd.append(" /description ").append(p.getDescription().get());
            }
            UniqueTagList tags = p.getTags();
            for (Tag t : tags) {
                cmd.append(" #").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an ToDoList with auto-generated tasks.
         */
        ToDoList generateToDoList(int numGenerated) throws Exception {
            ToDoList addressBook = new ToDoList();
            addToToDoList(addressBook, numGenerated);
            return addressBook;
        }

        /**
         * Generates an ToDoList based on the list of Tasks given.
         */
        ToDoList generateToDoList(List<Task> tasks) throws Exception {
            ToDoList addressBook = new ToDoList();
            addToToDoList(addressBook, tasks);
            return addressBook;
        }

        /**
         * Adds auto-generated Task objects to the given ToDoList
         *
         * @param addressBook
         *            The ToDoList to which the Tasks will be added
         */
        void addToToDoList(ToDoList toDoList, int numGenerated) throws Exception {
            addToToDoList(toDoList, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given ToDoList
         */
        void addToToDoList(ToDoList toDoList, List<Task> tasksToAdd) throws Exception {
            for (Task p : tasksToAdd) {
                toDoList.addTask(p);
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
        Task generateTaskWithTitle(String name) throws Exception {
            return new Task(
                    new Title(name),
                    new Venue("location"),
                    new StartTime("Today"),
                    new EndTime("Tomorrow"),
                    new UrgencyLevel("3"),
                    new Description("I love 2103!!"),
                    new UniqueTagList(new Tag("tag")));
        }
    }
}
