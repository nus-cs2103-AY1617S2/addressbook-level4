package seedu.tasklist.testutil;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withComment("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withComment("311, Clementi Ave 2, #02-25")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withComment("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withComment("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withComment("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withComment("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withComment("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withComment("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withComment("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskListWithSampleData(TaskList ab) {
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

    public TaskList getTypicalTaskList() {
        TaskList ab = new TaskList();
        loadTaskListWithSampleData(ab);
        return ab;
    }
}
