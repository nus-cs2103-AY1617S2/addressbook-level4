package guitests;

import org.junit.Test;
import guitests.guihandles.TaskCardHandle;

public class CompleteCommandTest extends ToDoListGuiTest {
    
    @Test
    public void complete() {
        
        TestTask taskToComplete;
        // Runs complete command on a task.
        taskToComplete = td.task;
        commandBox.runCommand("list all");
        commandBox.runCommand("clear");
        commandBox.runCommand(taskToComplete.getAddCommand());
        assertCompleteSuccess(taskToComplete, 1);
       
        // Runs complete command on positive/negative/0 invalid indexes
        commandBox.runCommand("clear");
        commandBox.runCommand(taskToComplete.getAddCommand());
        assertInvalidIndex(taskToComplete, 2); // Only 1 activity in the list.
        assertInvalidIndex(taskToComplete, 0);
        assertInvalidIndex(taskToComplete, -1);

        // Runs complete command on empty list
        commandBox.runCommand("clear");
        assertInvalidIndex(taskToComplete, 1);
        
        //Runs complete command without index
        assertMissingIndex();
    }

    /**
     * checks whether a complete command correctly updates the UI
     */
    private void assertCompleteSuccess(TestTask taskToComplete, int index) {
        
        commandBox.runCommand(taskToComplete.getCompleteCommand(index));
        boolean isTask = false;
        
        //Confirms new Activity card has correct Completed status.
        if (taskToComplete.getTaskType().equals(Task.TASK_TYPE)) {
            isTask = true;
            TaskCardHandle completedCard = taskListPanel.navigateToTask(taskToComplete);
            assertTaskMatching(taskToComplete, completedCard);
        }
        
        if (isTask) {
            taskToComplete = taskListPanel.returnsUpdatedTask(taskToComplete.getTaskName().fullName);
        }

        // Confirms the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETED_ACTIVITY_SUCCESS, taskToComplete));
    }
    
    private void assertInvalidIndex(TestTask taskToComplete, int index) {
        
        commandBox.runCommand(taskToComplete.getCompleteCommand(index));
        assertResultMessage(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }
    
    private void assertMissingIndex() {
        
        commandBox.runCommand("complete task");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
    }
}