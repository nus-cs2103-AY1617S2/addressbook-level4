package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.opus.testutil.TestTask;

//@@author A0148081H
public class SortCommandTest extends TaskManagerGuiTest {

    @Test
    public void sortPriorityTestSuccess() throws Exception {
        String parameter = "priority";
        TestTask[] expectedSortedByPriorityTasksList = td.getSortedByPriorityTasks();

        assertSortSuccess(parameter, expectedSortedByPriorityTasksList);
    }

    @Test
    public void sortStartDateTestSuccess() throws Exception {
        String parameter = "start";
        TestTask[] expectedSortedByStartDateTasksList = td.getSortedByStartDateTasks();

        assertSortSuccess(parameter, expectedSortedByStartDateTasksList);
    }

    @Test
    public void sortEndDateTestSuccess() throws Exception {
        String parameter = "end";
        TestTask[] expectedSortedByEndDateTasksList = td.getSortedByEndDateTasks();

        assertSortSuccess(parameter, expectedSortedByEndDateTasksList);
    }

    /**
     * Checks whether the sorted task lists has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list.
     * @param taskManagerIndex index of task to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param editedTask the expected task after editing the task's details.
     */
    private void assertSortSuccess(String parameter, TestTask[] expectedTasksList) {
        commandBox.runCommand("sort " + parameter);

        // confirm the two lists match
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
    }

}
