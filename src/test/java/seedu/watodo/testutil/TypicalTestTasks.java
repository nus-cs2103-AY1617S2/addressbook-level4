package seedu.watodo.testutil;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.TaskManager;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask eat, study, shop, code, play, sleep;

    public TypicalTestTasks() {
        try {
            eat = new TaskBuilder().withDescription("Time to eat").withTags("tag").build(); //TODO continue
            study = new TaskBuilder().withDescription("Study mug slog").withTags("tag").build();
            shop = new TaskBuilder().withDescription("no money spend money").withTags("tag").build();
            code = new TaskBuilder().withDescription("coding everyday every minute every sec").withTags("tag").build();
            play = new TaskBuilder().withDescription("what is play?").withTags("tag").build();
            sleep = new TaskBuilder().withDescription("sleep?").withTags("tag").build();
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
        return new TestTask[]{eat, study, sleep};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
