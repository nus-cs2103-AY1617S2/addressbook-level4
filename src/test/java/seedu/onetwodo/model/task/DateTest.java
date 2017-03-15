package seedu.onetwodo.model.task;

//import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTest {

    @Test
    public void isValidDate() {
        // "TODO test for start & end date
        
        /*
        assertTrue(Date.isValidDate(""));
        // blank email
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(Date.isValidDate("@webmail.com")); // missing local part
        assertFalse(Date.isValidDate("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(Date.isValidDate("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Date.isValidDate("-@webmail.com")); // invalid local part
        assertFalse(Date.isValidDate("peterjack@-")); // invalid domain name
        assertFalse(Date.isValidDate("peter jack@webmail.com")); // spaces in local part
        assertFalse(Date.isValidDate("peterjack@web mail.com")); // spaces in domain name
        assertFalse(Date.isValidDate("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(Date.isValidDate("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(Date.isValidDate("peterjack@webmail@com")); // '@' symbol in domain name

        // valid date
        assertTrue(Date.isValidDate("PeterJack_1190@WEB.Mail.com"));
        assertTrue(Date.isValidDate("a@b"));  // minimal
        assertTrue(Date.isValidDate("test@localhost"));   // alphabets only
        assertTrue(Date.isValidDate("123@145"));  // numeric local part and domain name
        assertTrue(Date.isValidDate("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(Date.isValidDate("_user_@_do_main_.com_"));    // underscores
        assertTrue(Date.isValidDate("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(Date.isValidDate("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part*/
    }
}
