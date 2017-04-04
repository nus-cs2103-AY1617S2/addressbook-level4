package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.logic.commands.ListCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

public class ListByDoneUndoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void listbydoneundone_nonEmptyList() {
        TestTask[] currentList = td.getTypicalTasks();
        // done the first task
        commandBox.runCommand("done 1");
        currentList[0].setIsDone(true);
        assertListByDone(currentList);
        assertListByUndone(currentList);
    }


    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertListByDone(); // no results
        assertListByUndone();

    }

    private void assertListByDone(TestTask... currentList) {

        commandBox.runCommand(ListCommand.COMMAND_WORD_1 + " done");
        TestTask[] expectedList = TestUtil.filterDoneTasks(currentList);
        assertListSize(expectedList.length);
        assertResultMessage(expectedList.length + " done tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    private void assertListByUndone(TestTask... currentList) {
        commandBox.runCommand(ListCommand.COMMAND_WORD_1 + " notdone");
        TestTask[] expectedList = TestUtil.filterUndoneTasks(currentList);
        assertListSize(expectedList.length);
        assertResultMessage(expectedList.length + " undone tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
