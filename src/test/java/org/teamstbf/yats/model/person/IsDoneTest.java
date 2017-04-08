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
		assertTrue(IsDone.isValidDone("Yes")); // done task
		assertTrue(IsDone.isValidDone("No")); // undone task

		// invalid IsDone
		assertFalse(IsDone.isValidDone("yes"));
		assertFalse(IsDone.isValidDone("no"));
		assertFalse(IsDone.isValidDone("gg.com"));
	}

}
