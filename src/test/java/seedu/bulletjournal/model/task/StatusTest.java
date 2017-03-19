package seedu.bulletjournal.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StatusTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(Status.isValidEmail("")); // empty string
        assertFalse(Status.isValidEmail(" ")); // spaces only

        // missing parts
//        assertFalse(Status.isValidEmail("@webmail.com")); // missing local part
//        assertFalse(Status.isValidEmail("peterjackwebmail.com")); // missing '@' symbol
//        assertFalse(Status.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
//        assertFalse(Status.isValidEmail("-@webmail.com")); // invalid local part
//        assertFalse(Status.isValidEmail("peterjack@-")); // invalid domain name
//        assertFalse(Status.isValidEmail("peter jack@webmail.com")); // spaces in local part
//        assertFalse(Status.isValidEmail("peterjack@web mail.com")); // spaces in domain name
//        assertFalse(Status.isValidEmail("peterjack@@webmail.com")); // double '@' symbol
//        assertFalse(Status.isValidEmail("peter@jack@webmail.com")); // '@' symbol in local part
//        assertFalse(Status.isValidEmail("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Status.isValidEmail("done"));
//        assertTrue(Status.isValidEmail("a@b"));  // minimal
//        assertTrue(Status.isValidEmail("test@localhost"));   // alphabets only
//        assertTrue(Status.isValidEmail("123@145"));  // numeric local part and domain name
//        assertTrue(Status.isValidEmail("a1@sg50.org"));  // mixture of alphanumeric and dot characters
//        assertTrue(Status.isValidEmail("_user_@_do_main_.com_"));    // underscores
//        assertTrue(Status.isValidEmail("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
//        assertTrue(Status.isValidEmail("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
