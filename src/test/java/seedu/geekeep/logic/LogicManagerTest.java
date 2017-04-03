package seedu.geekeep.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.geekeep.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.geekeep.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.geekeep.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

import seedu.geekeep.commons.core.EventsCenter;
import seedu.geekeep.commons.events.model.GeeKeepChangedEvent;
import seedu.geekeep.commons.events.ui.JumpToListRequestEvent;
import seedu.geekeep.commons.events.ui.ShowHelpRequestEvent;
import seedu.geekeep.logic.commands.AddCommand;
import seedu.geekeep.logic.commands.ClearCommand;
import seedu.geekeep.logic.commands.Command;
import seedu.geekeep.logic.commands.CommandResult;
import seedu.geekeep.logic.commands.DeleteCommand;
import seedu.geekeep.logic.commands.DoneCommand;
import seedu.geekeep.logic.commands.ExitCommand;
import seedu.geekeep.logic.commands.FindCommand;
import seedu.geekeep.logic.commands.HelpCommand;
import seedu.geekeep.logic.commands.ListCommand;
import seedu.geekeep.logic.commands.ListDoneCommand;
import seedu.geekeep.logic.commands.ListUndoneCommand;
import seedu.geekeep.logic.commands.RedoCommand;
import seedu.geekeep.logic.commands.UndoCommand;
import seedu.geekeep.logic.commands.UndoneCommand;
import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.model.GeeKeep;
import seedu.geekeep.model.Model;
import seedu.geekeep.model.ModelManager;
import seedu.geekeep.model.ReadOnlyGeeKeep;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Description;
import seedu.geekeep.model.task.ReadOnlyTask;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.Title;

public class LogicManagerTest {
    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();
    private Model model;

    private Logic logic;
    //These are for checking the correctness of the events raised
    private ReadOnlyGeeKeep latestSavedGeeKeep;
    private boolean helpShown;

    private int targetedJumpIndex;

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        public Task event() throws Exception {
            Title title = new Title("Event");
            DateTime endDateTime = new DateTime("01-05-17 1630");
            DateTime startDateTime = new DateTime("01-04-17 1630");
            Description description = new Description("111, alpha street");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(title, startDateTime, endDateTime, description, tags, false);
        }

        //@@author A0139438W
        public Task deadline() throws Exception {
            Title title = new Title("Deadline");
            DateTime endDateTime = new DateTime("01-05-17 1630");
            Description description = new Description("222, beta street");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(title, null, endDateTime, description, tags, false);
        }

        public Task eventWithoutTime() throws Exception {
            Title title = new Title("Event Without Time");
            DateTime endDateTime = new DateTime("01-05-17");
            DateTime startDateTime = new DateTime("01-04-17");
            Description description = new Description("111, alpha street");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(title, startDateTime, endDateTime, description, tags, false);
        }

        public Task floatingTask() throws Exception {
            Title title = new Title("Floating Task");
            Description description = new Description("333, charlie street");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(title, null, null, description, tags, false);
        }

