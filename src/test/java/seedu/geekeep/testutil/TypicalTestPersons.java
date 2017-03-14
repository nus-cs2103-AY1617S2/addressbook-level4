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
                    .withLocation("123, Jurong West Ave 6, #08-111").withStartDateTime("2017-04-01T10:16:30")
                    .withEndDateTime("2017-04-01T10:16:30")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withLocation("311, Clementi Ave 2, #02-25")
                    .withStartDateTime("2017-04-01T10:16:30").withEndDateTime("2017-04-01T10:16:30")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withEndDateTime("2017-04-01T10:16:30")
                    .withStartDateTime("2017-04-01T10:16:30").withLocation("wall street").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withEndDateTime("2017-04-01T10:16:30")
                    .withStartDateTime("2017-04-01T10:16:30").withLocation("10th street").build();
            elle = new PersonBuilder().withName("Elle Meyer").withEndDateTime("2017-04-01T10:16:30")
                    .withStartDateTime("2017-04-01T10:16:30").withLocation("michegan ave").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withEndDateTime("2017-04-01T10:16:30")
                    .withStartDateTime("2017-04-01T10:16:30").withLocation("little tokyo").build();
            george = new PersonBuilder().withName("George Best").withEndDateTime("2017-04-01T10:16:30")
                    .withStartDateTime("2017-04-01T10:16:30").withLocation("4th street").build();

            // Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withEndDateTime("2017-04-01T10:16:30")
                    .withStartDateTime("2017-04-01T10:16:30").withLocation("little india").build();
            ida = new PersonBuilder().withName("Ida Mueller").withEndDateTime("2017-04-01T10:16:30")
                    .withStartDateTime("2017-04-01T10:16:30").withLocation("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask person : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
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
