package seedu.address.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NotesTest {

    @Test
    public void isValidNotes() {

        //valid notes
        assertTrue(Notes.isValidNotes("")); // empty string
        assertTrue(Notes.isValidNotes(" ")); // space
        assertTrue(Notes.isValidNotes("new")); // string
        assertTrue(Notes.isValidNotes("very important")); // string with space
        assertTrue(Notes.isValidNotes("must done by 17 Mar"));  // string with space and integer
        assertTrue(Notes.isValidNotes("member: peter")); // string with symbol
    }
}
