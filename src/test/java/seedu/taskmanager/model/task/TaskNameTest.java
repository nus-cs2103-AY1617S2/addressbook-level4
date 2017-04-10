package seedu.taskmanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TaskNameTest {

    // @@author A0141102H
    @Test
    public void isValidName() {
        // invalid task name
        assertFalse(TaskName.isValidName("")); // empty string
        assertFalse(TaskName.isValidName(" ")); // spaces only

        // valid task name
        assertTrue(TaskName.isValidName("eat food")); // alphabets only
        assertTrue(TaskName.isValidName("1 22 333 4444 55555")); // numbers only
        assertTrue(TaskName.isValidName("hungry b0i")); // alphanumeric
                                                        // characters
        assertTrue(TaskName.isValidName("EAT FOOD")); // with capital letters
        assertTrue(TaskName.isValidName("This is a run on sentence created" + "solely to prove a point")); // long
                                                                                                           // task
                                                                                                           // names
    }
}
