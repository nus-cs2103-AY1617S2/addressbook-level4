package seedu.doit.commons.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doit.logic.commands.exceptions.CommandExistedException;
import seedu.doit.logic.commands.exceptions.NoSuchCommandException;

public class CommandSettingTest {

    public static final String MESSAGE_ADD_COMMAND = "add";
    public static final String MESSAGE_UNDO_COMMAND = "undo";

    public static final String MESSAGE_TEST_SET_CHANGED = "changed";


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
        changedSettings.setCommand(MESSAGE_ADD_COMMAND, "changed");
        differentSettings.setCommand(MESSAGE_ADD_COMMAND, "different");
        assertFalse(originalSettings.equals(changedSettings));
        assertFalse(differentSettings.equals(changedSettings));
    }




}
