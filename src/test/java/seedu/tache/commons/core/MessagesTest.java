package seedu.tache.commons.core;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MessagesTest {

    @Test
    public void initMessageNotNull() {
        Messages msg = new Messages();
        assertNotNull(msg);
    }

}
