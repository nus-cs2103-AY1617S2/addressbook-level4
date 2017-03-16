package savvytodo.testutil;

import savvytodo.commons.exceptions.IllegalValueException;
import savvytodo.model.TaskManager;
import savvytodo.model.task.Recurrence;
import savvytodo.model.task.Task;
import savvytodo.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask assignment, appointment, birthday, meeting, test, presentation, project, discussion, interview;

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
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addCategory(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{assignment, appointment, birthday, meeting, test, presentation, project};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
