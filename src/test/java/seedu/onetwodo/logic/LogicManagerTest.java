package seedu.onetwodo.logic;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.onetwodo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.events.model.ToDoListChangedEvent;
import seedu.onetwodo.commons.events.ui.JumpToListRequestEvent;
import seedu.onetwodo.commons.events.ui.ShowHelpRequestEvent;
import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.CommandResult;
import seedu.onetwodo.logic.commands.DeleteCommand;
import seedu.onetwodo.logic.commands.ExitCommand;
import seedu.onetwodo.logic.commands.FindCommand;
import seedu.onetwodo.logic.commands.HelpCommand;
import seedu.onetwodo.logic.commands.ListCommand;
import seedu.onetwodo.logic.commands.SelectCommand;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.Model;
import seedu.onetwodo.model.ModelManager;
import seedu.onetwodo.model.ReadOnlyToDoList;
import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.model.tag.Tag;
import seedu.onetwodo.model.tag.UniqueTagList;
import seedu.onetwodo.model.task.Description;
import seedu.onetwodo.model.task.EndDate;
import seedu.onetwodo.model.task.Name;
import seedu.onetwodo.model.task.Priority;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.Recurring;
import seedu.onetwodo.model.task.StartDate;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.storage.StorageManager;


public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
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

        latestSavedToDoList = new ToDoList(model.getToDoList()); // last saved assumed to be up to date
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
     * Also confirms that both the 'toDo List' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyToDoList, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyToDoList expectedToDoList,
                                      List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedToDoList, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'toDo List' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyToDoList, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        ToDoList expectedToDoList = new ToDoList(model.getToDoList());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedToDoList, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedToDoList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedToDoList} was saved to the storage file. <br>
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

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, model.getFilteredTaskList());

        //Confirm the state of data (saved and in-memory) is as expected
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
        // TODO improve paser to prevent double prefix for s/ e/ and d/

        /*assertCommandFailure("add Valid Name 12345 e/tomorrow 5pm e/tomorrow 6pm d/many EndDate", expectedMessage);
        assertCommandFailure("add Valid Name d/more than one description d/more description", expectedMessage);
        assertCommandFailure("add Valid Name s/tomorrow s/tomorrow e/tomorrow d/too much StartDate", expectedMessage);*/
    }

    @Test
    public void execute_add_invalidTaskData() {
        assertCommandFailure("add []\\[;] s/today e/tomorrow d/valid, address",
                Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandFailure("add Valid Name s/wrong start date :) e/tomorrow 9pm d/valid, address",
                StartDate.MESSAGE_DATE_INPUT_CONSTRAINTS);
        assertCommandFailure("add Valid Name s/tomorrow 9pm e/wrong end date :) d/valid, address",
                EndDate.MESSAGE_DATE_INPUT_CONSTRAINTS);
        assertCommandFailure("add Valid Name s/tomorrow 8pm e/tomorrow 9pm d/valid, address t/invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertCommandFailure("add Valid Name r/daily d/valid",
                Recurring.RECURRING_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
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
        ToDoList expectedAB = helper.generateToDoList(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare toDo List state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
                ListCommand.MESSAGE_LIST_UNDONE_SUCCESS,
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
        List<Task> taskList = helper.generateTaskList(2, TaskType.TODO);

        // set AB state to 2 tasks
        model.resetData(new ToDoList());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandFailure(commandWord + " t3", expectedMessage);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        // TODO: solve the bug below
        // assertIncorrectIndexFormatBehaviorForCommand("select z1", expectedMessage);
        // assertIncorrectIndexFormatBehaviorForCommand("select 65868434", expectedMessage);
        assertIncorrectIndexFormatBehaviorForCommand("select 1", expectedMessage);
        assertIncorrectIndexFormatBehaviorForCommand("select asd", expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("select");
    }

    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeEventTasks = helper.generateTaskList(3, TaskType.EVENT);
        List<Task> threeDeadlineTasks = helper.generateTaskList(3, TaskType.DEADLINE);
        List<Task> threeToDoTasks = helper.generateTaskList(3, TaskType.TODO);
        List<Task> allTasks = new ArrayList<Task>(threeEventTasks);
        allTasks.addAll(threeDeadlineTasks);
        allTasks.addAll(threeToDoTasks);
        ToDoList expectedAB = helper.generateToDoList(allTasks);
        helper.addToModel(model, allTasks);

        assertCommandSuccess("select e2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, "E2"),
                expectedAB,
                expectedAB.getTaskList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeEventTasks.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete z1", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeEventTasks = helper.generateTaskList(3, TaskType.EVENT);
        List<Task> threeDeadlineTasks = helper.generateTaskList(3, TaskType.DEADLINE);
        List<Task> threeToDoTasks = helper.generateTaskList(3, TaskType.TODO);
        List<Task> allTasks = new ArrayList<Task>(threeEventTasks);
        allTasks.addAll(threeDeadlineTasks);
        allTasks.addAll(threeToDoTasks);
        ToDoList expectedAB = helper.generateToDoList(allTasks);
        expectedAB.removeTask(threeEventTasks.get(1));
        helper.addToModel(model, allTasks);

        assertCommandSuccess("delete e2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeEventTasks.get(1)),
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
        Task pTarget1 = helper.generateToDoTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateToDoTaskWithName("bla KEY bla bceofeia");
        Task p1 = helper.generateToDoTaskWithName("KE Y");
        Task p2 = helper.generateToDoTaskWithName("KEYKEYKEY sduauo");

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
        Task p1 = helper.generateToDoTaskWithName("bla bla KEY bla");
        Task p2 = helper.generateToDoTaskWithName("bla KEY bla bceofeia");
        Task p3 = helper.generateToDoTaskWithName("key key");
        Task p4 = helper.generateToDoTaskWithName("KEy sduauo");

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
        Task pTarget1 = helper.generateToDoTaskWithName("bla bla KEY bla");
        Task pTarget2 = helper.generateToDoTaskWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generateToDoTaskWithName("key key");
        Task p1 = helper.generateToDoTaskWithName("sduauo");

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

        Task adam() throws Exception {
            Name name = new Name("Meet boss");
            StartDate startDate = new StartDate("tomorrow 7pm");
            EndDate endDate = new EndDate("tomorrow 8pm");
            Recurring recur = new Recurring("weekly");
            Priority priority = new Priority("high");
            Description description = new Description("Bring report, laptop and coffee for boss");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longerTag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, startDate, endDate, recur, priority, description, tags);
        }


        /** Generates the correct add command string based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");
            cmd.append(p.getName().toString());

            if (p.hasStartDate()) {
                cmd.append(" s/").append(p.getStartDate());
            }
            if (p.hasEndDate()) {
                cmd.append(" e/").append(p.getEndDate());
            }
            if (p.hasRecur()) {
                cmd.append(" r/").append(p.getRecur());
            }
            if (p.hasPriority()) {
                cmd.append(" p/").append(p.getPriority());
            }
            if (p.hasDescription()) {
                cmd.append(" d/").append(p.getDescription());
            }
            if (p.hasTag()) {
                UniqueTagList tags = p.getTags();
                for (Tag t: tags) {
                    cmd.append(" t/").append(t.tagName);
                }
            }
            return cmd.toString();
        }

        /**
         * Generates an ToDoList with auto-generated tasks.
         */
        ToDoList generateToDoList(int numGenerated) throws Exception {
            ToDoList toDoList = new ToDoList();
            addToToDoList(toDoList, numGenerated);
            return toDoList;
        }

        /**
         * Generates an ToDoList based on the list of Tasks given.
         */
        ToDoList generateToDoList(List<Task> tasks) throws Exception {
            ToDoList toDoList = new ToDoList();
            addToToDoList(toDoList, tasks);
            return toDoList;
        }

        /**
         * Adds auto-generated Task objects to the given ToDoList
         * @param toDoList The ToDoList to which the Tasks will be added
         */
        void addToToDoList(ToDoList toDoList, int numGenerated) throws Exception {
            addToToDoList(toDoList, generateTaskList(numGenerated, TaskType.TODO));
        }

        /**
         * Adds the given list of Tasks to the given ToDoList
         */
        void addToToDoList(ToDoList toDoList, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                toDoList.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Tasks will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated, TaskType.TODO));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Tasks based on the flags.
         * @param taskType specified type of task to generate.
         */
        List<Task> generateTaskList(int numGenerated, TaskType taskType) throws Exception {
            List<Task> tasks = new ArrayList<>();

            for (int i = 1; i <= numGenerated; i++) {
                switch(taskType) {
                case EVENT:
                    tasks.add(generateEventTaskWithName("Event Task " + i));
                    break;
                case DEADLINE:
                    tasks.add(generateDeadlineTaskWithName("Deadline Task " + i));
                    break;
                case TODO:
                    tasks.add(generateToDoTaskWithName("ToDo Task " + i));
                    break;
                }
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }

        /**
         * Generates a Event Task object with given name. Other fields will have some dummy values.
         */
        Task generateEventTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new StartDate("tomorrow 8pm"),
                    new EndDate("tomorrow 9pm"),
                    new Recurring(""),
                    new Priority(""),
                    new Description(""),
                    new UniqueTagList(new Tag("tag"))
            );
        }
        /**
         * Generates a Event Task object with given name. Other fields will have some dummy values.
         */
        Task generateDeadlineTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new StartDate(""),
                    new EndDate("tomorrow 9pm"),
                    new Recurring(""),
                    new Priority(""),
                    new Description(""),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a ToDo Task object with given name. Other fields will have some dummy values.
         */
        Task generateToDoTaskWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new StartDate(""),
                    new EndDate(""),
                    new Recurring(""),
                    new Priority(""),
                    new Description(""),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a ToDo Task object with given names. Other fields will have some dummy values.
         */
        Task generateToDoTaskWithTags(String name, String tag1, String tag2) throws Exception {
            return new Task (
                    new Name(name),
                    new StartDate(""),
                    new EndDate(""),
                    new Recurring(""),
                    new Priority(""),
                    new Description(""),
                    new UniqueTagList(tag1, tag2)
            );
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
                    new Name("Task " + seed),
                    new StartDate("tomorrow 10pm"),
                    new EndDate("tomorrow 11pm"),
                    new Recurring("weekly"),
                    new Priority("h"),
                    new Description("This is task numner " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }
    }
}
