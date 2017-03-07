package seedu.geekeep.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.model.task.StartDateTime;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(StartDateTime.isValidDateTime("")); // empty string
        assertFalse(StartDateTime.isValidDateTime(" ")); // spaces only

        // missing parts
        assertFalse(StartDateTime.isValidDateTime("@webmail.com")); // missing local part
        assertFalse(StartDateTime.isValidDateTime("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(StartDateTime.isValidDateTime("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(StartDateTime.isValidDateTime("-@webmail.com")); // invalid local part
        assertFalse(StartDateTime.isValidDateTime("peterjack@-")); // invalid domain name
        assertFalse(StartDateTime.isValidDateTime("peter jack@webmail.com")); // spaces in local part
        assertFalse(StartDateTime.isValidDateTime("peterjack@web mail.com")); // spaces in domain name
        assertFalse(StartDateTime.isValidDateTime("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(StartDateTime.isValidDateTime("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(StartDateTime.isValidDateTime("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(StartDateTime.isValidDateTime("PeterJack_1190@WEB.Mail.com"));
        assertTrue(StartDateTime.isValidDateTime("a@b"));  // minimal
        assertTrue(StartDateTime.isValidDateTime("test@localhost"));   // alphabets only
        assertTrue(StartDateTime.isValidDateTime("123@145"));  // numeric local part and domain name
        assertTrue(StartDateTime.isValidDateTime("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(StartDateTime.isValidDateTime("_user_@_do_main_.com_"));    // underscores
        assertTrue(StartDateTime.isValidDateTime("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(StartDateTime.isValidDateTime("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
