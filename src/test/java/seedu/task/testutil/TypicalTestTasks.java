package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

//@@author A0146757R
public class TypicalTestTasks {

    public static TestTask apples, cereals, yam, zoo, yam2, zoo2, yam3, zoo3;

    public TypicalTestTasks() {
        try {
            apples = new TaskBuilder().withTaskName("Deliver apples").withTaskDate("120217")
                    .withTaskStartTime("1000").withTaskEndTime("1200")
                    .withTaskDescription("Deliver to Crescent Road").withTaskStatus("Ongoing").build();
            cereals = new TaskBuilder().withTaskName("Buy cereals").withTaskDate("020217")
                    .withTaskStartTime("0800").withTaskEndTime("1000")
                    .withTaskDescription("Look for promo cereals").withTaskStatus("Ongoing").build();

            // Manually added
            yam = new TaskBuilder().withTaskName("Grow yam").withTaskDate("100217")
                    .withTaskStartTime("0700").withTaskEndTime("1700")
                    .withTaskDescription("Buy fertilizers").withTaskStatus("Ongoing").build();
            zoo = new TaskBuilder().withTaskName("Visit zoo").withTaskDate("030217")
                    .withTaskStartTime("0800").withTaskEndTime("1700")
                    .withTaskDescription("Bring Jesse along").withTaskStatus("Ongoing").build();
            yam2 = new TaskBuilder().withTaskName("Grow yam2").withTaskDate("100217")
                    .withTaskStartTime("0700").withTaskEndTime("1700")
                    .withTaskDescription("Buy fertilizers").withTaskStatus("Ongoing").build();
            zoo2 = new TaskBuilder().withTaskName("Visit zoo2").withTaskDate("030217")
                    .withTaskStartTime("0800").withTaskEndTime("1700")
                    .withTaskDescription("Bring Jesse along").withTaskStatus("Ongoing").build();
            yam3 = new TaskBuilder().withTaskName("Grow yam3").withTaskDate("100217")
                    .withTaskStartTime("0700").withTaskEndTime("1700")
                    .withTaskDescription("Buy fertilizers").withTaskStatus("Ongoing").build();
            zoo3 = new TaskBuilder().withTaskName("Visit zoo3").withTaskDate("030217")
                    .withTaskStartTime("0800").withTaskEndTime("1700")
                    .withTaskDescription("Bring Jesse along").withTaskStatus("Ongoing").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
            try {
                tm.addJobTask(new Task(apples));
                tm.addJobTask(new Task(cereals));
                tm.addJobTask(new Task(yam));
                tm.addJobTask(new Task(zoo));
                
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { apples, cereals, yam, zoo, yam2, zoo2, yam3, zoo3 };
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
// @@author
