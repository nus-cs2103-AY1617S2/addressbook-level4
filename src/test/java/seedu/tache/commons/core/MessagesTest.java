//@@author A0139961U
package seedu.tache.commons.core;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MessagesTest {

    @Test
    public void messages_initMessageNotNull_success() {
        Messages msg = new Messages();
        assertNotNull(msg);
    }

}
