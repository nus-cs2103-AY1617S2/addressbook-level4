package seedu.tasklist.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import seedu.tasklist.commons.exceptions.IllegalValueException;

import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.Comment;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.Name;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.Status;
import seedu.tasklist.model.task.Task;


public class TaskTest {
    private Task task;
    private TaskTestHelper helper;

    @Before
    public void initialize() {
        helper = new TaskTestHelper();
    }

    @Test
    public void create_FloatingTask_returnsTrue() throws Exception {
        task = helper.createFloatingTask();
        assertEquals(task.getType(), FloatingTask.TYPE);
    }

    @Test
    public void create_EventTask_returnsTrue() throws Exception {
        task = helper.createEventTask();
        assertEquals(task.getType(), EventTask.TYPE);
    }

    @Test
    public void create_DeadlineTask_returnsTrue() throws Exception {
        task = helper.createDeadlineTask();
        assertEquals(task.getType(), DeadlineTask.TYPE);
    }

    @Test
    public void setName_FloatingTask_successful() throws Exception {
        task = helper.createFloatingTask();
        task.setName(new Name("testSetName"));
        assertEquals(task.getName().toString(), "testSetName");

    }

    @Test
    public void setName_DeadlineTask_successful() throws Exception {
        task = helper.createDeadlineTask();
        task.setName(new Name("testDeadlineSetName"));
        assertEquals(task.getName().toString(), "testDeadlineSetName");

    }

    @Test
    public void setName_EventTask_successful() throws Exception {
        task = helper.createEventTask();
        task.setName(new Name("testEventSetName"));
        assertEquals(task.getName().toString(), "testEventSetName");

    }

    @Test
    public void setPriority_FloatingTask_successful() throws Exception {
        task = helper.createFloatingTask();
        task.setPriority(new Priority("high"));
        assertEquals(task.getPriority().toString(), "high");

    }

    @Test
    public void setPriority_EventTask_successful() throws Exception {
        task = helper.createEventTask();
        task.setPriority(new Priority("high"));
        assertEquals(task.getPriority().toString(), "high");

    }

    @Test
    public void setPriority_DeadlineTask_successful() throws Exception {
        task = helper.createDeadlineTask();
        task.setPriority(new Priority("high"));
        assertEquals(task.getPriority().toString(), "high");

    }

    @Test
    public void setStatus_FloatingTask_successful() throws Exception {
        task = helper.createFloatingTask();
        task.setStatus(new Status(true));
        assertEquals(task.getStatus().toString(), "completed");

    }

    @Test
    public void setStatus_DeadlineTask_successful() throws Exception {
        task = helper.createDeadlineTask();
        task.setStatus(new Status(true));
        assertEquals(task.getStatus().toString(), "completed");

    }

    @Test
    public void setStatus_EventTask_successful() throws Exception {
        task = helper.createEventTask();
        task.setStatus(new Status(true));
        assertEquals(task.getStatus().toString(), "completed");

    }

    @Test
    public void setComment_FloatingTask_successful() throws Exception {
        task = helper.createFloatingTask();
        task.setComment(new Comment("testCommentFT"));
        assertEquals(task.getComment().toString(), "testCommentFT");

    }

    @Test
    public void setComment_DeadlineTask_successful() throws Exception {
        task = helper.createDeadlineTask();
        task.setComment(new Comment("testCommentDT"));
        assertEquals(task.getComment().toString(), "testCommentDT");

    }

    @Test
    public void setComment_EventTask_successful() throws Exception {
        task = helper.createEventTask();
        task.setComment(new Comment("testCommentET"));
        assertEquals(task.getComment().toString(), "testCommentET");

    }

    class TaskTestHelper {

        /**
         * Creates floating task
         */
        public Task createFloatingTask() throws IllegalValueException {
            return new FloatingTask(new Name("testFloatingTask1"), new Comment("test"),
                                     new Priority("medium"), new Status(), new UniqueTagList());
        }

        /**
         * Creates an event task
         */
        public Task createEventTask() throws IllegalValueException {
            return new EventTask(new Name("testEventTask1"), new Comment("test"),
                                    new Priority("low"), new Status(), new Date(), new Date(), new UniqueTagList());
        }

        /**
         * Creates a deadline task
         */
        public Task createDeadlineTask() throws IllegalValueException {
            return new DeadlineTask(new Name("testDeadlineTask1"), new Comment("test"),
                                    new Priority("low"), new Status(), new Date(), new UniqueTagList());
        }
    }
}
