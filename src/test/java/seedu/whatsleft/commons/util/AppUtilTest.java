package seedu.whatsleft.commons.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


//@@author A0124377A
public class AppUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void getImage_exitingImage() {
        assertNotNull(AppUtil.getImage("/images/WhatsLeft.png"));
    }


    @Test
    public void getImage_nullGiven_assertionError() {
        thrown.expect(AssertionError.class);
        AppUtil.getImage(null);
    }


}
