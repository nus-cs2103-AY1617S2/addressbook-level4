package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 *
 */
public class TypicalTestPersons {

    public TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new PersonBuilder().withName("Alice Pauline")
//                    .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@gmail.com")
//                    .withPhone("85355255")
            		.withGroup("group1").withEmail("alice@gmail.com")
                    .withDate("12.12")
                    .withTags("friends").build();
//            benson = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
//                    .withEmail("johnd@gmail.com").withPhone("98765432")
//                    .withTags("owesMoney", "friends").build();
//            carl = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
//                    .withEmail("heinz@yahoo.com").withAddress("wall street").build();
//            daniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
//                    .withEmail("cornelia@google.com").withAddress("10th street").build();
//            elle = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
//                    .withEmail("werner@gmail.com").withAddress("michegan ave").build();
//            fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
//                    .withEmail("lydia@gmail.com").withAddress("little tokyo").build();
//            george = new PersonBuilder().withName("George Best").withPhone("9482442")
//                    .withEmail("anna@google.com").withAddress("4th street").build();
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
//            hoon = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
//                    .withEmail("stefan@mail.com").withAddress("little india").build();
//            ida = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
//                    .withEmail("hans@google.com").withAddress("chicago ave").build();
            hoon = new PersonBuilder().withName("Hoon Meier").withDate("05.05")
                    .withEmail("stefan@mail.com").withGroup("little india").build();
            ida = new PersonBuilder().withName("Ida Mueller").withDate("08.08")
                    .withEmail("hans@google.com").withGroup("chicago").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {
        for (TestPerson person : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addPerson(new Person(person));
            } catch (UniquePersonList.DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
    }

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
