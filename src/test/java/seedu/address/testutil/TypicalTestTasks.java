package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline").withTime("17/02/2017").withClockTime("23:59")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withTime("17/02/2017").withClockTime("23:59")
            .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withTime("17/02/2017").withClockTime("23:59").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withTime("17/02/2017").withClockTime("23:59").build();
            elle = new TaskBuilder().withName("Elle Meyer").withTime("17/02/2017").withClockTime("23:59").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withTime("17/02/2017").withClockTime("23:59").build();
            george = new TaskBuilder().withName("George Best").withTime("17/02/2017").withClockTime("23:59").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withTime("17/02/2017").withClockTime("23:59").build();
            ida = new TaskBuilder().withName("Ida Mueller").withTime("17/02/2017").withClockTime("23:59").build();
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
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
