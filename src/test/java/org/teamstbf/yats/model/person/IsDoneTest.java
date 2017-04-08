package org.teamstbf.yats.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.teamstbf.yats.model.item.IsDone;

// @@author A0139448U
public class IsDoneTest {

	@Test
	public void test() {
		// valid IsDone
		assertTrue(IsDone.isValidIsDone("Yes")); // done task
		assertTrue(IsDone.isValidIsDone("No")); // undone task

		// invalid IsDone
		assertFalse(IsDone.isValidIsDone("yes"));
		assertFalse(IsDone.isValidIsDone("no"));
		assertFalse(IsDone.isValidIsDone("gg.com"));
	}

}
