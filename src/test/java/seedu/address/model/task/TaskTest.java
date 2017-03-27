//@@author: A0144422R
package seedu.address.model.task;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.time.DateUtils;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;

public class TaskTest {
    @Test
    public void FloatingTaskTest() throws Exception {
        try {
            Task task = new FloatingTask(new Name("a floating task"),
                    new UniqueTagList("tag1", "tag2"), false, false);
            assertTrue(task.getName().fullName.equals("a floating task"));
            assertTrue(task.getDeadline().isPresent() == false);
            assertTrue(task.getStartingTime().isPresent() == false);
            assertTrue(task.getTags()
                    .equalsOrderInsensitive(new UniqueTagList("tag1", "tag2")));
            assertTrue(!task.isDone());
            assertTrue(!task.isManualToday());
            task.setDone(true);
            assertTrue(task.isDone());
            task.setToday();
            assertTrue(task.isManualToday());
        } catch (IllegalValueException e) {
            fail();
        }
    }

    @Test
    public void DeadlineTaskTest() throws Exception {
        try {
            Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            Task task = new DeadlineTask(new Name("a deadline task"),
                    new UniqueTagList("tag1", "tag2"), date, false, false);
            assertTrue(task.getName().fullName.equals("a deadline task"));
            assertTrue(task.getDeadline().isPresent() == true);
            assertTrue(task.getDeadline().get().getDate().equals(date));
            assertTrue(task.getStartingTime().isPresent() == false);
            assertTrue(task.getTags()
                    .equalsOrderInsensitive(new UniqueTagList("tag1", "tag2")));
            assertTrue(!task.isDone());
            assertTrue(!task.isManualToday());
            task.setDone(true);
            assertTrue(task.isDone());
            task.setToday();
            assertTrue(task.isManualToday());
        } catch (IllegalValueException e) {
            fail();
        }
    }

    @Test
    public void EventTaskTest() throws Exception {
        try {
            Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            Date date2 = DateUtils.addDays(date, 1);
            Task task = new EventTask(new Name("a deadline task"),
                    new UniqueTagList("tag1", "tag2"), date2, date, false,
                    false);
            assertTrue(task.getName().fullName.equals("a deadline task"));
            assertTrue(task.getDeadline().isPresent() == true);
            assertTrue(task.getDeadline().get().getDate().equals(date2));
            assertTrue(task.getStartingTime().isPresent() == true);
            assertTrue(task.getStartingTime().get().getDate().equals(date));
            assertTrue(task.getTags()
                    .equalsOrderInsensitive(new UniqueTagList("tag1", "tag2")));
            assertTrue(!task.isDone());
            assertTrue(!task.isManualToday());
            task.setDone(true);
            assertTrue(task.isDone());
            task.setToday();
            assertTrue(task.isManualToday());
        } catch (IllegalValueException e) {
            fail();
        }
    }
}
