package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.testutil.EventBuilder;
import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestUtil;

public class RecurCommandTest extends WhatsLeftGuiTest {

    @Test
    public void recurEvent() {
        TestEvent[] currentEventList = te.getTypicalEvents();
        String recurCommand = "recur 1 daily 2";
        TestEvent recurEvent1 = null;
        TestEvent recurEvent2 = null;
        try {
            recurEvent1 = new EventBuilder().withDescription("GEH Presentation")
                    .withStartTime("1200")
                    .withStartDate("260517")
                    .withEndTime("1400")
                    .withEndDate("260517")
                    .withLocation("FASS")
                    .withTags("formal").build();
            recurEvent2 = new EventBuilder().withDescription("GEH Presentation")
                    .withStartTime("1200")
                    .withStartDate("270517")
                    .withEndTime("1400")
                    .withEndDate("270517")
                    .withLocation("FASS")
                    .withTags("formal").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        currentEventList = TestUtil.addEventsToList(currentEventList, recurEvent1, recurEvent2);
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        assertRecurEventSuccess(recurCommand, currentEventList);
    }

    private void assertRecurEventSuccess(String recurcommand, TestEvent[] eventslist) {
        commandBox.runCommand(recurcommand);
        assertTrue(eventListPanel.isListMatching(eventslist));
    }

}
