package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

//@@author A0146757R
public class TypicalTestTasks {

    public static TestTask apples;
    public static TestTask cereals;
    public TestTask homework;
    public TestTask report;
    public TestTask lab;
    public TestTask police;
    public TestTask jog;
    public static TestTask yam;
    public static TestTask zoo;

    public TypicalTestTasks() {
        try {
            apples = new TaskBuilder().withTaskName("Deliver apples").withTaskDate("120217")
                    .withTaskStartTime("1000").withTaskEndTime("1200")
                    .withTaskDescription("Deliver to Crescent Road").build();
            cereals = new TaskBuilder().withTaskName("Buy cereals").withTaskDate("020217")
                    .withTaskStartTime("0800").withTaskEndTime("1000")
                    .withTaskDescription("Look for promo cereals").build();
            homework = new TaskBuilder().withTaskName("Do homework").withTaskDate("020217")
                    .withTaskStartTime("0800").withTaskEndTime("1000")
                    .withTaskDescription("why am I having soo many homework").build();
            report = new TaskBuilder().withTaskName("write report").withTaskDate("020217")
                    .withTaskStartTime("0800").withTaskEndTime("1000")
                    .withTaskDescription("the boring report from GE mods").build();
            lab = new TaskBuilder().withTaskName("pick up lab test result").withTaskDate("020217")
                    .withTaskStartTime("0800").withTaskEndTime("1000")
                    .withTaskDescription("the lady looks not so nice").build();
            police = new TaskBuilder().withTaskName("change address").withTaskDate("020217")
                    .withTaskStartTime("0800").withTaskEndTime("1000")
                    .withTaskDescription("change the address to your new house").build();
            jog = new TaskBuilder().withTaskName("do jogging").withTaskDate("020217")
                    .withTaskStartTime("0800").withTaskEndTime("1000")
                    .withTaskDescription("I hate IPPT").build();

            // Manually added
            yam = new TaskBuilder().withTaskName("Grow yam").withTaskDate("100217").withTaskStartTime("0700")
                    .withTaskEndTime("1700").withTaskDescription("Buy fertilizers").withTaskStatus("Ongoing").build();
            zoo = new TaskBuilder().withTaskName("Visit zoo").withTaskDate("030217").withTaskStartTime("0800")
                    .withTaskEndTime("1700").withTaskDescription("Bring Jesse along").withTaskStatus("Ongoing").build();

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
        return new TestTask[] { apples, cereals, homework, report, lab, police, jog, yam, zoo};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
// @@author
