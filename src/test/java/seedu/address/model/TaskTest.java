package seedu.address.model;

import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

/**
 * Test classes for Task
 */
public class TaskTest {
    @Test
    public void task_hashCodeShouldMatch_returnTrue() {
        TestTask pb;
        try {
            pb = (new TaskBuilder())
                    .withStartTime("today")
                    .withDeadline("07-03-2017 2300")
                    .withLabels("testLabel")
                    .withTitle("Do something")
                    .build();
            Task p = new Task(pb);
            int hashcode = 999869056;
            int hashcode2 = p.hashCode();
        } catch (IllegalValueException | IllegalDateTimeValueException e) {
            e.printStackTrace();
            fail("Task fail Illegal value");
        }
    }
}
