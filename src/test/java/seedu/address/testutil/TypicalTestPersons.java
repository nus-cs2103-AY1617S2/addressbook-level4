package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.YTomorrow;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniquePersonList;

/**
 *
 */
public class TypicalTestPersons {

    public TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;
    
    //@@author A0164889E
    public TypicalTestPersons() {
        try {
            alice = new PersonBuilder()
                    .withName("Alice Paul")
                    .withGroup("group1")
                    .withEndDate("12.12")
                    .withTags("incomplete")
                    .build();
            benson = new PersonBuilder().withName("Benson Meier")
                    .withGroup("311")
                    .withEndDate("12.11")
                    .withTags("owesMoney", "friends")
                    .build();
            carl = new PersonBuilder().withName("Carl Kurz")
                    .withEndDate("10.01")
                    .withGroup("wall street")
                    .build();
            daniel = new PersonBuilder()
                    .withName("Daniel Meier")
                    .withEndDate("11.11")
                    .withGroup("street")
                    .build();
            elle = new PersonBuilder()
                    .withName("Elle Meyer")
                    .withEndDate("05.05")
                    .withGroup("michegan")
                    .build();
            fiona = new PersonBuilder()
                    .withName("Fiona Kunz")
                    .withEndDate("07.07")
                    .withGroup("little tokyo")
                    .build();
            george = new PersonBuilder()
                    .withName("George Best")
                    .withEndDate("04.12")
                    .withGroup("street")
                    .build();

            // Manually added
            hoon = new PersonBuilder()
                    .withName("Hoon Meier")
                    .withEndDate("05.05")
                    .withGroup("little india")
                    .withTags("incomplete")
                    .build();
            ida = new PersonBuilder()
                    .withName("Ida Mueller")
                    .withEndDate("08.08")
                    .withGroup("chicago")
                    .withTags("incomplete")
                    .build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(YTomorrow ab) {
        for (TestPerson person : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addPerson(new Task(person));
            } catch (UniquePersonList.DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
    }

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public YTomorrow getTypicalAddressBook() {
        YTomorrow ab = new YTomorrow();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
