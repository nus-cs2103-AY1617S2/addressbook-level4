package seedu.watodo.logic.commands;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.exceptions.CommandException;

//@@author A0143076J
/**
 * Manages (adds or deletes) shortcut commands that the user can use
 * to execute one of the default commands to allow for customization of Watodo by the user.
 */
public class ShortcutCommand extends Command {

    private String operation;
    private String commandWord;
    private String shortcutKey;

    private final String SHORTCUT_ADD_OPERATION = "+";
    private final String SHORTCUT_DEL_OPERATION = "-";

    public static final String COMMAND_WORD = "shortcut";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or removes a shortcut key to one of the "
            + "standard functionalities of the task manager. "
            + "Parameters: OPERATION COMMAND_WORD SHORTCUT_KEY\n" + "Example: "
            + COMMAND_WORD + " + add a/\n"
            + "adds the shortcut key 'a/' for the 'add' command.";

    public static final String MESSAGE_SUCCESS_ADDED = "New shortcut key added: %1$s";
    public static final String MESSAGE_SUCCESSS_DELETED = "Existing shortcut key deleted: %1$s";
    public static final String MESSAGE_DUPLICATE_SHORTCUT_KEY = "This shortcut key already exists!";
    public static final String MESSAGE_DELETE_INVALID_SHORTCUT_KEY = "This shortcut-command pair does not exist!";
    public static final String MESSAGE_DELETE_NOT_ALLOWED = "Standard command words cannot be deleted!";

    /**
     * Constructs a Shortcut command object from the given args. Checks that the args are valid.
     */
    public ShortcutCommand(String operation, String commandWord, String shortcutKey) throws IllegalValueException {
        if  (!isArgsValid(operation, commandWord)) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        this.operation = operation;
        this.commandWord = commandWord;
        this.shortcutKey = shortcutKey;
    }

    /**
     * Returns true only if the commandWord is one of the standard commandWords supported from the user guide
     * AND the operation is either '+' (add) or '-' (delete)
     */
    private boolean isArgsValid(String operation, String commandWord) {
        return (operation.equals(SHORTCUT_ADD_OPERATION) || operation.equals(SHORTCUT_DEL_OPERATION)) &&
               AlternativeCommandsLibrary.COMMANDS_WORDS.contains(commandWord);
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (operation.equals(SHORTCUT_ADD_OPERATION)) {
            return executeAddShortcut();
        } else {
            return executeDelShortcut();
        }
    }

    /**
     * Adds the shortcutKey to the given commandWord.
     *
     * @throws CommandException if the shortcutKey already exists in the task manager
     */
    private CommandResult executeAddShortcut() throws CommandException {
        if (AlternativeCommandsLibrary.isAlternative(shortcutKey)) {
            throw new CommandException(MESSAGE_DUPLICATE_SHORTCUT_KEY);
        }
        AlternativeCommandsLibrary.addAlternative(shortcutKey, commandWord);
        return new CommandResult(String.format(MESSAGE_SUCCESS_ADDED, shortcutKey + "->" + commandWord));
    }

    /**
     * Deletes the shortcutKey from the given commandWord.
     *
     * @throws CommandException if the shortcutKey, commandWord pair does not exist in the task manager
     */
    private CommandResult executeDelShortcut() throws CommandException {
        if (!AlternativeCommandsLibrary.isAlternative(shortcutKey)) {
            throw new CommandException(MESSAGE_DELETE_INVALID_SHORTCUT_KEY);
        }
        if (shortcutKey.equals(commandWord)) {
            throw new CommandException(MESSAGE_DELETE_NOT_ALLOWED);
        }
        try {
            AlternativeCommandsLibrary.deleteAlternative(shortcutKey, commandWord);
            return new CommandResult(String.format(MESSAGE_SUCCESSS_DELETED, shortcutKey + "->" + commandWord));
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

}

