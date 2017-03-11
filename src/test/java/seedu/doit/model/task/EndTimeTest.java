package seedu.doit.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doit.model.item.EndTime;

public class EndTimeTest {

    @Test
    public void isValidEndTime() {
        // blank deadline
        assertFalse(EndTime.isValidEndTime("")); // empty string
        assertFalse(EndTime.isValidEndTime(" ")); // spaces only

        // valid deadline
        assertTrue(EndTime.isValidEndTime("PeterJack_1190@WEB.Mail.com"));
        assertTrue(EndTime.isValidEndTime("a@b"));  // minimal
        assertTrue(EndTime.isValidEndTime("test@localhost"));   // alphabets only
        assertTrue(EndTime.isValidEndTime("123@145"));  // numeric local part and domain name
        assertTrue(EndTime.isValidEndTime("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(EndTime.isValidEndTime("_user_@_do_main_.com_"));    // underscores
        assertTrue(EndTime.isValidEndTime("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(EndTime.isValidEndTime("if.you.dream.it_you.can.do.it@youth_emai.com"));    // long local part
    }
}
