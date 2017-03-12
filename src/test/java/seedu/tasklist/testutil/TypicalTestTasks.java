package seedu.tasklist.testutil;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask tutorial, homework, groceries, lifegoals, java, CS2103T, drink, hoon, ida;

    public TypicalTestTasks() {
        try {
            tutorial = new TaskBuilder().withName("CS2103T tutorial")
                    .withComment("prepare V0.2 presentation")
                    .withTags("class").build();
            homework = new TaskBuilder().withName("CS3245 homework 3").withComment("discuss with classmates")
                    .withTags("class").build();
            groceries = new TaskBuilder().withName("Buy groceries").withComment("go NTUC").build();
            lifegoals = new TaskBuilder().withName("Find ways to not feel left out").withComment("#IFeelLeftOut").build();
            java = new TaskBuilder().withName("Update Java for CS2103T").withComment("Find out why jdk is not displaying the correct ver").build();
            CS2103T = new TaskBuilder().withName("Implement undo for this").withComment("By today").build();
            drink = new TaskBuilder().withName("Drink water").withComment("To improve brain function").build();

            // Manually added
            hoon = new TaskBuilder().withName("Internship interview").withComment("at mediacorp").build();
            ida = new TaskBuilder().withName("Yet another interview").withComment("also at mediacorp").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{tutorial, homework, groceries, lifegoals, java, CS2103T, drink};
    }

    public TaskList getTypicalTaskList() {
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}
