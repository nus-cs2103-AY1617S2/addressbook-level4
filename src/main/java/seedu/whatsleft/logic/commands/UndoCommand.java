package seedu.whatsleft.logic.commands;

import seedu.whatsleft.logic.commands.exceptions.CommandException;
import seedu.whatsleft.model.ModelManager;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "undid the previous %1$s operation";
    public static final String MESSAGE_NOTHING_TO_UNDO = "No Previous Edit, Delete or Clear Operation to undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the last operation if it was "
            + "an edit, delete or clear command.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        String previousCommand = ModelManager.getPreviousCommand();
        if (previousCommand.equals("")) {
            return new CommandResult(MESSAGE_NOTHING_TO_UNDO);
        }
        if (!previousCommand.equals("edit") && !previousCommand.equals("delete") && !previousCommand.equals("clear")
                && !previousCommand.equals("finish") && !previousCommand.equals("add") && !previousCommand.
                equals("redo")) {
            return new CommandResult(MESSAGE_NOTHING_TO_UNDO);
        }
        ReadOnlyWhatsLeft previousState = ModelManager.getPreviousState();
        model.resetData(previousState);
        model.storePreviousCommand("");
        return new CommandResult(String.format(MESSAGE_SUCCESS, previousCommand));
    }

}
