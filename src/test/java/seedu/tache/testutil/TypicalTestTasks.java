package seedu.tache.testutil;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.TaskManager;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask eggsAndBread;
    public TestTask readBook;
    public TestTask visitGrandma;
    public TestTask payDavid;
    public TestTask getFit;
    public TestTask findGirlfriend;
    public TestTask visitSarah;

    public TypicalTestTasks() {
        try {
            eggsAndBread = new TaskBuilder().withName("Buy Eggs and Bread")
                    .withEndDateTime("04-01-17 19:55:12")
                    .withTags("HighPriority").build();
            readBook = new TaskBuilder().withName("Read Book about Software Engineering")
                    .withEndDateTime("04-21-17 23:59:59")
                    .withTags("LowPriority").build();
            visitGrandma = new TaskBuilder().withName("Visit Grandma")
                    .withStartDateTime("04-15-17 16:00:00")
                    .withEndDateTime("04-21-17 19:00:00").build();
            payDavid = new TaskBuilder().withName("Pay David 20 for cab").build();
            visitSarah = new TaskBuilder().withName("Visit Sarah")
                                          .withTags("MediumPriority").build();

            // Manually added
            getFit = new TaskBuilder().withName("Get fit").build();
            findGirlfriend = new TaskBuilder().withName("Find a girlfriend").build();
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
        return new TestTask[]{eggsAndBread, readBook, visitGrandma, payDavid, visitSarah};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
