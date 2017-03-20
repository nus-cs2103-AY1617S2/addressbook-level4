package seedu.tasklist.testutil;

import java.text.ParseException;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.task.DeadlineTask;
import seedu.tasklist.model.task.EventTask;
import seedu.tasklist.model.task.FloatingTask;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;

public class TypicalTestTasks {

    public TestTask tutorial, homework, groceries, java, cs2103T, drink, internship, ida;

    public TypicalTestTasks() {
        try {

            tutorial = new EventTaskBuilder().withName("CS2103T tutorial")
                    .withComment("prepare V0.2 presentation").withStatus(false)
                    .withPriority("high")
                    .withTags("class").withStartDate("15/3/2017 15:00:10")
                    .withEndDate("15/3/2017 18:00:10").build();
            homework = new FloatingTaskBuilder().withName("CS3245 homework 3")
                    .withComment("discuss with classmates").withPriority("high")
                    .withStatus(false)
                    .withTags("class").build();
            groceries = new FloatingTaskBuilder().withName("Buy groceries").withComment("go NTUC")
                    .withPriority("low").withStatus(false).build();
            java = new FloatingTaskBuilder().withName("Update Java for CS2103T").withStatus(false)
                    .withPriority("high").withComment("Find out why jdk is not displaying the correct ver").build();
            cs2103T = new DeadlineTaskBuilder().withName("Implement undo for this").withComment("By today")
                    .withPriority("high").withDeadline("15/3/2017 18:00:10").withStatus(false).build();
            drink = new FloatingTaskBuilder().withName("Drink water").withComment("To improve brain function")
                    .withPriority("high").withStatus(false).build();

            // Manually added
            internship = new FloatingTaskBuilder().withName("Internship interview")
                    .withComment("at mediacorp").withPriority("high").withStatus(false).build();
            ida = new FloatingTaskBuilder().withName("Yet another interview")
                    .withComment("also at mediacorp").withPriority("high").withStatus(false).build();
        } catch (IllegalValueException | ParseException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList ab) throws ParseException {
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
        return new TestTask[]{tutorial, homework, groceries, java, cs2103T, drink};
    }

    public TaskList getTypicalTaskList() throws ParseException {
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}
