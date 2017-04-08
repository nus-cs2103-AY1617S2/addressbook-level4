package seedu.watodo.testutil;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.TaskManager;
import seedu.watodo.model.task.Task;
import seedu.watodo.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask code, study, eat, sleep, shop, play;

    public TypicalTestTasks() {
        try {
            code = new TaskBuilder().withDescription("coding everyday every minute every sec")
                    .withStartDate("now").withEndDate("next mon 11.59am").withTags("CS").build();
            study = new TaskBuilder().withDescription("Study mug slog").build();
            eat = new TaskBuilder().withDescription("Time to eat").withEndDate("tmr 1pm").withTags("yumm").build();
            sleep = new TaskBuilder().withDescription("sleep?").withEndDate("fri").build();
            shop = new TaskBuilder().withDescription("no money spend money").withTags("tag").build();
            play = new TaskBuilder().withDescription("what is play?").withTags("tag").build();

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
        return new TestTask[]{eat, sleep, play};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
