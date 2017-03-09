package savvytodo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(Description.isValidEmail("")); // empty string
        assertFalse(Description.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Description.isValidEmail("@webmail.com")); // missing local part
        assertFalse(Description.isValidEmail("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(Description.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Description.isValidEmail("-@webmail.com")); // invalid local part
        assertFalse(Description.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Description.isValidEmail("peter jack@webmail.com")); // spaces in local part
        assertFalse(Description.isValidEmail("peterjack@web mail.com")); // spaces in domain name
        assertFalse(Description.isValidEmail("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(Description.isValidEmail("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(Description.isValidEmail("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Description.isValidEmail("PeterJack_1190@WEB.Mail.com"));
        assertTrue(Description.isValidEmail("a@b"));  // minimal
        assertTrue(Description.isValidEmail("test@localhost"));   // alphabets only
        assertTrue(Description.isValidEmail("123@145"));  // numeric local part and domain name
        assertTrue(Description.isValidEmail("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(Description.isValidEmail("_user_@_do_main_.com_"));    // underscores
        assertTrue(Description.isValidEmail("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(Description.isValidEmail("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
