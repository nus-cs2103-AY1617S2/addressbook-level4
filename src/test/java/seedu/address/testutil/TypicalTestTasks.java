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
            alice = new TaskBuilder().withName("Alice Pauline").withDate("85355255").withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withDate("98765432").withTags("owesMoney", "friends")
                    .build();
            carl = new TaskBuilder().withName("Carl Kurz").withDate("95352563").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDate("87652533").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDate("9482224").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDate("9482427").build();
            george = new TaskBuilder().withName("George Best").withDate("9482442").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDate("8482424").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDate("8482131").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                tm.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[] { alice, benson, carl, daniel, elle, fiona, george };
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
