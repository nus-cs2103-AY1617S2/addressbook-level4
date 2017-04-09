package seedu.tache.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void config_toStringDefaultObject_stringReturned() {
        String defaultConfigAsString = "App title : Tache\n"
                + "Current log level : INFO\n"
                + "Preference file Location : preferences.json\n"
                + "Local data file location : data/taskmanager.xml\n"
                + "TaskManager name : Tache";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    //@@author A0142255M
    @Test
    public void config_getTaskManagerName_success() {
        assertEquals("Tache", new Config().getTaskManagerName());
    }

    @Test
    public void config_equalsNullValue_failure() {
        assertFalse(new Config() == null);
    }
    //@@author

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));
    }


}
