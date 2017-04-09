package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.taskit.testutil.TestTask;

//@@author A0141872E
public class MenuBarPanelTest extends TaskManagerGuiTest {

    public static final String MENU_HOME = "Home";
    public static final String MENU_TODAY_TASK = "Today";
    public static final String MENU_OVERDUE_TASK = "Overdue";
    public static final String MENU_FLOATING_TASK = "Simple Tasks";
    public static final String MENU_EVENT_TASK = "Event";
    public static final String MENU_DEADLINE_TASK = "Deadline";

    @Test
    public void navigateToHomeTasks() {
        assertMenuBarPanelResult(MENU_HOME, td.getTypicalTasks());
        assertResultMessage("Listed all tasks");
    }

    @Test
    public void navigateToTodayTasks() {
        assertMenuBarPanelResult(MENU_TODAY_TASK, td.getTodayTasks());//no today task
        assertResultMessage("There is no incomplete task for today! Great");
    }

    @Test
    public void navigateToOverdueTasks() {
        commandBox.runCommand("add task3 by 04/04/17");
        assertMenuBarPanelResult(MENU_OVERDUE_TASK, td.overdue);
        assertResultMessage("Listed all relevant tasks for overdue");

    }

    @Test
    public void navigateToFloatingTasks() {
        assertMenuBarPanelResult(MENU_FLOATING_TASK, td.getTypicalTasks());
        assertResultMessage("Listed all relevant tasks for floating");
    }

    @Test
    public void navigateToEventTasks() {
        commandBox.runCommand("add date from 3pm to 6pm");
        assertMenuBarPanelResult(MENU_EVENT_TASK, td.date);
        assertResultMessage("Listed all relevant tasks for event");
    }

    @Test
    public void navigateToDeadlineTasks() {
        commandBox.runCommand("add project deadline by 11pm");
        assertMenuBarPanelResult(MENU_DEADLINE_TASK, td.deadline);
        assertResultMessage("Listed all relevant tasks for deadline");
    }

    private void assertMenuBarPanelResult(String menuBarItem, TestTask... expectedHits) {
        menuBarPanel.navigateTo(menuBarItem);
        assertListSize(expectedHits.length);
        Arrays.sort(expectedHits);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
