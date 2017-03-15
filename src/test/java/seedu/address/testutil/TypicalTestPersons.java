package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestPersons {

    public TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new PersonBuilder().withName("Alice Pauline")
                    .withEmail("alice@gmail.com")
                    .withPhone("85355255")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
                    .withEmail("heinz@yahoo.com").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
                    .withEmail("cornelia@google.com").build();
            elle = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
                    .withEmail("werner@gmail.com").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
                    .withEmail("lydia@gmail.com").build();
            george = new PersonBuilder().withName("George Best").withPhone("9482442")
                    .withEmail("anna@google.com").build();

            // Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
                    .withEmail("stefan@mail.com").build();
            ida = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
                    .withEmail("hans@google.com").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskList ab) {
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

    public TaskList getTypicalAddressBook() {
        TaskList ab = new TaskList();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
