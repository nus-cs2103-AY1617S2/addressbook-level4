package seedu.task.commons.core;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // @@author A0146757R
    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "App title : Fast Task\n" + "Current log level : INFO\n"
                + "Preference file Location : preferences.json\n" + "Local data file location : "
                + FileNameHandler.getFileName() + "\n" + "Task Manager name : MyTaskManager";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    // @@author
    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));
    }

}
