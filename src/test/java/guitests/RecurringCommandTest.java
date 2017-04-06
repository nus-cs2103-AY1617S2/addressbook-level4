package guitests;

import org.junit.Test;

import seedu.taskmanager.testutil.TestTask;

public class RecurringCommandTest extends TaskManagerGuiTest {

    // @@author A0141102H
    @Test
    public void recurFloatingTaskFailure() {
        TestTask taskToAdd = td.sampleFloatingTask;

    }

    @Test
    public void recurEventTaskSuccess() {
        TestTask taskToAdd = td.sampleEvent;

    }

    @Test
    public void recurDeadlineTaskSuccess() {
        TestTask taskToAdd = td.sampleDeadline;

    }

    private void AssertRecurSuccess() {

    }
}
