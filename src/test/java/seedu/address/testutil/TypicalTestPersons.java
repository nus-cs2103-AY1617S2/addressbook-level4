package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList;

/**
 *
 */
public class TypicalTestPersons {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new TaskBuilder().withTitle("Alice Pauline")
                    .withDeadline("11-11-2017").withLabels("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier").withDeadline("11-11-2017")
                    .withLabels("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withDeadline("11-11-2017").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier").withDeadline("11-11-2017").build();
            elle = new TaskBuilder().withTitle("Elle Meyer").withDeadline("11-11-2017").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz").withDeadline("11-11-2017").build();
            george = new TaskBuilder().withTitle("George Best").withDeadline("11-11-2017").build();

            // Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").withDeadline("11-11-2017").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").withDeadline("11-11-2017").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {
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

    public AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
