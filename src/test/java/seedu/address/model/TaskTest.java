package seedu.address.model;

import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Task;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

/**
 * Test classes for Person
 */
public class TaskTest {
    @Test
    public void person_hashCodeShouldMatch_returnTrue() {
        TestTask pb;
        try {
            pb = (new TaskBuilder())
                    .withDeadline("07-03-2017")
                    .withLabels("testLabel")
                    .withTitle("Title")
                    .build();
            Task p = new Task(pb);
            int hashcode = 1027801006;
            assert(p.hashCode() == hashcode);
        } catch (IllegalValueException e) {
            e.printStackTrace();
            fail("Person fail Illegal value");
        }
    }
}
