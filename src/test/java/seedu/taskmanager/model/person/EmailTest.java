package seedu.taskmanager.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.Date;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(Date.isValidEmail("")); // empty string
        assertFalse(Date.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Date.isValidEmail("@webmail.com")); // missing local part
        assertFalse(Date.isValidEmail("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(Date.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Date.isValidEmail("-@webmail.com")); // invalid local part
        assertFalse(Date.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Date.isValidEmail("peter jack@webmail.com")); // spaces in local part
        assertFalse(Date.isValidEmail("peterjack@web mail.com")); // spaces in domain name
        assertFalse(Date.isValidEmail("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(Date.isValidEmail("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(Date.isValidEmail("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Date.isValidEmail("PeterJack_1190@WEB.Mail.com"));
        assertTrue(Date.isValidEmail("a@b"));  // minimal
        assertTrue(Date.isValidEmail("test@localhost"));   // alphabets only
        assertTrue(Date.isValidEmail("123@145"));  // numeric local part and domain name
        assertTrue(Date.isValidEmail("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(Date.isValidEmail("_user_@_do_main_.com_"));    // underscores
        assertTrue(Date.isValidEmail("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(Date.isValidEmail("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
