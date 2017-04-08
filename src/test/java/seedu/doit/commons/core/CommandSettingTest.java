package seedu.doit.commons.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.doit.logic.commands.exceptions.CommandExistedException;
import seedu.doit.logic.commands.exceptions.NoSuchCommandException;

public class CommandSettingTest {

    public static final String MESSAGE_ADD_COMMAND = "add";
    public static final String MESSAGE_CLEAR_COMMAND = "clear";
    public static final String MESSAGE_DELETE_COMMAND = "delete";
    public static final String MESSAGE_DONE_COMMAND = "done";
    public static final String MESSAGE_EDIT_COMMAND = "edit";
    public static final String MESSAGE_EXIT_COMMAND = "exit";
    public static final String MESSAGE_FIND_COMMAND = "find";
    public static final String MESSAGE_HELP_COMMAND = "help";
    public static final String MESSAGE_LIST_COMMAND = "list";
    public static final String MESSAGE_LOAD_COMMAND = "load";
    public static final String MESSAGE_MARK_COMMAND = "mark";
    public static final String MESSAGE_REDO_COMMAND = "redo";
    public static final String MESSAGE_SAVE_COMMAND = "save";
    public static final String MESSAGE_SELECT_COMMAND = "select";
    public static final String MESSAGE_SET_COMMAND = "set";
    public static final String MESSAGE_SORT_COMMAND = "sort";
    public static final String MESSAGE_UNDO_COMMAND = "undo";
    public static final String MESSAGE_UNMARK_COMMAND = "unmark";
    public static final String MESSAGE_TEST_SET_CHANGED = "changed";


    CommandSettings originalSettings = new CommandSettings();

    @Test
    public void equals_settings_true() {
        CommandSettings testSettings = new CommandSettings();
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
        changedSettings.setCommand(MESSAGE_ADD_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_clear() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_CLEAR_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_delete() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_DELETE_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_done() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_DONE_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_edit() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_EDIT_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_exit() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_EXIT_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_find() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_FIND_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_help() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_HELP_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_list() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_LIST_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_load() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_LOAD_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_mark() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_MARK_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_redo() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_REDO_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_save() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_SAVE_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_select() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_SELECT_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_set() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_SET_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_sort() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_SORT_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_undo() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_UNDO_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }

    @Test
    public void equals_unmark() throws NoSuchCommandException, CommandExistedException {
        CommandSettings changedSettings = new CommandSettings();
        changedSettings.setCommand(MESSAGE_UNMARK_COMMAND, MESSAGE_TEST_SET_CHANGED);
        assertFalse(originalSettings.equals(changedSettings));
    }








}
