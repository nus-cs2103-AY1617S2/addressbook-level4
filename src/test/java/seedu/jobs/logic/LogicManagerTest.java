package seedu.jobs.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jobs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jobs.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.jobs.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

import seedu.jobs.commons.core.EventsCenter;
import seedu.jobs.commons.events.model.AddressBookChangedEvent;
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
import seedu.jobs.model.AddressBook;
import seedu.jobs.model.Model;
import seedu.jobs.model.ModelManager;
import seedu.jobs.model.ReadOnlyAddressBook;
import seedu.jobs.model.tag.Tag;
import seedu.jobs.model.tag.UniqueTagList;
import seedu.jobs.model.task.Address;
import seedu.jobs.model.task.Email;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Phone;
import seedu.jobs.model.task.ReadOnlyPerson;
import seedu.jobs.model.task.Task;
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
    private ReadOnlyAddressBook latestSavedAddressBook;
    private boolean helpShown;
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(AddressBookChangedEvent abce) {
        latestSavedAddressBook = new AddressBook(abce.data);
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
        String tempAddressBookFile = saveFolder.getRoot().getPath() + "TempAddressBook.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempAddressBookFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedAddressBook = new AddressBook(model.getAddressBook()); // last saved assumed to be up to date
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
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyAddressBook, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyAddressBook expectedAddressBook,
                                      List<? extends ReadOnlyPerson> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedAddressBook, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'address book' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyAddressBook, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        List<ReadOnlyPerson> expectedShownList = new ArrayList<>(model.getFilteredPersonList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedAddressBook, expectedShownList);
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
                                       ReadOnlyAddressBook expectedAddressBook,
                                       List<? extends ReadOnlyPerson> expectedShownList) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        }

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, model.getFilteredPersonList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedAddressBook, model.getAddressBook());
        assertEquals(expectedAddressBook, latestSavedAddressBook);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new AddressBook(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new AddressBook(), Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addPerson(helper.generatePerson(1));
        model.addPerson(helper.generatePerson(2));
        model.addPerson(helper.generatePerson(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new AddressBook(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add wrong args wrong args", expectedMessage);
        assertCommandFailure("add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid,address", expectedMessage);
        assertCommandFailure("add Valid Name p/12345 valid@email.butNoPrefix a/valid, address", expectedMessage);
        assertCommandFailure("add Valid Name p/12345 e/valid@email.butNoAddressPrefix valid, address", expectedMessage);
    }

    @Test
    public void execute_add_invalidPersonData() {
        assertCommandFailure("add []\\[;] p/12345 e/valid@e.mail a/valid, address",
                Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandFailure("add Valid Name p/not_numbers e/valid@e.mail a/valid, address",
                Phone.MESSAGE_PHONE_CONSTRAINTS);
        assertCommandFailure("add Valid Name p/12345 e/notAnEmail a/valid, address",
                Email.MESSAGE_EMAIL_CONSTRAINTS);
        assertCommandFailure("add Valid Name p/12345 e/valid@e.mail a/valid, address t/invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();
        AddressBook expectedAB = new AddressBook();
        expectedAB.addPerson(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getPersonList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.adam();

        // setup starting state
        model.addPerson(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_DUPLICATE_PERSON);

    }


    @Test
    public void execute_list_showsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        AddressBook expectedAB = helper.generateAddressBook(2);
        List<? extends ReadOnlyPerson> expectedList = expectedAB.getPersonList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list
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
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list
     *                    based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> personList = helper.generatePersonList(2);

        // set AB state to 2 persons
        model.resetData(new AddressBook());
        for (Task p : personList) {
            model.addPerson(p);
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
        List<Task> threePersons = helper.generatePersonList(3);

        AddressBook expectedAB = helper.generateAddressBook(threePersons);
        helper.addToModel(model, threePersons);

        assertCommandSuccess("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, 2),
                expectedAB,
                expectedAB.getPersonList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredPersonList().get(1), threePersons.get(1));
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
        List<Task> threePersons = helper.generatePersonList(3);

        AddressBook expectedAB = helper.generateAddressBook(threePersons);
        expectedAB.removePerson(threePersons.get(1));
        helper.addToModel(model, threePersons);

        assertCommandSuccess("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, threePersons.get(1)),
                expectedAB,
                expectedAB.getPersonList());
    }


    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Task pTarget2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Task p1 = helper.generatePersonWithName("KE Y");
        Task p2 = helper.generatePersonWithName("KEYKEYKEY sduauo");

        List<Task> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expectedAB = helper.generateAddressBook(fourPersons);
        List<Task> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToModel(model, fourPersons);

        assertCommandSuccess("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = helper.generatePersonWithName("bla bla KEY bla");
        Task p2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Task p3 = helper.generatePersonWithName("key key");
        Task p4 = helper.generatePersonWithName("KEy sduauo");

        List<Task> fourPersons = helper.generatePersonList(p3, p1, p4, p2);
        AddressBook expectedAB = helper.generateAddressBook(fourPersons);
        List<Task> expectedList = fourPersons;
        helper.addToModel(model, fourPersons);

        assertCommandSuccess("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Task pTarget2 = helper.generatePersonWithName("bla rAnDoM bla bceofeia");
        Task pTarget3 = helper.generatePersonWithName("key key");
        Task p1 = helper.generatePersonWithName("sduauo");

        List<Task> fourPersons = helper.generatePersonList(pTarget1, p1, pTarget2, pTarget3);
        AddressBook expectedAB = helper.generateAddressBook(fourPersons);
        List<Task> expectedList = helper.generatePersonList(pTarget1, pTarget2, pTarget3);
        helper.addToModel(model, fourPersons);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForPersonListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task adam() throws Exception {
            Name name = new Name("Adam Brown");
            Phone privatePhone = new Phone("111111");
            Email email = new Email("adam@gmail.com");
            Address privateAddress = new Address("111, alpha street");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Task(name, privatePhone, email, privateAddress, tags);
        }

        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Task object.
         *
         * @param seed used to generate the person data field values
         */
        Task generatePerson(int seed) throws Exception {
            return new Task(
                    new Name("Task " + seed),
                    new Phone("" + Math.abs(seed)),
                    new Email(seed + "@email"),
                    new Address("House of " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the person given */
        String generateAddCommand(Task p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getName().toString());
            cmd.append(" e/").append(p.getEmail());
            cmd.append(" p/").append(p.getPhone());
            cmd.append(" a/").append(p.getAddress());

            UniqueTagList tags = p.getTags();
            for (Tag t: tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated persons.
         */
        AddressBook generateAddressBook(int numGenerated) throws Exception {
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, numGenerated);
            return addressBook;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        AddressBook generateAddressBook(List<Task> tasks) throws Exception {
            AddressBook addressBook = new AddressBook();
            addToAddressBook(addressBook, tasks);
            return addressBook;
        }

        /**
         * Adds auto-generated Task objects to the given AddressBook
         * @param addressBook The AddressBook to which the Persons will be added
         */
        void addToAddressBook(AddressBook addressBook, int numGenerated) throws Exception {
            addToAddressBook(addressBook, generatePersonList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given AddressBook
         */
        void addToAddressBook(AddressBook addressBook, List<Task> personsToAdd) throws Exception {
            for (Task p: personsToAdd) {
                addressBook.addPerson(p);
            }
        }

        /**
         * Adds auto-generated Task objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generatePersonList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<Task> personsToAdd) throws Exception {
            for (Task p: personsToAdd) {
                model.addPerson(p);
            }
        }

        /**
         * Generates a list of Persons based on the flags.
         */
        List<Task> generatePersonList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generatePerson(i));
            }
            return tasks;
        }

        List<Task> generatePersonList(Task... persons) {
            return Arrays.asList(persons);
        }

        /**
         * Generates a Task object with given name. Other fields will have some dummy values.
         */
        Task generatePersonWithName(String name) throws Exception {
            return new Task(
                    new Name(name),
                    new Phone("1"),
                    new Email("1@email"),
                    new Address("House of 1"),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
