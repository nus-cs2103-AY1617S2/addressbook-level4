package seedu.doit.testutil;

import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.model.TaskManager;
import seedu.doit.model.task.Task;
import seedu.doit.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                .withDescription("123, Jurong West Ave 6, #08-111").withDeadline("1")
                .withPriority("85355255")
                .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withDescription("311, Clementi Ave 2, #02-25")
                .withDeadline("2").withPriority("98765432")
                .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("95352563")
                .withDeadline("3").withDescription("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("87652533")
                .withDeadline("5").withDescription("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriority("9482224")
                .withDeadline("2").withDescription("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriority("9482427")
                .withDeadline("3").withDescription("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPriority("9482442")
                .withDeadline("4").withDescription("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriority("8482424")
                .withDeadline("5").withDescription("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriority("8482131")
                .withDeadline("7").withDescription("chicago ave").build();
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
