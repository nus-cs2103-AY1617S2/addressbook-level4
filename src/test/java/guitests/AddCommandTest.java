package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.ActivityCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.testutil.TestActivity;
import seedu.address.testutil.TestUtil;

public class AddCommandTest extends WhatsLeftGuiTest {

    @Test
    public void add() {
        //add one activity
        TestActivity[] currentList = td.getTypicalActivities();
        TestActivity activityToAdd = td.hoon;
        assertAddSuccess(activityToAdd, currentList);
        currentList = TestUtil.addActivitiesToList(currentList, activityToAdd);

        //add another activity
        activityToAdd = td.ida;
        assertAddSuccess(activityToAdd, currentList);
        currentList = TestUtil.addActivitiesToList(currentList, activityToAdd);

        //add duplicate activity
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_ACTIVITY);
        //assertTrue(activityListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestActivity activityToAdd, TestActivity... currentList) {
        commandBox.runCommand(activityToAdd.getAddCommand());

        //confirm the new card contains the right data
        //ActivityCardHandle addedCard = activityListPanel.navigateToActivity(activityToAdd.getDescription().description);
        //assertMatching(activityToAdd, addedCard);

        //confirm the list now contains all previous activities plus the new activity
        TestActivity[] expectedList = TestUtil.addActivitiesToList(currentList, activityToAdd);
        //assertTrue(activityListPanel.isListMatching(expectedList));
    }

}
