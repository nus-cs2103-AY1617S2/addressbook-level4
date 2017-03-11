package seedu.onetwodo.model.task;

//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;

import org.junit.Test;

//import seedu.onetwodo.model.task.EndDate;

public class DateTest {

    @Test
    public void isValidDate() {
        
        //assertTrue(EndDate.isValidDate(""));
        
        /*// blank email
        assertFalse(EndDate.isValidDate("")); // empty string
        assertFalse(EndDate.isValidDate(" ")); // spaces only

        // missing parts
        assertFalse(EndDate.isValidDate("@webmail.com")); // missing local part
        assertFalse(EndDate.isValidDate("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(EndDate.isValidDate("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(EndDate.isValidDate("-@webmail.com")); // invalid local part
        assertFalse(EndDate.isValidDate("peterjack@-")); // invalid domain name
        assertFalse(EndDate.isValidDate("peter jack@webmail.com")); // spaces in local part
        assertFalse(EndDate.isValidDate("peterjack@web mail.com")); // spaces in domain name
        assertFalse(EndDate.isValidDate("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(EndDate.isValidDate("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(EndDate.isValidDate("peterjack@webmail@com")); // '@' symbol in domain name

        // valid date
        assertTrue(EndDate.isValidDate("PeterJack_1190@WEB.Mail.com"));
        assertTrue(EndDate.isValidDate("a@b"));  // minimal
        assertTrue(EndDate.isValidDate("test@localhost"));   // alphabets only
        assertTrue(EndDate.isValidDate("123@145"));  // numeric local part and domain name
        assertTrue(EndDate.isValidDate("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(EndDate.isValidDate("_user_@_do_main_.com_"));    // underscores
        assertTrue(EndDate.isValidDate("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(EndDate.isValidDate("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part*/
    }
}
