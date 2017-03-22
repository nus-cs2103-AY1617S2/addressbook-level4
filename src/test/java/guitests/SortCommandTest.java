package guitests;

import java.util.Arrays;
import java.util.Comparator;

//import java.util.Arrays;

import org.junit.Test;

import seedu.doit.model.item.EndTimeComparator;
import seedu.doit.model.item.PriorityComparator;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.model.item.StartTimeComparator;
import seedu.doit.model.item.TaskNameComparator;
import seedu.doit.testutil.TestTask;

public class SortCommandTest extends TaskManagerGuiTest {

    @Test
    public void sort() {
        // check default sorting by name
        TestTask[] currentList = td.getTypicalTasks();
        assertSortSuccess("name", currentList);
        assertSortSuccess("priority", currentList);
        assertSortSuccess("start time", currentList);
        assertSortSuccess("end time", currentList);

    }

    private void assertSortSuccess(String sortType, TestTask... currentList) {
        commandBox.runCommand("sort " + sortType);

        switch (sortType) {
        case "name":
            Comparator<ReadOnlyTask> nameComparator = new TaskNameComparator();
            Arrays.sort(currentList, nameComparator);
            assertAllPanelsMatch(currentList, nameComparator);
            break;
        case "priority":
            Comparator<ReadOnlyTask> priorityComparator = new PriorityComparator();
            Arrays.sort(currentList, priorityComparator);
            assertAllPanelsMatch(currentList, priorityComparator);
            break;
        case "start time":
            Comparator<ReadOnlyTask> startTimeComparator = new StartTimeComparator();
            Arrays.sort(currentList, startTimeComparator);
            assertAllPanelsMatch(currentList, startTimeComparator);
            break;
        case "end time":
            Comparator<ReadOnlyTask> endTimeComparator = new EndTimeComparator();
            Arrays.sort(currentList, endTimeComparator);
            assertAllPanelsMatch(currentList, endTimeComparator);
            break;
        default:
            break;
        }


    }

}
