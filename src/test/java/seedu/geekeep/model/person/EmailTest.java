package seedu.geekeep.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.model.task.DateTime;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(DateTime.isValidEmail("")); // empty string
        assertFalse(DateTime.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(DateTime.isValidEmail("@webmail.com")); // missing local part
        assertFalse(DateTime.isValidEmail("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(DateTime.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(DateTime.isValidEmail("-@webmail.com")); // invalid local part
        assertFalse(DateTime.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(DateTime.isValidEmail("peter jack@webmail.com")); // spaces in local part
        assertFalse(DateTime.isValidEmail("peterjack@web mail.com")); // spaces in domain name
        assertFalse(DateTime.isValidEmail("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(DateTime.isValidEmail("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(DateTime.isValidEmail("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(DateTime.isValidEmail("PeterJack_1190@WEB.Mail.com"));
        assertTrue(DateTime.isValidEmail("a@b"));  // minimal
        assertTrue(DateTime.isValidEmail("test@localhost"));   // alphabets only
        assertTrue(DateTime.isValidEmail("123@145"));  // numeric local part and domain name
        assertTrue(DateTime.isValidEmail("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(DateTime.isValidEmail("_user_@_do_main_.com_"));    // underscores
        assertTrue(DateTime.isValidEmail("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(DateTime.isValidEmail("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
