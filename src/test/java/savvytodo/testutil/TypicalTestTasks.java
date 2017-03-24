package savvytodo.testutil;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.TaskManager;
import savvytodo.model.category.UniqueCategoryList;
import savvytodo.model.task.DateTime;
import savvytodo.model.task.Description;
import savvytodo.model.task.Location;
import savvytodo.model.task.Name;
import savvytodo.model.task.Priority;
import savvytodo.model.task.Recurrence;
import savvytodo.model.task.Status;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask assignment, appointment, birthday, meeting, test, presentation, project, discussion, interview, job;

    public TypicalTestTasks() {
        try {
            assignment = new TaskBuilder().withName("Assignment 1").withLocation("None").withDescription("Start early")
                    .withPriority("high").withCategories("friends").withDateTime("01/03/2017 1400", "02/03/2017 1400")
                    .withRecurrence(Recurrence.DEFAULT_VALUES).withStatus(false).build();
            appointment = new TaskBuilder().withName("dental").withLocation("KTPH").withDescription("Wisdom tooth")
                    .withPriority("high").withCategories("owesMoney", "friends")
                    .withDateTime("02/03/2017 1400", "03/03/2017 1400").withRecurrence(Recurrence.DEFAULT_VALUES)
                    .withStatus(false).build();
            birthday = new TaskBuilder().withName("My birthday").withPriority("medium")
                    .withDescription("Celebration @ 1pm").withLocation("wall street")
                    .withDateTime("03/03/2017 1400", "04/03/2017 1400").withRecurrence(Recurrence.DEFAULT_VALUES)
                    .withStatus(false).build();
            meeting = new TaskBuilder().withName("CS2103 Project Meeting").withPriority("medium")
                    .withDescription("cornelia@google.com").withLocation("10th street")
                    .withDateTime("04/03/2017 1400", "05/03/2017 1400").withRecurrence(Recurrence.DEFAULT_VALUES)
                    .withStatus(false).build();
            test = new TaskBuilder().withName("CS2103 midterm test").withPriority("high")
                    .withDescription("2pm Mon 23 Aug").withLocation("NUS MPSH 4")
                    .withDateTime("05/03/2017 1400", "06/03/2017 1400").withRecurrence(Recurrence.DEFAULT_VALUES)
                    .withStatus(false).build();
            presentation = new TaskBuilder().withName("Informal Presentation").withPriority("low")
                    .withDescription("3pm").withLocation("NUS HALL").withDateTime("06/03/2017 1400", "07/03/2017 1400")
                    .withRecurrence(Recurrence.DEFAULT_VALUES).withStatus(false).build();
            project = new TaskBuilder().withName("Project Milestone 2").withPriority("high")
                    .withDescription("anna@google.com").withLocation("4th street")
                    .withDateTime("07/03/2017 1400", "08/03/2017 1400").withRecurrence(Recurrence.DEFAULT_VALUES)
                    .withStatus(false).build();

            // Manually added
            discussion = new TaskBuilder().withName("CS2103 Project Discussion").withPriority("low")
                    .withDescription("stefan@mail.com").withLocation("NUS MALL")
                    .withDateTime("08/03/2017 1400", "09/03/2017 1400").withRecurrence(Recurrence.DEFAULT_VALUES)
                    .withStatus(false).build();
            interview = new TaskBuilder().withName("Google Interview").withPriority("medium")
                    .withDescription("Prepare for interview questions").withLocation("Google SG")
                    .withDateTime("09/03/2017 1400", "10/03/2017 1400").withRecurrence(Recurrence.DEFAULT_VALUES)
                    .withStatus(false).build();
            job = new TaskBuilder().withName("Potential Job Calling").withPriority("medium")
                    .withDescription("Call interview").withLocation("Marina Bay")
                    .withDateTime("10/03/2017 1000", "10/03/2017 1600").withRecurrence(Recurrence.DEFAULT_VALUES)
                    .withStatus(false).build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { assignment, appointment, birthday, meeting, test, presentation, project };
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }

    /**
     * generates a list of tasks with random details
     * @author A0140036x
     * @return
     */
    public TestTask[] getGeneratedTasks(int numberOfTasks) {
        TestTask[] ret = new TestTask[numberOfTasks];
        for (int i = 0; i < ret.length; i++) {
            TestTask temp = new TestTask();
            try {
                temp.setName(new Name("Task " + i));
                temp.setDescription(new Description("" + i));
                temp.setLocation(new Location("" + i));
                temp.setPriority(new Priority(i % 2 == 0 ? "low" : "high"));
                temp.setCategories(new UniqueCategoryList());
                temp.setDateTime(new DateTime());
                temp.setRecurrence(new Recurrence());
                temp.setCompleted(new Status(i % 2 == 0 ? true : false));
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
            ret[i] = temp;
        }
        return ret;
    }
}
