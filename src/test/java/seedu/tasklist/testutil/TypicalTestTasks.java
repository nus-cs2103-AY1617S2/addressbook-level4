package seedu.tasklist.testutil;

import java.util.Date;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask tutorial, homework, groceries, java, CS2103T, drink, internship, ida;

    public TypicalTestTasks() {
        try {
            tutorial = new EventTaskBuilder().withName("CS2103T tutorial")
                    .withComment("prepare V0.2 presentation")
                    .withTags("class").withStartDate(new Date(2017, 5, 5, 15, 0).toString())
                    .withEndDate(new Date(2017, 5, 5, 17, 0).toString()).build();
            homework = new FloatingTaskBuilder().withName("CS3245 homework 3").withComment("discuss with classmates")
                    .withTags("class").build();
            groceries = new FloatingTaskBuilder().withName("Buy groceries").withComment("go NTUC").build();
            java = new FloatingTaskBuilder().withName("Update Java for CS2103T")
                    .withComment("Find out why jdk is not displaying the correct ver").build();
            CS2103T = new DeadlineTaskBuilder().withName("Implement undo for this").withComment("By today")
                    .withDeadline(new Date().toString()).build();
            drink = new FloatingTaskBuilder().withName("Drink water").withComment("To improve brain function").build();

            // Manually added
            internship = new FloatingTaskBuilder().withName("Internship interview").withComment("at mediacorp").build();
            ida = new FloatingTaskBuilder().withName("Yet another interview").withComment("also at mediacorp").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                Task currentTask;
                String type = task.getType();
                switch (type) {
                case FloatingTask.TYPE:
                    currentTask = new FloatingTask((TestFloatingTask) task);
                    break;
                case DeadlineTask.TYPE:
                    currentTask = new DeadlineTask((TestDeadlineTask) task);
                    break;
                case EventTask.TYPE:
                    currentTask = new EventTask((TestEventTask) task);
                    break;
                default:
                    currentTask = null;
                }
                ab.addTask(currentTask);
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{tutorial, homework, groceries, java, CS2103T, drink};
    }

    public TaskList getTypicalTaskList() {
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}
