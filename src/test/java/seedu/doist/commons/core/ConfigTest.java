package seedu.doist.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ConfigTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "App title : Doist\n" +
                "Current log level : INFO\n" +
                "Absolute Storage Path : " + Paths.get(".").toAbsolutePath().normalize().toString() + "\n" +
                "Preference file Location : preferences.json\n" +
                "Alias List Map file location : data" + File.separator + "aliaslistmap.xml\n" +
                "Local data file location : data" + File.separator + "todolist.xml\n" +
                "TodoList name : MyTodoList";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));
    }


}
