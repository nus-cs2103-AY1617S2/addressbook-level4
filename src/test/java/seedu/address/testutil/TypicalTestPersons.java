package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniquePersonList;

/**
 *
 */
public class TypicalTestPersons {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withAddress("123, Jurong West Ave 6, #08-111").withLabels("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withLabels("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withAddress("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withAddress("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withAddress("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withAddress("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {
        for (TestTask person : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addPerson(new Task(person));
            } catch (UniquePersonList.DuplicatePersonException e) {
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
