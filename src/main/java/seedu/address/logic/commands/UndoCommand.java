package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author A0163848R
/**
 * Command that undoes changes caused by the last command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the changes made by the last command.\n";
    public static final String UNDO_SUCCESS = "Undo!";
    public static final String UNDO_FAILURE = "Nothing to undo!";

    public UndoCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        boolean result = model.undoLastModification();
        
        return new CommandResult(result ? UNDO_SUCCESS : UNDO_FAILURE);
    }

}
