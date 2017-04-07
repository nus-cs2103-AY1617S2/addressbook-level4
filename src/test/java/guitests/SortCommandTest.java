//@@author A0162011A
package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.model.Task;
import seedu.toluist.ui.UiStore;

/**
 * Gui tests for sort command
 */
public class SortCommandTest extends ToLuistGuiTest {
    @Test
    public void sort_byDefault() {
        commandBox.runCommand("sort by default");
        assertSortedByDefault();
    }

    @Test
    public void sort_byStartDate() {
        commandBox.runCommand("sort startdate");
        assertSortedByStartDate();
    }

    @Test
    public void sort_byEndDate() {
        commandBox.runCommand("sort enddate");
        assertSortedByEndDate();
    }

    @Test
    public void sort_byPriority() {
        commandBox.runCommand("sort priority");
        assertSortedByPriority();
    }

    @Test
    public void sort_byOverdue() {
        commandBox.runCommand("sort overdue");
        assertSortedByOverdue();
    }

    @Test
    public void sort_byDescription() {
        commandBox.runCommand("sort description");
        assertSortedByDescription();
    }

    @Test
    public void sort_byMultipleParameters() {
        commandBox.runCommand("sort priority enddate");
        assertSortedByPriorityThenEndDate();
    }

    @Test
    public void sort_byNothing() {
        runCommandThenCheckForResultMessage("sort", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, "sort"));
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, "sort"));
    }

    @Test
    public void sort_byInvalidParameter() {
        runCommandThenCheckForResultMessage("sort a", "No valid keyword entered."
                + " Please type 'help sort' for details");
        assertResultMessage("No valid keyword entered. Please type 'help sort' for details");
    }

    @Test
    public void sort_byMultipleParametersIncludingDefault() {
        runCommandThenCheckForResultMessage("sort priority default", "'Default' keyword may not"
                + " be used with other parameters.");
        assertResultMessage("'Default' keyword may not be used with other parameters.");
    }


    private void assertSortedByDefault() {
        ArrayList<Task> shownTasks = UiStore.getInstance().getShownTasks();
        for (int i = 0; i < shownTasks.size() - 1; i++) {
            assertTrue((shownTasks.get(i).isOverdue() && !shownTasks.get(i + 1).isOverdue()) ||
                (shownTasks.get(i).getTaskPriority().compareTo(shownTasks.get(i + 1).getTaskPriority())) > 0 ||
                DateTimeUtil.isBefore(shownTasks.get(i).getEndDateTime(), shownTasks.get(i + 1).getEndDateTime()) ||
                DateTimeUtil.isBefore(shownTasks.get(i).getStartDateTime(), shownTasks.get(i + 1).getStartDateTime()) ||
                shownTasks.get(i).getDescription().compareToIgnoreCase(shownTasks.get(i + 1).getDescription()) >= 0);
        }
    }

    private void assertSortedByPriority() {
        ArrayList<Task> shownTasks = UiStore.getInstance().getShownTasks();
        for (int i = 0; i < shownTasks.size() - 1; i++) {
            assertTrue(shownTasks.get(i).getTaskPriority().compareTo(shownTasks.get(i + 1).getTaskPriority()) >= 0);
        }
    }

    private void assertSortedByEndDate() {
        ArrayList<Task> shownTasks = UiStore.getInstance().getShownTasks();
        for (int i = 0; i < shownTasks.size() - 1; i++) {
            assertTrue(DateTimeUtil.isBeforeOrEqual(
                    shownTasks.get(i).getEndDateTime(), shownTasks.get(i + 1).getEndDateTime()));
        }
    }

    private void assertSortedByStartDate() {
        ArrayList<Task> shownTasks = UiStore.getInstance().getShownTasks();
        for (int i = 0; i < shownTasks.size() - 1; i++) {
            assertTrue(DateTimeUtil.isBeforeOrEqual(
                    shownTasks.get(i).getStartDateTime(), shownTasks.get(i + 1).getStartDateTime()));
        }
    }

    private void assertSortedByDescription() {
        ArrayList<Task> shownTasks = UiStore.getInstance().getShownTasks();
        for (int i = 0; i < shownTasks.size() - 1; i++) {
            assertTrue(shownTasks.get(i).getDescription().
                    compareToIgnoreCase(shownTasks.get(i + 1).getDescription()) <= 0);
        }
    }

    private void assertSortedByOverdue() {
        ArrayList<Task> shownTasks = UiStore.getInstance().getShownTasks();
        for (int i = 0; i < shownTasks.size() - 1; i++) {
            assertTrue(shownTasks.get(i).isOverdue() ||
                    (shownTasks.get(i).isOverdue() == shownTasks.get(i + 1).isOverdue()));
        }
    }

    private void assertSortedByPriorityThenEndDate() {
        ArrayList<Task> shownTasks = UiStore.getInstance().getShownTasks();
        for (int i = 0; i < shownTasks.size() - 1; i++) {
            assertTrue(
                    shownTasks.get(i).getTaskPriority().compareTo(shownTasks.get(i + 1).getTaskPriority()) > 0 ||
                    DateTimeUtil.isBeforeOrEqual(
                            shownTasks.get(i).getEndDateTime(), shownTasks.get(i + 1).getEndDateTime()));
        }
    }
}
