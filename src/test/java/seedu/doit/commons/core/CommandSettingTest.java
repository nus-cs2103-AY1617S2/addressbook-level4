package seedu.doit.commons.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doit.logic.commands.exceptions.CommandExistedException;
import seedu.doit.logic.commands.exceptions.NoSuchCommandException;

public class CommandSettingTest {

    CommandSettings originalSettings = new CommandSettings();
    CommandSettings testSettings = new CommandSettings();

    @Test
    public void equals_settings_true() {
        assertTrue(originalSettings.equals(testSettings));
    }

    @Test
    public void equals_null_false() {
        assertFalse(originalSettings.equals(null));
    }

    @Test
    public void equals_notCommandSetting_false() {
        assertFalse(originalSettings.equals(new Object()));
    }

    @Test
    public void equals_add() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        CommandSettings differentSettings = new CommandSettings();
        changedSettings.setCommand("add", "changed");
        differentSettings.setCommand("add", "different");
        assertFalse(originalSettings.equals(changedSettings));
        assertFalse(differentSettings.equals(changedSettings));

    }




}
