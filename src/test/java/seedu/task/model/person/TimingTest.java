package seedu.task.model.person;

import org.junit.Test;

public class TimingTest {


    @Test
    public void isValidEmail() {
        // // blank email
        // Timing testTiming = new Timing("03/05/2017");
     //     assertFalse(testTiming.isValidTiming("")); // empty string
        // assertFalse(Timing.isValidTiming(" ")); // spaces only
        //
        // // missing parts
        // assertFalse(Timing.isValidTiming("@webmail.com")); // missing local
        // part
        // assertFalse(Timing.isValidTiming("peterjackwebmail.com")); // missing
        // '@' symbol
        // assertFalse(Timing.isValidTiming("peterjack@")); // missing domain
        // name
        //
        // // invalid parts
        // assertFalse(Timing.isValidTiming("-@webmail.com")); // invalid local
        // part
        // assertFalse(Timing.isValidTiming("peterjack@-")); // invalid domain
        // name
        // assertFalse(Timing.isValidTiming("peter jack@webmail.com")); //
        // spaces in local part
        // assertFalse(Timing.isValidTiming("peterjack@web mail.com")); //
        // spaces in domain name
        // assertFalse(Timing.isValidTiming("peterjack@@webmail.com")); //
        // double '@' symbol
        // assertFalse(Timing.isValidTiming("peter@jack@webmail.com")); // '@'
        // symbol in local part
        // assertFalse(Timing.isValidTiming("peterjack@webmail@com")); // '@'
        // symbol in domain name
        //
        // // valid email
        // assertTrue(Timing.isValidTiming("PeterJack_1190@WEB.Mail.com"));
        // assertTrue(Timing.isValidTiming("a@b")); // minimal
        // assertTrue(Timing.isValidTiming("test@localhost")); // alphabets only
        // assertTrue(Timing.isValidTiming("123@145")); // numeric local part
        // and domain name
        // assertTrue(Timing.isValidTiming("a1@sg50.org")); // mixture of
        // alphanumeric and dot characters
        // assertTrue(Timing.isValidTiming("_user_@_do_main_.com_")); //
        // underscores
        // assertTrue(Timing.isValidTiming("peter_jack@a_very_long_domain_AVLD.com"));
        // // long domain name
        // assertTrue(Timing.isValidTiming("if.you.dream.it_you.can.do.it@youth_email.com"));
        // // long local part
    }
}
