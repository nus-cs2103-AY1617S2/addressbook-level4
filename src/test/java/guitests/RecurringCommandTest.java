package guitests;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;
import seedu.taskmanager.testutil.TestTask;

public class RecurringCommandTest extends TaskManagerGuiTest {

    // @@author A0141102H
    @Test
    public void recur() {
        // recur event
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.sampleEvent;
        commandBox.runCommand(taskToAdd.getAddCommand());

        // recur deadline

        // recur floating task

        // invalid command
        commandBox.runCommand("Recur 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void recurFloatingTaskFailure() {

    }

    @Test
    public void recurEventTask() {

    }

    @Test
    public void recurDeadlineTask() {
        TestTask taskToAdd = td.sampleDeadline;

    }

    private void AssertRecurSuccess() {

    }
}
