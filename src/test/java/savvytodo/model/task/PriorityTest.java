package savvytodo.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Priority.isValidPhone("")); // empty string
        assertFalse(Priority.isValidPhone(" ")); // spaces only
        assertFalse(Priority.isValidPhone("phone")); // non-numeric
        assertFalse(Priority.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Priority.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Priority.isValidPhone("93121534"));
        assertTrue(Priority.isValidPhone("4")); // short phone numbers
        assertTrue(Priority.isValidPhone("124293842033123")); // long phone numbers
    }
}
