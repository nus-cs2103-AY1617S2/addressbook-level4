package guitests;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.toluist.controller.SwitchController;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;

/**
 * Gui tests for switch command
 */
public class SwitchCommandTest extends ToLuistGuiTest {
    private Task floatingTask = new Task("floating task");
    private Task taskWithDeadline = new Task("last week task", LocalDateTime.now());
    private Task eventIn6Days = new Task("event", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(4));

    @Before
    public void setUp() {
        TodoList todoList = TodoList.load();
        todoList.setTasks(new ArrayList<>());
        taskWithDeadline.setCompleted(true);
        todoList.add(floatingTask);
        todoList.add(taskWithDeadline);
        todoList.add(eventIn6Days);
        todoList.save();
        commandBox.runCommand("list");
        commandBox.runCommand("switch i");
    }

    @Test
    public void switch_noTab() {
        String command = "switch ";
        commandBox.runCommand(command);
        assertResultMessage(SwitchController.RESULT_MESSAGE_NO_TAB);
    }

    @Test
    public void switch_ToInvalidTab() {
        String tab = "fffs";
        String command = "switch " + tab;
        commandBox.runCommand(command);
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_FAILURE, tab));
    }

    @Test
    public void switch_ToValidTabUsingShortcutName() {
        assertTrue(areTasksShown(floatingTask, eventIn6Days));
        assertFalse(isTaskShown(taskWithDeadline));

        String tabToday = "Today";
        String switchToToday = "switch t";
        commandBox.runCommand(switchToToday);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertFalse(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, tabToday, 1, 3));

        String tabWithin7Days = "Within 7 Days";
        String switchToWithin7Days = "switch w";
        commandBox.runCommand(switchToWithin7Days);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertTrue(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, tabWithin7Days, 2, 3));
    }

    @Test
    public void switch_ToValidTabUsingNumber() {
        assertTrue(areTasksShown(floatingTask, eventIn6Days));
        assertFalse(isTaskShown(taskWithDeadline));

        String tabToday = "Today";
        String switchToToday = "switch 2";
        commandBox.runCommand(switchToToday);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertFalse(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, tabToday, 1, 3));

        String tabWithin7Days = "Within 7 Days";
        String switchToWithin7Days = "switch 3";
        commandBox.runCommand(switchToWithin7Days);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertTrue(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, tabWithin7Days, 2, 3));
    }

    @Test
    public void switch_ToValidTabAfterFiltering() {
        assertTrue(areTasksShown(floatingTask, eventIn6Days));
        assertFalse(isTaskShown(taskWithDeadline));

        String commandFilter = "filter task";
        commandBox.runCommand(commandFilter);

        String tabToday = "Today";
        String switchToToday = "switch t";
        commandBox.runCommand(switchToToday);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertFalse(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_FILTERED, tabToday, 1, 2));

        String tabWithin7Days = "Within 7 Days";
        String switchToWithin7Days = "switch w";
        commandBox.runCommand(switchToWithin7Days);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertFalse(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_FILTERED, tabWithin7Days, 1,
                2));
    }
}
