package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.StartDateTime;

public class StartDateTimeTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(StartDateTime.isValidEmail("")); // empty string
        assertFalse(StartDateTime.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(StartDateTime.isValidEmail("@webmail.com")); // missing local part
        assertFalse(StartDateTime.isValidEmail("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(StartDateTime.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(StartDateTime.isValidEmail("-@webmail.com")); // invalid local part
        assertFalse(StartDateTime.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(StartDateTime.isValidEmail("peter jack@webmail.com")); // spaces in local part
        assertFalse(StartDateTime.isValidEmail("peterjack@web mail.com")); // spaces in domain name
        assertFalse(StartDateTime.isValidEmail("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(StartDateTime.isValidEmail("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(StartDateTime.isValidEmail("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(StartDateTime.isValidEmail("PeterJack_1190@WEB.Mail.com"));
        assertTrue(StartDateTime.isValidEmail("a@b"));  // minimal
        assertTrue(StartDateTime.isValidEmail("test@localhost"));   // alphabets only
        assertTrue(StartDateTime.isValidEmail("123@145"));  // numeric local part and domain name
        assertTrue(StartDateTime.isValidEmail("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(StartDateTime.isValidEmail("_user_@_do_main_.com_"));    // underscores
        assertTrue(StartDateTime.isValidEmail("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(StartDateTime.isValidEmail("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
