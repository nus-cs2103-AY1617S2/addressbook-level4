package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.model.task.Remark;

public class RemarkTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(Remark.isValidEmail("")); // empty string
        assertFalse(Remark.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Remark.isValidEmail("@webmail.com")); // missing local part
        assertFalse(Remark.isValidEmail("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(Remark.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Remark.isValidEmail("-@webmail.com")); // invalid local part
        assertFalse(Remark.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Remark.isValidEmail("peter jack@webmail.com")); // spaces in local part
        assertFalse(Remark.isValidEmail("peterjack@web mail.com")); // spaces in domain name
        assertFalse(Remark.isValidEmail("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(Remark.isValidEmail("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(Remark.isValidEmail("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Remark.isValidEmail("PeterJack_1190@WEB.Mail.com"));
        assertTrue(Remark.isValidEmail("a@b"));  // minimal
        assertTrue(Remark.isValidEmail("test@localhost"));   // alphabets only
        assertTrue(Remark.isValidEmail("123@145"));  // numeric local part and domain name
        assertTrue(Remark.isValidEmail("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(Remark.isValidEmail("_user_@_do_main_.com_"));    // underscores
        assertTrue(Remark.isValidEmail("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(Remark.isValidEmail("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
