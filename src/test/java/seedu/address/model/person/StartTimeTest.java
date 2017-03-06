package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.StartTime;

public class StartTimeTest {

    @Test
    public void isValidStartTime() {
        // blank email
        assertFalse(StartTime.isValidStartTime("")); // empty string
        assertFalse(StartTime.isValidStartTime(" ")); // spaces only

        // missing parts
        assertFalse(StartTime.isValidStartTime("@webmail.com")); // missing local part
        assertFalse(StartTime.isValidStartTime("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(StartTime.isValidStartTime("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(StartTime.isValidStartTime("-@webmail.com")); // invalid local part
        assertFalse(StartTime.isValidStartTime("peterjack@-")); // invalid domain name
        assertFalse(StartTime.isValidStartTime("peter jack@webmail.com")); // spaces in local part
        assertFalse(StartTime.isValidStartTime("peterjack@web mail.com")); // spaces in domain name
        assertFalse(StartTime.isValidStartTime("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(StartTime.isValidStartTime("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(StartTime.isValidStartTime("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(StartTime.isValidStartTime("PeterJack_1190@WEB.Mail.com"));
        assertTrue(StartTime.isValidStartTime("a@b"));  // minimal
        assertTrue(StartTime.isValidStartTime("test@localhost"));   // alphabets only
        assertTrue(StartTime.isValidStartTime("123@145"));  // numeric local part and domain name
        assertTrue(StartTime.isValidStartTime("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(StartTime.isValidStartTime("_user_@_do_main_.com_"));    // underscores
        assertTrue(StartTime.isValidStartTime("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(StartTime.isValidStartTime("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
