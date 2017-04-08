package org.teamstbf.yats.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.model.item.Title;

// @@author A0139448U
public class TitleTest {

	@Test
	public void isValidName() {
		// invalid name
		assertFalse(Title.isValidTitle("")); // empty string
		assertFalse(Title.isValidTitle(" ")); // spaces only
		assertFalse(Title.isValidTitle("!@#$")); // only non-alphanumeric
													// characters
		assertFalse(Title.isValidTitle("DO _____??")); // contains
														// non-alphanumeric
														// characters

		// valid name
		assertTrue(Title.isValidTitle("do work now")); // alphabets only
		assertTrue(Title.isValidTitle("DO PAGE 1 TO 14")); // alphanumeric
															// characters
		assertTrue(Title.isValidTitle("12345")); // numbers only
		assertTrue(Title.isValidTitle("Do Work NOW")); // with capital letters
		assertTrue(Title.isValidTitle("Do this homework drink coffee eat bread and do whatever you want")); // long
																											// titles
	}
}
