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
                    .withLocation("123, Jurong West Ave 6, #08-111")
                    .withPriority("high")
                    .withTags("friends").build();
            benson = new ActivityBuilder().withDescription("Benson Meier").withLocation("311, Clementi Ave 2, #02-25")
                    .withPriority("high")
                    .withTags("owesMoney", "friends").build();
            carl = new ActivityBuilder().withDescription("Carl Kurz").withPriority("high")
                    .withLocation("wall street").build();
            daniel = new ActivityBuilder().withDescription("Daniel Meier").withPriority("high")
                    .withLocation("10th street").build();
            elle = new ActivityBuilder().withDescription("Elle Meyer").withPriority("high")
                    .withLocation("michegan ave").build();
            fiona = new ActivityBuilder().withDescription("Fiona Kunz").withPriority("high")
                    .withLocation("little tokyo").build();
            george = new ActivityBuilder().withDescription("George Best").withPriority("high")
                    .withLocation("4th street").build();

            // Manually added
            hoon = new ActivityBuilder().withDescription("Hoon Meier").withPriority("high")
                    .withLocation("little india").build();
            ida = new ActivityBuilder().withDescription("Ida Mueller").withPriority("high")
                    .withLocation("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadWhatsLeftWithSampleData(WhatsLeft ab) {
        for (TestActivity activity : new TypicalTestActivities().getTypicalActivities()) {
            try {
                ab.addActivity(new Activity(activity));
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
