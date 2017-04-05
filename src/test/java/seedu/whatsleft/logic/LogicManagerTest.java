package seedu.whatsleft.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatsleft.commons.core.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.whatsleft.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.time.format.DateTimeFormatter;
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

import seedu.whatsleft.commons.core.EventsCenter;
import seedu.whatsleft.commons.events.model.WhatsLeftChangedEvent;
import seedu.whatsleft.commons.events.ui.JumpToEventListRequestEvent;
import seedu.whatsleft.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.whatsleft.commons.events.ui.ShowHelpRequestEvent;

import seedu.whatsleft.logic.commands.AddCommand;
import seedu.whatsleft.logic.commands.ClearCommand;
import seedu.whatsleft.logic.commands.CommandResult;
import seedu.whatsleft.logic.commands.DeleteCommand;
import seedu.whatsleft.logic.commands.ExitCommand;
import seedu.whatsleft.logic.commands.FindCommand;
import seedu.whatsleft.logic.commands.HelpCommand;
import seedu.whatsleft.logic.commands.ListCommand;
import seedu.whatsleft.logic.commands.SelectCommand;
import seedu.whatsleft.logic.commands.exceptions.CommandException;
import seedu.whatsleft.model.Model;
import seedu.whatsleft.model.ModelManager;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;
import seedu.whatsleft.model.WhatsLeft;
import seedu.whatsleft.model.activity.Description;
import seedu.whatsleft.model.activity.EndDate;
import seedu.whatsleft.model.activity.EndTime;
import seedu.whatsleft.model.activity.Event;
import seedu.whatsleft.model.activity.Location;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.StartDate;
import seedu.whatsleft.model.activity.StartTime;
import seedu.whatsleft.model.tag.Tag;
import seedu.whatsleft.model.tag.UniqueTagList;
import seedu.whatsleft.storage.StorageManager;


public class LogicManagerTest {
    static int fordate = 1;
    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyWhatsLeft latestSavedWhatsLeft;
    private boolean helpShown;
    private int targetedEventJumpIndex;
    private int targetedTaskJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(WhatsLeftChangedEvent abce) {
        latestSavedWhatsLeft = new WhatsLeft(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToEventListRequestEvent(JumpToEventListRequestEvent je) {
        targetedEventJumpIndex = je.targetIndex;
    }

    @Subscribe
    private void handleJumpToTaskListRequestEvent(JumpToTaskListRequestEvent jt) {
        targetedTaskJumpIndex = jt.targetIndex;
    }

    @Before
    public void setUp() {
        model = new ModelManager();
        String tempWhatsLeftFile = saveFolder.getRoot().getPath() + "TempWhatsLeft.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempWhatsLeftFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedWhatsLeft = new WhatsLeft(model.getWhatsLeft()); // last saved assumed to be up to date
        helpShown = false;
        targetedEventJumpIndex = -1; // non yet
        targetedTaskJumpIndex = -1;
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
     * Also confirms that both the 'WhatsLeft' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyWhatsLeft, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyWhatsLeft expectedWhatsLeft,
                                      List<? extends ReadOnlyEvent> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedWhatsLeft, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'WhatsLeft' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyWhatsLeft, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        WhatsLeft expectedWhatsLeft = new WhatsLeft(model.getWhatsLeft());
        List<ReadOnlyEvent> expectedShownList = new ArrayList<>(model.getFilteredEventList());
        assertCommandBehavior(true, inputCommand, expectedMessage, expectedWhatsLeft, expectedShownList);
    }

    /**
     * Executes the command, confirms that the result message is correct
     * and that a CommandException is thrown if expected
     * and also confirms that the following three parts of the LogicManager object's state are as expected:<br>
     *      - the internal WhatsLeft data are same as those in the {@code expectedWhatsLeft} <br>
     *      - the backing list shown by UI matches the {@code shownList} <br>
     *      - {@code expectedWhatsLeft} was saved to the storage file. <br>
     */
    private void assertCommandBehavior(boolean isCommandExceptionExpected, String inputCommand, String expectedMessage,
                                       ReadOnlyWhatsLeft expectedWhatsLeft,
                                       List<? extends ReadOnlyEvent> expectedShownList) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        }

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, model.getFilteredEventList());

