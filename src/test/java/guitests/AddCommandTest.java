package guitests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.TestApp;
import seedu.task.commons.core.Config;
import seedu.task.commons.core.Messages;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.model.task.RecurringTaskOccurrence;
import seedu.task.model.task.Task;
import seedu.task.model.task.Timing;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class AddCommandTest extends AddressBookGuiTest {

    @Before
    public void reset_config() throws IOException {
        TestApp testApp = new TestApp();
        Config config = testApp.initConfig(Config.DEFAULT_CONFIG_FILE);
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        commandBox.runCommand("clear");
    }

    @Test
    public void add() throws IllegalValueException {
        // add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // //add another task
        taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        ////
        // //add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());

        assertTrue(taskListPanel.isListMatching(currentList));
        ////
        //// //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);
        //// //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        //@@author A0164212U
        //
        // //invalid timing order
        //start date = end date (start time > end time)
        commandBox.runCommand("add timeOrderTest sd/11:45 ed/10:45");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        //start date > end date (same time)
        commandBox.runCommand("add timeOrderTest sd/10:45 26/03/2017 ed/10:45 25/03/2016");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        //start date > end date (no time)
        commandBox.runCommand("add timeOrderTest sd/03/08/2017 ed/25/03/2016");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        //start date > end date (start time > end time)
        commandBox.runCommand("add timeOrderTest sd/10:45 01/01/2017 ed/09:45 01/01/2016");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        //start date > end date (start time < end time)
        commandBox.runCommand("add timeOrderTest sd/08:45 05/06/2012 ed/09:45 02/05/2012");
        assertResultMessage(Messages.MESSSAGE_INVALID_TIMING_ORDER);
        // //invalid syntax for timing
        commandBox.runCommand("add timeSyntaxTest sd/1:45");
        assertResultMessage(Timing.MESSAGE_TIMING_CONSTRAINTS);
        //should be 01:45
        commandBox.runCommand("add timeSyntaxTest sd/01:45 1/01/2017");
        assertResultMessage(Timing.MESSAGE_TIMING_CONSTRAINTS);
        //should be 01/01/2017 for date
        commandBox.runCommand("add timeSyntaxTest sd/01:45 01/01/2017 ed/3:15 01/01/2017");
        assertResultMessage(Timing.MESSAGE_TIMING_CONSTRAINTS);
        //should be 03:15
        commandBox.runCommand("add timeSyntaxTest sd/01:45 01/01/2017 ed/03:15 01/2/2017");
        assertResultMessage(Timing.MESSAGE_TIMING_CONSTRAINTS);
        //should be 01/02/2017
        //@@author
    }

    @Test
    public void addRecurringTask() throws IllegalValueException {
        //test monthly recurring task
        TestTask[] currentList = td.getEmptyTasks();
        TestTask taskToAdd = td.recMonth;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(currentList));
        ArrayList<RecurringTaskOccurrence> hardCodedMonth = new ArrayList<RecurringTaskOccurrence>();
        hardCodedMonth.add(new RecurringTaskOccurrence(new Timing("01/01/2017"), new Timing("05/01/2017")));
        hardCodedMonth.add(new RecurringTaskOccurrence(new Timing("01/03/2017"), new Timing("05/03/2017")));
        hardCodedMonth.add(new RecurringTaskOccurrence(new Timing("01/05/2017"), new Timing("05/05/2017")));
        hardCodedMonth.add(new RecurringTaskOccurrence(new Timing("01/07/2017"), new Timing("05/07/2017")));
        hardCodedMonth.add(new RecurringTaskOccurrence(new Timing("01/09/2017"), new Timing("05/09/2017")));
        hardCodedMonth.add(new RecurringTaskOccurrence(new Timing("01/11/2017"), new Timing("05/11/2017")));
        hardCodedMonth.add(new RecurringTaskOccurrence(new Timing("01/01/2018"), new Timing("05/01/2018")));
        Task taskMonth = new Task(taskToAdd.getDescription(), taskToAdd.getPriority(),
                taskToAdd.getStartTiming(), taskToAdd.getEndTiming(), taskToAdd.getTags(),
                taskToAdd.isRecurring(), taskToAdd.getFrequency());
        assertOccurrenceSame(hardCodedMonth, taskMonth.getOccurrences());
        //@@author A0164212U
        //test daily recurring tasks
        taskToAdd = td.recDay;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(currentList));
        ArrayList<RecurringTaskOccurrence> hardCodedDay = new ArrayList<RecurringTaskOccurrence>();
        hardCodedDay.add(new RecurringTaskOccurrence(new Timing("01/05/2017"), new Timing("01/05/2017")));
        hardCodedDay.add(new RecurringTaskOccurrence(new Timing("11/05/2017"), new Timing("11/05/2017")));
        hardCodedDay.add(new RecurringTaskOccurrence(new Timing("21/05/2017"), new Timing("21/05/2017")));
        hardCodedDay.add(new RecurringTaskOccurrence(new Timing("31/05/2017"), new Timing("31/05/2017")));
        hardCodedDay.add(new RecurringTaskOccurrence(new Timing("10/06/2017"), new Timing("10/06/2017")));
        hardCodedDay.add(new RecurringTaskOccurrence(new Timing("20/06/2017"), new Timing("20/06/2017")));
        hardCodedDay.add(new RecurringTaskOccurrence(new Timing("30/06/2017"), new Timing("30/06/2017")));
        Task taskDay = new Task(taskToAdd.getDescription(), taskToAdd.getPriority(),
                taskToAdd.getStartTiming(), taskToAdd.getEndTiming(), taskToAdd.getTags(),
                taskToAdd.isRecurring(), taskToAdd.getFrequency());
        assertOccurrenceSame(hardCodedDay, taskDay.getOccurrences());

        //test yearly recurring tasks
        taskToAdd = td.recYear;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(currentList));
        ArrayList<RecurringTaskOccurrence> hardCodedYear = new ArrayList<RecurringTaskOccurrence>();
        hardCodedYear.add(new RecurringTaskOccurrence(new Timing("25/07/2017"), new Timing("25/07/2017")));
        hardCodedYear.add(new RecurringTaskOccurrence(new Timing("25/07/2018"), new Timing("25/07/2018")));
        hardCodedYear.add(new RecurringTaskOccurrence(new Timing("25/07/2019"), new Timing("25/07/2019")));
        hardCodedYear.add(new RecurringTaskOccurrence(new Timing("25/07/2020"), new Timing("25/07/2020")));
        Task taskYear = new Task(taskToAdd.getDescription(), taskToAdd.getPriority(),
                taskToAdd.getStartTiming(), taskToAdd.getEndTiming(), taskToAdd.getTags(),
                taskToAdd.isRecurring(), taskToAdd.getFrequency());
        assertOccurrenceSame(hardCodedYear, taskYear.getOccurrences());
        //@@author
    }


    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) throws IllegalValueException {
        commandBox.runCommand("clear");
        for (int i = 0; i < currentList.length; i++) {
            commandBox.runCommand(currentList[i].getAddCommand());
        }
        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getDescription().toString());
        assertMatching(taskToAdd, addedCard);


        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    private void assertOccurrenceSame(ArrayList<RecurringTaskOccurrence> hardCoded
            , ArrayList<RecurringTaskOccurrence> taskOccurrences) {
        assertTrue(hardCoded.size() == taskOccurrences.size());
        for (int i = 0; i < hardCoded.size(); i++) {
            assertTrue(hardCoded.get(i).equals(taskOccurrences.get(i)));
        }
    }
}
