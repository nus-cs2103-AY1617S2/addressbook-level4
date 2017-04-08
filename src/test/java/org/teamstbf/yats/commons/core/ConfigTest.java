package org.teamstbf.yats.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.teamstbf.yats.commons.core.Config;

public class ConfigTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void toString_defaultObject_stringReturned() {
		String defaultConfigAsString = "App title : Yet Another Task Scheduler\n" + "Current log level : INFO\n"
				+ "Preference file Location : preferences.json\n" + "Local data file location : data/YATS.xml\n"
				+ "TaskManager name : MyYATSList";

		assertEquals(defaultConfigAsString, new Config().toString());
	}

	@Test
	public void equalsMethod() {
		Config defaultConfig = new Config();
		assertNotNull(defaultConfig);
		assertTrue(defaultConfig.equals(defaultConfig));
	}

}
