package seedu.address.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.EndDate;
import seedu.task.model.task.Group;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartDate;
import seedu.task.model.task.Task;

//@@author A0163848R
/**
 * Tests to check the validity of the Task.factory() method.
 */
public class TaskFactoryTest {

    @Test
    public void isValidTask() {

        Object task = null;

        // Test: Is Task
        try {
            task = Task.factory(
                    new Name("Go to the beach"),
                    new Group("Vacation"),
                    new StartDate("today"),
                    new EndDate("tomorrow"),
                    UniqueTagList.build(Tag.TAG_INCOMPLETE));
        } catch (IllegalValueException e) {
        }
        assertTrue(task instanceof Task);

        // Test: No end date
        try {
            task = Task.factory(
                    new Name("Go to the beach"),
                    new Group("Vacation"),
                    new StartDate("today"),
                    UniqueTagList.build(Tag.TAG_INCOMPLETE));
        } catch (IllegalValueException e) {
        }
        assertTrue(task == null);

        // Test: without required field (UniqueTagList)
        try {
            task = Task.factory(
                    new Name("Go to the beach"),
                    new Group("Vacation"),
                    new StartDate("today"),
                    new EndDate("tomorrow"));
        } catch (IllegalValueException e) {
            assertTrue(true);
        }
    }
}