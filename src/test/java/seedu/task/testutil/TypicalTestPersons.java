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
                    .withAddress("123, Jurong West Ave 6, #08-111").withEmail("130613 0909")
                    .withPhone("130613 0909")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("130613 0909").withPhone("130613 0909")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withPhone("130613 0909")
                    .withEmail("130613 0909").withAddress("wall street").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withPhone("130613 0909")
                    .withEmail("130613 0909").withAddress("10th street").build();
            elle = new PersonBuilder().withName("Elle Meyer").withPhone("130613 0909")
                    .withEmail("130613 0909").withAddress("michegan ave").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("130613 0909")
                    .withEmail("130613 0909").withAddress("little tokyo").build();
            george = new PersonBuilder().withName("George Best").withPhone("130613 0909")
                    .withEmail("130613 0909").withAddress("4th street").build();

            // Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withPhone("130613 0909")
                    .withEmail("130613 0909").withAddress("little india").build();
            ida = new PersonBuilder().withName("Ida Mueller").withPhone("130613 0909")
                    .withEmail("130613 0909").withAddress("chicago ave").build();
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