        //Confirm the state of data (saved and in-memory) is as expected
        assertEquals(expectedWhatsLeft, model.getWhatsLeft());
        assertEquals(expectedWhatsLeft, latestSavedWhatsLeft);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertCommandFailure(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess("help", HelpCommand.SHOWING_HELP_MESSAGE, new WhatsLeft(), Collections.emptyList());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess("exit", ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                new WhatsLeft(), Collections.emptyList());
    }

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addEvent(helper.generateEvent(1));
        model.addEvent(helper.generateEvent(2));
        model.addEvent(helper.generateEvent(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new WhatsLeft(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add wrong args wrong args", expectedMessage);
        assertCommandFailure("add Valid Name 12345 .butNoPhonePrefix a/valid,address", expectedMessage);
        assertCommandFailure("add Valid Name s/12345 a/not valid prefix", expectedMessage);
    }

    @Test
    public void execute_add_invalidActivityData() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add  p/high e/valid@email.com l/address",
                expectedMessage);
        assertCommandFailure("add Valid Name sd/230516 l/valid, address ta/invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.adam();
        WhatsLeft expectedAB = new WhatsLeft();
        expectedAB.addEvent(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getEventList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Event toBeAdded = helper.adam();

        // setup starting state
        model.addEvent(toBeAdded); // activity already in internal address book

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_DUPLICATE_ACTIVITY);

    }


    @Test
    public void execute_list_showsAllActivities() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        WhatsLeft expectedAB = helper.generateWhatsLeft(2);
        List<? extends ReadOnlyEvent> expectedList = expectedAB.getEventList();

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandSuccess("list",
                ListCommand.MESSAGE_SUCCESS,
                expectedAB,
                expectedList);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single activity in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single activity in the last shown list
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
     * targeting a single activity in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single activity in the last shown list
     *                    based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Event> eventList = helper.generateEventList(2);

        // set AB state to 2 activities
        model.resetData(new WhatsLeft());
        for (Event p : eventList) {
            model.addEvent(p);
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
        assertIndexNotFoundBehaviorForCommand("select ev");
    }

    @Test
    public void execute_select_jumpsToCorrectEvent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Event> threeEvents = helper.generateEventList(3);

        WhatsLeft expectedAB = helper.generateWhatsLeft(threeEvents);
        helper.addToModel(model, threeEvents);

        assertCommandSuccess("select ev 2",
                String.format(SelectCommand.MESSAGE_SELECT_EVENT_SUCCESS, expectedAB.getEventList().get(1)),
                expectedAB,
                expectedAB.getEventList());
        assertEquals(1, targetedEventJumpIndex);
        assertEquals(model.getFilteredEventList().get(1), threeEvents.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand("delete", expectedMessage);
    }

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand("delete ev");
    }

    @Test
    public void execute_delete_removesCorrectActivity() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Event> threeEvents = helper.generateEventList(3);

        WhatsLeft expectedAB = helper.generateWhatsLeft(threeEvents);
        expectedAB.removeEvent(threeEvents.get(1));
        helper.addToModel(model, threeEvents);

