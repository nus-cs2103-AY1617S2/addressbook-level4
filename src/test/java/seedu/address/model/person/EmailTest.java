package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.TaskDate;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(TaskDate.isValidDate("")); // empty string
        assertFalse(TaskDate.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(TaskDate.isValidDate("@webmail.com")); // missing local part
        assertFalse(TaskDate.isValidDate("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(TaskDate.isValidDate("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(TaskDate.isValidDate("-@webmail.com")); // invalid local part
        assertFalse(TaskDate.isValidDate("peterjack@-")); // invalid domain name
        assertFalse(TaskDate.isValidDate("peter jack@webmail.com")); // spaces in local part
        assertFalse(TaskDate.isValidDate("peterjack@web mail.com")); // spaces in domain name
        assertFalse(TaskDate.isValidDate("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(TaskDate.isValidDate("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(TaskDate.isValidDate("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(TaskDate.isValidDate("PeterJack_1190@WEB.Mail.com"));
        assertTrue(TaskDate.isValidDate("a@b"));  // minimal
        assertTrue(TaskDate.isValidDate("test@localhost"));   // alphabets only
        assertTrue(TaskDate.isValidDate("123@145"));  // numeric local part and domain name
        assertTrue(TaskDate.isValidDate("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(TaskDate.isValidDate("_user_@_do_main_.com_"));    // underscores
        assertTrue(TaskDate.isValidDate("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(TaskDate.isValidDate("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
