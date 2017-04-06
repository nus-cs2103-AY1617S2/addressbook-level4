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
    public void addDeadlineTask_success() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("add deadline by: 10 oct 1993");
        TestTask deadlineTask = new TaskBuilder().withName("deadline")
                .withDate("").withEndDate("Sun Oct 10 1993 23:59:59")
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
