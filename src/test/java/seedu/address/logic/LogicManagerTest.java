package seedu.address.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

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

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.WhatsLeftChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.Activity;
import seedu.address.model.person.Description;
import seedu.address.model.person.Location;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.storage.StorageManager;


public class LogicManagerTest {

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
    private int targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(WhatsLeftChangedEvent abce) {
        latestSavedWhatsLeft = new WhatsLeft(abce.data);
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
        String tempWhatsLeftFile = saveFolder.getRoot().getPath() + "TempWhatsLeft.xml";
        String tempPreferencesFile = saveFolder.getRoot().getPath() + "TempPreferences.json";
        logic = new LogicManager(model, new StorageManager(tempWhatsLeftFile, tempPreferencesFile));
        EventsCenter.getInstance().registerHandler(this);

        latestSavedWhatsLeft = new WhatsLeft(model.getWhatsLeft()); // last saved assumed to be up to date
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
     * Also confirms that both the 'WhatsLeft' and the 'last shown list' are as specified.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyWhatsLeft, List)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      ReadOnlyWhatsLeft expectedWhatsLeft,
                                      List<? extends ReadOnlyActivity> expectedShownList) {
        assertCommandBehavior(false, inputCommand, expectedMessage, expectedWhatsLeft, expectedShownList);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * Both the 'WhatsLeft' and the 'last shown list' are verified to be unchanged.
     * @see #assertCommandBehavior(boolean, String, String, ReadOnlyWhatsLeft, List)
     */
    private void assertCommandFailure(String inputCommand, String expectedMessage) {
        WhatsLeft expectedWhatsLeft = new WhatsLeft(model.getWhatsLeft());
        List<ReadOnlyActivity> expectedShownList = new ArrayList<>(model.getFilteredActivityList());
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
                                       List<? extends ReadOnlyActivity> expectedShownList) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertFalse("CommandException expected but was not thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException e) {
            assertTrue("CommandException not expected but was thrown.", isCommandExceptionExpected);
            assertEquals(expectedMessage, e.getMessage());
        }

        //Confirm the ui display elements should contain the right data
        assertEquals(expectedShownList, model.getFilteredActivityList());

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
        model.addActivity(helper.generateActivity(1));
        model.addActivity(helper.generateActivity(2));
        model.addActivity(helper.generateActivity(3));

        assertCommandSuccess("clear", ClearCommand.MESSAGE_SUCCESS, new WhatsLeft(), Collections.emptyList());
    }


    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add wrong args wrong args", expectedMessage);
        assertCommandFailure("add Valid Name 12345 .butNoPhonePrefix a/valid,address", expectedMessage);
        assertCommandFailure("add Valid Name p/12345 a/not valid prefix", expectedMessage);
    }

    @Test
    public void execute_add_invalidActivityData() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandFailure("add  p/high e/valid@email.com l/address",
                expectedMessage);
        assertCommandFailure("add Valid Name p/123 l/valid, address",
                Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        assertCommandFailure("add Valid Name p/high l/valid, address t/invalid_-[.tag",
                Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Activity toBeAdded = helper.adam();
        WhatsLeft expectedAB = new WhatsLeft();
        expectedAB.addActivity(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expectedAB,
                expectedAB.getActivityList());

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Activity toBeAdded = helper.adam();

        // setup starting state
        model.addActivity(toBeAdded); // activity already in internal address book

        // execute command and verify result
        assertCommandFailure(helper.generateAddCommand(toBeAdded),  AddCommand.MESSAGE_DUPLICATE_ACTIVITY);

    }


    @Test
    public void execute_list_showsAllActivities() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        WhatsLeft expectedAB = helper.generateWhatsLeft(2);
        List<? extends ReadOnlyActivity> expectedList = expectedAB.getActivityList();

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
        String expectedMessage = MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Activity> activityList = helper.generateActivityList(2);

        // set AB state to 2 activities
        model.resetData(new WhatsLeft());
        for (Activity p : activityList) {
            model.addActivity(p);
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
    public void execute_select_jumpsToCorrectActivity() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Activity> threeActivities = helper.generateActivityList(3);

        WhatsLeft expectedAB = helper.generateWhatsLeft(threeActivities);
        helper.addToModel(model, threeActivities);

        assertCommandSuccess("select 2",
                String.format(SelectCommand.MESSAGE_SELECT_EVENT_SUCCESS, 2),
                expectedAB,
                expectedAB.getActivityList());
        assertEquals(1, targetedJumpIndex);
        assertEquals(model.getFilteredActivityList().get(1), threeActivities.get(1));
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
    public void execute_delete_removesCorrectActivity() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Activity> threeActivities = helper.generateActivityList(3);

        WhatsLeft expectedAB = helper.generateWhatsLeft(threeActivities);
        expectedAB.removeActivity(threeActivities.get(1));
        helper.addToModel(model, threeActivities);

        assertCommandSuccess("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS, threeActivities.get(1)),
                expectedAB,
                expectedAB.getActivityList());
    }


    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure("find ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Activity aTarget1 = helper.generateActivityWithName("bla bla KEY bla");
        Activity aTarget2 = helper.generateActivityWithName("bla KEY bla bceofeia");
        Activity a1 = helper.generateActivityWithName("KE Y");
        Activity a2 = helper.generateActivityWithName("KEYKEYKEY sduauo");

        List<Activity> fourActivities = helper.generateActivityList(a1, aTarget1, a2, aTarget2);
        WhatsLeft expectedAB = helper.generateWhatsLeft(fourActivities);
        List<Activity> expectedList = helper.generateActivityList(aTarget1, aTarget2);
        helper.addToModel(model, fourActivities);

        assertCommandSuccess("find KEY",
                Command.getMessageForActivityListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Activity a1 = helper.generateActivityWithName("bla bla KEY bla");
        Activity a2 = helper.generateActivityWithName("bla KEY bla bceofeia");
        Activity a3 = helper.generateActivityWithName("key key");
        Activity a4 = helper.generateActivityWithName("KEy sduauo");

        List<Activity> fourActivities = helper.generateActivityList(a3, a1, a4, a2);
        WhatsLeft expectedAB = helper.generateWhatsLeft(fourActivities);
        List<Activity> expectedList = fourActivities;
        helper.addToModel(model, fourActivities);

        assertCommandSuccess("find KEY",
                Command.getMessageForActivityListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Activity aTarget1 = helper.generateActivityWithName("bla bla KEY bla");
        Activity aTarget2 = helper.generateActivityWithName("bla rAnDoM bla bceofeia");
        Activity aTarget3 = helper.generateActivityWithName("key key");
        Activity a1 = helper.generateActivityWithName("sduauo");

        List<Activity> fourActivities = helper.generateActivityList(aTarget1, a1, aTarget2, aTarget3);
        WhatsLeft expectedAB = helper.generateWhatsLeft(fourActivities);
        List<Activity> expectedList = helper.generateActivityList(aTarget1, aTarget2, aTarget3);
        helper.addToModel(model, fourActivities);

        assertCommandSuccess("find key rAnDoM",
                Command.getMessageForActivityListShownSummary(expectedList.size()),
                expectedAB,
                expectedList);
    }


    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Activity adam() throws Exception {
            Description name = new Description("Assignment2");
            Priority privatePhone = new Priority("high");
            Location privateLocation = new Location("111, alpha street");
            Tag tag1 = new Tag("tag1");
            Tag tag2 = new Tag("longertag2");
            UniqueTagList tags = new UniqueTagList(tag1, tag2);
            return new Activity(name, privatePhone, privateLocation, tags);
        }

        /**
         * Generates a valid activity using the given seed.
         * Running this function with the same parameter values
         * guarantees the returned activity will have the same state.
         * Each unique seed will generate a unique Activity object.
         *
         * @param seed used to generate the activity data field values
         */
        Activity generateActivity(int seed) throws Exception {
            return new Activity(
                    new Description("Activity " + seed),
                    new Priority("high"),
                    new Location("House of " + seed),
                    new UniqueTagList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1)))
            );
        }

        /** Generates the correct add command based on the activity given */
        String generateAddCommand(Activity p) {
            StringBuffer cmd = new StringBuffer();

            cmd.append("add ");

            cmd.append(p.getDescription().toString());
            cmd.append(" p/").append(p.getPriority());
            cmd.append(" l/").append(p.getLocation());
            UniqueTagList tags = p.getTags();
            for (Tag t: tags) {
                cmd.append(" t/").append(t.tagName);
            }

            return cmd.toString();
        }

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
        WhatsLeft generateWhatsLeft(List<Activity> activities) throws Exception {
            WhatsLeft whatsLeft = new WhatsLeft();
            addToWhatsLeft(whatsLeft, activities);
            return whatsLeft;
        }

        /**
         * Adds auto-generated Activity objects to the given WhatsLeft
         * @param whatsLeft The WhatsLeft to which the Activities will be added
         */
        void addToWhatsLeft(WhatsLeft whatsLeft, int numGenerated) throws Exception {
            addToWhatsLeft(whatsLeft, generateActivityList(numGenerated));
        }

        /**
         * Adds the given list of Activities to the given WhatsLeft
         */
        void addToWhatsLeft(WhatsLeft whatsLeft, List<Activity> activitiesToAdd) throws Exception {
            for (Activity p: activitiesToAdd) {
                whatsLeft.addActivity(p);
            }
        }

        /**
         * Adds auto-generated Activity objects to the given model
         * @param model The model to which the Activities will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateActivityList(numGenerated));
        }

        /**
         * Adds the given list of Activities to the given model
         */
        void addToModel(Model model, List<Activity> activitiesToAdd) throws Exception {
            for (Activity p: activitiesToAdd) {
                model.addActivity(p);
            }
        }

        /**
         * Generates a list of Activities based on the flags.
         */
        List<Activity> generateActivityList(int numGenerated) throws Exception {
            List<Activity> activities = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                activities.add(generateActivity(i));
            }
            return activities;
        }

        List<Activity> generateActivityList(Activity... activities) {
            return Arrays.asList(activities);
        }

        /**
         * Generates an Activity object with given name. Other fields will have some dummy values.
         */
        Activity generateActivityWithName(String description) throws Exception {
            return new Activity(
                    new Description(description),
                    new Priority("low"),
                    new Location("House of 1"),
                    new UniqueTagList(new Tag("tag"))
            );
        }
    }
}
