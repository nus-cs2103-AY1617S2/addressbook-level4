package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask something, nothing, somethingMore, watchWebcasts, levelUp, exam, graduate, tutorials, reading;

    public TypicalTestTasks() {
        try {
            something = new TaskBuilder().withName("do something")
                    .withAddress(false).withEmail("060317 0000")
                    .withPhone("060317 0000")
                    .withTags("something").build();
            nothing = new TaskBuilder().withName("do nothing").withAddress(false)
                    .withEmail("060317 0000").withPhone("060317 0000")
                    .withTags("nothing", "boliao").build();
            somethingMore = new TaskBuilder().withName("something more").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress(false).withTags("test").build();
            watchWebcasts = new TaskBuilder().withName("Watch webcasts").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress(false).withTags("study").build();
            levelUp = new TaskBuilder().withName("Level up").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress(false).withTags("test").build();
            exam = new TaskBuilder().withName("exam").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress(false).withTags("test").build();
            graduate = new TaskBuilder().withName("Graduate").withPhone("060317 0000")
                    .withEmail("290719 0000").withAddress(false).withTags("test").build();

            // Manually added
            tutorials = new TaskBuilder().withName("do tutorials").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress(false).withTags("test").build();
            reading = new TaskBuilder().withName("Read readings").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress(false).withTags("test").build();
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
        return new TestTask[]{something, nothing, somethingMore, watchWebcasts, levelUp, exam, graduate};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
