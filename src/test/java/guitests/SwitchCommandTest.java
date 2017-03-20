package guitests;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.scene.input.KeyCode;
import org.junit.Before;
import org.junit.Test;

import seedu.toluist.controller.SwitchController;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import sun.security.krb5.internal.KdcErrException;

/**
 * Gui tests for switch command
 */
public class SwitchCommandTest extends ToLuistGuiTest {
    private static final String TAB_INCOMPLETE = "INCOMPLETE";
    private static final String TAB_TODAY = "TODAY";
    private static final String TAB_NEXT_7_DAYS = "NEXT 7 DAYS";
    private static final String TAB_COMPLETED = "COMPLETED";
    private static final String TAB_ALL = "ALL";

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
    public void switch_toInvalidTab() {
        String tab = "fffs";
        String command = "switch " + tab;
        commandBox.runCommand(command);
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_FAILURE, tab));
    }

    @Test
    public void switch_toValidTabUsingShortcutName() {
        assertTrue(areTasksShown(floatingTask, eventIn6Days));
        assertFalse(isTaskShown(taskWithDeadline));

        String switchToToday = "switch t";
        commandBox.runCommand(switchToToday);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertFalse(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_TODAY, 1, 3));

        String switchToNext7Days = "switch n";
        commandBox.runCommand(switchToNext7Days);
        assertFalse(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertTrue(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_NEXT_7_DAYS, 1, 3));
    }

    @Test
    public void switch_toValidTabUsingShortcutNameCaseInsensitive() {
        assertTrue(areTasksShown(floatingTask, eventIn6Days));
        assertFalse(isTaskShown(taskWithDeadline));

        String switchToToday = "switch T ";
        commandBox.runCommand(switchToToday);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertFalse(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_TODAY, 1, 3));
    }

    @Test
    public void switch_toValidTabUsingNumber() {
        assertTrue(areTasksShown(floatingTask, eventIn6Days));
        assertFalse(isTaskShown(taskWithDeadline));

        String switchToToday = "switch 2";
        commandBox.runCommand(switchToToday);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertFalse(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_TODAY, 1, 3));

        String switchToNext7Days = "switch 3";
        commandBox.runCommand(switchToNext7Days);
        assertFalse(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertTrue(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_NEXT_7_DAYS, 1, 3));
    }

    @Test
    public void switch_toValidTabAfterFiltering() {
        assertTrue(areTasksShown(floatingTask, eventIn6Days));
        assertFalse(isTaskShown(taskWithDeadline));

        String commandFilter = "filter task";
        commandBox.runCommand(commandFilter);

        String switchToToday = "switch t";
        commandBox.runCommand(switchToToday);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertFalse(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_FILTERED, TAB_TODAY, 1, 2));

        String switchToNext7Days = "switch n";
        commandBox.runCommand(switchToNext7Days);
        assertFalse(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertFalse(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_FILTERED,
                                         TAB_NEXT_7_DAYS, 0, 2));
    }

    @Test
    public void switch_toValidTabUsingHotkey() {
        mainGui.press(KeyCode.CONTROL, KeyCode.T);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertFalse(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_TODAY, 1, 3));

        mainGui.press(KeyCode.CONTROL, KeyCode.N);
        assertFalse(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertTrue(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_NEXT_7_DAYS, 1, 3));

        mainGui.press(KeyCode.CONTROL, KeyCode.C);
        assertTrue(isTaskShown(taskWithDeadline));
        assertFalse(isTaskShown(floatingTask));
        assertFalse(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_COMPLETED, 1, 3));

        mainGui.press(KeyCode.CONTROL, KeyCode.A);
        assertTrue(isTaskShown(taskWithDeadline));
        assertTrue(isTaskShown(floatingTask));
        assertTrue(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_ALL, 3, 3));

        mainGui.press(KeyCode.CONTROL, KeyCode.I);
        assertFalse(isTaskShown(taskWithDeadline));
        assertTrue(isTaskShown(floatingTask));
        assertTrue(isTaskShown(eventIn6Days));
        assertResultMessage(String.format(SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_INCOMPLETE, 2, 3));
    }
}
