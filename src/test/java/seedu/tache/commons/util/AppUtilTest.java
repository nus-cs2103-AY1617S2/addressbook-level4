package seedu.tache.commons.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AppUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void getImageExitingImage() {
        assertNotNull(AppUtil.getImage("/images/tache.png"));
    }


    @Test
    public void getImageNullGivenAssertionError() {
        thrown.expect(AssertionError.class);
        AppUtil.getImage(null);
    }

}
