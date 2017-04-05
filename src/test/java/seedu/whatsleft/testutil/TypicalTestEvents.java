package seedu.whatsleft.testutil;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.model.WhatsLeft;
import seedu.whatsleft.model.activity.Event;
import seedu.whatsleft.model.activity.UniqueEventList.DuplicateEventException;

//@@author A0148038A
/**
 * TypicalTestEvents for GUI test
 */
public class TypicalTestEvents {

    public TestEvent tutorial, lecture, meeting, talk, exam, presentation, discussion, consultation, workshop;

    public TypicalTestEvents() {
        try {
            tutorial = new EventBuilder().withDescription("CS2103 Tutorial")
                    .withStartTime("0900")
                    .withStartDate("010517")
                    .withEndTime("1000")
                    .withEndDate("010517")
                    .withLocation("COM1-B103")
                    .withTags("demo").build();
            lecture = new EventBuilder().withDescription("CS2010 Lecture")
                    .withStartTime("1000")
                    .withStartDate("050218")
                    .withEndTime("1200")
                    .withEndDate("050218")
                    .withLocation("LT19")
                    .withTags("webcasted").build();
            meeting = new EventBuilder().withDescription("CCA Meeting")
                    .withStartTime("1900")
                    .withStartDate("200418")
                    .withEndTime("2200")
                    .withEndDate("200418")
                    .withLocation("FoS")
                    .withTags("CCA").build();
            talk = new EventBuilder().withDescription("Enrichment Talk")
                    .withStartTime("1800")
                    .withStartDate("150418")
                    .withEndTime("2000")
                    .withEndDate("150418")
                    .withLocation("LT28")
                    .withTags("talk").build();
            exam = new EventBuilder().withDescription("CS2107 Exam")
                    .withStartTime("0900")
                    .withStartDate("040217")
                    .withEndTime("1030")
                    .withEndDate("040217")
                    .withLocation("MPSH1A")
                    .withTags("exam").build();
            presentation = new EventBuilder().withDescription("GEH Presentation")
                    .withStartTime("1200")
                    .withStartDate("250518")
                    .withEndTime("1400")
                    .withEndDate("250518")
                    .withLocation("123, Jurong West Ave 6, #08-111")
                    .withTags("friends").build();
            discussion = new EventBuilder().withDescription("CS2103 Project Discussion")
                    .withStartTime("1500")
                    .withStartDate("260517")
                    .withEndTime("1900")
                    .withEndDate("260517")
                    .withLocation("CLB")
                    .withTags("project").build();

            // Manually added
            consultation = new EventBuilder().withDescription("MA2101 Consultation")
                    .withStartTime("1000")
                    .withStartDate("270517")
                    .withEndTime("1100")
                    .withEndDate("270517")
                    .withLocation("S17")
                    .withTags("consultation").build();
            workshop = new EventBuilder().withDescription("PS Workshop")
                    .withStartTime("0900")
                    .withStartDate("280517")
                    .withEndTime("1000")
                    .withEndDate("280517")
                    .withLocation("UHALL")
                    .withTags("formal").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadWhatsLeftWithSampleData(WhatsLeft wl) {
        for (TestEvent event : new TypicalTestEvents().getTypicalEvents()) {
            try {
                wl.addEvent(new Event(event));
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
    }

    public TestEvent[] getTypicalEvents() {
        return new TestEvent[]{tutorial, lecture, meeting, talk, exam, presentation, discussion};
    }

    public WhatsLeft getTypicalWhatsLeft() {
        WhatsLeft wl = new WhatsLeft();
        loadWhatsLeftWithSampleData(wl);
        return wl;
    }
}
