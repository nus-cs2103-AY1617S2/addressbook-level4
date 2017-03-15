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
            alice = new TaskBuilder().withName("Alice Pauline").withDate("").withTime("").build();
            benson = new TaskBuilder().withName("Benson Meier").withDate("").withTime("").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDate("").withTime("").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDate("").withTime("").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDate("").withTime("").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDate("").withTime("").build();
            george = new TaskBuilder().withName("George Best").withDate("").withTime("").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDate("").withTime("").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDate("").withTime("").build();
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
