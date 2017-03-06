package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.Venue;

public class VenueTest {

    @Test
    public void isValidVenue() {
        // invalid phone numbers
        assertFalse(Venue.isValidVenue("")); // empty string
        assertFalse(Venue.isValidVenue(" ")); // spaces only
        assertFalse(Venue.isValidVenue("phone")); // non-numeric
        assertFalse(Venue.isValidVenue("9011p041")); // alphabets within digits
        assertFalse(Venue.isValidVenue("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Venue.isValidVenue("93121534"));
        assertTrue(Venue.isValidVenue("4")); // short phone numbers
        assertTrue(Venue.isValidVenue("124293842033123")); // long phone numbers
    }
}
