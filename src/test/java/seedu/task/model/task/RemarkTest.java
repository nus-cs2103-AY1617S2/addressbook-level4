package seedu.task.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {

    @Test
    public void isValidRemark() {
        // blank remark
        assertTrue(Remark.isValidRemark("")); // empty string
        assertTrue(Remark.isValidRemark(" ")); // spaces only

        // missing parts
        assertTrue(Remark.isValidRemark("@webmail.com")); // missing local part
        assertTrue(Remark.isValidRemark("peterjackwebmail.com")); // missing '@' symbol
        assertTrue(Remark.isValidRemark("peterjack@")); // missing domain name

        // invalid parts
        assertTrue(Remark.isValidRemark("-@webmail.com")); // invalid local part
        assertTrue(Remark.isValidRemark("peterjack@-")); // invalid domain name
        assertTrue(Remark.isValidRemark("peter jack@webmail.com")); // spaces in local part
        assertTrue(Remark.isValidRemark("peterjack@web mail.com")); // spaces in domain name
        assertTrue(Remark.isValidRemark("peterjack@@webmail.com")); // double '@' symbol
        assertTrue(Remark.isValidRemark("peter@jack@webmail.com")); // '@' symbol in local part
        assertTrue(Remark.isValidRemark("peterjack@webmail@com")); // '@' symbol in domain name

        // valid remark
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
