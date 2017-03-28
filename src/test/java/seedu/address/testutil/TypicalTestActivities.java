package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.WhatsLeft;
import seedu.address.model.person.Event;
import seedu.address.model.person.UniqueEventList;
import seedu.address.model.person.UniqueEventList.DuplicateTimeClashException;

/**
 *
 */
public class TypicalTestActivities {

    public TestActivity alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestActivities() {
        try {
            alice = new ActivityBuilder().withDescription("CS2103 TUT 1")
                    .withStartDate("200517")
                    .withStartTime("0900")
                    .withEndTime("1000")
                    .withEndDate("200517")
                    .withLocation("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();
            benson = new ActivityBuilder().withDescription("CS2104 TUT 1")
                    .withStartDate("210517")
                    .withStartTime("0900")
                    .withEndTime("1000")
                    .withEndDate("210517")
                    .withLocation("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();
            carl = new ActivityBuilder().withDescription("CS2105 TUT 1")
                    .withStartDate("220517")
                    .withStartTime("0900")
                    .withEndTime("1000")
                    .withEndDate("220517")
                    .withLocation("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();
            daniel = new ActivityBuilder().withDescription("CS2106 TUT 1")
                    .withStartDate("230517")
                    .withStartTime("0900")
                    .withEndTime("1000")
                    .withEndDate("230517")
                    .withLocation("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();
            elle = new ActivityBuilder().withDescription("CS2107 TUT 1")
                    .withStartDate("240517")
                    .withStartTime("0900")
                    .withEndTime("1000")
                    .withEndDate("240517")
                    .withLocation("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();
            fiona = new ActivityBuilder().withDescription("CS2108 TUT 1")
                    .withStartDate("250517")
                    .withStartTime("0900")
                    .withEndTime("1000")
                    .withEndDate("250517")
                    .withLocation("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();
            george = new ActivityBuilder().withDescription("CS2109 TUT 1")
                    .withStartDate("260517")
                    .withStartTime("0900")
                    .withEndTime("1000")
                    .withEndDate("260517")
                    .withLocation("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();

            // Manually added
            hoon = new ActivityBuilder().withDescription("CS2110 TUT 1")
                    .withStartDate("270517")
                    .withStartTime("0900")
                    .withEndTime("1000")
                    .withEndDate("270517")
                    .withLocation("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();
            ida = new ActivityBuilder().withDescription("CS2111 TUT 1")
                    .withStartDate("280517")
                    .withStartTime("0900")
                    .withEndTime("1000")
                    .withEndDate("280517")
                    .withLocation("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadWhatsLeftWithSampleData(WhatsLeft ab) {
        for (TestActivity event : new TypicalTestActivities().getTypicalActivities()) {
            try {
                ab.addEvent(new Event(event));
            } catch (UniqueEventList.DuplicateEventException | DuplicateTimeClashException e) {
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
