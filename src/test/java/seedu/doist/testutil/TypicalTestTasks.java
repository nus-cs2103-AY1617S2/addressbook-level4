package seedu.doist.testutil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import guitests.DoistGUITest;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.model.TodoList;
import seedu.doist.model.task.FinishedStatus;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask laundry, homework, chores, work, school, groceries, shopping, email, exercise, meeting, movie;
    private static final Logger logger = LogsCenter.getLogger(TypicalTestTasks.class);

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

            String dateStringFrom = "Jan 1 20:29:30 2017";
            String dateStringTo = "Jan 2 21:00:30 2017";
            // The default medium/short DateFormat
            DateFormat format = new SimpleDateFormat("MMM dd kk:mm:ss yyyy");
            movie = new TaskBuilder().withName("Watch movie").withDates(stringToDate(format, dateStringFrom),
                    stringToDate(format, dateStringTo)).build();
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
                assert false : "TypicalTestTasks: Sample data has duplicates";
            }
        }
    }

    //@@author A0140887W
    public static void loadDoistWithSampleDataAllFinished(TodoList ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                Task newTask = new Task(task);
                newTask.setFinishedStatus(new FinishedStatus(true));
                ab.addTask(newTask);
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        TestTask[] tasks = new TestTask[]{laundry, homework, work, school, groceries, shopping};
        DoistGUITest.sortTasksByDefault(tasks);
        return tasks;
    }

    public TestTask[] getAllFinishedTypicalTasks() {
        TestTask[] testTasks = new TestTask[]{laundry, homework, work, school, groceries, shopping};
        for (TestTask task : testTasks) {
            task.setFinishedStatus(true);
        }
        DoistGUITest.sortTasksByDefault(testTasks);
        return testTasks;
    }

    //@@author
    public TodoList getTypicalTodoList() {
        TodoList ab = new TodoList();
        loadDoistWithSampleData(ab);
        return ab;
    }

    /**
     * Parse the string to Date
     * @param dateString
     * @return Date
     * @throws IllegalValueException
     */
    public Date stringToDate(DateFormat format, String dateString) throws IllegalValueException {
        try {
            Date date = format.parse(dateString);
            return date;
        } catch (ParseException pe) {
            logger.warning("TypicalTestTasks: Could not parse date in string \"" +
                dateString + "\"");
            throw new IllegalValueException(dateString + " is not a valid date");
        }
    }
}
