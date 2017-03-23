package seedu.geekeep.testutil;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.model.TaskManager;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestPersons {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new PersonBuilder().withName("Alice Pauline")
                    .withLocation("123, Jurong West Ave 6, #08-111").withStartDateTime("01-04-17 1630")
                    .withEndDateTime("01-05-17 1630")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withLocation("311, Clementi Ave 2, #02-25")
                    .withStartDateTime("01-04-17 1630").withEndDateTime("01-05-17 1630")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("wall street").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("10th street").build();
            elle = new PersonBuilder().withName("Elle Meyer").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("michegan ave").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("little tokyo").build();
            george = new PersonBuilder().withName("George Best").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("4th street").build();

            // Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("little india").build();
            ida = new PersonBuilder().withName("Ida Mueller").withEndDateTime("01-05-17 1630")
                    .withStartDateTime("01-04-17 1630").withLocation("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            } catch (IllegalValueException ive) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
