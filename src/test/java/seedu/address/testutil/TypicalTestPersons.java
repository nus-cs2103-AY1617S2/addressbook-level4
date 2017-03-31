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
            alice = new PersonBuilder().withName("Alice Paul").withGroup("group1")
                    .withEmail("a@gml.com").withDate("12.12")
                    .withTags("incomplete").build();
            benson = new PersonBuilder().withName("Benson Meier").withGroup("311")
                    .withEmail("johnd@gmail.com").withDate("12.11")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withDate("10.01")
                    .withEmail("heinz@yahoo.com").withGroup("wall street").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withDate("11.11")
                    .withEmail("cornelia@google.com").withGroup("street").build();
            elle = new PersonBuilder().withName("Elle Meyer").withDate("05.05")
                    .withEmail("werner@gmail.com").withGroup("michegan").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withDate("07.07")
                    .withEmail("lydia@gmail.com").withGroup("little tokyo").build();
            george = new PersonBuilder().withName("George Best").withDate("04.12")
                    .withEmail("anna@google.com").withGroup("street").build();

            // Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withDate("05.05")
                    .withEmail("stefan@mail.com").withGroup("little india")
                    .withTags("incomplete").build();
            ida = new PersonBuilder().withName("Ida Mueller").withDate("08.08")
                    .withEmail("hans@google.com").withGroup("chicago")
                    .withTags("incomplete").build();
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
