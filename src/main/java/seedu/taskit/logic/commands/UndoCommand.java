//@@author A0141011J

package seedu.taskit.logic.commands;

import seedu.taskit.commons.exceptions.NoValidStateException;

public class UndoCommand extends Command{

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo previous command.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_UNDO_SUCCESS = "Previous action undone.";
    public static final String MESSAGE_NO_PREVIOUS_STATE = "No previous command to undo.";


    public UndoCommand(){

    }

    @Override
    public CommandResult execute() {
        try {
            //Command previousCommand = commandList.getLastCommand();
            //model.resetData(previousCommand.model.getAddressBook());
            model.revert();
        } catch (NoValidStateException nvse) {
            return new CommandResult(MESSAGE_NO_PREVIOUS_STATE);
        }
        return new CommandResult(MESSAGE_UNDO_SUCCESS);

    }
}
