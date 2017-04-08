package org.teamstbf.yats.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.model.item.Description;

public class DescriptionTest {

	@Test
	public void isValidDescription() {
		// valid descriptions
		assertTrue(Description.isValidDescription("")); // empty string
		assertTrue(Description.isValidDescription(" ")); // spaces only
		assertTrue(Description.isValidDescription("I have a task that is less than 1000 characters"));

		// invalid descriptions
		assertFalse(Description.isValidDescription(new String(new char[101]).replace("\0", "overloaded")));
	}
}
