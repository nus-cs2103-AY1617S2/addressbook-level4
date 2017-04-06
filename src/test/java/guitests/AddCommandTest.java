package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import typetask.commons.core.Messages;
import typetask.commons.exceptions.IllegalValueException;
import typetask.testutil.TaskBuilder;
import typetask.testutil.TestTask;
import typetask.testutil.TestUtil;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);


        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void addTaskWithEndDateBeforeStartDate_fail() {
        commandBox.runCommand("add failEvent from: today to: yesterday");
        assertResultMessage(Messages.MESSAGE_INVALID_START_AND_END_DATE);
    }
    @Test
    public void addTaskWithInvalidDate_fail() {
        commandBox.runCommand("add invalidDate by: lol");
        assertResultMessage(Messages.MESSAGE_INVALID_DATE_FORMAT_FOR_DATE);
    }
    @Test
    public void addDeadlineTaskWithDateNoTime_success() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("add deadline by: 10 oct 1993 p/Low");
        TestTask deadlineTask = new TaskBuilder().withName("deadline")
                .withDate("").withEndDate("Sun Oct 10 1993 23:59:59")
                .withCompleted(false).withPriority("Low").build();
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, deadlineTask);
        assertTrue(personListPanel.isListMatching(expectedList));
    }
    @Test
    public void addDeadlineTaskwithDateWithTime_success() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("add deadline @ 10 oct 1993 4pm");
        TestTask deadlineTask = new TaskBuilder().withName("deadline")
                .withDate("").withEndDate("Sun Oct 10 1993 16:00:00")
                .withCompleted(false).withPriority("Low").build();
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, deadlineTask);
        assertTrue(personListPanel.isListMatching(expectedList));
    }
    @Test
    public void addEventTask_success() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("add event from: 10 oct 1993 1pm to: 10 oct 1993 4pm p/Low");
        TestTask deadlineTask = new TaskBuilder().withName("event")
                .withDate("Sun Oct 10 1993 13:00:00").withEndDate("Sun Oct 10 1993 16:00:00")
                .withCompleted(false).withPriority("Low").build();
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, deadlineTask);
        assertTrue(personListPanel.isListMatching(expectedList));
    }
    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        PersonCardHandle addedCard = personListPanel.navigateToPerson(personToAdd.getName().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
