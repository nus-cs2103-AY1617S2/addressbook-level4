package seedu.doist.testutil;

import java.util.Date;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.model.TodoList;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask laundry, homework, chores, work, school, groceries, shopping, email, exercise, meeting;

    public TypicalTestTasks() {
        try {
            laundry = new TaskBuilder().withName("Do laundry").build();
            homework = new TaskBuilder().withName("Complete math homework").withTags("school", "math").build();
            work = new TaskBuilder().withName("Schedule meeting with boss").withPriority("IMPORTANT").build();
            school = new TaskBuilder().withName("Submit chemistry assignment").build();
            groceries = new TaskBuilder().withName("Pick up milk").build();
            shopping = new TaskBuilder().withName("Buy new clock").build();

            // Manually added
            email = new TaskBuilder().withName("Send emails to client").build();
            exercise = new TaskBuilder().withName("Go for a run").build();
            chores = new TaskBuilder().withName("Clean up house").withPriority("VERY IMPORTANT").build();
            meeting = new TaskBuilder().withName("Meet coworkers").withDates(new Date(), new Date()).build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadDoistWithSampleData(TodoList ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public static void loadDoistWithSampleDataAllFinished(TodoList ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                Task newTask = new Task(task);
                newTask.setFinishedStatus(true);
                ab.addTask(newTask);
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{laundry, homework, work, school, groceries, shopping};
    }

    public TestTask[] getAllFinishedTypicalTasks() {
        TestTask[] testTasks = new TestTask[]{laundry, homework, work, school, groceries, shopping};
        for (TestTask task : testTasks) {
            task.setFinishedStatus(true);
        }
        return testTasks;
    }

    public TodoList getTypicalAddressBook() {
        TodoList ab = new TodoList();
        loadDoistWithSampleData(ab);
        return ab;
    }
}
