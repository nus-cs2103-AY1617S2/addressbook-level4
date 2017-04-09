package seedu.tache.testutil;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.TaskManager;
import seedu.tache.model.recurstate.RecurState.RecurInterval;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask eggsAndBread;
    public TestTask readBook;
    public TestTask visitFriend;
    public TestTask payDavid;
    public TestTask getFit;
    public TestTask findGirlfriend;
    public TestTask visitSarah;
    public TestTask startDateOnly;
    public TestTask recurringMacdonalds;
    public TestTask recurringKFC;

    public TypicalTestTasks() {
        try {
            eggsAndBread = new TaskBuilder().withName("Buy Eggs and Bread")
                    .withEndDateTime("04-01-17 19:55:12")
                    .withTags("HighPriority").build();
            readBook = new TaskBuilder().withName("Read Book about Software Engineering")
                    .withEndDateTime("23:59:59 two weeks later")
                    .withTags("LowPriority").build();
            visitFriend = new TaskBuilder().withName("Visit Friend")
                    .withStartDateTime("16:00:00 next week")
                    .withEndDateTime("19:00:00 two weeks later").build();
            payDavid = new TaskBuilder().withName("Pay David 20 for cab").build();
            visitSarah = new TaskBuilder().withName("Visit Sarah")
                                          .withTags("MediumPriority").build();

            // Manually added
            getFit = new TaskBuilder().withName("Get fit").build();
            findGirlfriend = new TaskBuilder().withName("Find a girlfriend").build();
            startDateOnly = new TaskBuilder().withName("Task with only start date")
                    .withStartDateTime("04-01-17 19:55:12").build();

            //Recurring tasks
            recurringMacdonalds = new TaskBuilder().withName("Macdonald Breakfast")
                    .withStartDateTime("16:00:00 next week")
                    .withEndDateTime("19:00:00 two weeks later")
                    .withRecurringInterval(RecurInterval.WEEK).build();
            recurringKFC = new TaskBuilder().withName("KFC Dinner")
                    .withStartDateTime("16:00:00 next week")
                    .withEndDateTime("19:00:00 10 months later")
                    .withRecurringInterval(RecurInterval.MONTH).build();
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
        return new TestTask[]{payDavid, visitSarah, eggsAndBread, visitFriend, readBook};
    }

    public TestTask[] getTypicalRecurringTasks() {
        return new TestTask[]{recurringMacdonalds, recurringKFC};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
