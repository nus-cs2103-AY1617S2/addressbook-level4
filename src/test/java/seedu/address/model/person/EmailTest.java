package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(Email.isValidPriority("")); // empty string
        assertFalse(Email.isValidPriority(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidPriority("@webmail.com")); // missing local part
        assertFalse(Email.isValidPriority("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(Email.isValidPriority("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidPriority("-@webmail.com")); // invalid local part
        assertFalse(Email.isValidPriority("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidPriority("peter jack@webmail.com")); // spaces in local part
        assertFalse(Email.isValidPriority("peterjack@web mail.com")); // spaces in domain name
        assertFalse(Email.isValidPriority("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(Email.isValidPriority("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(Email.isValidPriority("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Email.isValidPriority("PeterJack_1190@WEB.Mail.com"));
        assertTrue(Email.isValidPriority("a@b"));  // minimal
        assertTrue(Email.isValidPriority("test@localhost"));   // alphabets only
        assertTrue(Email.isValidPriority("123@145"));  // numeric local part and domain name
        assertTrue(Email.isValidPriority("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(Email.isValidPriority("_user_@_do_main_.com_"));    // underscores
        assertTrue(Email.isValidPriority("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(Email.isValidPriority("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
