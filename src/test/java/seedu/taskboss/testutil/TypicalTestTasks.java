package seedu.taskboss.testutil;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withAddress("123, Jurong West Ave 6, #08-111").withPhone("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPhone("95352563")
                    .withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPhone("87652533")
                    .withAddress("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPhone("9482224")
                    .withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPhone("9482427")
                    .withAddress("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPhone("9482442")
                    .withAddress("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPhone("8482424")
                   .withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPhone("8482131")
                    .withAddress("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskBossWithSampleData(TaskBoss ab) {
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

    public TaskBoss getTypicalTaskBoss() {
        TaskBoss ab = new TaskBoss();
        loadTaskBossWithSampleData(ab);
        return ab;
    }
}
