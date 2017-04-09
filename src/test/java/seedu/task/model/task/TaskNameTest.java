package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TaskNameTest {

     @Test
     public void isValidName() {
     // invalid task name
     assertFalse(TaskName.isValidTaskName("")); // empty string
     assertFalse(TaskName.isValidTaskName(" ")); // spaces only
     assertFalse(TaskName.isValidTaskName("^")); // only non-alphanumeric
     // characters
     assertFalse(TaskName.isValidTaskName("Shopping*")); // contains
     // non-alphanumeric
     // characters
    
     // valid task name
     assertTrue(TaskName.isValidTaskName("Fix phone")); // alphabets only
     assertTrue(TaskName.isValidTaskName("Collect phone at 10am")); // alphanumeric
     // characters
     assertTrue(TaskName.isValidTaskName("Throw Phone")); // with capital
     // letters
     }
}
