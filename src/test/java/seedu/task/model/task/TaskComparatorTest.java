package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.testutil.TaskBuilder;
import seedu.task.testutil.TestTask;

public class TaskComparatorTest {

    TaskComparator tc = new TaskComparator();

    @Test
    public void equal_EndDates() throws IllegalValueException {
        TestTask o1 = new TaskBuilder().withName("A this goes first").withStartDate("04-03-1989 23:59")
                .withEndDate("08-03-1989 23:59").build();
        TestTask o2 = new TaskBuilder().withName("B this goes second").withStartDate("04-03-1989 23:59")
                .withEndDate("08-03-1989 23:59").build();
        assertTrue(tc.compare(o1, o2) == o1.getName().fullName.compareTo(o2.getName().fullName));
    }

    @Test
    public void both_Null_EndDates() throws IllegalValueException {
        TestTask o1 = new TaskBuilder().withName("A this goes first").withStartDate("04-03-1989 23:59").withEndDate("")
                .build();
        TestTask o2 = new TaskBuilder().withName("B this goes second").withStartDate("04-03-1989 23:59").withEndDate("")
                .build();
        assertTrue(tc.compare(o1, o2) == o1.getName().fullName.compareTo(o2.getName().fullName));

    }

    @Test
    public void first_Null_EndDate() throws IllegalValueException {
        TestTask o1 = new TaskBuilder().withName("this goes second").withStartDate("04-03-1989 23:59").withEndDate("")
                .build();
        TestTask o2 = new TaskBuilder().withName("this goes first").withStartDate("04-03-1989 23:59")
                .withEndDate("08-03-1989 23:59").build();
        assertTrue(tc.compare(o1, o2) == 1);

    }

    @Test
    public void second_Null_EndDate() throws IllegalValueException {
        TestTask o1 = new TaskBuilder().withName("this goes first").withStartDate("04-03-1989 23:59")
                .withEndDate("08-03-1989 23:59").build();
        TestTask o2 = new TaskBuilder().withName("this goes second").withStartDate("04-03-1989 23:59").withEndDate("")
                .build();
        assertTrue(tc.compare(o1, o2) == -1);
    }

    @Test
    public void different_EndDates() throws IllegalValueException {
        TestTask o1 = new TaskBuilder().withName("A this goes first").withStartDate("04-03-1989 23:59")
                .withEndDate("08-03-1989 23:59").build();
        TestTask o2 = new TaskBuilder().withName("B this goes second").withStartDate("04-03-1989 23:59")
                .withEndDate("08-03-2017 23:59").build();
        assertTrue(Date.isBefore(o1.getEndDate(), o2.getEndDate()));
        
        TestTask o3 = new TaskBuilder().withName("A this goes first").withStartDate("04-03-1989 23:59")
                .withEndDate("08-03-2017 23:59").build();
        TestTask o4 = new TaskBuilder().withName("B this goes second").withStartDate("04-03-1989 23:59")
                .withEndDate("08-03-1989 23:59").build();
        assertFalse(Date.isBefore(o3.getEndDate(), o4.getEndDate()));
    }

}
