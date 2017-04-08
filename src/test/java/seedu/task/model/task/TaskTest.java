package seedu.task.model.task;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.task.model.tag.UniqueTagList;

public class TaskTest {

    TaskName name = null;
    TaskDate date = null;
    TaskTime start = null;
    TaskTime end = null;
    String descrip = null;
    TaskStatus status = null;
    UniqueTagList tags = null;
    Task task = null;

    public void setUp() {
        try {
            name = new TaskName("task name");
            date = new TaskDate("030317-040417");
            start = new TaskTime("0900");
            end = new TaskTime("1100");
            descrip = "description";
            status = new TaskStatus("Ongoing");
            tags = new UniqueTagList();
            task = new Task(name, date, start, end, descrip, status, tags);
        } catch (Exception e) {
            System.out.println("Construct Sample Task Fail");
        }
    }

    @Test
    public void TaskEquals() {
        this.setUp();
        Task another = new Task(name, date, start, end, descrip, status, tags);
        assertEquals(this.task, another);
    }

}
