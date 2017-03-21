package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClockTimeTest {

    @Test
    public void isValidClockTime() {
        // blank email
        assertFalse(ClockTime.isValidClockTime("")); // empty string
        assertFalse(ClockTime.isValidClockTime(" ")); // spaces only

        // missing parts
        assertFalse(ClockTime.isValidClockTime("@webmail.com")); // missing local part
        assertFalse(ClockTime.isValidClockTime("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(ClockTime.isValidClockTime("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(ClockTime.isValidClockTime("-@webmail.com")); // invalid local part
        assertFalse(ClockTime.isValidClockTime("peterjack@-")); // invalid domain name
        assertFalse(ClockTime.isValidClockTime("peter jack@webmail.com")); // spaces in local part
        assertFalse(ClockTime.isValidClockTime("peterjack@web mail.com")); // spaces in domain name
        assertFalse(ClockTime.isValidClockTime("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(ClockTime.isValidClockTime("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(ClockTime.isValidClockTime("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(ClockTime.isValidClockTime("PeterJack_1190@WEB.Mail.com"));
        assertTrue(ClockTime.isValidClockTime("a@b"));  // minimal
        assertTrue(ClockTime.isValidClockTime("test@localhost"));   // alphabets only
        assertTrue(ClockTime.isValidClockTime("123@145"));  // numeric local part and domain name
        assertTrue(ClockTime.isValidClockTime("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(ClockTime.isValidClockTime("_user_@_do_main_.com_"));    // underscores
        assertTrue(ClockTime.isValidClockTime("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(ClockTime.isValidClockTime("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
