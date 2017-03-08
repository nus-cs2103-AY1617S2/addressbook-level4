package org.teamstbf.yats.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.model.item.Title;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Title.isValidName("")); // empty string
        assertFalse(Title.isValidName(" ")); // spaces only
        assertFalse(Title.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidName("peter jack")); // alphabets only
        assertTrue(Title.isValidName("12345")); // numbers only
        assertTrue(Title.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidName("Capital Tan")); // with capital letters
        assertTrue(Title.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
