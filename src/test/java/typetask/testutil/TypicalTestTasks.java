package typetask.testutil;

import typetask.commons.exceptions.IllegalValueException;
import typetask.model.TaskManager;
import typetask.model.task.Task;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline").withDate("").withEndDate("")
                    .withCompleted(false).withPriority("Low").build();
            benson = new TaskBuilder().withName("Benson Meier").withDate("").withEndDate("")
                    .withCompleted(false).withPriority("Low").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDate("").withEndDate("")
                    .withCompleted(false).withPriority("Low").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDate("").withEndDate("")
                    .withCompleted(false).withPriority("Low").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDate("").withEndDate("")
                    .withCompleted(false).withPriority("Low").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDate("").withEndDate("")
                    .withCompleted(false).withPriority("Low").build();
            george = new TaskBuilder().withName("George Best").withDate("Sun Oct 02 1993 23:59:59")
                    .withEndDate("Sun Oct 10 1993 23:59:59")
                    .withCompleted(false).withPriority("Low").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDate("").withEndDate("")
                    .withCompleted(false).withPriority("Low").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDate("").withEndDate("")
                    .withCompleted(false).withPriority("Low").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            tm.addTask(new Task(task));
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
