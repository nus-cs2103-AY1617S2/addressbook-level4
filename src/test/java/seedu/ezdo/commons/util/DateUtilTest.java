package seedu.ezdo.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ezdo.testutil.TaskBuilder;
import seedu.ezdo.testutil.TestTask;

public class DateUtilTest {
    @Test
    public void noStartDate_pass() throws Exception {
        TestTask task = new TaskBuilder().withName("Alson").withPriority("3").withStartDate("")
                .withDueDate("10/10/2019")
                .withTags("guy").build();
        assertTrue(DateUtil.isTaskDateValid(task));
    }

    @Test
    public void noDueDate_pass() throws Exception {
        TestTask task = new TaskBuilder().withName("Alson").withPriority("3").withStartDate("02/02/2017")
                .withDueDate("")
                .withTags("guy").build();
        assertTrue(DateUtil.isTaskDateValid(task));
    }

    @Test
    public void twoDates_equal_pass() throws Exception {
        TestTask task = new TaskBuilder().withName("Alson").withPriority("3")
                .withStartDate("02/02/2017").withDueDate("02/02/2017")
                .withTags("guy").build();
        assertTrue(DateUtil.isTaskDateValid(task));
    }

    @Test
    public void startAfterDue_fail() throws Exception {
        TestTask task = new TaskBuilder().withName("Alson").withPriority("3")
                .withStartDate("02/02/2020").withDueDate("10/10/2019")
                .withTags("guy").build();
        assertFalse(DateUtil.isTaskDateValid(task));
    }
}
