package seedu.taskmanager.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskmanager.model.task.EndDate;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(EndDate.isValidEndDate("")); // empty string
        assertFalse(EndDate.isValidEndDate(" ")); // spaces only

        // missing parts
        assertFalse(EndDate.isValidEndDate("@webmail.com")); // missing local part
        assertFalse(EndDate.isValidEndDate("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(EndDate.isValidEndDate("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(EndDate.isValidEndDate("-@webmail.com")); // invalid local part
        assertFalse(EndDate.isValidEndDate("peterjack@-")); // invalid domain name
        assertFalse(EndDate.isValidEndDate("peter jack@webmail.com")); // spaces in local part
        assertFalse(EndDate.isValidEndDate("peterjack@web mail.com")); // spaces in domain name
        assertFalse(EndDate.isValidEndDate("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(EndDate.isValidEndDate("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(EndDate.isValidEndDate("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(EndDate.isValidEndDate("PeterJack_1190@WEB.Mail.com"));
        assertTrue(EndDate.isValidEndDate("a@b"));  // minimal
        assertTrue(EndDate.isValidEndDate("test@localhost"));   // alphabets only
        assertTrue(EndDate.isValidEndDate("123@145"));  // numeric local part and domain name
        assertTrue(EndDate.isValidEndDate("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(EndDate.isValidEndDate("_user_@_do_main_.com_"));    // underscores
        assertTrue(EndDate.isValidEndDate("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(EndDate.isValidEndDate("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
