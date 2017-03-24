package seedu.geekeep.testutil;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.TaskManager;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withTitle("Alice Pauline")
                    .withLocation("123, Jurong West Ave 6, #08-111").withStartDateTime("01-04-17 1630")
                    .withEndDateTime("01-05-17 1630")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier").withLocation("311, Clementi Ave 2, #02-25")
                    .withStartDateTime("01-04-17 1630").withEndDateTime("01-05-17 1630")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("wall street").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("10th street").build();
            elle = new TaskBuilder().withTitle("Elle Meyer").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("michegan ave").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("little tokyo").build();
            george = new TaskBuilder().withTitle("George Best").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("little india").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("chicago ave").build();
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
            } catch (IllegalValueException ive) {
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
