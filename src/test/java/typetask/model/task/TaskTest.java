package typetask.model.task;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import typetask.commons.exceptions.IllegalValueException;
import typetask.testutil.TaskBuilder;
import typetask.testutil.TestTask;

//@@author A0139926R
public class TaskTest {


    Task sampleTaskForTest;
    private void createSampleTask() throws IllegalValueException {
        sampleTaskForTest = new Task(new Name("test"), new DueDate(""), new DueDate(""), false,
                new Priority("Low"));
    }
    @Test
    public void createTask_success() throws IllegalValueException {
        Name taskName = new Name("Buy bread for breakfast");
        DueDate deadline = new DueDate("Mon Apr 10 2017 16:00:00");
        DueDate endDate = new DueDate("");
        Priority priority = new Priority("Low");
        Task testTask = new Task(taskName, deadline, endDate, false, priority);
        TestTask expectedResult =
                new TaskBuilder().withName("Buy bread for breakfast")
                .withDate("Mon Apr 10 2017 16:00:00")
                .withEndDate("").withCompleted(false).withPriority("Low").build();
        assertEquals(testTask, expectedResult);
    }
    @Test(expected = AssertionError.class)
    public void createTask_withNullForName_fail() throws IllegalValueException {
        DueDate emptyDate = new DueDate("");
        Task testTask = new Task(null, emptyDate, emptyDate, false, new Priority("Low"));
    }
    @Test(expected = AssertionError.class)
    public void setTaskName_withNull_fail() throws IllegalValueException {
        createSampleTask();
        sampleTaskForTest.setName(null);
    }
    @Test
    public void setTaskName_success() throws IllegalValueException {
        createSampleTask();
        sampleTaskForTest.setName(new Name("edited"));
        Task expectedTask = new Task(new Name("edited"), new DueDate(""),
                new DueDate(""), false, new Priority("Low"));
        assertEquals(sampleTaskForTest, expectedTask);
    }
    @Test
    public void setStartDate_success() throws IllegalValueException {
        createSampleTask();
        Task expectedTask = new Task(new Name("test"), new DueDate("Mon Apr 10 2017 16:00:00"),
                new DueDate(""), false, new Priority("Low"));
        sampleTaskForTest.setDate(new DueDate("Mon Apr 10 2017 16:00:00"));
        assertEquals(sampleTaskForTest, expectedTask);
    }
    @Test
    public void setEndDate_success() throws IllegalValueException {
        createSampleTask();
        Task expectedTask = new Task(new Name("test"), new DueDate(""),
                new DueDate("Mon Apr 10 2017 16:00:00"), false, new Priority("Low"));
        sampleTaskForTest.setEndDate(new DueDate("Mon Apr 10 2017 16:00:00"));
        assertEquals(sampleTaskForTest, expectedTask);
    }
    @Test
    public void setIsCompleted_success() throws IllegalValueException {
        createSampleTask();
        Task expectedTask = new Task(new Name("test"), new DueDate(""),
                new DueDate(""), true, new Priority("Low"));
        sampleTaskForTest.setIsCompleted(true);
        assertEquals(sampleTaskForTest, expectedTask);
    }
    @Test
    public void setPriority_success() throws IllegalValueException {
        createSampleTask();
        Task expectedTask = new Task(new Name("test"), new DueDate(""),
                new DueDate(""), true, new Priority("High"));
        sampleTaskForTest.setPriority(new Priority("High"));
        assertEquals(sampleTaskForTest, expectedTask);
    }
    @Test
    public void getName_success() throws IllegalValueException {
        createSampleTask();
        String expectedName = "test";
        String testName = sampleTaskForTest.getName().toString();
        assertEquals(testName, expectedName);
    }
    @Test
    public void getDate_success() throws IllegalValueException {
        createSampleTask();
        String expectedDate = "";
        String testDate = sampleTaskForTest.getDate().toString();
        assertEquals(testDate, expectedDate);
    }
    @Test
    public void getEndDate_success() throws IllegalValueException {
        createSampleTask();
        String expectedEndDate = "";
        String testEndDate = sampleTaskForTest.getEndDate().toString();
        assertEquals(testEndDate, expectedEndDate);
    }
    @Test
    public void getIsCompleted_success() throws IllegalValueException {
        createSampleTask();
        boolean expectedIsCompleted = false;
        boolean testIsCompleted = sampleTaskForTest.getIsCompleted();
        assertEquals(testIsCompleted, expectedIsCompleted);
    }
    @Test
    public void getPriority_success() throws IllegalValueException {
        createSampleTask();
        String expectedpriority = "Low";
        String testPriority = sampleTaskForTest.getPriority().toString();
        assertEquals(testPriority, expectedpriority);
    }
}
