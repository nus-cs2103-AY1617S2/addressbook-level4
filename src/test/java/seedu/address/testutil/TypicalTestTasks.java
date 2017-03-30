/* @@author A0119505J */
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline").withTime("17/02/2017").withPriority("high").
                withTags("friends").withPriority("med").withStatus(0).build();
            benson = new TaskBuilder().withName("Benson Meier").withTime("17/02/2017").
                withTags("owesMoney", "friends").withPriority("med").withStatus(0).build();
            carl = new TaskBuilder().withName("Carl Kurz").withTime("17/02/2017").withPriority("med").withStatus(0).build();
            daniel = new TaskBuilder().withName("Daniel Meier").withTime("17/02/2017").withPriority("low").withStatus(0).build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withTime("17/02/2017").withPriority("med").withStatus(0).build();
            ida = new TaskBuilder().withName("Ida Mueller").withTime("17/02/2017").withPriority("low").withStatus(0).build();
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
        return new TestTask[]{alice, benson, carl, daniel};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
