package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestPersons {

    public TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new PersonBuilder().withName("Alice Pauline")
                    .withAddress("123, Jurong West Ave 6,").withEmail("060317 0000")
                    .withPhone("060317 0000")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2,")
                    .withEmail("060317 0000").withPhone("060317 0000")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress("wall street").withTags("test").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress("10th street").withTags("test").build();
            elle = new PersonBuilder().withName("Elle Meyer").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress("michegan ave").withTags("test").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress("little tokyo").withTags("test").build();
            george = new PersonBuilder().withName("George Best").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress("4th street").withTags("test").build();

            // Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress("little india").withTags("test").build();
            ida = new PersonBuilder().withName("Ida Mueller").withPhone("060317 0000")
                    .withEmail("060317 0000").withAddress("chicago ave").withTags("test").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestPerson person : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
