package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class ListFloatingDateCommandTest extends TaskManagerGuiTest {

    @Test
    public void listbyfloatingTask_nonEmptyList() {
        //add one task that does not contain start and end date, floating task 
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.milk;
        commandBox.runCommand(taskToAdd.getAddCommand());
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertListByFloat(currentList);
        
    }


    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertListByFloat(); // no results
       
    }

    private void assertListByFloat(TestTask... currentList) {

        commandBox.runCommand("list float");
        TestTask[] expectedList = TestUtil.filterFloatTasks(currentList);
        assertListSize(expectedList.length);
        assertResultMessage(expectedList.length + " floating tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

  
}
