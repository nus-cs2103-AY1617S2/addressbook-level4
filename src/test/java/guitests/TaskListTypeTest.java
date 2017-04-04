//@@author A0142255M

package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TaskListTypeTest extends TaskManagerGuiTest {

    public static final String ALL_TASK_LIST_TYPE = "All Tasks";
    public static final String COMPLETED_TASK_LIST_TYPE = "Completed Tasks";
    public static final String UNCOMPLETED_TASK_LIST_TYPE = "Uncompleted Tasks";
    public static final String TIMED_TASK_LIST_TYPE = "Timed Tasks";
    public static final String FLOATING_TASK_LIST_TYPE = "Floating Tasks";

    @Test
    public void defaultTaskListTypeShouldBeUncompleted() {
        // get task list type at app startup: show uncompleted tasks
        assertEquals(taskListType.getTaskListType().getText(), UNCOMPLETED_TASK_LIST_TYPE);
    }

    @Test
    public void showTaskListTypeAfterListCommand() {
        // completed tasks
        commandBox.runCommand("list completed");
        assertEquals(taskListType.getTaskListType().getText(), COMPLETED_TASK_LIST_TYPE);

        // uncompleted tasks
        commandBox.runCommand("list uncompleted");
        assertEquals(taskListType.getTaskListType().getText(), UNCOMPLETED_TASK_LIST_TYPE);

        // timed tasks
        commandBox.runCommand("list timed");
        assertEquals(taskListType.getTaskListType().getText(), TIMED_TASK_LIST_TYPE);

        // floating tasks
        commandBox.runCommand("list floating");
        assertEquals(taskListType.getTaskListType().getText(), FLOATING_TASK_LIST_TYPE);
    }

    @Test
    public void showAllTaskListTypeAfterFindCommand() {
        commandBox.runCommand("find visit");
        assertEquals(taskListType.getTaskListType().getText(), "Find \"visit\"");
    }
}
