package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.SortCommand;
import seedu.doist.logic.commands.SortCommand.SortType;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskAlphabetComparator;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskPriorityComparator;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskTimingComparator;

//@@author A0140887W
public class SortCommandTest extends DoistGUITest {

    /**
     * Runs sort priority command, asserts if tasks are sorted by priority, then asserts if message is correct
     */
    @Test
    public void testSortByPriority() {
        List<SortType> list = new ArrayList<SortType>();
        list.add(SortType.PRIORITY);
        commandBox.runCommand("sort priority");
        assertSortedByPriority();
        assertResultMessage(Command.getMessageForTaskListSortedSummary(list));
    }

    /**
     * Runs sort alpha command, asserts if tasks are sorted by alphabetical order, then asserts if message is correct
     */
    @Test
    public void testSortByAlphabetical() {
        List<SortType> list = new ArrayList<SortType>();
        list.add(SortType.ALPHA);
        commandBox.runCommand("sort alpha");
        assertSortedByAlphabetical();
        assertResultMessage(Command.getMessageForTaskListSortedSummary(list));
    }

    /**
     * Runs sort time command, asserts if tasks are sorted by timing, then asserts if message is correct
     */
    @Test
    public void testSortByTiming() {
        List<SortType> list = new ArrayList<SortType>();
        list.add(SortType.TIME);
        commandBox.runCommand("sort time");
        assertSortedByTime();
        assertResultMessage(Command.getMessageForTaskListSortedSummary(list));
    }

    @Test
    public void testSortByInvalidCriterion() {
        commandBox.runCommand("sort some_invalid_criterion");
        assertResultMessage(SortCommand.MESSAGE_SORT_CONSTRAINTS);
    }

    private void assertSortedByPriority() {
        List<ReadOnlyTask> displayedList = taskListPanel.getListView().getItems();
        Comparator<ReadOnlyTask> priorityComparator = new ReadOnlyTaskPriorityComparator();
        for (int i = 0; i < displayedList.size() - 1; i++) {
            assertTrue(priorityComparator.compare(displayedList.get(i), displayedList.get(i + 1)) <= 0);
        }
    }

    private void assertSortedByAlphabetical() {
        List<ReadOnlyTask> displayedList = taskListPanel.getListView().getItems();
        Comparator<ReadOnlyTask> alphaComparator = new ReadOnlyTaskAlphabetComparator();
        for (int i = 0; i < displayedList.size() - 1; i++) {
            assertTrue(alphaComparator.compare(displayedList.get(i), displayedList.get(i + 1)) <= 0);
        }
    }

    private void assertSortedByTime() {
        List<ReadOnlyTask> displayedList = taskListPanel.getListView().getItems();
        Comparator<ReadOnlyTask> timeComparator = new ReadOnlyTaskTimingComparator();
        for (int i = 0; i < displayedList.size() - 1; i++) {
            assertTrue(timeComparator.compare(displayedList.get(i), displayedList.get(i + 1)) <= 0);
        }
    }
}
