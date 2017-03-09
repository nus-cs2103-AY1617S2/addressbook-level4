package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.EndDate;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(EndDate.isValidEmail("")); // empty string
        assertFalse(EndDate.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(EndDate.isValidEmail("@webmail.com")); // missing local part
        assertFalse(EndDate.isValidEmail("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(EndDate.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(EndDate.isValidEmail("-@webmail.com")); // invalid local part
        assertFalse(EndDate.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(EndDate.isValidEmail("peter jack@webmail.com")); // spaces in local part
        assertFalse(EndDate.isValidEmail("peterjack@web mail.com")); // spaces in domain name
        assertFalse(EndDate.isValidEmail("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(EndDate.isValidEmail("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(EndDate.isValidEmail("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(EndDate.isValidEmail("PeterJack_1190@WEB.Mail.com"));
        assertTrue(EndDate.isValidEmail("a@b"));  // minimal
        assertTrue(EndDate.isValidEmail("test@localhost"));   // alphabets only
        assertTrue(EndDate.isValidEmail("123@145"));  // numeric local part and domain name
        assertTrue(EndDate.isValidEmail("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(EndDate.isValidEmail("_user_@_do_main_.com_"));    // underscores
        assertTrue(EndDate.isValidEmail("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(EndDate.isValidEmail("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
