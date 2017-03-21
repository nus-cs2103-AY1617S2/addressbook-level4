package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

//import java.util.Arrays;

import org.junit.Test;

import seedu.doit.model.item.EndTimeComparator;
import seedu.doit.model.item.PriorityComparator;
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
            Arrays.sort(currentList, new TaskNameComparator());
            break;
        case "priority":
            Arrays.sort(currentList, new PriorityComparator());
            break;
        case "start time":
            Arrays.sort(currentList, new StartTimeComparator());
            break;
        case "end time":
            Arrays.sort(currentList, new EndTimeComparator());
            break;
        default:
            break;
        }

        assertTrue(taskListPanel.isListMatching(currentList));
    }

}