        //@@author
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
            for (Task p: tasksToAdd) {
                model.addTask(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given GeeKeep
         * @param geeKeep The GeeKeep to which the Tasks will be added
         */
        void addToGeeKeep(GeeKeep geeKeep, int numGenerated) throws Exception {
            addToGeeKeep(geeKeep, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given GeeKeep
         */
        void addToGeeKeep(GeeKeep geeKeep, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                geeKeep.addTask(p);
            }
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getTitle().toString());
            if (!p.isFloatingTask()) {
                if (p.isEvent()) {
                    cmd.append(" s/").append(p.getStartDateTime());
                }
                cmd.append(" e/").append(p.getEndDateTime());
            }
            cmd.append(" d/").append(p.getDescriptoin());

            UniqueTagList tags = p.getTags();
            for (Tag t: tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
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
                    new Title("Task " + seed),
                    new DateTime("01-04-17 1630"),
                    new DateTime("01-05-17 1630"),
                    new Description("House of " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))),
                    false
            );
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
         * Generates a GeeKeep with auto-generated tasks.
         */
        GeeKeep generateGeeKeep(int numGenerated) throws Exception {
            GeeKeep geeKeep = new GeeKeep();
            addToGeeKeep(geeKeep, numGenerated);
            return geeKeep;
        }

        /**
         * Generates a GeeKeep based on the list of Tasks given.
         */
        GeeKeep generateGeeKeep(List<Task> tasks) throws Exception {
            GeeKeep geeKeep = new GeeKeep();
            addToGeeKeep(geeKeep, tasks);
            return geeKeep;
        }

        /**
         * Generates a Task object with given title. Other fields will have some dummy values.
         */
        Task generateTaskWithTitle(String title) throws Exception {
            return new Task(
                    new Title(title),
                    new DateTime("01-04-17 1630"),
                    new DateTime("01-05-17 1630"),
                    new Description("House of 1"),
                    new UniqueTagList(new Tag("tag")),
                    false
            );
        }
    }



    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal geekeep data are same as those in the {@code expectedGeeKeep} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedGeeKeep} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
                                       ReadOnlyGeeKeep expectedGeeKeep,
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
        assertEquals(expectedGeeKeep, model.getGeeKeep());
        assertEquals(expectedGeeKeep, latestSavedGeeKeep);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'geekeep' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyGeeKeep, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        GeeKeep expectedGeeKeep = new GeeKeep(model.getGeeKeep());
        List<ReadOnlyTask> expectedShownList = new ArrayList<>(model.getFilteredTaskList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedGeeKeep, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is not thrown and that the result message is correct.
     * Also confirms that both the 'geekeep' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyGeeKeep, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyGeeKeep expectedGeeKeep,
                                      List<? extends ReadOnlyTask> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedGeeKeep, expectedShownList);
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
        model.resetData(new GeeKeep());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandFailure(commandWord + " 3", expectedMessage);
    }

    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add",
                expectedMessage);

    }

    //@@author A0139438W
    @Test
    public void execute_add_invalidTaskData() {
        assertCommandFailure("add []\\[;] s/01-04-17 1630 e/01-05-17 1630 d/valid, location",
                Title.MESSAGE_TITLE_CONSTRAINTS);
        assertCommandFailure("add Valid Title s/not_numbers e/01-05-17 1630 d/valid, location",
                DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        assertCommandFailure("add Valid Title s/01-04-17 1630 e/not_numbers d/valid, location",
                DateTime.MESSAGE_DATETIME_CONSTRAINTS);
        assertCommandFailure("add Valid Title s/01-05-17 1630 e/01-04-17 1630 d/valid, location",
                Task.MESSAGE_ENDDATETIME_LATER_CONSTRAINTS);
        assertCommandFailure("add Valid Title s/01-05-17 1630 d/valid, location",
                Task.MESSAGE_DATETIME_MATCH_CONSTRAINTS);
        assertCommandFailure(
                "add Valid Title s/01-04-17 1630 e/01-05-17 1630 d/valid, location t/invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();

        // add a new event
        Task toBeAdded = helper.event();
        GeeKeep expectedAB = new GeeKeep();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

        // add a new event without time field
        toBeAdded = helper.eventWithoutTime();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

        // add a new deadline
        toBeAdded = helper.deadline();
        expectedAB.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTaskList());

        // add a new floating task
        toBeAdded = helper.floatingTask();
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
        Task toBeAdded = helper.event();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal geekeep

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_DUPLICATE_TASK);

    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new GeeKeep(), Collections.emptyList());
    }

    @Test
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        GeeKeep expectedAB = helper.generateGeeKeep(threeTasks);
        expectedAB.removeTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete");
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    //@@author A0147622H
    @Test
    public void execute_undo_redo_errorMessageShown() throws Exception {
        String expectedMessage = String.format(UndoCommand.MESSAGE_NOTHING_TO_UNDO);
        assertCommandFailure("undo", expectedMessage);

        expectedMessage = String.format(RedoCommand.MESSAGE_NOTHING_TO_REDO);
        assertCommandFailure("redo", expectedMessage);
    }

    @Test
    public void execute_undo_redo_Success() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        GeeKeep expectedAB = helper.generateGeeKeep(threeTasks);
        expectedAB.removeTask(threeTasks.get(2));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("delete 3",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(2)),
                expectedAB,
                expectedAB.getTaskList());

        // Add deleted task back to expected Geekeep
        expectedAB.addTask(threeTasks.get(2));

        assertCommandSuccess("undo",
                String.format(UndoCommand.MESSAGE_SUCCESS, "delete 3"),
                expectedAB,
                expectedAB.getTaskList());

        // Deleted task from expected Geekeep again
        expectedAB.removeTask(threeTasks.get(2));

        assertCommandSuccess("redo",
                String.format(RedoCommand.MESSAGE_SUCCESS, "delete 3"),
                expectedAB,
                expectedAB.getTaskList());
    }

    //@@author A0139438W
    @Test
    public void execute_done_undone_CorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        GeeKeep expectedAB = helper.generateGeeKeep(threeTasks);
        expectedAB.markTaskDone(1);
        helper.addToModel(model, threeTasks);

        assertCommandSuccess("done 2",
                String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());

        expectedAB.markTaskUndone(1);

        assertCommandSuccess("undone 2",
                String.format(UndoneCommand.MESSAGE_UNDONE_TASK_SUCCESS, threeTasks.get(1)),
                expectedAB,
                expectedAB.getTaskList());
    }

    @Test
    public void execute_done_undone_IndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("done");
        assertIndexNotFoundBehaviorForCommand("undone");
    }


    @Test
    public void execute_done_undone_InvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedDoneMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("done", expectedDoneMessage);
        String expectedUndoneMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoneCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("undone", expectedUndoneMessage);
    }

    //@@author
    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new GeeKeep(), Collections.emptyList());
    }

    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generateTaskWithTitle("bla bla KEY bla");
        Task p2 = helper.generateTaskWithTitle("bla KEY bla bceofeia");
        Task p3 = helper.generateTaskWithTitle("key key");
        Task p4 = helper.generateTaskWithTitle("KEy sduauo");

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        GeeKeep expectedAB = helper.generateGeeKeep(fourTasks);
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
        GeeKeep expectedAB = helper.generateGeeKeep(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    @Test
    public void execute_find_onlyMatchesFullWordsInTitles() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generateTaskWithTitle("bla bla KEY bla");
        Task pTarget2 = helper.generateTaskWithTitle("bla KEY bla bceofeia");
        Task p1 = helper.generateTaskWithTitle("KE Y");
        Task p2 = helper.generateTaskWithTitle("KEYKEYKEY sduauo");

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        GeeKeep expectedAB = helper.generateGeeKeep(fourTasks);
        List<Task> expectedList = helper.generateTaskList(pTarget1, pTarget2);
        helper.addToModel(model, fourTasks);

        assertCommandSuccess("find KEY",
                Command.getMessageForTaskListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new GeeKeep(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_invalid() {
        String invalidCommand = "       ";
        assertCommandFailure(invalidCommand, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        GeeKeep expectedAB = helper.generateGeeKeep(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare geekeep state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }

    //@@author A0139438W
    @Test
    public void execute_listdone_listundone_showsTasksCorrectly() throws Exception {
        // prepare expectations for list undone tasks
        TestDataHelper helper = new TestDataHelper();
        GeeKeep expectedAB = helper.generateGeeKeep(2);
        List<? extends ReadOnlyTask> expectedList = expectedAB.getTaskList();

        // prepare geekeep state
        helper.addToModel(model, 2);

        assertCommandSuccess("listundone",
                ListUndoneCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);

        // prepare expectations for list done tasks
        expectedAB.markTaskDone(0);
        expectedList = expectedAB.getTaskList().filtered(t -> t.isDone());

        assertCommandSuccess("done 1",
                String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, expectedList.get(0)),
                expectedAB,
                expectedAB.getTaskList());

        assertCommandSuccess("listdone",
                ListDoneCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }

    //@@author
    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }


    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = je.targetIndex;
    }

    @Subscribe
    private void handleLocalModelChangedEvent(GeeKeepChangedEvent abce) {
        latestSavedGeeKeep = new GeeKeep(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Before
    public void setUp() {
        model = new ModelManager();
        logic = new LogicManager(model);
        EventsCenter.getInstance().registerHandler(this);

        latestSavedGeeKeep = new GeeKeep(model.getGeeKeep()); // last saved assumed to be up to date
        helpShown = false;
        targetedJumpIndex = -1; // non yet
    }


    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }
}
