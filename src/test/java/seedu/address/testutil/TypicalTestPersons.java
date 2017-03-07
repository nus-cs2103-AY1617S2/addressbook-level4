package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskBook;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 *
 */
public class TypicalTestPersons {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withEndDateTime("123, Jurong West Ave 6, #08-111").withStartDateTime("alice@gmail.com")
                    .withDescription("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withEndDateTime("311, Clementi Ave 2, #02-25")
                    .withStartDateTime("johnd@gmail.com").withDescription("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDescription("95352563")
                    .withStartDateTime("heinz@yahoo.com").withEndDateTime("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDescription("87652533")
                    .withStartDateTime("cornelia@google.com").withEndDateTime("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDescription("9482224")
                    .withStartDateTime("werner@gmail.com").withEndDateTime("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDescription("9482427")
                    .withStartDateTime("lydia@gmail.com").withEndDateTime("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withDescription("9482442")
                    .withStartDateTime("anna@google.com").withEndDateTime("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDescription("8482424")
                    .withStartDateTime("stefan@mail.com").withEndDateTime("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDescription("8482131")
                    .withStartDateTime("hans@google.com").withEndDateTime("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskBook ab) {
        for (TestTask person : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addTask(new Task(person));
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskBook getTypicalAddressBook() {
        TaskBook ab = new TaskBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
