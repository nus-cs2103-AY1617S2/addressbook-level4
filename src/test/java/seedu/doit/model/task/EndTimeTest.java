package seedu.doit.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doit.model.item.EndTime;

public class EndTimeTest {

    @Test
    public void isValidEndTime() {
        // blank deadline
        assertFalse(EndTime.isValidDeadline("")); // empty string
        assertFalse(EndTime.isValidDeadline(" ")); // spaces only

        // valid deadline
        assertTrue(EndTime.isValidDeadline("PeterJack_1190@WEB.Mail.com"));
        assertTrue(EndTime.isValidDeadline("a@b"));  // minimal
        assertTrue(EndTime.isValidDeadline("test@localhost"));   // alphabets only
        assertTrue(EndTime.isValidDeadline("123@145"));  // numeric local part and domain name
        assertTrue(EndTime.isValidDeadline("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(EndTime.isValidDeadline("_user_@_do_main_.com_"));    // underscores
        assertTrue(EndTime.isValidDeadline("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(EndTime.isValidDeadline("if.you.dream.it_you.can.do.it@youth_emai.com"));    // long local part
    }
}
