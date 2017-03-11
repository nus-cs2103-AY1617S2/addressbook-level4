package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.Activity;
import seedu.address.model.person.UniqueActivityList;

/**
 *
 */
public class TypicalTestActivities {

    public TestActivity alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestActivities() {
        try {
            alice = new ActivityBuilder().withDescription("Alice Pauline")
                    .withLocation("123, Jurong West Ave 6, #08-111").withEmail("alice@gmail.com")
                    .withPhone("85355255")
                    .withTags("friends").build();
            benson = new ActivityBuilder().withDescription("Benson Meier").withLocation("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new ActivityBuilder().withDescription("Carl Kurz").withPhone("95352563")
                    .withEmail("heinz@yahoo.com").withLocation("wall street").build();
            daniel = new ActivityBuilder().withDescription("Daniel Meier").withPhone("87652533")
                    .withEmail("cornelia@google.com").withLocation("10th street").build();
            elle = new ActivityBuilder().withDescription("Elle Meyer").withPhone("9482224")
                    .withEmail("werner@gmail.com").withLocation("michegan ave").build();
            fiona = new ActivityBuilder().withDescription("Fiona Kunz").withPhone("9482427")
                    .withEmail("lydia@gmail.com").withLocation("little tokyo").build();
            george = new ActivityBuilder().withDescription("George Best").withPhone("9482442")
                    .withEmail("anna@google.com").withLocation("4th street").build();

            // Manually added
            hoon = new ActivityBuilder().withDescription("Hoon Meier").withPhone("8482424")
                    .withEmail("stefan@mail.com").withLocation("little india").build();
            ida = new ActivityBuilder().withDescription("Ida Mueller").withPhone("8482131")
                    .withEmail("hans@google.com").withLocation("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadWhatsLeftWithSampleData(WhatsLeft ab) {
        for (TestActivity person : new TypicalTestActivities().getTypicalActivities()) {
            try {
                ab.addActivity(new Activity(person));
            } catch (UniqueActivityList.DuplicateActivityException e) {
                assert false : "not possible";
            }
        }
    }

    public TestActivity[] getTypicalActivities() {
        return new TestActivity[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public WhatsLeft getTypicalWhatsLeft() {
        WhatsLeft ab = new WhatsLeft();
        loadWhatsLeftWithSampleData(ab);
        return ab;
    }
}
