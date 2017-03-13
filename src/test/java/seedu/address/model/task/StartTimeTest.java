package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StartTimeTest {

    @Test
    public void isValidStartTime() {
        // blank email
        assertFalse(StartTime.isValidStartTime("")); // empty string
        //assertFalse(StartTime.isValidStartTime(" ")); // spaces only

        // missing parts
        //assertFalse(StartTime.isValidStartTime("@webmail.com")); // missing
                                                                 // local part
        //assertFalse(StartTime.isValidStartTime("peterjackwebmail.com")); // missing
                                                                         // '@'
                                                                         // symbol
        //assertFalse(StartTime.isValidStartTime("peterjack@")); // missing domain
                                                               // name

        // invalid parts
        //assertFalse(StartTime.isValidStartTime("-@webmail.com")); // invalid
                                                                  // local part
        //assertFalse(StartTime.isValidStartTime("peterjack@-")); // invalid
                                                                // domain name
        //assertFalse(StartTime.isValidStartTime("peter jack@webmail.com")); // spaces
                                                                           // in
                                                                           // local
                                                                           // part
        //assertFalse(StartTime.isValidStartTime("peterjack@web mail.com")); // spaces
                                                                           // in
                                                                           // domain
                                                                           // name
        //assertFalse(StartTime.isValidStartTime("peterjack@@webmail.com")); // double
                                                                           // '@'
                                                                           // symbol
        //assertFalse(StartTime.isValidStartTime("peter@jack@webmail.com")); // '@'
                                                                           // symbol
                                                                           // in
                                                                           // local
                                                                           // part
        //assertFalse(StartTime.isValidStartTime("peterjack@webmail@com")); // '@'
                                                                          // symbol
                                                                          // in
                                                                          // domain
                                                                          // name

        // valid email
        assertTrue(StartTime.isValidStartTime("march fifteenth")); //alphabets
        assertTrue(StartTime.isValidStartTime("20170315")); // number
        assertTrue(StartTime.isValidStartTime("By end of March")); // alphabets with capital
        assertTrue(StartTime.isValidStartTime("March 15 2017")); // numeric and alphabet
                                                           // and domain name
        assertTrue(StartTime.isValidStartTime("March 15, 2017")); // mixture of
                                                               // alphanumeric
                                                               // and comma
                                                               // characters
    }
}
