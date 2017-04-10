package seedu.tache.commons.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AppUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void appUtilGetImage_exitingImage_success() {
        assertNotNull(AppUtil.getImage("/images/tache.png"));
    }


    @Test
    public void appUtilGetImage_nullGiven_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        AppUtil.getImage(null);
        fail();
    }

}
