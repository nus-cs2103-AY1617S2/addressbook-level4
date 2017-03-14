package seedu.ezdo.logic.commands;

import seedu.ezdo.logic.commands.exceptions.CommandException;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the previous undoable command.";
    public static final String MESSAGE_SUCCESS = "Previous undoable command has been undone!";
    public static final String MESSAGE_NO_PREV_COMMAND = "There is no previous undoable command!";

    @Override
    public CommandResult execute() throws CommandException {

        return null;
    }

}
