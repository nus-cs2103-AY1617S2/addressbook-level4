package seedu.address.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StatusTest {

    @Test
    public void isValidStatus() {
        // invalid status

        // valid status
        assertTrue(Status.isValidStatus("PeterJack_1190@WEB.Mail.com"));
        assertTrue(Status.isValidStatus("a@b"));  // minimal
        assertTrue(Status.isValidStatus("test@localhost"));   // alphabets only
        assertTrue(Status.isValidStatus("123@145"));  // numeric local part and domain name
        assertTrue(Status.isValidStatus("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(Status.isValidStatus("_user_@_do_main_.com_"));    // underscores
        assertTrue(Status.isValidStatus("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(Status.isValidStatus("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
