//@@author A0142255M

package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TaskListTypeTest extends TaskManagerGuiTest {

    public static final String TASK_LIST_TYPE_ALL = "All Tasks";
    public static final String TASK_LIST_TYPE_COMPLETED = "Completed Tasks";
    public static final String TASK_LIST_TYPE_UNCOMPLETED = "Uncompleted Tasks";
    public static final String TASK_LIST_TYPE_TIMED = "Timed Tasks";
    public static final String TASK_LIST_TYPE_FLOATING = "Floating Tasks";
    public static final String TASK_LIST_TYPE_DUE_TODAY = "Tasks Due Today";
    public static final String TASK_LIST_TYPE_DUE_THIS_WEEK = "Tasks Due This Week";
    public static final String TASK_LIST_TYPE_OVERDUE = "Overdue Tasks";

    @Test
    public void taskListType_defaultUncompleted_success() {
        // get task list type at app startup: show uncompleted tasks
        assertEquals(taskListType.getTaskListType().getText(), TASK_LIST_TYPE_UNCOMPLETED);
    }

    @Test
    public void taskListType_afterListCommand_success() {
        // completed tasks
        commandBox.runCommand("list completed");
        assertEquals(taskListType.getTaskListType().getText(), TASK_LIST_TYPE_COMPLETED);

        // uncompleted tasks
        commandBox.runCommand("list uncompleted");
        assertEquals(taskListType.getTaskListType().getText(), TASK_LIST_TYPE_UNCOMPLETED);

        // timed tasks
        commandBox.runCommand("list timed");
        assertEquals(taskListType.getTaskListType().getText(), TASK_LIST_TYPE_TIMED);

        // floating tasks
        commandBox.runCommand("list floating");
        assertEquals(taskListType.getTaskListType().getText(), TASK_LIST_TYPE_FLOATING);

        // tasks due today
        commandBox.runCommand("list today");
        assertEquals(taskListType.getTaskListType().getText(), TASK_LIST_TYPE_DUE_TODAY);

        // tasks due this week
        commandBox.runCommand("list this week");
        assertEquals(taskListType.getTaskListType().getText(), TASK_LIST_TYPE_DUE_THIS_WEEK);

        // overdue tasks
        commandBox.runCommand("list overdue");
        assertEquals(taskListType.getTaskListType().getText(), TASK_LIST_TYPE_OVERDUE);
    }

    @Test
    public void taskListType_allAfterFindCommand_success() {
        commandBox.runCommand("find visit");
        assertEquals(taskListType.getTaskListType().getText(), "Find \"visit\"");

        commandBox.runCommand("find 21/04/2017");
        assertEquals(taskListType.getTaskListType().getText(), "Find \"21/04/2017\"");
    }
}
