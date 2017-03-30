package seedu.todolist.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX;
import static seedu.todolist.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

import seedu.todolist.commons.core.EventsCenter;
import seedu.todolist.commons.events.model.TodoListChangedEvent;
import seedu.todolist.commons.events.ui.JumpToListRequestEvent;
import seedu.todolist.commons.events.ui.ShowHelpRequestEvent;
import seedu.todolist.logic.commands.AddCommand;
import seedu.todolist.logic.commands.ClearCommand;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.CommandResult;
import seedu.todolist.logic.commands.DeleteCommand;
import seedu.todolist.logic.commands.ExitCommand;
import seedu.todolist.logic.commands.FindCommand;
import seedu.todolist.logic.commands.HelpCommand;
import seedu.todolist.logic.commands.ListCommand;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.Model;
import seedu.todolist.model.ModelManager;
import seedu.todolist.model.ReadOnlyTodoList;
import seedu.todolist.model.TodoList;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.todo.Name;
import seedu.todolist.model.todo.ReadOnlyTodo;
import seedu.todolist.model.todo.Todo;
import seedu.todolist.storage.StorageManager;


public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTodoList latestSavedTodoList;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TodoListChangedEvent abce) {
        latestSavedTodoList = new TodoList(abce.data);
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
        String tempTodoListFile = saveFolder.getRoot().getPath() + "TempTodoList.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempTodoListFile, tempPreferencesFile));
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
     * Also confirms that both the 'address book' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTodoList, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyTodoList expectedTodoList,
                                      List<? extends ReadOnlyTodo> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedTodoList, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'address book' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyTodoList, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        TodoList expectedTodoList = new TodoList(model.getTodoList());
        List<ReadOnlyTodo> expectedShownList = new ArrayList<>(model.getFilteredTodoList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedTodoList, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedTodoList} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedTodoList} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
                                       ReadOnlyTodoList expectedTodoList,
                                       List<? extends ReadOnlyTodo> expectedShownList) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        }

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, model.getFilteredTodoList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedTodoList, model.getTodoList());
        assertEquals(expectedTodoList, latestSavedTodoList);
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
        model.addTodo(helper.generateTodo(1));
        model.addTodo(helper.generateTodo(2));
        model.addTodo(helper.generateTodo(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new TodoList(), Collections.emptyList());
    }

    @Test
    public void execute_add_invalidTodoData() {
        assertCommandFailure("add []\\[;] ",
                Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandFailure("add Valid Name t/invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Todo toBeAdded = helper.adam();
        TodoList expectedAB = new TodoList();
        expectedAB.addTodo(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getTodoList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Todo toBeAdded = helper.adam();

        // setup starting state
        model.addTodo(toBeAdded); // todo already in internal address book

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_DUPLICATE_TODO);

    }


    @Test
    public void execute_list_showsAllTodos() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        TodoList expectedAB = helper.generateTodoListReturnTodoList(2);
        List<? extends ReadOnlyTodo> expectedList = expectedAB.getTodoList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single todo in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single todo in the last shown list
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
     * targeting a single todo in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single todo in the last shown list
     *                    based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TODO_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Todo> todoList = helper.generateTodoList(2);

        // set AB state to 2 todos
        model.resetData(new TodoList());
        for (Todo p : todoList) {
            model.addTodo(p);
        }

        assertCommandFailure(commandWord + " 3", expectedMessage);
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
    public void execute_delete_removesCorrectTodo() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Todo> threeTodos = helper.generateTodoList(3);

        TodoList expectedAB = helper.generateTodoList(threeTodos);
        expectedAB.removeTodo(threeTodos.get(1));
        helper.addToModel(model, threeTodos);

        assertCommandSuccess("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TODO_SUCCESS, threeTodos.get(1)),
                expectedAB,
                expectedAB.getTodoList());
    }


    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Todo pTarget1 = helper.generateTodoWithName("bla bla KEY bla");
        Todo pTarget2 = helper.generateTodoWithName("bla KEY bla bceofeia");
        Todo p1 = helper.generateTodoWithName("KE Y");
        Todo p2 = helper.generateTodoWithName("KEYKEYKEY sduauo");

        List<Todo> fourTodos = helper.generateTodoList(p1, pTarget1, p2, pTarget2);
        TodoList expectedAB = helper.generateTodoList(fourTodos);
        List<Todo> expectedList = helper.generateTodoList(pTarget1, pTarget2);
        helper.addToModel(model, fourTodos);

        assertCommandSuccess("find KEY",
                Command.getMessageForTodoListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Todo p1 = helper.generateTodoWithName("bla bla KEY bla");
        Todo p2 = helper.generateTodoWithName("bla KEY bla bceofeia");
        Todo p3 = helper.generateTodoWithName("key key");
        Todo p4 = helper.generateTodoWithName("KEy sduauo");

        List<Todo> fourTodos = helper.generateTodoList(p3, p1, p4, p2);
        TodoList expectedAB = helper.generateTodoList(fourTodos);
        List<Todo> expectedList = fourTodos;
        helper.addToModel(model, fourTodos);

        assertCommandSuccess("find KEY",
                Command.getMessageForTodoListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Todo pTarget1 = helper.generateTodoWithName("bla bla KEY bla");
        Todo pTarget2 = helper.generateTodoWithName("bla rAnDoM bla bceofeia");
        Todo pTarget3 = helper.generateTodoWithName("key key");
        Todo p1 = helper.generateTodoWithName("sduauo");

        List<Todo> fourTodos = helper.generateTodoList(pTarget1, p1, pTarget2, pTarget3);
        TodoList expectedAB = helper.generateTodoList(fourTodos);
        List<Todo> expectedList = helper.generateTodoList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourTodos);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForTodoListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Todo adam() throws Exception {
            Name name = new Name("Adam Brown");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Todo(name, tags);
        }

        /**
         * Generates a valid todo using the given seed.
         * Running this function with the same parameter values guarantees the returned todo will have the same state.
         * Each unique seed will generate a unique Todo object.
         *
         * @param seed used to generate the todo data field values
         */
        Todo generateTodo(int seed) throws Exception {
            return new Todo(
                    new Name("Todo " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the todo given */
        String generateAddCommand(Todo p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());

            UniqueTagList tags = p.getTags();
            for (Tag t: tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an TodoList with auto-generated todos.
         */

        TodoList generateTodoListReturnTodoList(int numGenerated) throws Exception {
            TodoList todoList = new TodoList();
            addToTodoList(todoList, numGenerated);
            return todoList;
        }

        /**
         * Generates a list of Todos based on the flags.
         */

        List<Todo> generateTodoList(int numGenerated) throws Exception {
            List<Todo> todos = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                todos.add(generateTodo(i));
            }
            return todos;
        }

        List<Todo> generateTodoList(Todo... todos) {
            return Arrays.asList(todos);
        }

        /**
         * Generates an TodoList based on the list of Todos given.
         */
        TodoList generateTodoList(List<Todo> todos) throws Exception {
            TodoList addressBook = new TodoList();
            addToTodoList(addressBook, todos);
            return addressBook;
        }

        /**
         * Adds auto-generated Todo objects to the given TodoList
         * @param addressBook The TodoList to which the Todos will be added
         */
        void addToTodoList(TodoList addressBook, int numGenerated) throws Exception {
            addToTodoList(addressBook, generateTodoList(numGenerated));
        }

        /**
         * Adds the given list of Todos to the given TodoList
         */
        void addToTodoList(TodoList addressBook, List<Todo> todosToAdd) throws Exception {
            for (Todo p: todosToAdd) {
                addressBook.addTodo(p);
            }
        }

        /**
         * Adds auto-generated Todo objects to the given model
         * @param model The model to which the Todos will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTodoList(numGenerated));
        }

        /**
         * Adds the given list of Todos to the given model
         */
        void addToModel(Model model, List<Todo> todosToAdd) throws Exception {
            for (Todo p: todosToAdd) {
                model.addTodo(p);
            }
        }

        /**
         * Generates a Todo object with given name. Other fields will have some dummy values.
         */
        Todo generateTodoWithName(String name) throws Exception {
            return new Todo(
                    new Name(name),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
