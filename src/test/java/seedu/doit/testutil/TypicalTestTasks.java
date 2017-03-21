package seedu.doit.testutil;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.TaskManager;
import seedu.doit.model.item.Task;
import seedu.doit.model.item.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                .withDescription("123, Jurong West Ave 6, #08-111").withDeadline("friday")
                .withPriority("low")
                .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withDescription("311, Clementi Ave 2, #02-25")
                .withDeadline("thursday").withPriority("med")
                .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("high")
                .withDeadline("next wednesday").withDescription("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("high")
                .withDeadline("tomorrow").withDescription("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriority("low")
                .withDeadline("next tuesday").withDescription("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriority("med")
                .withDeadline("sunday").withDescription("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPriority("high")
                .withDeadline("3/20/17").withDescription("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriority("low")
                .withDeadline("3/14/18").withDescription("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriority("med")
                .withDeadline("04/04/17").withDescription("chicago ave").build();
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
        return new TestTask[] {alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
