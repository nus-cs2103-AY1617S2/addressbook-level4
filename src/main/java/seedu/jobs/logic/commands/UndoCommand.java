package seedu.jobs.logic.commands;

import java.util.EmptyStackException;

import seedu.jobs.logic.commands.exceptions.CommandException;

//@@author A0164440M
/**
 * Undo last commands which has modified the data.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo last command";
    public static final String MESSAGE_FAILUIRE = "No more commands to undo";


    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.undoCommand();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException e) {
            throw new CommandException(MESSAGE_FAILUIRE);
        }
    }
}
