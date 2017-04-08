//@@author A0144422R
package seedu.today.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.time.DateUtils;

import seedu.today.commons.exceptions.IllegalValueException;
import seedu.today.model.tag.UniqueTagList;
import seedu.today.model.task.DeadlineTask;
import seedu.today.model.task.EventTask;
import seedu.today.model.task.FloatingTask;
import seedu.today.model.task.Name;
import seedu.today.model.task.Task;

public class TaskTest {
    @Test
    public void floatingTaskTest() throws Exception {
        try {
            Task task = new FloatingTask(new Name("a floating task"), new UniqueTagList("tag1", "tag2"), false, false);
            assertTrue(task.getName().toString().equals("a floating task"));
            assertTrue(task.getDeadline().isPresent() == false);
            assertTrue(task.getStartingTime().isPresent() == false);
            assertFalse(task.isToday());
            assertTrue(task.getTags().equalsOrderInsensitive(new UniqueTagList("tag1", "tag2")));
            assertTrue(!task.isDone());
            assertTrue(!task.isManualToday());
            task.setDone(true);
            assertTrue(task.isDone());
            task.setToday(true);
            assertTrue(task.isManualToday());
            assertTrue(task.getTaskAbsoluteDateTime().equals(""));
        } catch (IllegalValueException e) {
            fail();
        }
    }

    @Test
    public void deadlineTaskTest() throws Exception {
        try {
            Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            Task task = new DeadlineTask(new Name("a deadline task"), new UniqueTagList("tag1", "tag2"), date, false,
                    false);
            assertTrue(task.getName().toString().equals("a deadline task"));
            assertTrue(task.getDeadline().isPresent() == true);
            assertTrue(task.getDeadline().get().getDate().equals(date));
            assertTrue(task.getStartingTime().isPresent() == false);
            assertTrue(task.isToday());
            assertTrue(((DeadlineTask) task).isOverdue());
            assertTrue(task.getTags().equalsOrderInsensitive(new UniqueTagList("tag1", "tag2")));
            assertTrue(!task.isDone());
            assertTrue(!task.isManualToday());
            task.setDone(true);
            assertTrue(task.isDone());
            task.setToday(true);
            assertTrue(task.isManualToday());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            assertTrue(task.getTaskAbsoluteDateTime().equals("Due: " + dateFormat.format(date)));
        } catch (IllegalValueException e) {
            fail();
        }
    }

    @Test
    public void eventTaskTest() throws Exception {
        try {
            Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            Date date2 = DateUtils.addDays(date, 1);
            Task task = new EventTask(new Name("a deadline task"), new UniqueTagList("tag1", "tag2"), date2, date,
                    false, false);
            assertTrue(task.getName().toString().equals("a deadline task"));
            assertTrue(task.getDeadline().isPresent() == true);
            assertTrue(task.getDeadline().get().getDate().equals(date2));
            assertTrue(task.getStartingTime().isPresent() == true);
            assertTrue(task.getStartingTime().get().getDate().equals(date));
            assertTrue(task.isToday());
            assertFalse(((EventTask) task).isOverdue());
            assertTrue(task.getTags().equalsOrderInsensitive(new UniqueTagList("tag1", "tag2")));
            assertTrue(!task.isDone());
            assertTrue(!task.isManualToday());
            task.setDone(true);
            assertTrue(task.isDone());
            task.setToday(true);
            assertTrue(task.isManualToday());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            assertTrue(task.getTaskAbsoluteDateTime()
                    .equals("Begin: " + dateFormat.format(date) + "\n   End: " + dateFormat.format(date2)));
        } catch (IllegalValueException e) {
            fail();
        }
    }

    @Test
    public void eventTaskWrongDateTimeTest() throws Exception {
        try {
            Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            Date date2 = DateUtils.addDays(date, 1);
            Task task = new EventTask(new Name("a deadline task"), new UniqueTagList("tag1", "tag2"), date, date2,
                    false, false);
            fail();
        } catch (IllegalValueException e) {
            assertTrue(e.getMessage().equals("Deadline should be after starting time."));
        }
    }
}
