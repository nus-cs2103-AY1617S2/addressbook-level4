package t15b1.taskcrusher.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import t15b1.taskcrusher.model.shared.Description;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid addresses
        assertTrue(Description.isValidDescription("Blk 456, Den Road, #01-355"));
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
