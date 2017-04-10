package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.testutil.TaskBuilder;
import seedu.task.testutil.TestTask;

public class IsDoneTest {

    @Test
    public void isDone() throws IllegalValueException {

        TestTask taskToTest = new TaskBuilder().withName("Buy milk").withEndDate("12-03-2017")
                .withRemark("remark").withLocation("Block 123, Bobby Street 3").withTags("shopping").build();

        assertFalse(taskToTest.isDone()); // default should be not done

        taskToTest.setIsDone(true);
        assertTrue(taskToTest.isDone());  //able to mark as done
    }
}
