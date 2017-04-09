package seedu.watodo.logic.commands;

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
            + "Parameters: <+><-> COMMAND_WORD SHORTCUT_KEY" + "Example: "
            + COMMAND_WORD + " + add a/";

    public static final String MESSAGE_SUCCESS_ADDED = "New shortcut key added: %1$s";
    public static final String MESSAGE_SUCCESSS_DELETED = "Existing shortcut key deleted: %1$s";
    public static final String MESSAGE_DUPLICATE_SHORTCUT_KEY = "This shortcut key already exists!";
    public static final String MESSAGE_DELETE_INVALID_SHORTCUT_KEY = "This shortcut key does not exists!";

    /**
     * Constructs a Shortcut command object from the given args. Checks that the args are valid.
     */
    public ShortcutCommand(String operation, String commandWord, String shortcutKey) throws IllegalValueException {
        if  (!isArgsValid(operation, commandWord)) {
            throw new IllegalValueException(MESSAGE_USAGE);
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
               AlternativeCommand.COMMANDS_WORDS.contains(commandWord);
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (operation.equals(SHORTCUT_ADD_OPERATION)) {
            return executeAdd();
        } else {
            return executeDel();
        }
    }

    private CommandResult executeAdd() throws CommandException {
        if (AlternativeCommand.containsAlternative(shortcutKey)) {
            throw new CommandException(MESSAGE_DUPLICATE_SHORTCUT_KEY);
        }
        AlternativeCommand.addAlternative(shortcutKey, commandWord);
        return new CommandResult(String.format(MESSAGE_SUCCESS_ADDED, shortcutKey + "->" + commandWord));
    }

    private CommandResult executeDel() throws CommandException {
        if (!AlternativeCommand.containsAlternative(shortcutKey)) {
            throw new CommandException(MESSAGE_DELETE_INVALID_SHORTCUT_KEY);
        }
        if (!AlternativeCommand.deleteAlternative(shortcutKey, commandWord)) { //shortcut key exists but is mapped to
                                                                     //a comandWord different from that given by user
            throw new CommandException(MESSAGE_DELETE_INVALID_SHORTCUT_KEY);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESSS_DELETED, shortcutKey + "->" + commandWord));
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

}

