package t15b1.taskcrusher.testutil;

import t15b1.taskcrusher.commons.exceptions.IllegalValueException;
import t15b1.taskcrusher.model.UserInbox;
import t15b1.taskcrusher.model.task.Task;
import t15b1.taskcrusher.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestPersons {

    public TestCard alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new PersonBuilder().withName("Alice Pauline")
                    .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@gmail.com")
                    .withPhone("85355255")
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
                    .withEmail("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
                    .withEmail("cornelia@google.com").withAddress("10th street").build();
            elle = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
                    .withEmail("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
                    .withEmail("lydia@gmail.com").withAddress("little tokyo").build();
            george = new PersonBuilder().withName("George Best").withPhone("9482442")
                    .withEmail("anna@google.com").withAddress("4th street").build();

            // Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
                    .withEmail("stefan@mail.com").withAddress("little india").build();
            ida = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
                    .withEmail("hans@google.com").withAddress("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(UserInbox ab) {
        for (TestCard person : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestCard[] getTypicalPersons() {
        return new TestCard[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public UserInbox getTypicalAddressBook() {
        UserInbox ab = new UserInbox();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
