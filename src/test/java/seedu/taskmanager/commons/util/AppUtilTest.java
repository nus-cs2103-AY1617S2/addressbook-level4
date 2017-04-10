package seedu.taskmanager.commons.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AppUtilTest {

    // @@author A0141102H
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void getImage_exitingImage() {
        assertNotNull(AppUtil.getImage("/images/task_manager.png"));
    }


    @Test
    public void getImage_nullGiven_assertionError() {
        thrown.expect(AssertionError.class);
        AppUtil.getImage(null);
    }


}
