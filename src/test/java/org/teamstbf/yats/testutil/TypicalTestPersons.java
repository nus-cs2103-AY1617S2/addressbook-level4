package org.teamstbf.yats.testutil;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.model.TaskManager;
import org.teamstbf.yats.model.item.Task;
import org.teamstbf.yats.model.item.UniqueItemList;

import t16b4.yats.testutil.TestEvent;

/**
 *
 */
public class TypicalTestPersons {

    public TestEvent alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new TaskBuilder().withTitle("Alice Pauline")
                    .withDescription("123, Jurong West Ave 6, #08-111").withEmail("alice@gmail.com")
                    .withPhone("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier").withDescription("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withPhone("95352563")
                    .withEmail("heinz@yahoo.com").withDescription("wall street").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier").withPhone("87652533")
                    .withEmail("cornelia@google.com").withDescription("10th street").build();
            elle = new TaskBuilder().withTitle("Elle Meyer").withPhone("9482224")
                    .withEmail("werner@gmail.com").withDescription("michegan ave").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz").withPhone("9482427")
                    .withEmail("lydia@gmail.com").withDescription("little tokyo").build();
            george = new TaskBuilder().withTitle("George Best").withPhone("9482442")
                    .withEmail("anna@google.com").withDescription("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").withPhone("8482424")
                    .withEmail("stefan@mail.com").withDescription("little india").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").withPhone("8482131")
                    .withEmail("hans@google.com").withDescription("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskManager ab) {
        for (TestEvent person : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueItemList.DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
    }

    public TestEvent[] getTypicalPersons() {
        return new TestEvent[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalAddressBook() {
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
