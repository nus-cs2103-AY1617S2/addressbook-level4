package seedu.ezdo.logic.commands;

import seedu.ezdo.logic.commands.exceptions.CommandException;

/*
 * Redo the last undone command
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo the last command you have undone.";
    public static final String MESSAGE_SUCCESS = "Last command undone has been redone!";
    public static final String MESSAGE_NO_PREV_COMMAND = "There is no redoable command!";


    @Override
    public CommandResult execute() throws CommandException {
        // TODO Auto-generated method stub
        return null;
    }

}
