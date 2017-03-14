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
                                    new Priority("high"), new Status(), new Date(), new UniqueTagList());
        }
    }
}
