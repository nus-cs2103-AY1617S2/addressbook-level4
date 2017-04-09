//@@author A0163744B
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.TaskList;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TypicalTestTasks;

public class ListCommandTest extends TaskListGuiTest {

    @Override
    protected TaskList getInitialData() {
        TaskList ab = new TaskList();
        TypicalTestTasks.loadAddressBookWithSampleData(
                ab, new TypicalTestTasks().getTypicalTasksWithDates()
        );
        return ab;
    }

    /**
     * Swaps the tasks at the two indices in the given {@code taskArray}
     * @param taskArray the array containing the tasks to swap
     * @param index1 the index of the first task to swap in {@code taskArray}
     * @param index2 the index of the second task to swap in {@code taskArray}
     */
    private void swapTestTasks(TestTask[] taskArray, int index1, int index2) {
        assert index1 < taskArray.length && index2 < taskArray.length;
        assert index1 > 0 && index2 > 0;
        TestTask temp = taskArray[index2];
        taskArray[index2] = taskArray[index1];
        taskArray[index1] = temp;
    }

    @Test
    public void listTest() {
        commandBox.runCommand("list");
        TestTask[] currentList = td.getTypicalTasksWithDates();
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void listByDueTest() {
        commandBox.runCommand("list by due");
        TestTask[] currentList = td.getTypicalTasksWithDates();
        swapTestTasks(currentList, 1, 2);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void listByStartsTest() {
        commandBox.runCommand("list by starts");
        TestTask[] currentList = td.getTypicalTasksWithDates();
        swapTestTasks(currentList, 1, 2);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void listByEndsTest() {
        commandBox.runCommand("list by ends");
        TestTask[] currentList = td.getTypicalTasksWithDates();
        swapTestTasks(currentList, 1, 2);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void listByAddedTest() {
        TestTask[] currentList = td.getTypicalTasksWithDates();
        commandBox.runCommand("list by due");
        commandBox.runCommand("list by added");
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void listCompleteTest() {
        TestTask[] currentList = td.getTypicalTasksWithDates();
        TestTask[] completeList = new TestTask[] {currentList[0]};
        commandBox.runCommand("list complete");
        assertTrue(taskListPanel.isListMatching(completeList));
    }

    @Test
    public void listIncompleteTest() {
        TestTask[] currentList = td.getTypicalTasksWithDates();
        TestTask[] uncompleteList = new TestTask[] {currentList[1], currentList[2]};
        commandBox.runCommand("list incomplete");
        assertTrue(taskListPanel.isListMatching(uncompleteList));
    }
}
