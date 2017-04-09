//@@author A0163935X
package guitests;

import static seedu.task.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import seedu.task.TestApp;
import seedu.task.commons.core.Config;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class DeleteCommandTest extends AddressBookGuiTest {

    @Before
    public void reset_config() throws IOException {
        TestApp testApp = new TestApp();
        Config config = testApp.initConfig(Config.DEFAULT_CONFIG_FILE);
        ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
        commandBox.runCommand("clear");
    }

    @Test
    public void delete() {
        //  valid-partition: Boundary value just above the boundary
        //  delete the first in the list
        commandBox.runCommand("clear");
        TestTask[] currentList = {td.fiona , td.ida};
        for (int i = 0; i < currentList.length; i++) commandBox.runCommand(currentList[i].getAddCommand());

        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);
        //  valid-partition: Boundary value just below the boundary
        //  delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);



        //  valid-partition: Boundary value just below the boundary
        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        TestTask[] temp = {td.fiona , td.ida , td.hoon};
        currentList = temp;
        for (int i = 0; i < currentList.length; i++) commandBox.runCommand(currentList[i].getAddCommand());

        targetIndex = currentList.length / 2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid partition: value outside the boundary
        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
    }

    //@@author A0113795Y
    public void deletethis() {
        commandBox.runCommand("clear");
        TestTask taskToDelete = td.recMonth;
        commandBox.runCommand(taskToDelete.getAddCommand());
        commandBox.runCommand("01/03/2017");
        commandBox.runCommand("deletethis 1");

        // Check the first occurrence
        commandBox.runCommand("find 01/01/2017");
        assertResultMessage("1 tasks listed!");

        // Check the deleted occurrence
        commandBox.runCommand("find 01/03/2017");
        assertResultMessage("0 tasks listed!");

        // Check the second occurrence
        commandBox.runCommand("find 01/05/2017");
        assertResultMessage("1 tasks listed!");

        // Check the third occurrence
        commandBox.runCommand("find 01/07/2017");
        assertResultMessage("1 tasks listed!");

        // Check the fourth occurrence
        commandBox.runCommand("find 01/09/2017");
        assertResultMessage("1 tasks listed!");

        // Check the fifth occurrence
        commandBox.runCommand("find 01/11/2017");
        assertResultMessage("1 tasks listed!");

        // Check the sixth occurrence
        commandBox.runCommand("find 01/01/2018");
        assertResultMessage("1 tasks listed!");
    }
    //@@author

    /**
     * Runs the delete command to delete the person at specified index and
     confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first person in
     the list,
     * @param currentList A copy of the current list of persons (before
     deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final
            TestTask[] currentList) {
        TestTask personToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder =
                TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous persons except the deleted person



        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS,
                personToDelete));
    }

}
