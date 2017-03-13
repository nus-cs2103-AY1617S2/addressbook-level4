package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EndTimeTest {

    @Test
    public void isValidEndTime() {
        // blank email
        //        assertFalse(EndTime.isValidTime("")); // empty string
        //        assertFalse(EndTime.isValidTime(" ")); // spaces only

        // missing parts
        assertFalse(EndTime.isValidTime("2359")); // missing date part
        assertFalse(EndTime.isValidTime("270717")); // missing time part
        assertFalse(EndTime.isValidTime("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(EndTime.isValidTime("-@webmail.com")); // invalid local part
        assertFalse(EndTime.isValidTime("peterjack@-")); // invalid domain name
        assertFalse(EndTime.isValidTime("peter jack@webmail.com")); // spaces in local part
        assertFalse(EndTime.isValidTime("peterjack@web mail.com")); // spaces in domain name
        assertFalse(EndTime.isValidTime("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(EndTime.isValidTime("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(EndTime.isValidTime("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(EndTime.isValidTime("270717 2359"));
        //assertTrue(EndTime.isValidTime("a@b")); // minimal
        //assertTrue(EndTime.isValidTime("test@localhost"));   // alphabets only
        //assertTrue(EndTime.isValidTime("123@145"));  // numeric local part and domain name
        //assertTrue(EndTime.isValidTime("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        //assertTrue(EndTime.isValidTime("_user_@_do_main_.com_"));    // underscores
        //assertTrue(EndTime.isValidTime("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        //assertTrue(EndTime.isValidTime("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
