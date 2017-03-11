package seedu.typed.testutil;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.TaskManager;
import seedu.typed.model.task.Task;
import seedu.typed.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Meet Alice Pauline").withDate("01/01/2018").withTags("friends").build();
            benson = new TaskBuilder().withName("Meet Benson Meier").withDate("02/01/2018").
                    withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Meet Carl Kurz").withDate("03/01/2018").build();
            daniel = new TaskBuilder().withName("Meet Daniel Meier").withDate("04/01/2018").build();
            elle = new TaskBuilder().withName("Meet Elle Meyer").withDate("05/01/2018").build();
            fiona = new TaskBuilder().withName("Meet Fiona Kunz").withDate("06/01/2018").build();
            george = new TaskBuilder().withName("Meet George Best").withDate("07/01/2018").build();

            // Manually added
            hoon = new TaskBuilder().withName("Meet Hoon Meier").withDate("08/02/2018").build();
            ida = new TaskBuilder().withName("Meet Ida Mueller").withDate("09/02/2018").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                tm.addTask(new Task.TaskBuilder(task)
                        .build());
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
