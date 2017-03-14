package org.teamstbf.yats.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.model.item.Schedule;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(Schedule.isValidTiming("")); // empty string
        assertFalse(Schedule.isValidTiming(" ")); // spaces only

        // missing parts
        assertFalse(Schedule.isValidTiming("@webmail.com")); // missing local part
        assertFalse(Schedule.isValidTiming("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(Schedule.isValidTiming("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Schedule.isValidTiming("-@webmail.com")); // invalid local part
        assertFalse(Schedule.isValidTiming("peterjack@-")); // invalid domain name
        assertFalse(Schedule.isValidTiming("peter jack@webmail.com")); // spaces in local part
        assertFalse(Schedule.isValidTiming("peterjack@web mail.com")); // spaces in domain name
        assertFalse(Schedule.isValidTiming("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(Schedule.isValidTiming("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(Schedule.isValidTiming("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Schedule.isValidTiming("PeterJack_1190@WEB.Mail.com"));
        assertTrue(Schedule.isValidTiming("a@b"));  // minimal
        assertTrue(Schedule.isValidTiming("test@localhost"));   // alphabets only
        assertTrue(Schedule.isValidTiming("123@145"));  // numeric local part and domain name
        assertTrue(Schedule.isValidTiming("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(Schedule.isValidTiming("_user_@_do_main_.com_"));    // underscores
        assertTrue(Schedule.isValidTiming("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(Schedule.isValidTiming("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
