package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.SortCommand;
import seedu.doist.logic.commands.SortCommand.SortType;
import seedu.doist.model.task.ReadOnlyTask;
import seedu.doist.model.task.ReadOnlyTask.ReadOnlyTaskPriorityComparator;

public class SortCommandTest extends DoistGUITest {

    @Test
    public void testSortByPriority() {
        commandBox.runCommand("sort priority");
        assertSortedByPriority();
        assertResultMessage(Command.getMessageForPersonListSortedSummary(SortType.PRIORITY));
    }

    @Test
    public void testSortByInvalidCriterion() {
        commandBox.runCommand("sort some_invalid_criterion");
        assertResultMessage(SortCommand.MESSAGE_SORT_CONSTRAINTS);
    }

    private void assertSortedByPriority() {
        List<ReadOnlyTask> displayedList = personListPanel.getListView().getItems();
        Comparator<ReadOnlyTask> priorityComparator = new ReadOnlyTaskPriorityComparator();
        for (int i = 0; i < displayedList.size() - 1; i++) {
            assertTrue(priorityComparator.compare(displayedList.get(i), displayedList.get(i + 1)) <= 0);
        }
    }
}
