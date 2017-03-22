package seedu.onetwodo.logic.commands;

import seedu.onetwodo.logic.commands.exceptions.CommandException;

//@@author A0135739W
/**
 * Undo the most recent command that modifies the toDoList.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo the most recent command that modifies any of the 3 lists.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo the most recent command.";

    @Override
    public CommandResult execute() throws CommandException {
        model.undo();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
