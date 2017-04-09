package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.taskit.commons.core.Messages;
import seedu.taskit.logic.commands.AddCommand;
import seedu.taskit.testutil.TestTask;
import seedu.taskit.testutil.TestUtil;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add a floating task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.meeting;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another floating task
        taskToAdd = td.assignment;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add an event task
        taskToAdd = td.date;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add a deadline task
        taskToAdd = td.deadline;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        Arrays.sort(currentList);
        commandBox.runCommand(td.meeting.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.interview);

        //invalid command
        commandBox.runCommand("adds meeting");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

    }

    @Test
    //@@author A0141872E
    public void add_keyWords_Success() {
        assertKeywordsSuccess("add priority low tag leisure \"today movie night\"", td.today);
        assertKeywordsSuccess("add priority low \"to\" tag testing", td.to);
        assertKeywordsSuccess("add priority low tag testing \"from\" to 3pm", td.from);

    }

    private void assertKeywordsSuccess(String detailToAdd, TestTask taskToAdd) {
        commandBox.runCommand("clear");
        commandBox.runCommand(detailToAdd);
        assertTaskExistedSuccess(taskToAdd);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        assertTaskExistedSuccess(taskToAdd, currentList);
    }

    private void assertTaskExistedSuccess(TestTask taskToAdd, TestTask... currentList) {
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTitle().title);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        Arrays.sort(expectedList);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }//@@author

}
