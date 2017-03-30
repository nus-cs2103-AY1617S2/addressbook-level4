package seedu.jobs.logic.commands;

import java.util.EmptyStackException;

import seedu.jobs.logic.commands.exceptions.CommandException;

/**
 * Redo previoud undoed commands.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redo previous command";
    public static final String MESSAGE_FAILUIRE = "No more commands to redo";


    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.redoCommand();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException e) {
            throw new CommandException(MESSAGE_FAILUIRE);
        }
    }
}