        assertCommandSuccess("delete ev 2",
                String.format(DeleteCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS, threeEvents.get(1)),
                expectedAB,
                expectedAB.getEventList());
    }


    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }
    /*
    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Event aTarget1 = helper.generateEventWithName("bla bla KEY bla");
        Event aTarget2 = helper.generateEventWithName("bla KEY bla bceofeia");
        Event a1 = helper.generateEventWithName("KE Y");
        Event a2 = helper.generateEventWithName("KEYKEYKEY sduauo");

        fordate = 1;
        List<Event> fourActivities = helper.generateEventList(a1, aTarget1, a2, aTarget2);
        WhatsLeft expectedAB = helper.generateWhatsLeft(fourActivities);
        fordate = 1;
        List<Event> expectedList = helper.generateEventList(aTarget1, aTarget2);
        helper.addToModel(model, fourActivities);

        assertCommandSuccess("find KEY",
                Command.getMessageForActivityListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    */
    /*
    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Event a1 = helper.generateEventWithName("bla bla KEY bla");
        Event a2 = helper.generateEventWithName("bla KEY bla bceofeia");
        Event a3 = helper.generateEventWithName("key key");
        Event a4 = helper.generateEventWithName("KEy sduauo");
        System.out.println(a1.getAsText());
        System.out.println(a2.getAsText());
        System.out.println(a3.getAsText());
        System.out.println(a4.getAsText());

        List<Event> fourActivities = helper.generateEventList(a3, a1, a4, a2);
        WhatsLeft expectedAB = helper.generateWhatsLeft(fourActivities);
        List<Event> expectedList = fourActivities;
        helper.addToModel(model, fourActivities);
        for (ReadOnlyEvent a : model.getFilteredEventList()) {
            System.out.println(a.getAsText());
        }
        assertCommandSuccess("find KEY",
                Command.getMessageForActivityListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    */
    /*
    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Event aTarget1 = helper.generateEventWithName("bla bla KEY bla");
        Event aTarget2 = helper.generateEventWithName("bla rAnDoM bla bceofeia");
        Event aTarget3 = helper.generateEventWithName("key key");
        Event a1 = helper.generateEventWithName("sduauo");

        List<Event> fourEvents = helper.generateEventList(aTarget1, a1, aTarget2, aTarget3);
        WhatsLeft expectedAB = helper.generateWhatsLeft(fourEvents);
        List<Event> expectedList = helper.generateEventList(aTarget1, aTarget2, aTarget3);
        helper.addToModel(model, fourEvents);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForActivityListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }
    */

    /**
     * A utility class to generate test data for events.
     */
    class TestDataHelper {

        Event adam() throws Exception {
            Description name = new Description("Assignment2");
            StartTime starttime = new StartTime("0900");
            StartDate startdate = new StartDate("200517");
            EndTime endtime = new EndTime("1000");
            EndDate enddate = new EndDate("200517");
            Location privateLocation = new Location("111, alpha street");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Event(name, starttime, startdate, endtime, enddate, privateLocation, tags);
        }

        /**
         * Generates a valid event using the given seed.
         * Running this function with the same parameter values
         * guarantees the returned event will have the same state.
         * Each unique seed will generate a unique Event object.
         *
         * @param seed used to generate the event data field values
         */
        Event generateEvent(int seed) throws Exception {
            return new Event(
                    new Description("Event " + seed),
                    new StartTime("0900"),
                    new StartDate(Integer.toString((Integer.parseInt("200317") + seed))),
                    new EndTime("1100"),
                    new EndDate(Integer.toString((Integer.parseInt("200317") + seed))),
                    new Location("House of " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }
        //@@author A0110491U
        /** Generates the correct add command based on the event given */
        String generateAddCommand(Event p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getDescription().toString());
            cmd.append(" sd/").append(p.getStartDate().getValue().format(DateTimeFormatter.ofPattern("ddMMyy")));
            cmd.append(" st/").append(p.getStartTime().getValue().format(DateTimeFormatter.ofPattern("HHmm")));
            cmd.append(" ed/").append(p.getEndDate().getValue().format(DateTimeFormatter.ofPattern("ddMMyy")));
            cmd.append(" et/").append(p.getEndTime().getValue().format(DateTimeFormatter.ofPattern("HHmm")));
            cmd.append(" l/").append(p.getLocation().value);
            UniqueTagList tags = p.getTags();
            for (Tag t: tags) {
                cmd.append(" ta/").append(t.tagName);
            }

            return cmd.toString();
        }
        //@@author
        /**
         * Generates an WhatsLeft with auto-generated activities.
         */
        WhatsLeft generateWhatsLeft(int numGenerated) throws Exception {
            WhatsLeft whatsLeft = new WhatsLeft();
            addToWhatsLeft(whatsLeft, numGenerated);
            return whatsLeft;
        }

        /**
         * Generates an WhatsLeft based on the list of Activities given.
         */
        WhatsLeft generateWhatsLeft(List<Event> events) throws Exception {
            WhatsLeft whatsLeft = new WhatsLeft();
            addToWhatsLeft(whatsLeft, events);
            return whatsLeft;
        }

        /**
         * Adds auto-generated Event objects to the given WhatsLeft
         * @param whatsLeft The WhatsLeft to which the Activities will be added
         */
        void addToWhatsLeft(WhatsLeft whatsLeft, int numGenerated) throws Exception {
            addToWhatsLeft(whatsLeft, generateEventList(numGenerated));
        }

        /**
         * Adds the given list of Activities to the given WhatsLeft
         */
        void addToWhatsLeft(WhatsLeft whatsLeft, List<Event> eventsToAdd) throws Exception {
            for (Event p: eventsToAdd) {
                whatsLeft.addEvent(p);
            }
        }

        /**
         * Adds auto-generated Activity objects to the given model
         * @param model The model to which the Activities will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateEventList(numGenerated));
        }

        /**
         * Adds the given list of Activities to the given model
         */
        void addToModel(Model model, List<Event> eventsToAdd) throws Exception {
            for (Event p: eventsToAdd) {
                model.addEvent(p);
            }
        }

        /**
         * Generates a list of Events based on the flags.
         */
        List<Event> generateEventList(int numGenerated) throws Exception {
            List<Event> events = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                events.add(generateEvent(i));
            }
            return events;
        }

        List<Event> generateEventList(Event... activities) {
            return Arrays.asList(activities);
        }

        /**
         * Generates an Event object with given name. Other fields will have some dummy values.
         */
        Event generateEventWithName(String description) throws Exception {
            fordate++;
            return new Event(
                    new Description(description),
                    new StartTime("0800"),
                    new StartDate(Integer.toString(Integer.parseInt("100301") + fordate)),
                    new EndTime("1200"),
                    new EndDate(Integer.toString(Integer.parseInt("100301") + fordate)),
                    new Location("House of 1"),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
