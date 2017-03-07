package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Address.isValidInformation("")); // empty string
        assertFalse(Address.isValidInformation(" ")); // spaces only

        // valid addresses
        assertTrue(Address.isValidInformation("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidInformation("-")); // one character
        assertTrue(Address.isValidInformation("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
