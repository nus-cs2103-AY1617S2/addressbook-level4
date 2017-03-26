package guitests;

import org.junit.Test;
import guitests.guihandles.TaskCardHandle;

public class CompleteCommandTest extends ToDoListGuiTest {
    
    @Test
    public void complete() {
        
        TestActivity activityToComplete;
        // Runs complete command on a task.
        activityToComplete = td.task;
        commandBox.runCommand("list all");
        commandBox.runCommand("clear");
        commandBox.runCommand(activityToComplete.getAddCommand());
        assertCompleteSuccess(activityToComplete, 1);
        
        // Runs complete command on positive/negative/0 invalid indexes
        commandBox.runCommand("clear");
        commandBox.runCommand(activityToComplete.getAddCommand());
        assertInvalidIndex(activityToComplete, 2); // Only 1 activity in the list.
        assertInvalidIndex(activityToComplete, 0);
        assertInvalidIndex(activityToComplete, -1);

        // Runs complete command on empty list
        commandBox.runCommand("clear");
        assertInvalidIndex(activityToComplete, 1);
        
        //Runs complete command without index
        assertMissingIndex();
    }

    /**
     * checks whether a complete command correctly updates the UI
     */
    private void assertCompleteSuccess(TestActivity activityToComplete, int index) {
        
        commandBox.runCommand(activityToComplete.getCompleteCommand(index));
        boolean isTask = false;
        
        //Confirms new Activity card has correct Completed status.
        if (activityToComplete.getActivityType().equals(Activity.TASK_TYPE)) {
            isTask = true;
            TaskCardHandle completedCard = activityListPanel.navigateToTask(activityToComplete);
            assertTaskMatching(activityToComplete, completedCard);
        }
        
        if (isTask) {
            activityToComplete = activityListPanel.returnsUpdatedTask(activityToComplete.getActivityName().fullName);
        }

        // Confirms the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETED_ACTIVITY_SUCCESS, activityToComplete));
    }
    
    private void assertInvalidIndex(TestActivity activityToComplete, int index) {
        
        commandBox.runCommand(activityToComplete.getCompleteCommand(index));
        assertResultMessage(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }
    
    private void assertMissingIndex() {
        
        commandBox.runCommand("complete task");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
    }
}