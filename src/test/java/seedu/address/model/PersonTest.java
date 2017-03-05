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
public class PersonTest {
    @Test
    public void person_hashCodeShouldMatch_returnTrue() {
        TestTask pb;
        try {
            pb = (new TaskBuilder())
                    .withAddress("Singapore")
                    .withLabels("testLabel")
                    .withName("Name")
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
