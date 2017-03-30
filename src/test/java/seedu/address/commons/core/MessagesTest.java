package seedu.address.commons.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MessagesTest {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASKS_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";

    @Test
    public void test_SameMessages_ReturnsTrue() {
        assertTrue("Unknown command".equals(Messages.MESSAGE_UNKNOWN_COMMAND));
        assertTrue("Invalid command format! \n%1$s".equals(Messages.MESSAGE_INVALID_COMMAND_FORMAT));
        assertTrue("The task index provided is invalid".equals(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX));
        assertTrue("%1$d tasks listed!".equals(Messages.MESSAGE_TASKS_LISTED_OVERVIEW));
    }

    @Test
    public void test_DifferentMessages_ReturnFalse() {
        assertFalse("unknown command".equals(Messages.MESSAGE_UNKNOWN_COMMAND));
        assertFalse("Invalid command format \n%1$s".equals(Messages.MESSAGE_INVALID_COMMAND_FORMAT));
        assertFalse("The task index Provided is invalid".equals(Messages.MESSAGE_INVALID_TASKS_DISPLAYED_INDEX));
        assertFalse("%1$d task listed!".equals(Messages.MESSAGE_TASKS_LISTED_OVERVIEW));
    }
}
