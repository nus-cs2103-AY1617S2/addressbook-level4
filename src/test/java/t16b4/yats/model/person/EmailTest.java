package t16b4.yats.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import t16b4.yats.model.item.Timing;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(Timing.isValidEmail("")); // empty string
        assertFalse(Timing.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Timing.isValidEmail("@webmail.com")); // missing local part
        assertFalse(Timing.isValidEmail("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(Timing.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Timing.isValidEmail("-@webmail.com")); // invalid local part
        assertFalse(Timing.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Timing.isValidEmail("peter jack@webmail.com")); // spaces in local part
        assertFalse(Timing.isValidEmail("peterjack@web mail.com")); // spaces in domain name
        assertFalse(Timing.isValidEmail("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(Timing.isValidEmail("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(Timing.isValidEmail("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Timing.isValidEmail("PeterJack_1190@WEB.Mail.com"));
        assertTrue(Timing.isValidEmail("a@b"));  // minimal
        assertTrue(Timing.isValidEmail("test@localhost"));   // alphabets only
        assertTrue(Timing.isValidEmail("123@145"));  // numeric local part and domain name
        assertTrue(Timing.isValidEmail("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(Timing.isValidEmail("_user_@_do_main_.com_"));    // underscores
        assertTrue(Timing.isValidEmail("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(Timing.isValidEmail("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
