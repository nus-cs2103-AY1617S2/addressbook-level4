package seedu.tache.testutil;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.TaskManager;
import seedu.tache.model.task.DetailedTask;
import seedu.tache.model.task.UniqueDetailedTaskList;

public class TypicalTestDetailedTasks {

    public TestDetailedTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestDetailedTasks() {
        try {
            alice = new DetailedTaskBuilder().withName("Alice Pauline")
                    .withTags("friends").build();
            benson = new DetailedTaskBuilder().withName("Benson Meier")
                    .withTags("owesMoney", "friends").build();
            carl = new DetailedTaskBuilder().withName("Carl Kurz").build();
            daniel = new DetailedTaskBuilder().withName("Daniel Meier").build();
            elle = new DetailedTaskBuilder().withName("Elle Meyer").build();
            fiona = new DetailedTaskBuilder().withName("Fiona Kunz").build();
            george = new DetailedTaskBuilder().withName("George Best").build();

            // Manually added
            hoon = new DetailedTaskBuilder().withName("Hoon Meier").build();
            ida = new DetailedTaskBuilder().withName("Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestDetailedTask task : new TypicalTestDetailedTasks().getTypicalDetailedTasks()) {
            try {
                ab.addDetailedTask(new DetailedTask(task));
            } catch (UniqueDetailedTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestDetailedTask[] getTypicalDetailedTasks() {
        return new TestDetailedTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
