package guitests;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.SortCommand;
import seedu.doit.model.comparators.EndTimeComparator;
import seedu.doit.model.comparators.PriorityComparator;
import seedu.doit.model.comparators.StartTimeComparator;
import seedu.doit.model.comparators.TaskNameComparator;
import seedu.doit.model.item.ReadOnlyTask;
import seedu.doit.testutil.TaskBuilder;
import seedu.doit.testutil.TestTask;

public class SortCommandTest extends TaskManagerGuiTest {

    public static final int INDEX_TEST_MARK_VALID = 2;

    @Test
    public void sort() throws IllegalValueException {

        // check default sorting by name
        TestTask[] currentList = td.getTypicalTasks();
        assertSortSuccess("name", currentList);
        assertSortSuccess("priority", currentList);
        assertSortSuccess("start time", currentList);
        assertSortSuccess("end time", currentList);

        // invalid sort command
        this.commandBox.runCommand("sort invalid");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // check sort with done tasks
        commandBox.runCommand("mark " + INDEX_TEST_MARK_VALID);

        TestTask[] expectedTasksList = currentList;
        TestTask taskToMark = expectedTasksList[INDEX_TEST_MARK_VALID - 1];
        TestTask markedTask = new TaskBuilder(taskToMark).withIsDone(true).build();
        expectedTasksList[INDEX_TEST_MARK_VALID - 1] = markedTask;

        assertSortSuccess("name", expectedTasksList);
        assertSortSuccess("priority", expectedTasksList);
        assertSortSuccess("start time", expectedTasksList);
        assertSortSuccess("end time", expectedTasksList);
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
