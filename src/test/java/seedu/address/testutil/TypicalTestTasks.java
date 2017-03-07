package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestPerson alice
            , benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new PersonBuilder().withName("Alice Pauline")
                    .withDescription("123, Jurong West Ave 6, #08-111").withDeadline("1")
                    .withPriority("85355255")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withDescription("311, Clementi Ave 2, #02-25")
                    .withDeadline("2").withPriority("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withPriority("95352563")
                    .withDeadline("3").withDescription("wall street").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withPriority("87652533")
                    .withDeadline("5").withDescription("10th street").build();
            elle = new PersonBuilder().withName("Elle Meyer").withPriority("9482224")
                    .withDeadline("2").withDescription("michegan ave").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withPriority("9482427")
                    .withDeadline("3").withDescription("little tokyo").build();
            george = new PersonBuilder().withName("George Best").withPriority("9482442")
                    .withDeadline("4").withDescription("4th street").build();

            // Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withPriority("8482424")
                    .withDeadline("5").withDescription("little india").build();
            ida = new PersonBuilder().withName("Ida Mueller").withPriority("8482131")
                    .withDeadline("7").withDescription("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskManager ab) {
        for (TestPerson person : new TypicalTestTasks().getTypicalPersons()) {
            try {
                ab.addPerson(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalAddressBook() {
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
