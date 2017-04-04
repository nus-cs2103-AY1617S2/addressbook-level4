//@@author A0131125Y
package guitests;

import static org.junit.Assert.assertFalse;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TaskListHandle;
import javafx.scene.input.KeyCode;
import seedu.toluist.controller.SwitchController;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;

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
        TodoList todoList = TodoList.getInstance();
        todoList.setTasks(new ArrayList<>());
        taskWithDeadline.setCompleted(true);
        todoList.add(floatingTask);
        todoList.add(taskWithDeadline);
        todoList.add(eventIn6Days);
        todoList.save();
        commandBox.runCommand("list");
        commandBox.runCommand("switch i");

        // Check that some default tasks are there
        assertTasksShown(true, floatingTask, eventIn6Days);
        assertFalse(isTaskShown(taskWithDeadline));
    }

    @Test
    public void switch_noTab() {
        String command = "switch ";
        runCommandThenCheckForResultMessage(command, SwitchController.RESULT_MESSAGE_NO_TAB);
    }

    @Test
    public void switch_toInvalidTab() {
        String tab = "fffs";
        String command = "switch " + tab;
        runCommandThenCheckForResultMessage(command,
                String.format(SwitchController.RESULT_MESSAGE_SWITCH_FAILURE, tab));
    }

    @Test
    public void switch_toValidTabUsingShortcutName() {
        String switchToToday = "switch t";
        commandBox.runCommand(switchToToday);
        assertSwitchResult(new Task[] { taskWithDeadline }, new Task[] { eventIn6Days, floatingTask },
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_TODAY, 1, 3);

        String switchToNext7Days = "switch n";
        commandBox.runCommand(switchToNext7Days);
        assertSwitchResult(new Task[] { eventIn6Days }, new Task[] { taskWithDeadline, floatingTask },
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_NEXT_7_DAYS, 1, 3);
    }

    @Test
    public void switch_toValidTabUsingShortcutNameCaseInsensitive() {
        String switchToToday = "switch T ";
        commandBox.runCommand(switchToToday);
        assertSwitchResult(new Task[] { taskWithDeadline }, new Task[] { eventIn6Days, floatingTask },
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_TODAY, 1, 3);
    }

    @Test
    public void switch_toValidTabUsingNumber() {
        String switchToToday = "switch 2";
        commandBox.runCommand(switchToToday);
        assertSwitchResult(new Task[] { taskWithDeadline }, new Task[] { eventIn6Days, floatingTask },
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_TODAY, 1, 3);

        String switchToNext7Days = "switch 3";
        commandBox.runCommand(switchToNext7Days);
        assertSwitchResult(new Task[] { eventIn6Days }, new Task[] { taskWithDeadline, floatingTask },
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_NEXT_7_DAYS, 1, 3);
    }

    @Test
    public void switch_toValidTabAfterFiltering() {
        String commandFilter = "filter task";
        commandBox.runCommand(commandFilter);

        String switchToToday = "switch t";
        commandBox.runCommand(switchToToday);
        assertSwitchResult(new Task[] { taskWithDeadline }, new Task[] { eventIn6Days, floatingTask },
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_FILTERED, TAB_TODAY, 1, 2);

        String switchToNext7Days = "switch n";
        commandBox.runCommand(switchToNext7Days);
        assertSwitchResult(new Task[0], new Task[] { taskWithDeadline, eventIn6Days, floatingTask },
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_FILTERED, TAB_NEXT_7_DAYS, 0, 2);
    }

    @Test
    public void switch_toValidTabUsingHotkey() {
        mainGui.press(KeyCode.CONTROL, KeyCode.T);
        assertSwitchResult(TAB_TODAY + " (1/3)", new Task[] { taskWithDeadline },
                new Task[] { eventIn6Days, floatingTask },
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_TODAY, 1, 3);

        mainGui.press(KeyCode.CONTROL, KeyCode.N);
        assertSwitchResult(TAB_NEXT_7_DAYS + " (1/3)", new Task[] { eventIn6Days },
                new Task[] { taskWithDeadline, floatingTask },
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_NEXT_7_DAYS, 1, 3);

        mainGui.press(KeyCode.CONTROL, KeyCode.C);
        assertSwitchResult(TAB_COMPLETED + " (1/3)", new Task[] { taskWithDeadline },
                new Task[] { eventIn6Days, floatingTask },
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_COMPLETED, 1, 3);

        mainGui.focusOnView(TaskListHandle.TASK_LIST_VIEW_ID);
        mainGui.press(KeyCode.CONTROL, KeyCode.A);
        assertSwitchResult(TAB_ALL + " (3/3)",
                new Task[] { taskWithDeadline, eventIn6Days, floatingTask }, new Task[0],
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_ALL, 3, 3);

        mainGui.press(KeyCode.CONTROL, KeyCode.I);
        assertSwitchResult(TAB_INCOMPLETE + " (2/3)",
                new Task[] { floatingTask, eventIn6Days }, new Task[] { taskWithDeadline },
                SwitchController.RESULT_MESSAGE_SWITCH_SUCCESS_ALL, TAB_INCOMPLETE, 2, 3);

        runCommandThenCheckForResultMessage("history", "list\nswitch i\n2 commands displayed.");
    }

    /**
     * Helper method to check the result of switching
     * @param tasksToBeShown tasks that should be shown
     * @param tasksNotToBeShown tasks that should not be shown
     * @param resultMessageTemplate template for result message
     * @param templateParams params for the template, if any
     */
    private void assertSwitchResult(Task[] tasksToBeShown, Task[] tasksNotToBeShown,
                                    String resultMessageTemplate, Object... templateParams) {
        assertTasksShown(true, tasksToBeShown);
        assertTasksShown(false, tasksNotToBeShown);
        assertResultMessage(String.format(resultMessageTemplate, templateParams));
    }

    /**
     * Helper method to check the result of switching
     * @param highlightedTab expected label of the highlighed tab
     * @param tasksToBeShown tasks that should be shown
     * @param tasksNotToBeShown tasks that should not be shown
     * @param resultMessageTemplate template for result message
     * @param templateParams params for the template, if any
     */
    private void assertSwitchResult(String highlightedTab, Task[] tasksToBeShown, Task[] tasksNotToBeShown,
                                    String resultMessageTemplate, Object... templateParams) {
        assertSwitchResult(tasksToBeShown, tasksNotToBeShown, resultMessageTemplate, templateParams);
        assertTabShown(highlightedTab);
    }
}
