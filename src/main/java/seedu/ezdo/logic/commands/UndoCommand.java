package seedu.ezdo.logic.commands;

import seedu.ezdo.logic.commands.exceptions.CommandException;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the previous undoable task.";
    public static final String MESSAGE_SUCCESS = "Previous undoable task has been undone!";
    public static final String MESSAGE_NO_PREV_TASK = "There is no previous undoable task!";

    @Override
    public CommandResult execute() throws CommandException {

        return null;
    }

}
