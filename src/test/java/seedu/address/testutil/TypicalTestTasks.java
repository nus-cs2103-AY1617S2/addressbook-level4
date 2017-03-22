package seedu.address.testutil;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask task1, task2, task3, task4, task5, task6, task7, task8, task9;

    public TypicalTestTasks() {
        try {
            task1 = new TaskBuilder().withTitle("Complete task 1").withStartTime("10-10-2017 0100")
                    .withDeadline("11-11-2017 2300").withLabels("friends")
                    .withStatus(false).build();
            task2 = new TaskBuilder().withTitle("Complete task 2").withStartTime("10-10-2017 0100")
                    .withDeadline("11-11-2017 2300").withLabels("owesMoney", "friends")
                    .withStatus(false).build();
            task3 = new TaskBuilder().withTitle("Complete task 3").withStartTime("10-10-2017 0100")
                    .withDeadline("11-11-2017 2300").withStatus(false).build();
            task4 = new TaskBuilder().withTitle("Complete task 4").withStartTime("10-10-2017 0100")
                    .withDeadline("11-11-2017 2300").withStatus(false).build();
            task5 = new TaskBuilder().withTitle("Complete task 5").withStartTime("10-10-2017 0100")
                    .withDeadline("11-11-2017 2300").withStatus(false).build();
            task6 = new TaskBuilder().withTitle("Complete task 6").withStartTime("10-10-2017 0100")
                    .withDeadline("10-11-2017 2300").withStatus(false).build();
            task7 = new TaskBuilder().withTitle("Complete task 7").withStartTime("10-10-2017 0100")
                    .withDeadline("11-11-2017 2300").withStatus(false).build();

            // Manually added
            task8 = new TaskBuilder().withTitle("Complete task 8").withStartTime("10-10-2017 0100")
                    .withDeadline("11-11-2017 2300").withStatus(false).build();
            task9 = new TaskBuilder().withTitle("Complete task 9").withStartTime("10-10-2017 0100")
                    .withDeadline("11-11-2017 2300").withStatus(false).build();
        } catch (IllegalValueException | IllegalDateTimeValueException e) {
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
        TestTask[] tasks = new TestTask[]{task1, task2, task3, task4, task5, task6, task7};
        Arrays.sort(tasks);
        return tasks;
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
