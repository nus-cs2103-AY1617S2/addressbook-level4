package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class CheckListOrderTest extends TaskManagerGuiTest {

    @Test
    public void check_nonemptyList() {

        //Check after deletion
        TestTask[] currentList = td.getTypicalTasks();
        assertCheckListOrder(currentList);
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 1");
        assertCheckListOrder(currentList);
    }


    private void assertCheckListOrder(TestTask... expectedList) {
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
