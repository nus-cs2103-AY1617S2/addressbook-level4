package seedu.task.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {

    @Test
    public void isValidRemark() {
        // blank email
        assertFalse(Remark.isValidRemark("")); // empty string
        assertFalse(Remark.isValidRemark(" ")); // spaces only

        // missing parts
        assertFalse(Remark.isValidRemark("@webmail.com")); // missing local part
        assertFalse(Remark.isValidRemark("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(Remark.isValidRemark("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Remark.isValidRemark("-@webmail.com")); // invalid local part
        assertFalse(Remark.isValidRemark("peterjack@-")); // invalid domain name
        assertFalse(Remark.isValidRemark("peter jack@webmail.com")); // spaces in local part
        assertFalse(Remark.isValidRemark("peterjack@web mail.com")); // spaces in domain name
        assertFalse(Remark.isValidRemark("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(Remark.isValidRemark("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(Remark.isValidRemark("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Remark.isValidRemark("PeterJack_1190@WEB.Mail.com"));
        assertTrue(Remark.isValidRemark("a@b"));  // minimal
        assertTrue(Remark.isValidRemark("test@localhost"));   // alphabets only
        assertTrue(Remark.isValidRemark("123@145"));  // numeric local part and domain name
        assertTrue(Remark.isValidRemark("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(Remark.isValidRemark("_user_@_do_main_.com_"));    // underscores
        assertTrue(Remark.isValidRemark("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(Remark.isValidRemark("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
